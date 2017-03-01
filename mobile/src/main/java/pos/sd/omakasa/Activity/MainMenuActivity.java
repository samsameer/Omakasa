package pos.sd.omakasa.Activity;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import pos.sd.omakasa.Adapters.GridAdapter;
import pos.sd.omakasa.Adapters.HeaderListAdapter;
import pos.sd.omakasa.R;
import pos.sd.omakasa.Utils.ExpandCollapseAnimation;
import pos.sd.omakasa.Utils.RecyclerItemClickListener;
import pos.sd.omakasa.Utils.Utils;

/**
 * Created by jabbir on 30/8/16.
 */
public class MainMenuActivity extends MainMenuBaseActivity {
    private List<String> al;
    private LinearLayoutManager lManager;
    private int xMain = 0;
    private int subPo = 0;
    private String xs;
    private Timer timerMain = new Timer();
    private Button bckBtn, mreBtn;
    private String outPut = "Documents/FNFC.txt";
    private File sdcard = Environment.getExternalStorageDirectory();
    private EditText xx;
    static pos.sd.omakasa.Utils.TextViewPlus ttlAmunt;
    private RelativeLayout txEdit;
    static TextView count, counter;
    private int intlScroll = 0;
    Handler handler = new Handler();
    private int StartX, StartY, endX, endY;
    private int finalScroll = 3;
    private int finalCount = 0;
    private ProgressDialog progressDialog;
    private int AnimationDuration = 1000;


    //正在执行的动画数量
    private int number = 0;
    //是否完成清理
    private boolean isClean = false;
    private FrameLayout animation_viewGroup;
    private Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
//用来清除动画后留下的垃圾
                    try {
                        animation_viewGroup.removeAllViews();
                    } catch (Exception e) {
                    }
                    isClean = false;
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
        _rcylMenuListing = (RecyclerView) findViewById(R.id.titl);
        _recylMenuDetail = (RecyclerView) findViewById(R.id.dtile);
        ttlAmunt = ((pos.sd.omakasa.Utils.TextViewPlus) findViewById(R.id.ttl));
        bckBtn = (Button) findViewById(R.id.bck);
        mreBtn = (Button) findViewById(R.id.mre);
        count = ((TextView) findViewById(R.id.count));
        counter = ((TextView) findViewById(R.id.counter));
        txEdit = (RelativeLayout) findViewById(R.id.cunttr);
//        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
//        layoutParams.width = view.getMeasuredWidth();
//        layoutParams.height = view.getMeasuredHeight();
        int[] screenLocationB = new int[2];
        animation_viewGroup = createAnimLayout();
        ((Button) findViewById(R.id.resrt)).getLocationInWindow(screenLocationB);
        endX = screenLocationB[0];
        endY = screenLocationB[1];
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Validatig NFC.....");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);

        eeernl("");
        //scrollMtd();
        //tffmer.start();
        //timer.cancel();

        mDListRec = (RecyclerView) findViewById(R.id.myview);
        mDListRec.setHasFixedSize(true);
        mDListManager = new GridLayoutManager(this, 1);
        mDListRec.setLayoutManager(mDListManager);
        _lecylMenuDetail = new GridLayoutManager(this, 1);
        lManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        _rcylMenuListing.setLayoutManager(lManager);
        _rcylMenuListing.setHasFixedSize(true);
        _recylMenuDetail.setLayoutManager(_lecylMenuDetail);

        _recylMenuDetail.setHasFixedSize(true);
        al = Utils.readList(MainMenuActivity.this, "Main_Cat");

        listAdapter = new HeaderListAdapter(MainMenuActivity.this, al, 0);
        _rcylMenuListing.setAdapter(listAdapter);
        Log.d("scroll postion", "" + _recylMenuDetail.computeVerticalScrollOffset());
        _recylMenuDetail.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d("scroll newState", "" + (intlScroll = intlScroll + newState));
            }
        });
        mreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intlScroll = intlScroll + 4;
                _lecylMenuDetail.scrollToPosition(intlScroll);


            }
        });
        Typeface face = Typeface.createFromAsset(getAssets(),
                "avenri_bold.ttc");
        bckBtn.setTypeface(face);
        mreBtn.setTypeface(face);
        count.setTypeface(face);
        counter.setTypeface(face);
        ((Button) findViewById(R.id.viewo)).setTypeface(face);
        ((Button) findViewById(R.id.home)).setTypeface(face);
        ((Button) findViewById(R.id.hidec)).setTypeface(face);
        ((Button) findViewById(R.id.resrt)).setTypeface(face);
        ((Button) findViewById(R.id.fsubmit)).setTypeface(face);


        bckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int xyz = 0;
                if (intlScroll - 4 < 1) {
                    intlScroll = 0;
                } else
                    intlScroll = intlScroll - 4;
                _lecylMenuDetail.scrollToPosition(intlScroll);
            }
        });


        ((Button) findViewById(R.id.home)).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                startActivity(new Intent(MainMenuActivity.this, ServerConfig.class));
                return false;
            }
        });

