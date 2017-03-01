package pos.sd.omakasa.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pos.sd.omakasa.R;
import pos.sd.omakasa.Utils.Utils;

/**
 * Created by jabbir on 30/8/16.
 */
public class ServerConfig extends Activity {
    private int[] tzEttext = {R.id.input_host, R.id.input_port, R.id.input_cofig, R.id.input_password, R.id.input_reg, R.id.input_cmp, R.id.input_out};
    private ArrayList<String> _srFields = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        getWindow().setLayout(1000, 1600);


        if (Utils.readList(getApplicationContext(), Utils._ARRYLISTPREF).size() == 7) {
            for (int i = 0; i < tzEttext.length; i++) {
                ((EditText) findViewById(tzEttext[i])).setText(Utils.readList(getApplicationContext(), Utils._ARRYLISTPREF).get(i));
            }
            ((EditText) findViewById(R.id.mealid)).setText(Utils.getString(ServerConfig.this, "Malid"));
        }



        ((EditText) findViewById(tzEttext[6])).setOnEditorActionListener(new EditText.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    mthd();
                }
                return false;
            }
        });


        ((Button) findViewById(R.id.subMit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.saveString(ServerConfig.this, ((EditText) findViewById(R.id.mealid)).getText().toString(), "Malid");
                Log.d("tetttt",  Utils.getString(ServerConfig.this, "Malid"));

                mthd();
            }
        });


        ((Button) findViewById(R.id.canMit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private void mthd() {
        _srFields = new ArrayList<String>();
        for (int i = 0; i < tzEttext.length; i++) {
            if (((EditText) findViewById(tzEttext[i])).getText().toString().matches("")) {
                Toast.makeText(ServerConfig.this, "Please enter the missing fields", Toast.LENGTH_SHORT).show();
                return;
            } else {
                _srFields.add(((EditText) findViewById(tzEttext[i])).getText().toString());
                if (_srFields.size() == tzEttext.length) {
                    Utils.removeJsonSharedPreferences(getApplicationContext(), "orderNum");
                    Utils.removeJsonSharedPreferences(getApplicationContext(), "SAVED");
                    Utils.removeJsonSharedPreferences(getApplicationContext(), "totalArry");
                    Utils.removeJsonSharedPreferences(getApplicationContext(), "server");
                    Utils.removeJsonSharedPreferences(getApplicationContext(), "basha");


                    Utils.writeList(ServerConfig.this, _srFields, Utils._ARRYLISTPREF);
                    Intent xc = new Intent(ServerConfig.this, MainActivity.class);
                    xc.putExtra("xyzwe", "" + 1);
                    startActivity(xc);

                    finish();

                }
            }
        }

    }
}
