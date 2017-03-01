package pos.sd.omakasa.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import java.util.Timer;
import java.util.TimerTask;

import pos.sd.omakasa.R;
import pos.sd.omakasa.Utils.Utils;

/**
 * Created by jabbir on 8/9/16.
 */
public class NFCReaderActivity extends Activity {
    private ProgressDialog progressDialog;
    private Timer timer = new Timer();
    private JSONArray jsArry = new JSONArray();
    private File sdcard = Environment.getExternalStorageDirectory();
    private String outPut = "Documents/FNFC.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        getWindow().setLayout(1600, 1400);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Validatig NFC.....");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
//        File myFile = new File(Environment.getExternalStorageDirectory() + "/sdcard/Documents/FNFC.txt");
//        if (myFile.exists()) {
//            myFile.delete();
//            Toast.makeText(NFCReaderActivity.this, "Deleted file", Toast.LENGTH_SHORT).show();
//        }

       // tffmer.start();

        TimerTask timerTaskAsync = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String dtss = read(outPut);

                        Toast.makeText(getApplicationContext(), "Please dtss..."+dtss, Toast.LENGTH_SHORT).show();
                        Log.i("Background Perform",
                                "" + dtss);
                        if (dtss != null && !dtss.isEmpty() && !dtss.trim().isEmpty()) {
                            dtss=dtss.substring(0,4);
                            ((EditText) findViewById(R.id.buzn)).setText(dtss);
                            Toast.makeText(getApplicationContext(), "Please dtaaaaa..."+dtss, Toast.LENGTH_SHORT).show();
                            String xys = ((EditText) findViewById(R.id.buzn)).getText().toString();
                            redear(xys);
                            timer.cancel();

                        } else
                            Toast.makeText(getApplicationContext(), "Please wait...", Toast.LENGTH_SHORT).show();


                    }
                });
            }
        };
        timer.schedule(timerTaskAsync, 0, 1500);




        ((Button) findViewById(R.id.ok)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dtss = read(outPut);
               // ((EditText) findViewById(R.id.buzn)).setText(dtss);
                                                 Toast.makeText(NFCReaderActivity.this,dtss, Toast.LENGTH_SHORT).show();
                if (!((EditText) findViewById(R.id.buzn)).getText().toString().matches("")) {
                    redear(((EditText) findViewById(R.id.buzn)).getText().toString());
                    timer.cancel();
//                    timer.cancel();
//                    tffmer.cancel();
                }
            }
        });
//        TimerTask timerTaskAsync = new TimerTask() {
//            @Override
//            public void run() {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        doSomethingRepeatedly();
//                    }
//                });
//            }
//        };
//       // timer.schedule(timerTaskAsync, 0, 1500);


        ((Button) findViewById(R.id.cnle)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
                startActivity(new Intent(NFCReaderActivity.this, MainActivity.class));
            }
        });
    }


    private void redear(final String nfcNumb) {

        String urtMl = Utils.INTIALSTRI + Utils._NFCSTATUS + Utils.COMPCODE + "&whid=" + Utils.getString(NFCReaderActivity.this, "wh_id") + "&nfcno=" + ((EditText) findViewById(R.id.buzn)).getText().toString() + "&date=" + Utils.currenDate();
        Log.d("tag", "" + urtMl.trim());
        if (!Utils.isConnected(NFCReaderActivity.this)) {
            Utils.alertDialogShow(getApplicationContext(), "WIFI Failure", "Please Connect to the WIFI Network.", 0);

        } else {

            Utils.get(urtMl.trim(), null, new JsonHttpResponseHandler() {
                @Override
                public void onStart() {
                    ernl("delete");
                    progressDialog.show();
                    progressDialog.setMessage("Connecting and Validating Your Buzzer.....");

                }


                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                    // Pull out the first event on the public timeline
                    try {
                        jsArry = new JSONArray();
                        jsArry = timeline;
                        Log.d("Timeline", timeline.length() + "" + timeline.getJSONObject(0).getString("Status"));
                        if (timeline.getJSONObject(0).getString("Status").equalsIgnoreCase("VALID")) {
                            Utils.saveString(NFCReaderActivity.this, nfcNumb, "buzNo");
                            Utils.saveString(NFCReaderActivity.this, timeline.getJSONObject(0).getString("Group_no"), "Group_no");
                            Intent i = new Intent(getApplicationContext(), MainMenuActivity.class);
                            i.putExtra("nextLev", 0);
                            startActivity(i);
                            timer.cancel();
                            tffmer.cancel();
                            // timer.purge();

                        } else {
                            timer.cancel();
                            Toast.makeText(getBaseContext(), "Invalid Buzzer" + "\n" +
                                            "Please tab the Valid Buzzer.",
                                    Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                            timer.cancel();
                            tffmer.cancel();
                        }

                    } catch (Exception exception) {
                        Log.d("this error", "" + exception);
                        timer.cancel();
                        tffmer.cancel();

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
                    timer.cancel();
                    tffmer.cancel();

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

//
//    private void doSomethingRepeatedly() {
//
//        try {
//            String dtss = read(outPut);
//            Toast.makeText(getApplicationContext(), "Please wait1111..." + dtss, Toast.LENGTH_SHORT).show();
//            Log.i("Background Perform",
//                    "" + dtss.trim());
//            if (dtss != null && !dtss.isEmpty() && !dtss.trim().isEmpty())
//
//            {
//                String txt = dtss.trim().toString();
//                ((EditText) findViewById(R.id.buzn)).setText(txt);
//                Toast.makeText(getApplicationContext(), "Please wait221..." + dtss, Toast.LENGTH_SHORT).show();
//                Log.d("testttt", "" + ((EditText) findViewById(R.id.buzn)).getText().toString());
//                redear(((EditText) findViewById(R.id.buzn)).getText().toString());
//                timer.cancel();
//
//            } else {
//                dtss = read(outPut);
//                Toast.makeText(getApplicationContext(), "Please wait..." + dtss, Toast.LENGTH_SHORT).show();
//            }
//        } catch (Exception e) {
//            timer.cancel();
//            timer.purge();
//        }
//
//    }



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



    CountDownTimer tffmer = new CountDownTimer(1 * 60 * 1000, 1000) {

        public void onTick(long millisUntilFinished) {


        }

        public void onFinish() {

            if (tffmer != null) {
                // Utils.addback = -1;
                tffmer.cancel();
                Utils.alertDialogShow(NFCReaderActivity.this, getString(R.string.failOrder), " Fail Alert", 1);
                finish();
            }

        }
    };

}