//        try{
//          //  detailAdapter = new GridAdapter(MainMenuActivity.this, Utils.TtlItem.get(al.get(0)), xMain);
//            _rcylMenuListing.SetOnSetHolderClickListener(new GridAdapter.HolderClickListener(){
//                @Override
//                public void onHolderClick(Drawable drawable,int[] start_location) {
//// TODO Auto-generated method stub
//
//                }
//            });
//        }
//      catch (Exception e){
//          Log.d("Exception",e.getMessage());
//      }

        _rcylMenuListing.setItemAnimator(new DefaultItemAnimator());
        _rcylMenuListing.addOnItemTouchListener(
                new RecyclerItemClickListener(MainMenuActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        listAdapter = new HeaderListAdapter(MainMenuActivity.this, al, position);
                        _rcylMenuListing.setAdapter(listAdapter);


                        // mListManager.scrollToPosition(position);
                        //mListManager.scrollToPositionWithOffset(position, 0);
                        //sVie = al.get(position).toString();
                        //yxy.setText(sVie);

                        //tffmer.cancel(); // cancel
                        //tffmer.start();
                        if (Utils.TtlItem.get(al.get(position)) != null) {

                            xMain = position;

                            if (_recylMenuDetail.getVisibility() != View.VISIBLE) {
                                //mkChmage();
                            }
                            if (Utils.TtlItem.get(al.get(position)) != null) {

                                txMng(al.get(position));

                                detailAdapter = new GridAdapter(MainMenuActivity.this, Utils.TtlItem.get(al.get(position)), xMain);
                                _recylMenuDetail.setAdapter(detailAdapter);
                                //  Utils.txt(MainMenuActivity.this, _recylMenuDetail);
                                //Utils.txt(MainMenuActivity.this,_recylMenuDetail);
                            } else {
                                new gridViewLayout().execute();

                            }
                        }


                    }
                }

                )
        );
        _recylMenuDetail.setItemAnimator(new DefaultItemAnimator());
        _recylMenuDetail.addOnItemTouchListener(
                new RecyclerItemClickListener(MainMenuActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(final View view, final int position) {
                        subPo = position;





                        final ImageView xBtn = ((ImageView) view.findViewById(R.id.img));
                        final Button xyBtn = ((Button) view.findViewById(R.id.addiy));
                        xyBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                int[] start_location = new int[2];
                                xBtn.getLocationInWindow(start_location);//获取点击商品图片的位置
                                Drawable drawable = xBtn.getDrawable();//复制一个新的商品图标
                                Log.d("Start Location", "" + start_location[0]+ "fff" + start_location[1]);
                                start_location[0]=2;

                                doAnim(drawable, start_location);
                                //Log.i("getcount", xx);
                                xyBtn.setBackground(getResources().getDrawable(R.drawable.r_corner));
                                xyBtn.setText(getString(R.string.added));
                                new CountDownTimer(1300, 1300) {
                                    public void onTick(long ms) {
                                    }

                                    public void onFinish() {
//                                        ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1, ScaleAnimation.RELATIVE_TO_SELF, .5f, ScaleAnimation.RELATIVE_TO_SELF, .5f);
//                                        scale.setDuration(500);
//                                        scale.setInterpolator(new OvershootInterpolator());
//                                        ((TextView) findViewById(R.id.count)).startAnimation(scale);
//                                        ((Button) findViewById(R.id.resrt)).setTranslationX(StartX);
//                                        ((Button) findViewById(R.id.resrt)).setTranslationY(StartY);
//
//                                        xyBtn.animate().setDuration(300)
//                                                .translationX(endX).translationY(endY).start();

                                        xyBtn.setBackground(getResources().getDrawable(R.drawable.r_corner_white_yes));
                                        xyBtn.setText(getString(R.string.additm));
                                    }
                                }.start();
//                                view.animate().setDuration(300)
//                                        .translationX(endX).translationY(endX).start();
                                ExpandCollapseAnimation animation = null;
                                animation = new ExpandCollapseAnimation(xyBtn, 1000, 1);
                                try {
                                    JSONObject js = new JSONObject();
                                    js.put(Utils.mPar, "" + 0);
                                    js.put(Utils.subPar, "" + subPo);
                                    js.put(Utils.qtyPar, "" + 1);
                                    js.put(Utils.remPar, "");
                                    addJsn(js);
                                    xx.setText("" + Utils.cusOn);


                                    //  Toast.makeText(getApplicationContext(), "Added Item.  Please check View Order...", Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {

                                }

                            }
                        });


                    }
                }

                )
        );
        ((Button) findViewById(R.id.viewo)).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Utils.removeJsonSharedPreferences(getApplicationContext(), "rR");
                mkChmage();
                return false;
            }
        });


        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.viewo:
                        Utils.removeJsonSharedPreferences(MainMenuActivity.this,  "buzNo");
                        eeernl("");
                        Utils.removeJsonSharedPreferences(getApplicationContext(), "rR");
                        mkChmage();
                        break;


                    case R.id.home:
                        Utils.removeJsonSharedPreferences(MainMenuActivity.this,  "buzNo");
                        eeernl("");
                        String xyz[] = {"5201e27676d494214527a22bfcb980d9f9339f600238a80b3f3ab3dd8ec323b0636964da1359b18e36535587fbce25b0bb96", "5401107dab0be10be56ab3012f3a68e2ce6fa29f217ea16121b68763406470e25cdc6667fb3d61a61c47f7c7b1cfda85fb53", "5401688ad71b249337f64ad7e14eadd8e238776c287f9702931c8e7639effd0356ba64da1359b18e36535587fbce25b0bb96", "5401e2d071ce5add59a985f7e6594aa354f9ef1120f547b3aa54617fb3cee62119fa6667fb3d61a61c47f7c7b1cfda85fb53", "5201be456623bbc48e0bcf14be736293a932d39953dad967172c525f88b77f356175027deaa5c187288cca5bde5e240dabd9", "54013c614120f7d48e94d96368f8c64f2d6fd3d4a8dc2fdc0f7e77ab2ecc23281ae51e37a65b495f75120aa9a7a63116d881"};
                        for (int i = 0; i < xyz.length; i++) {
                            mthdCallbuzzer(xyz[i]);
                        }
                        alrialog(MainMenuActivity.this, getString(R.string.clhelp), getString(R.string.hlpDes), 0);


                        break;


                    case R.id.resrt:
                        eeernl("");
                        Utils.removeJsonSharedPreferences(MainMenuActivity.this,  "buzNo");
                        //MainMenuActivity.//timer.cancel();
                        Utils.removeJsonSharedPreferences(getApplicationContext(), "rR");
