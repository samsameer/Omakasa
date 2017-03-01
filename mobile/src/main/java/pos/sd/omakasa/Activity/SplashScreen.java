package pos.sd.omakasa.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pos.sd.omakasa.R;
import pos.sd.omakasa.Utils.Utils;


/**
 * Created by jabbir on 23/9/16.
 */

public class SplashScreen extends Activity {
    private ArrayList<String> tsMinr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_splash_screen);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();

        display.getSize(size);
        int width = size.x;
        int height = size.y;
        // Toast.makeText(SplashScreen.this, "W" + width + " Height" + height, Toast.LENGTH_LONG).show();


        Utils.removeJsonSharedPreferences(getApplicationContext(), "orderNum");
        Utils.removeJsonSharedPreferences(getApplicationContext(), "SAVED");
        Utils.removeJsonSharedPreferences(getApplicationContext(), "totalArry");
        Utils.removeJsonSharedPreferences(getApplicationContext(), "buzNo");
        Utils.removeJsonSharedPreferences(getApplicationContext(), "Group_no");


        if (Utils.readList(getApplicationContext(), Utils._ARRYLISTPREF).size() != 7) {
            startActivity(new Intent(SplashScreen.this, ServerConfig.class));
        } else {


            List<String> tsdArr = Utils.readList(getApplicationContext(), Utils._ARRYLISTPREF);
            Utils.userAuth = tsdArr.get(2);
            Utils.passAuth = tsdArr.get(3);
            Utils.COMPCODE = tsdArr.get(5);
            Utils.regCOde = tsdArr.get(4);
            String xyz = "" + 0;
            if (tsdArr.get(0).toString().contains("http://")) {
                tsdArr.set(0, "http://" + tsdArr.get(0));
            } else
                tsdArr.set(0, "http://" + tsdArr.get(0));

            Utils.INTIALSTRI = tsdArr.get(0) + ":" + tsdArr.get(1);
//            Picasso.with(SplashScreen.this)
//                    .load(Utils.INTIALSTRI+"/resources/"+ Utils.regCOde+"_LANDING.jp")
//                    .error(R.drawable.landing)
//                    .into((ImageView) findViewById(R.id.mxre));

            /****** Create Thread that will sleep for 5 seconds *************/
            Calendar rightNow = Calendar.getInstance();
            int hour = rightNow.get(Calendar.HOUR_OF_DAY);
            List<String> tsList = new ArrayList<>();
            tsList = Utils.readList(SplashScreen.this, "timingarraylist");
//            Toast.makeText(SplashScreen.this, "calender" + hour, Toast.LENGTH_SHORT).show();
//            Toast.makeText(SplashScreen.this, "calender" + Utils.getString(SplashScreen.this, "Malid"), Toast.LENGTH_SHORT).show();
            if (hour >= 18 && Utils.getString(SplashScreen.this, "Malid").equalsIgnoreCase("T1")) {
                Utils.saveString(SplashScreen.this, "T2", "Malid");
                Intent xc = new Intent(SplashScreen.this, MainActivity.class);
                xc.putExtra("xyzwe", "" + 2);
                startActivity(xc);
            } else if (hour <= 17 && Utils.getString(SplashScreen.this, "Malid").equalsIgnoreCase("T2")) {
                Utils.saveString(SplashScreen.this, "T1", "Malid");
                Intent xc = new Intent(SplashScreen.this, MainActivity.class);
                xc.putExtra("xyzwe", "" + 2);
                startActivity(xc);
            } else {


               // redear(Utils.regCOde);


                Thread background = new Thread() {
                    public void run() {

                        try {
                            // Thread will sleep for 5 seconds
                            sleep(1 * 500);
                            Intent i = new Intent(SplashScreen.this, MainMenuActivity.class);
                            i.putExtra("nextLev", 0);
                            startActivity(i);
                            finish();


                        } catch (Exception e) {

                        }
                    }
                };

                // start thread
                background.start();
            }
        }

        ((pos.sd.omakasa.Utils.TextViewPlus) findViewById(R.id.tuch)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SplashScreen.this, MainMenuActivity.class);
                i.putExtra("nextLev", 0);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }


    private void ernl(String sr) {
        // write text to file

        // add-write text into file
        try {
            File myFile = new File("/sdcard/Documents/RNFC.TXT");
            myFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(myFile);
            OutputStreamWriter myOutWriter =
                    new OutputStreamWriter(fOut);
            myOutWriter.append(sr);
            myOutWriter.close();
            fOut.close();

//            //Toast.makeText(getBaseContext(),
//                    "Done writing SD 'PAYMENT.txt'",
//                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void redear(final String nNumb) {
        String urtMl = Utils.INTIALSTRI + Utils._SOLDTATUS + Utils.COMPCODE + "&paramgroup_code=" + Utils.regCOde + "&date=" + Utils.currenDate();
        if (!Utils.isConnected(SplashScreen.this)) {
            Utils.alertDialogShow(getApplicationContext(), "WIFI Failure", "Please Connect to the WIFI Network.", 1);

        } else {
            Utils.get(urtMl.trim(), null, new JsonHttpResponseHandler() {
                @Override
                public void onStart() {


                }


                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {

                    // Pull out the first event on the public timeline
                    try {
                        if (timeline.length() != 0) {
                            Utils.itemId = new ArrayList<String>();
                            for (int i = 0; i < timeline.length(); i++) {
                                Utils.itemId.add(timeline.getJSONObject(i).getString("item_id"));
                            }
                        }


                    } catch (Exception exception) {
                        Log.d("this error", "" + exception);
                    }
                    System.out.println(timeline);


                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    //  Utils.alertDialogShow(getApplicationContext(), "Error", " Connection  refused", 0);
                }


                @Override
                public void onFinish() {
                    Intent i = new Intent(SplashScreen.this, MainMenuActivity.class);
                    i.putExtra("nextLev", 0);
                    startActivity(i);
                    finish();

                }
            });

        }


    }
}