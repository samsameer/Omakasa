package pos.sd.omakasa.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Timer;

import io.fabric.sdk.android.Fabric;
import pos.sd.omakasa.R;
import pos.sd.omakasa.Utils.Utils;

public class MainActivity extends BackgrodBaseActivity {
    private TextView tsMainMenu;
    private String foutPut = "Documents/NFC.txt";
    private ProgressDialog progressDialog;
    public static Timer timer = new Timer();
    private JSONArray jsArry = new JSONArray();
    private File sdcard = Environment.getExternalStorageDirectory();
    private String outPut = "Documents/FNFC.txt";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Validatig NFC.....");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        tsMainMenu = (TextView) findViewById(R.id.tsID);

        Utils.removeJsonSharedPreferences(MainActivity.this, "totalArry");
        Log.d("ggggg", "" + Utils.readList(MainActivity.this, Utils._ARRYLISTPREF).size());
        if (Utils.readList(getApplicationContext(), Utils._ARRYLISTPREF).size() != 7) {
            startActivity(new Intent(MainActivity.this, ServerConfig.class));
        } else {
            List<String> tsdArr = Utils.readList(getApplicationContext(), Utils._ARRYLISTPREF);
            Utils.userAuth = tsdArr.get(2);
            Utils.passAuth = tsdArr.get(3);
            Utils.COMPCODE = tsdArr.get(5);
            String xyz = "" + 0;
            if (tsdArr.get(0).toString().contains("http://")) {
                tsdArr.set(0, "http://" + tsdArr.get(0));
            } else
                tsdArr.set(0, "http://" + tsdArr.get(0));

            Utils.INTIALSTRI = tsdArr.get(0) + ":" + tsdArr.get(1);
            try {


                xyz = getIntent().getStringExtra("xyzwe");
                if (xyz.equalsIgnoreCase("" + 1))
                    checktimer(1);
                else if (xyz.equalsIgnoreCase("" + 2)) {
                    checktimer(2);
                } else {
//                    Intent i = new Intent(MainActivity.this,MainMenuActivity.class);
//                    i.putExtra("nextLev", 0);
//                    startActivity(i);

//                    TimerTask timerTaskAsync = new TimerTask() {
//                        @Override
//                        public void run() {
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    String dtss = read(outPut);
//
//                                    Toast.makeText(getApplicationContext(), "Please dtss..." + dtss, Toast.LENGTH_SHORT).show();
//                                    Log.i("Background Perform",
//                                            "" + dtss);
//                                    if (dtss != null && !dtss.isEmpty() && !dtss.trim().isEmpty()) {
//                                        dtss = dtss.substring(0, 4);
//                                        //  ((EditText) findViewById(R.id.buzn)).setText(dtss);
//                                        Toast.makeText(getApplicationContext(), "Please dtaaaaa..." + dtss, Toast.LENGTH_SHORT).show();
//
//                                        //  String xys = ((EditText) findViewById(R.id.buzn)).getText().toString();
//                                        redear(dtss);
//
//
//                                    } else
//                                        Toast.makeText(getApplicationContext(), "Please wait...", Toast.LENGTH_SHORT).show();
//
//
//                                }
//                            });
//                        }
//                    };
//                    timer.schedule(timerTaskAsync, 0, 1500);
                }


            } catch (Exception e) {

            }


        }
        ((TextView) findViewById(R.id.vrsn)).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                startActivity(new Intent(MainActivity.this, ServerConfig.class));
                return false;
            }
        });


        ((TextView) findViewById(R.id.rest)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainMenuActivity.class);
                i.putExtra("nextLev", 0);
                startActivity(i);
            }
        });


        //if(Utils.get)

//        tsMainMenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                tsMainMenu.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
////
//
//                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
//                        startActivity(i);
//                    }
//                });
//            }
//        });
    }

    @Override
    public void onBackPressed() {

    }

    private void redear(final String nfcNumb) {
        ernl("delete");
        String urtMl = Utils.INTIALSTRI + Utils._NFCSTATUS + Utils.COMPCODE + "&whid=" + Utils.getString(MainActivity.this, "wh_id") + "&nfcno=" + nfcNumb + "&date=" + Utils.currenDate();
        Log.d("tag", "" + urtMl.trim());
        if (!Utils.isConnected(MainActivity.this)) {
            Utils.alertDialogShow(getApplicationContext(), "WIFI Failure", "Please Connect to the WIFI Network.", 0);

        } else {
            Utils.get(urtMl.trim(), null, new JsonHttpResponseHandler() {
                @Override
                public void onStart() {
                    progressDialog.show();
                    progressDialog.setMessage("Connecting and Validating Your Buzzer.....");

                }


                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                    // Pull out the first event on the public timeline
                    try {
                        timer.cancel();
                        jsArry = new JSONArray();
                        jsArry = timeline;
                        Log.d("Timeline", timeline.length() + "" + timeline.getJSONObject(0).getString("Status"));
                        if (timeline.getJSONObject(0).getString("Status").equalsIgnoreCase("VALID")) {
                            Utils.saveString(MainActivity.this, nfcNumb, "buzNo");
                            Utils.saveString(MainActivity.this, timeline.getJSONObject(0).getString("Group_no"), "Group_no");
                            Intent i = new Intent(getApplicationContext(), MainMenuActivity.class);
                            i.putExtra("nextLev", 0);
                            startActivity(i);
                            timer.cancel();
                            //tffmer.cancel();
                            // timer.purge();

                        } else {

                            Utils.alrtDialog(MainActivity.this, getString(R.string.invalidtttt), getString(R.string.tttt), 1);
                            Toast.makeText(getBaseContext(), "Invalid Buzzer" + "\n" +
                                            "Please tab the Valid Buzzer.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception exception) {
                        Log.d("this error", "" + exception);
                        timer.cancel();
                        // //tffmer.cancel();

                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    //  Utils.alertDialogShow(getApplicationContext(), "Error", " Connection  refused", 0);
                }


                @Override
                public void onFinish() {
//                    try {
//                        if (jsArry.getJSONObject(0).getString("Status").equalsIgnoreCase("INVALID"))
//                            Utils.alertDialogShow(getApplicationContext(), "Invalid Buzzer", "Please tab the Valid Buzzer.", 0);
//
//
//                    } catch (Exception e) {
//
//                    }
                    progressDialog.dismiss();

                    // //tffmer.cancel();

                }
            });

        }


    }

    private String read(String xMai) {
        String aBuffer = "";
        try {
            File myFile = new File(sdcard, xMai);
            if (myFile.exists()) {
                FileInputStream fIn = new FileInputStream(myFile);
                BufferedReader myReader = new BufferedReader(
                        new InputStreamReader(fIn));
                String aDataRow = "";

                while ((aDataRow = myReader.readLine()) != null) {
                    aBuffer += aDataRow + "\n";
                }

//          
                myReader.close();
                fIn.close();

            } else {
//              
            }


        } catch (Exception e) {
//           
        }


        return aBuffer;
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




}