//                         mkChmage();
//                        //tffmer.cancel(); // cancel
                        alrialog(MainMenuActivity.this, getString(R.string.cmlcrt), getString(R.string.crtdes), 1);
                        break;


                    case R.id.hidec:
                        Utils.removeJsonSharedPreferences(MainMenuActivity.this,  "buzNo");
                        eeernl("");
                        //tffmer.cancel(); // cancel
                        //tffmer.start();
                        mkChmage();
//                        chkFull = 4;
//                        adminLogin();


                        break;

                    case R.id.rtclick:
                        Utils.removeJsonSharedPreferences(MainMenuActivity.this,  "buzNo");
                        eeernl("");
                        //tffmer.cancel(); // cancel
                        //tffmer.start();
                        mkChmage();
//                        chkFull = 4;
//                        adminLogin();


                        break;


                    case R.id.fsubmit:
                        eeernl("");
                        try {
                            if (Utils.loadJSONArray(MainMenuActivity.this, "totalArry", "" + 1).length() == 0) {
                                Utils.alrtDialog(MainMenuActivity.this, getString(R.string.crtempy), getString(R.string.addtm), 0);
                            } else
                                alrtDlg();

                        } catch (Exception e) {
                            alrtDlg();
                        }


                        break;


                }
            }
        };
        ((Button) findViewById(R.id.viewo)).setOnClickListener(listener);
        ((Button) findViewById(R.id.home)).setOnClickListener(listener);
        ((Button) findViewById(R.id.hidec)).setOnClickListener(listener);
        ((Button) findViewById(R.id.resrt)).setOnClickListener(listener);
        ((Button) findViewById(R.id.fsubmit)).setOnClickListener(listener);
        ((RelativeLayout) findViewById(R.id.rtclick)).setOnClickListener(listener);
    }

    private void moveit(final View view) {

        float x = view.getX();
        float y = view.getY();
        Path path = new Path();

        path.moveTo(x + 0, y + 0);
        path.lineTo(x + 0, y + 1000);
        path.lineTo(x + 400, y + 1000);
        path.lineTo(0, 0);
        ObjectAnimator objectAnimator =
                ObjectAnimator.ofFloat(view, View.X,
                        View.Y, path);
        objectAnimator.setDuration(500);
        objectAnimator.start();
    }

    public static void txtmrk(String xyzt) {
        ttlAmunt.setText("Cart Total: $ " + xyzt);


    }

    public static void Cunt(Context con) {

        try {

        } catch (Exception e) {

        }

    }


    public static void finalCount(Context con) {

        try {
            count.setText(Integer.toString(Utils.loadJSONArray(con, "totalArry", "" + 1).length()));
            counter.setText(Integer.toString(Utils.loadJSONArray(con, "totalArry", "" + 1).length()));
        } catch (Exception e) {

        }

    }

    @Override
    public void onBackPressed() {

    }

    private void addJsn(JSONObject jADD) {
        try {
            if (Utils.loadJSONArray(MainMenuActivity.this, "totalArry", "" + 1).equals("[]")) {
                JSONArray jsd = new JSONArray();
                jsd.put(jADD);
                Utils.saveJSONArray(MainMenuActivity.this, "totalArry", "" + 1, jsd);
                Log.d("@@@@@@@@33", "" + Utils.loadJSONArray(MainMenuActivity.this, "totalArry", "" + 1));
            } else {
                JSONArray jsw = new JSONArray();
                jsw = Utils.loadJSONArray(MainMenuActivity.this, "totalArry", "" + 1);
                Log.d("@@@@@@@@33", "" + Utils.loadJSONArray(MainMenuActivity.this, "totalArry", "" + 1));
                int xxxT = 0;
                for (int i = 0; i < jsw.length(); i++) {
                    //if (js.toString().contains("\"item_id\":" + Integer.parseInt(eSoon[0].toString()) ))
                    if ((jsw.getJSONObject(i).getString("main").equalsIgnoreCase(jADD.getString("main"))) && jsw.getJSONObject(i).getString("subcat").equalsIgnoreCase(jADD.getString("subcat"))) {
                        xxxT = 1;
                        int cx = (Integer.parseInt(jsw.getJSONObject(i).getString("qty")) + Integer.parseInt(jADD.getString("qty")));
                        jsw.getJSONObject(i).put("qty", "" + cx);
                        Utils.saveJSONArray(MainMenuActivity.this, "totalArry", "" + 1, jsw);
                        break;
                        //[{"main":"3","subcat":"0","qty":"1","remark":""}]
                    }
                }
                if (xxxT == 0) {
                    Log.d("@@@@@@@@33iiiii", "" + jADD);
                    jsw.put(jADD);
                    Utils.saveJSONArray(MainMenuActivity.this, "totalArry", "" + 1, jsw);
                }
                finalCount(MainMenuActivity.this);

                Log.d("@@@@@@@@test", "" + Utils.loadJSONArray(MainMenuActivity.this, "totalArry", "" + 1));

            }
            //  animate(1);
        } catch (Exception e) {

        }
    }


    public void mkChmage() {


        if (((LinearLayout) findViewById(R.id.footer)).getVisibility() == View.VISIBLE) {
            ((LinearLayout) findViewById(R.id.footer)).setVisibility(View.GONE);
        } else {
            ((LinearLayout) findViewById(R.id.footer)).setVisibility(View.VISIBLE);
            Intialick();
        }


    }

    public void txtChngd() {
        detailAdapter = new GridAdapter(MainMenuActivity.this, Utils.TtlItem.get(al.get(0)), xMain);
        _recylMenuDetail.setAdapter(detailAdapter);
    }

    private void txtmr() {

    }

    private void scrollMtd() {
        try {

            int length = Utils.loadJSONArray(MainMenuActivity.this, "totalArry", "" + 1).length();

            if (intlScroll == 0) {
                bckBtn.setTextColor(getResources().getColor(R.color.textcln));
                bckBtn.setClickable(false);
                mreBtn.setTextColor(getResources().getColor(R.color.yesbtn));
            } else if (intlScroll == length) {
                mreBtn.setClickable(false);
                bckBtn.setTextColor(getResources().getColor(R.color.yesbtn));
                mreBtn.setTextColor(getResources().getColor(R.color.textcln));
            } else {
                mreBtn.setClickable(true);
                bckBtn.setClickable(true);
                bckBtn.setTextColor(getResources().getColor(R.color.yesbtn));
                mreBtn.setTextColor(getResources().getColor(R.color.yesbtn));
            }
        } catch (Exception e) {

        }


    }

    private void alrtDlg() {
        final Dialog dialog = new Dialog(MainMenuActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.cutome_dilg);

        pos.sd.omakasa.Utils.TextViewPlus headerText = (pos.sd.omakasa.Utils.TextViewPlus) dialog.findViewById(R.id.hdr);
        pos.sd.omakasa.Utils.TextViewPlus msgText = (pos.sd.omakasa.Utils.TextViewPlus) dialog.findViewById(R.id.dtl);
        headerText.setText("" + getString(R.string.cfrcrt));
        msgText.setText("" + getString(R.string.tap));
        Utils.removeJsonSharedPreferences(MainMenuActivity.this,  "buzNo");

//        redear("8007");
//        dialog.dismiss();

        // btnFirstClick();
        TimerTask timerTaskAsync = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        String dtss = readFromFile(MainMenuActivity.this);

                        //Toast.makeText(MainMenuActivity.this, "..." + dtss, Toast.LENGTH_SHORT).show();
                        Log.i("Background Perform",
                                "" + dtss);


                        if (dtss != null && !dtss.isEmpty() && !dtss.trim().isEmpty()) {
                            dtss = dtss.substring(0, 4);

                            //tffmer.cancel();
                            //  ((EditText) findViewById(R.id.buzn)).setText(dtss);
                            Toast.makeText(getApplicationContext(), "" + dtss, Toast.LENGTH_SHORT).show();

                            //  String xys = ((EditText) findViewById(R.id.buzn)).getText().toString();
                            redear(dtss);
                            dialog.dismiss();
                            timerMain.cancel();


                        }
                        else{
                            String ds = readFmFile();

                            if (ds != null && !ds.isEmpty() && !ds.trim().isEmpty()) {
                                ds = ds.substring(0, 4);

                                //tffmer.cancel();
                                //  ((EditText) findViewById(R.id.buzn)).setText(dtss);
                                Toast.makeText(getApplicationContext(), "" + dtss, Toast.LENGTH_SHORT).show();

                                //  String xys = ((EditText) findViewById(R.id.buzn)).getText().toString();
                                redear(ds);
                                dialog.dismiss();
                                timerMain.cancel();


                            }



                        }




                    }
                });
            }
        };
        timerMain.schedule(timerTaskAsync, 0, 1500);


        Button diButton = (Button) dialog.findViewById(R.id._canbtn);
        // if button is clicked, close the custom dialog
        diButton.setVisibility(View.VISIBLE);

        Button dialogButton = (Button) dialog.findViewById(R.id.okbtn);
        // if button is clicked, close the custom dialog
        dialogButton.setVisibility(View.GONE);
        ImageButton digButton = (ImageButton) dialog.findViewById(R.id.btncln);
        // if button is clicked, close the custom dialog
        digButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //timer.cancel();
                //tffmer.start();
                dialog.dismiss();
            }
        });

        diButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //timer.cancel();
                //tffmer.start();
                dialog.dismiss();
            }
        });
        dialog.show();


    }


    private void trnl(String sr) {
        // write text to file

        // add-write text into file
        try {
            File myFile = new File(sdcard, "");
            if (myFile.exists()) {

                FileOutputStream fOut = new FileOutputStream(myFile);
                OutputStreamWriter myOutWriter =
                        new OutputStreamWriter(fOut);
                myOutWriter.append("");
                myOutWriter.close();
                fOut.close();
            }
//
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void doAnim(Drawable drawable, int[] start_location) {
        Utils.tstMap=new HashMap<>();

        if (!isClean) {
            setAnim(drawable, start_location);
        } else {
            try {
                animation_viewGroup.removeAllViews();
                isClean = false;
                setAnim(drawable, start_location);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                isClean = true;
            }
        }
    }

    private String read(String xMai) {

        String aBuffer = "";

        final String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {  // we can read the External Storage...
            //Retrieve the primary External Storage:
            final File primaryExternalStorage = Environment.getExternalStorageDirectory();

            //Retrieve the External Storages root directory:
            final String externalStorageRootDir;
            if ((externalStorageRootDir = primaryExternalStorage.getParent()) == null) {  // no parent...
                try {
                    File myFile = new File(sdcard, xMai);
                    if (myFile.exists()) {
//                Toast.makeText(getBaseContext(), "FIle exist at"+  xMai+
//                            "'",
//                    Toast.LENGTH_SHORT).show();
                        FileInputStream fIn = new FileInputStream(myFile);
                        BufferedReader myReader = new BufferedReader(
                                new InputStreamReader(fIn));
                        String aDataRow = "";

                        while ((aDataRow = myReader.readLine()) != null) {
                            aBuffer += aDataRow + "\n";
                        }

//                if(aBuffer.length()==0 ){
//                    aBuffer="Please wait...";
//                }


                        //txtData.setText(aBuffer);
                        myReader.close();
                        fIn.close();

                    } else {
//                Toast.makeText(getBaseContext(), aBuffer +
//                                "file not present '",
//                        Toast.LENGTH_SHORT).show();
                    }


                } catch (Exception e) {
//            Toast.makeText(getBaseContext(), e.getMessage(),
//                    Toast.LENGTH_SHORT).show();
                }

            } else {

                try {
                    File myFile = new File("/sdcard/Documents/FNFC.TXT");
                    if (myFile.exists()) {
                        FileInputStream fIn = new FileInputStream(myFile);
                        BufferedReader myReader = new BufferedReader(
                                new InputStreamReader(fIn));
                        String aDataRow = "";

                        while ((aDataRow = myReader.readLine()) != null) {
                            aBuffer += aDataRow + "\n";
                        }

                        myReader.close();
                        fIn.close();

                    }

                } catch (Exception e) {

                }


//                final File externalStorageRoot = new File( externalStorageRootDir );
//                final File[] files = externalStorageRoot.listFiles();
//
//                for ( final File file : files ) {
//                    if ( file.isDirectory() && file.canRead() && (file.listFiles().length > 0) ) {  // it is a real directory (not a USB drive)...
//                        Log.d(TAG, "External Storage: " + file.getAbsolutePath() + "\n");
//                    }
//                }
            }
        }


        return aBuffer;
    }
    private String readFmFile() {

        String ret = "";

        try {

            File myFile = new File("/sdcard/Documents/NFC.TXT");
            if (myFile.exists()) {
                FileInputStream fIn = new FileInputStream(myFile);
                BufferedReader myReader = new BufferedReader(
                        new InputStreamReader(fIn));
                String aDataRow = "";

                while ((aDataRow = myReader.readLine()) != null) {
                    ret += aDataRow + "\n";
                }
                // Toast.makeText(MainMenuActivity.this, "readerrrrfound: " + ret, Toast.LENGTH_SHORT).show();

                myReader.close();
                fIn.close();

            }


        } catch (FileNotFoundException e) {
            // Toast.makeText(MainMenuActivity.this, "File not found: " + e.toString(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            //Toast.makeText(MainMenuActivity.this, "Can not read file: " + e.toString(), Toast.LENGTH_SHORT).show();
        }


        return ret;
    }

    private String readFromFile(Context context) {

        String ret = "";

        try {

            File myFile = new File("/sdcard/Documents/FNFC.TXT");
            if (myFile.exists()) {
                FileInputStream fIn = new FileInputStream(myFile);
                BufferedReader myReader = new BufferedReader(
                        new InputStreamReader(fIn));
                String aDataRow = "";

                while ((aDataRow = myReader.readLine()) != null) {
                    ret += aDataRow + "\n";
                }
                // Toast.makeText(MainMenuActivity.this, "readerrrrfound: " + ret, Toast.LENGTH_SHORT).show();

                myReader.close();
                fIn.close();

            }


        } catch (FileNotFoundException e) {
            // Toast.makeText(MainMenuActivity.this, "File not found: " + e.toString(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            //Toast.makeText(MainMenuActivity.this, "Can not read file: " + e.toString(), Toast.LENGTH_SHORT).show();
        }


        return ret;
    }


    private void redear(final String nfcNumb) {
        eeernl("delete");
        timerMain.cancel();
        String urtMl = Utils.INTIALSTRI + Utils._NFCSTATUS + Utils.COMPCODE + "&whid=" + Utils.getString(MainMenuActivity.this, "wh_id") + "&nfcno=" + nfcNumb + "&date=" + Utils.currenDate();
        if (!Utils.isConnected(MainMenuActivity.this)) {
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

                        Log.d("Timeline", timeline.length() + "" + timeline.getJSONObject(0).getString("Status"));
                        if (timeline.getJSONObject(0).getString("Status").equalsIgnoreCase("VALID")) {
                            Utils.saveString(MainMenuActivity.this, nfcNumb, "buzNo");
                            Utils.saveString(MainMenuActivity.this, timeline.getJSONObject(0).getString("Group_no"), "Group_no");
                            //timer.cancel();
                            btnFirstClick();

                            ////tffmer.cancel();
                            // //timer.purge();

                        } else {

                            Utils.alrtDialog(MainMenuActivity.this, getString(R.string.invalidtttt), getString(R.string.tttt), 1);
//                            Toast.makeText(getBaseContext(), "Invalid Buzzer" + "\n" +
//                                            "Please tab the Valid Buzzer.",
//                                    Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception exception) {
                        Log.d("this error", "" + exception);
                        //timer.cancel();
                        // ////tffmer.cancel();

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

                    // ////tffmer.cancel();

                }
            });

        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {// record the start time, start the timer
            //tffmer.cancel();
            //tffmer.start();


        } else if (ev.getAction() == MotionEvent.ACTION_UP) {
            //tffmer.cancel();
            //tffmer.start();

        } else {
            //tffmer.cancel();
            //tffmer.start();
        }

        return super.onTouchEvent(ev);
    }

//
//    CountDownTimer //tffmer = new CountDownTimer(1 * 40 * 1000, 1000) {
//
//        public void onTick(long millisUntilFinished) {
//
//            //Toast.makeText(HomeScreen.this, "ticktick" + millisUntilFinished, Toast.LENGTH_SHORT).show();
//        }
//
//        public void onFinish() {
//
//            if (//tffmer != null) {
//                // //tffmer.cancel(); // cancel
//                ////tffmer.start();
//                alrialog(MainMenuActivity.this, getString(R.string.cmlcrt), getString(R.string.crtdes), 1);
//
//
//                handler = new Handler();
////                handler.postDelayed(new Runnable() {
////                    @Override
////                    public void run() {
////                        Intent i = new Intent(MainMenuActivity.this, SplashScreen.class);
////                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                        i.addCategory(Intent.CATEGORY_HOME);
////                        startActivity(i);
////                        android.os.Process.killProcess(android.os.Process.myPid());
////
////                        //Do something after 100ms
////                    }
////                }, 10000);
//
//
////
////                ((Button) findViewById(R.id.resrt)).postDelayed(new Runnable() {
////                    public void run() {
////                        Intent i = new Intent(MainMenuActivity.this, SplashScreen.class);
////                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                        i.addCategory(Intent.CATEGORY_HOME);
////                        startActivity(i);
////                        android.os.Process.killProcess(android.os.Process.myPid());
////                    }
////                }, 10000);
//
//            }
//        }
//
//    };

public void mthdanimation(Drawable key, int[] value){
    doAnim(key, value);
//    if (Utils.tstMap.size() != 0) {
//        for (Map.Entry<Drawable, int[]> entry : Utils.tmes.entrySet()) {
//            Drawable key = entry.getKey();
//            int[] value = entry.getValue();
//            doAnim(key, value);
//            // do stuff
//        }
////
////                        }
//    }
}
    private void alrialog(final Context contx, String Header, String body, final int xyInt) {
        //  hideWindow();
        final Dialog dialog = new Dialog(contx);


        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.confrcutome_dilg);

        pos.sd.omakasa.Utils.TextViewPlus headerText = (pos.sd.omakasa.Utils.TextViewPlus) dialog.findViewById(R.id._hdr);
        pos.sd.omakasa.Utils.TextViewPlus msgText = (pos.sd.omakasa.Utils.TextViewPlus) dialog.findViewById(R.id._dtl);
        headerText.setText("" + Header);
        msgText.setText("" + body);
        Typeface face = Typeface.createFromAsset(getAssets(),
                "avenri_bold.ttc");
        Button dialogButton = (Button) dialog.findViewById(R.id._yesbtn);
        Button diaButton = (Button) dialog.findViewById(R.id._nobtn);
        dialogButton.setTypeface(face);
        diaButton.setTypeface(face);

        if (xyInt == 2) {
            dialogButton.setBackground(contx.getDrawable(R.drawable.r_corner));
            dialogButton.setText(contx.getString(R.string.cnlt));
        }
        if (xyInt == 0)
            diaButton.setVisibility(View.GONE);

        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (xyInt == 0) {
                    //tffmer.cancel(); // cancel
                    //tffmer.start();

                    dialog.dismiss();
                } else if (xyInt == 1) {


                    Utils.removeJsonSharedPreferences(getApplicationContext(), "orderNum");
                    Utils.removeJsonSharedPreferences(getApplicationContext(), "SAVED");
                    Utils.removeJsonSharedPreferences(getApplicationContext(), "totalArry");

                    //tffmer.cancel(); // cancel
                    Intent i = new Intent(contx, SplashScreen.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addCategory(Intent.CATEGORY_HOME);
                    contx.startActivity(i);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    dialog.dismiss();

                } else if (xyInt == 2) {
                    //tffmer.cancel(); // cancel
                    Intent i = new Intent(contx, MainMenuActivity.class);
                    contx.startActivity(i);
                    dialog.dismiss();
                }

            }
        });


        ImageButton digButton = (ImageButton) dialog.findViewById(R.id._btncln);
        // if button is clicked, close the custom dialog
        digButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //tffmer.cancel(); // cancel
                //tffmer.start();
                dialog.dismiss();
                try {
                    handler.removeCallbacksAndMessages(null);
                } catch (Exception e) {

                }

            }
        });

        diaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //tffmer.cancel(); // cancel
                //tffmer.start();
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    private void hideWindow() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }


    private void eeernl(String sr) {
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

    private void mthdCallbuzzer(String xyBuzz) {
        String Urlmk = "https://apsoutheast.gateway.push.samsungosp.com:8090/spp/pns/api/push";
        try {

            JSONObject par = new JSONObject();
            par.put("regID", xyBuzz);
            par.put("requestID", "0002");
            par.put("message", "badgeOption=INCREASE&badgeNumber=1&action=ALERT&alertMessage= Ordering from " + Utils.regCOde + ". Call for help.");

            StringEntity entity = new StringEntity(par.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            Utils.postBuzzer(MainMenuActivity.this, Urlmk, null, entity, new JsonHttpResponseHandler() {
                // When success occurs
                public void onSuccess(JSONObject response) {
                    // We print the response
                    System.out.println(response);
                }
            });


        } catch (Exception e) {

        }

    }

    private FrameLayout createAnimLayout() {
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
        FrameLayout animLayout = new FrameLayout(this);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    /**
     * @param vg       动画运行的层 这里是frameLayout
     * @param view     要运行动画的View
     * @param location 动画的起始位置
     * @return
     * @deprecated 将要执行动画的view 添加到动画层
     */
    private View addViewToAnimLayout(ViewGroup vg, View view, int[] location) {
        int x = location[0];
        int y = location[1];
        vg.addView(view);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                dip2px(this, 240), dip2px(this, 240));
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setPadding(5, 5, 5, 5);
        view.setLayoutParams(lp);
        return view;
    }

    /**
     * dip，dp转化成px 用来处理不同分辨路的屏幕
     *
     * @param context
     * @param dpValue
     * @return
     */
    private int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 动画效果设置
     *
     * @param drawable       将要加入购物车的商品
     * @param start_location 起始位置
     */
    private void setAnim(Drawable drawable, int[] start_location) {
        Animation mScaleAnimation = new ScaleAnimation(1.5f, 0.0f, 1.5f, 0.0f, Animation.RELATIVE_TO_SELF, 0.1f, Animation.RELATIVE_TO_SELF, 0.1f);
        mScaleAnimation.setDuration(AnimationDuration);
        mScaleAnimation.setFillAfter(true);
        final ImageView iview = new ImageView(this);
        iview.setImageDrawable(drawable);
        final View view = addViewToAnimLayout(animation_viewGroup, iview, start_location);
        view.setAlpha(0.6f);
        int[] end_location = new int[2];
        ((TextView) findViewById(R.id.count)).getLocationInWindow(end_location);
        int endX = end_location[0];
        int endY = end_location[1] - start_location[1];
        //Toast.makeText(getApplicationContext(), "animation."+  endX+"enddddy"+ end_location[1]+"-"+ start_location[1] +"check View Order...", Toast.LENGTH_SHORT).show();

        Animation mTranslateAnimation = new TranslateAnimation(0, endX, 0, endY);
        Animation mRotateAnimation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateAnimation.setDuration(AnimationDuration);
        mTranslateAnimation.setDuration(AnimationDuration);
        AnimationSet mAnimationSet = new AnimationSet(true);
        mAnimationSet.setFillAfter(true);
        mAnimationSet.addAnimation(mRotateAnimation);
        mAnimationSet.addAnimation(mScaleAnimation);
        mAnimationSet.addAnimation(mTranslateAnimation);
        mAnimationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
// TODO Auto-generated method stub
                number++;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
// TODO Auto-generated method stub
                number--;
                if (number == 0) {
                    isClean = true;
                    myHandler.sendEmptyMessage(0);
                }

                ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1, ScaleAnimation.RELATIVE_TO_SELF, .5f, ScaleAnimation.RELATIVE_TO_SELF, .5f);
                scale.setDuration(500);
                scale.setInterpolator(new OvershootInterpolator());
                ((TextView) findViewById(R.id.count)).startAnimation(scale);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
// TODO Auto-generated method stub
            }
        });
        view.startAnimation(mAnimationSet);
    }

    /**
     * 内存过低时及时处理动画产生的未处理冗余
     */
    @Override
    public void onLowMemory() {
// TODO Auto-generated method stub
        isClean = true;
        try {
            animation_viewGroup.removeAllViews();
        } catch (Exception e) {
            e.printStackTrace();
        }
        isClean = false;
        super.onLowMemory();
    }


}

