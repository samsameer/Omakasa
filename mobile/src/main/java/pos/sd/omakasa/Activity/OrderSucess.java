package pos.sd.omakasa.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import pos.sd.omakasa.R;
import pos.sd.omakasa.Utils.Utils;


/**
 * Created by jabbir on 23/9/16.
 */

public class OrderSucess extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thnk_dilg);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        //Toast.makeText(OrderSucess.this,"W"+width+" Height"+ height,Toast.LENGTH_LONG).show();
// METHOD 1

        ernl("");
        Utils.removeJsonSharedPreferences(getApplicationContext(), "orderNum");
        Utils.removeJsonSharedPreferences(getApplicationContext(), "SAVED");
        Utils.removeJsonSharedPreferences(getApplicationContext(), "totalArry");
        Utils.removeJsonSharedPreferences(getApplicationContext(), "buzNo");
        Utils.removeJsonSharedPreferences(getApplicationContext(), "Group_no");
        /****** Create Thread that will sleep for 5 seconds *************/
        Thread background = new Thread() {
            public void run() {

                try {
                    // Thread will sleep for 5 seconds
                    sleep(4000);
                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage(getBaseContext().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addCategory(Intent.CATEGORY_HOME);
                    startActivity(i);
                    android.os.Process.killProcess(android.os.Process.myPid());
                } catch (Exception e) {

                }
            }
        };

        // start thread
        background.start();

//METHOD 2

        /*
        new Handler().postDelayed(new Runnable() {

            // Using handler with postDelayed called runnable run method

            @Override
            public void run() {
                Intent i = new Intent(MainSplashScreen.this, FirstScreen.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, 5*1000); // wait for 5 seconds
        */
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



}