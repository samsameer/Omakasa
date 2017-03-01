package pos.sd.omakasa.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import pos.sd.omakasa.Adapters.GridAdapter;
import pos.sd.omakasa.Adapters.ViewOrderadapter;
import pos.sd.omakasa.Modelclass.EndangeredItem;
import pos.sd.omakasa.Modelclass.NamesTopup;
import pos.sd.omakasa.Modelclass.SetDataTopup;
import pos.sd.omakasa.Modelclass.TotalClass;
import pos.sd.omakasa.R;
import pos.sd.omakasa.Utils.Utils;

/**
 * Created by jabbir on 2/9/16.
 */
public class MainMenuBaseActivity extends AppCompatActivity {
    public RecyclerView _rcylMenuListing, _recylMenuDetail;
    public RecyclerView.LayoutManager _lcyMenuListing, _lecylMenuDetail;
    public RecyclerView.Adapter listAdapter;
    public GridAdapter detailAdapter;
    RecyclerView mDListRec;
    private ArrayList<TotalClass> tItems = new ArrayList<TotalClass>();
    private HashMap<String, ArrayList<TotalClass>> ttlItems = new HashMap<String, ArrayList<TotalClass>>();
    public TextView countNum;
    private List<String> al_trr;
    private ArrayList<String> xMuti = new ArrayList<>();
    private ArrayList<NamesTopup> umItems = new ArrayList<NamesTopup>();
    private ArrayList<SetDataTopup> sUitItems = new ArrayList<SetDataTopup>();
    public RecyclerView.LayoutManager mDListManager;
    public ViewOrderadapter mDListAdapter;
    private String finish = "";
    private int totalSize = 1;
    private ArrayList<String> tyArry = new ArrayList<String>();
    private List<String> tesTT = new ArrayList<>();
    private Map<String, String> xcAdap = new HashMap<>();
    private String xmlStrMai = "";
    public static Map<String, String> xyzHash = new HashMap<>();
    private int xuuuPost = 0;
    private Map<String, String> noHash = new HashMap<>();
    private ArrayList<EndangeredItem> mItems = new ArrayList<EndangeredItem>();
    private ProgressDialog progressDialog;
    private HashMap<String, Bitmap> tsHash = new HashMap<String, Bitmap>();
    private Bitmap icon, con;
    private ArrayList insi = new ArrayList<String>();
    private JSONArray MainGetOrd = new JSONArray();
    private int cForm = 0;
    private String ServerBy = "Please Wait for Buzzer";
    private int postn = 0;
    private String xmlMain = "";
    public static int xMainfor = -1;
    private int seqVariable = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Connecting to Server.....");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        con = Utils.decodeSampledBitmapFromResource(getResources(), R.drawable.nopic, 100, 100);
        tesTT = Utils.readList(MainMenuBaseActivity.this, "Main_Cat");
        xcAdap = Utils.loadMap(MainMenuBaseActivity.this, "xhaNames");
        xyzHash = Utils.loadMap(MainMenuBaseActivity.this, "pageImages");
        noHash = Utils.loadMap(MainMenuBaseActivity.this, "pImages");
        cForm = getIntent().getExtras().getInt("nextLev");

        if (cForm <= 1) {
            Utils.TtlItem = new HashMap<String, ArrayList<EndangeredItem>>();
            new gridViewLayout().execute();
        }

    }

    public class gridViewLayout extends AsyncTask<String, Void, HashMap<String, ArrayList<EndangeredItem>>> {

        @Override
        protected void onPreExecute() {
            icon = Utils.decodeSampledBitmapFromResource(getResources(), R.drawable.nopic, 100, 100);
            //  progressDialog.show();

        }

        @Override
        protected HashMap<String, ArrayList<EndangeredItem>> doInBackground(String... urls) {


            byte[] decodedString;
            try {

                Utils.TtlItem = new HashMap<String, ArrayList<EndangeredItem>>();
                JSONArray jas = new JSONArray();
                jas = Utils.js(MainMenuBaseActivity.this);

                Log.i("came", "came");

                for (int j = 0; j < tesTT.size(); j++) {
                    JSONArray result = new JSONArray();
                    result = jas.getJSONArray(j);
                    mItems = new ArrayList<EndangeredItem>();

                    for (int i = 0; i < result.length(); i++) {

                        EndangeredItem species;
                        if (result.getJSONObject(i) != null) {
                            if (result.getJSONObject(i).isNull("userid")) {

                                species = new EndangeredItem();
                                Double value = Double.parseDouble(result.getJSONObject(i).getString("price"));
                                species.setName(result.getJSONObject(i).getString("item_desc"));
                                species.setDescr(result.getJSONObject(i).getString("describe_desc"));
                                species.setPrice(String.format("%.2f", value));
                                // species.setbtnPage(result.getJSONObject(i).getString("button_no"));
                                species.setitemId(result.getJSONObject(i).getString("item_id"));

//                                if(!result.getJSONObject(i).has("button_no"))
//                                    species.setbtnPage(result.getJSONObject(i).getString("button_no"));


                                if (result.getJSONObject(i).has("variable_item")) {
                                    species.setvName("yes");
                                    ArrayList<NamesTopup> tsd = new ArrayList<>();
                                    tsd = tsArryList(result.getJSONObject(i).getJSONArray("variable_item"));
                                    species.setvrblset(tsd);
                                } else {

                                    species.setvName("no");
                                }
                                if (result.getJSONObject(i).has("set_item")) {
                                    species.setsetName("yes");
                                } else {
                                    species.setsetName("no");
                                }
                                species.setThumbnail(icon);

                                if (!result.getJSONObject(i).getString("image").matches("") || xyzHash.get(result.getJSONObject(i).getString("item_id")).matches("")) {
                                    Log.i("st3333ring", result.getJSONObject(i).getString("image"));
                                    species.setThumbnail(con);
                                    tsHash.put(result.getJSONObject(i).getString("item_id"), con);
//
                                } else {

                                    tsHash.put(result.getJSONObject(i).getString("item_id"), icon);
                                    species.setThumbnail(icon);
                                }
                            } else {
                                species = new EndangeredItem();
                                if (result.getJSONObject(i).has("variable_item")) {
                                    species.setvName("yes");
                                    ArrayList<NamesTopup> tsd = new ArrayList<>();
                                    tsd = tsArryList(result.getJSONObject(i).getJSONArray("variable_item"));
                                    species.setvrblset(tsd);
                                } else {
                                    species.setvName("no");
                                }
                                if (result.getJSONObject(i).has("set_item")) {
                                    species.setsetName("yes");
                                } else {
                                    species.setsetName("no");
                                }
                                String namer = "";
                                //species.setbtnPage("yyy");
                                species.setitemId("yyy");
                                try {
                                    String id = Utils.loadMap(MainMenuBaseActivity.this, "xdcs").get(result.getJSONObject(i).getString("userid"));
                                    Log.d("id11", "" + id + "_________" + result.getJSONObject(i).getString("userid"));

                                    //namer=Utils.loadMap(NavHomeActivity.this,"userPage").get(id);
                                    species.setThumbnail(icon);
                                    if (noHash.get(id).matches(""))
                                        species.setThumbnail(icon);
                                    else {
                                        Bitmap bitmap = Utils.decodeBase64(MainMenuBaseActivity.this, noHash.get(id));
                                        species.setThumbnail(bitmap);
                                    }

                                } catch (Exception e) {
                                    //species.setThumbnail(icon);
                                }
                                String[] parts = (result.getJSONObject(i).getString("userid")).split("_");
                                System.out.println("ccccccccc" + parts[1]);
                                species.setName(xcAdap.get(parts[1]));
                                species.setPrice("");
                                species.setuserId(result.getJSONObject(i).getString("userid"));
                            }


                            mItems.add(species);
                        }


                        Log.d("Errorrr1111", "" + mItems.size());
                    }
                    Utils.TtlItem.put(tesTT.get(j), mItems);
                    Log.d("0********", "" + Utils.TtlItem);

                }
//
//                Utils.tImages = tsHash;
//                Log.d("1********", "" + Utils.tImages.size());

            } catch (Exception ex) {
                Log.d("Errorrr", "" + Utils.TtlItem.size());
                // Log.d("Errorrr", "" + ex);
            }

            return Utils.TtlItem;
        }

        @Override
        protected void onPostExecute(HashMap<String, ArrayList<EndangeredItem>> result) {
            //tsHash.clear();
//            noHash.clear();
//            mItems.clear();
//            xcAdap.clear();
            Utils.FirstTime = 0;
            try {
//                if ((progressDialog != null) && progressDialog.isShowing()) {
//                    //progressDialog.dismiss();
//                }
            } catch (final IllegalArgumentException e) {
                // Handle or log or ignore
            }
            tstMths();
            //  //progressDialog.dismiss();
            // tstMths();

        }
    }

    private void tstMths() {
        if (Utils.TtlItem.size() != 0) {
            txMng(tesTT.get(0));
            detailAdapter = new GridAdapter(MainMenuBaseActivity.this, Utils.TtlItem.get(tesTT.get(0)), xuuuPost);
            _recylMenuDetail.setAdapter(detailAdapter);
        } else {
            //startActivity(new Intent(MainMenuBaseActivity.this, MainActivity.class));
        }

    }

    protected void txMng(String dsxd) {
        for (int i = 0; i < tesTT.size(); i++) {
            if (tesTT.get(i).toString().equalsIgnoreCase(dsxd)) {
                xuuuPost = i;
                break;
            }

        }
    }

    public class DownloadFilesTask extends AsyncTask<Void, Integer, HashMap<String, ArrayList<TotalClass>>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // progressDialog.show();

        }

        protected HashMap<String, ArrayList<TotalClass>> doInBackground(Void... arg0) {
            tItems = new ArrayList<TotalClass>();
            ttlItems = new HashMap<String, ArrayList<TotalClass>>();
            ArrayList<NamesTopup> rIte = new ArrayList<NamesTopup>();
            JSONArray jVariable = new JSONArray();
            try {
                jVariable = Utils.js(MainMenuBaseActivity.this);
                Log.d("cccccc", "" + jVariable.length());

            } catch (Exception e) {
                Log.d("cccccc", "cmmmd");
            }
            String[] lString = new String[]{"item_id", "item_no", "print_status", "price", "qty", "remarks", "item_desc", "spec", "totalSize", "take_away_item"};
            String[] eSoon = new String[lString.length];
            if (MainGetOrd.length() != 0 && postn == 0) {

                tItems = new ArrayList<TotalClass>();
                Log.d("CCCCCCC", "" + MainGetOrd);
                Utils.sNo = MainGetOrd.length();
                int orderN = MainGetOrd.length() - 1;

                for (int q = 0; q < MainGetOrd.length(); q++) {
                    //Bitmap largeIcon;
                    try {
                        Utils.saveint(MainMenuBaseActivity.this, Integer.parseInt(MainGetOrd.getJSONObject(orderN).getString("order_no").toString()), "orderNum");
                        for (int w = 0; w < lString.length; w++) {
                            if (w < 7) {
                                if (w == 4) {
                                    Double value = Double.parseDouble(MainGetOrd.getJSONObject(q).getString(lString[w]).toString());
                                    eSoon[w] = String.format("%.2f", value);
                                } else {
                                    eSoon[w] = MainGetOrd.getJSONObject(q).getString(lString[w]).toString();
                                }


                            } else if (w == 7) {
                                rIte = new ArrayList<NamesTopup>();
                                if (MainGetOrd.getJSONObject(q).has(Utils.varPar)) {
                                    NamesTopup species = new NamesTopup();
                                    JSONArray varIt = MainGetOrd.getJSONObject(q).getJSONArray("variable_item");
                                    Log.d("catch1", "*******varIt******id******" + varIt.length());
                                    for (int t = 0; t < varIt.length(); t++) {
                                        species = new NamesTopup();
                                        Double value = Double.parseDouble(varIt.getJSONObject(t).getString("cprice"));
                                        Double qty = Double.parseDouble(varIt.getJSONObject(t).getString("cf_qty"));
                                        species.setName(varIt.getJSONObject(t).getString("citem_desc"));
                                        species.setcdef_flag(varIt.getJSONObject(t).getString("cdef_flag"));
                                        species.setPrice("$ " + String.format("%.2f", value));
                                        species.setcitem_no(varIt.getJSONObject(t).getString("citem_no"));
                                        species.setcseq_no(varIt.getJSONObject(t).getString("cseq_no").toString());
                                        species.setQty(String.format("%.2f", qty).replace("$", ""));
                                        species.setcuom(varIt.getJSONObject(t).getString("cuom"));
                                        species.setcremarks(varIt.getJSONObject(t).getString("cremarks").toString());
                                        rIte.add(species);
                                    }
                                    eSoon[7] = "yes";
                                } else {
                                    eSoon[7] = "No";
                                }

                            } else {
                                eSoon[8] = "" + totalSize;
                                eSoon[9] = MainGetOrd.getJSONObject(q).getString(lString[9]);
                            }
                        }
                        // Bitmap xyz = Utils.scaleDown(Utils.tImages.get(eSoon[0].toString()), 100, true);
                        tItems.add(tIMethtems(rIte, eSoon, Utils.tImages.get(eSoon[0].toString())));
                    } catch (Exception e) {
                    }


                }
                ttlItems.put("" + postn, tItems);
                Log.d("CCCCCCC", "" + ttlItems);

            } else {

                tItems = new ArrayList<TotalClass>();
                ttlItems = new HashMap<String, ArrayList<TotalClass>>();
                MainGetOrd = new JSONArray();
                Utils.sNo = 0;
            }
            try {
                JSONArray jsArryfor = new JSONArray();
                for (int p = 0; p < totalSize; p++) {
                    tItems = new ArrayList<TotalClass>();
                    if (p == 0 && MainGetOrd.length() != 0) {
                        ArrayList<TotalClass> xxxItems = new ArrayList<TotalClass>();
                        xxxItems = ttlItems.get("" + p);
                        if (xxxItems.size() != 0) {
                            tItems = ttlItems.get("" + 0);
                            Log.d("CCCCCCCeeeeeee", "" + tItems.size());
                        }

                    } else {
                        tItems = new ArrayList<TotalClass>();

                    }
                    jsArryfor = Utils.loadJSONArray(MainMenuBaseActivity.this, "totalArry", "" + 1);
                    Log.d("122222dddddd2", "" + jsArryfor);


                    for (int i = 0; i < jsArryfor.length(); i++) {
                        int main_pos = Integer.parseInt(jsArryfor.getJSONObject(i).getString(Utils.mPar));
                        int second_pos = Integer.parseInt(jsArryfor.getJSONObject(i).getString(Utils.subPar));
                        int Qty = Integer.parseInt(jsArryfor.getJSONObject(i).getString(Utils.qtyPar));
                        String remarks = jsArryfor.getJSONObject(i).getString("remark");
                        JSONArray inoArry = jVariable.getJSONArray(main_pos);
                        Log.d("1222222inoArrylen", "" + inoArry.getJSONObject(second_pos));
                        String jsd = inoArry.getJSONObject(second_pos).getString("item_desc");
                        String itemNo = jVariable.getJSONArray(main_pos).getJSONObject(second_pos).getString("item_no");
                        String itemId = jVariable.getJSONArray(main_pos).getJSONObject(second_pos).getString("item_id");
                        String value = (inoArry.getJSONObject(second_pos).getString("price"));
                        if ((inoArry.getJSONObject(second_pos).getString("serve_by")).equalsIgnoreCase("B")) {
                            ServerBy = "Please Wait for Buzzer";
                        } else {
                            ServerBy = "Please collect your Order";
                        }

                        Bitmap bitmap = Utils.tImages.get(itemId);
                        if (bitmap == null)
                            bitmap = con;
                        // bitmap = Utils.scaleDown(bitmap, 100, true);
                        Log.d("value", "" + value);

                        // bitmap = Utils.scaleDown(bitmap, 100, true);
                        Log.d("value", "" + itemNo + "itemId" + itemId + value);
                        JSONArray jk = new JSONArray();
                        String arry = "";
                        String Redeem = "No";
                        umItems = new ArrayList<NamesTopup>();
                        sUitItems = new ArrayList<SetDataTopup>();
                        JSONArray js = new JSONArray();
                        if ((jsArryfor.getJSONObject(i).toString().contains(Utils.varPar))) {
                            double xsd = 0;
                            js = jsArryfor.getJSONObject(i).getJSONArray(Utils.varPar);
                            for (int j = 0; j < js.length(); j++) {
                                JSONObject ksw = js.getJSONObject(j);
                                Iterator<String> iter = ksw.keys();
                                while (iter.hasNext()) {
                                    String key = iter.next();
                                    try {
                                        JSONArray jd = ksw.getJSONArray(key);
                                        if (jd.length() == 0) {
                                            Log.d("null", "null");
                                        }

                                        // jd.put(js.getJSONObject(j).getJSONArray("1"));
                                        if (!jd.equals("[]")) {
                                            Log.d("catch1", "*************jd******" + jd);
                                            NamesTopup species = new NamesTopup();
                                            for (int jw = 0; jw < jd.length(); jw++) {
                                                species = new NamesTopup();
                                                species.setName(jd.getString(0).toString());
                                                species.setPrice(jd.getString(1).toString());
                                                species.setcitem_no(jd.getString(2).toString());
                                                species.setcseq_no(jd.getString(3).toString());
                                                species.setQty(jd.getString(4).toString());
                                                species.setcuom(jd.getString(5).toString());
                                                species.setcremarks(jd.getString(6).toString().replace("\n", " "));
                                                species.setcdef_flag(jd.getString(7).toString());
                                            }
                                            xsd = (xsd + (Double.parseDouble(jd.getString(1).toString())));
                                            Log.d("catch1xsd", "*************value******" + xsd);
                                            umItems.add(species);
                                            Log.d("catch1", "*************jdid******" + umItems);
                                        } else {
                                            Log.d("catch1", "*************jdid******" + umItems.size());
                                        }

                                    } catch (JSONException e) {
                                        // Something went wrong!
                                    }
                                }
                            }
                            value = (String.format("%.2f", xsd));
                        }

                        Log.d("catch1", "*************jdcatchers******" + js.length());

                        //}

                        //[{"Main":0,"Subcat":1,"edit":"2","remark":{},"Variable":[]}]
                        //NamesTopup species = new NamesTopup();
                        String spec = "";
                        if (jsArryfor.getJSONObject(i).has(Utils.varPar)) {
                            spec = "yes";
                        } else {
                            spec = "no";
                        }
//                        else if (jsArryfor.getJSONObject(i).has(Utils.setitem)) {
//                            spec = "set";
//                        }

                        String[] ttlSw = new String[]{itemId, itemNo, Redeem, value, "" + Qty, remarks, jsd, spec, "" + totalSize, "N"};
                        Log.d("ttlsw", "" + ttlSw.length);

                        tItems.add(tIMethtems(umItems, ttlSw, bitmap));
                        Log.d("ttlswrrrrr", "" + tItems.size());

                    }
                    ttlItems.put("" + p, tItems);
                    Log.d("1111", "" + p);


                }


            } catch (Exception e) {
                Log.d("catch1", "*************idqqqqq******" + ttlItems.size());
            }

            return ttlItems;
        }

        protected void onPostExecute(HashMap<String, ArrayList<TotalClass>> jvr) {

            try {
                //tsHash.clear();
                //tItems.clear();
                // progressDialog.dismiss();
                tItems = new ArrayList<TotalClass>();
                tItems = jvr.get("" + 0);
                Log.d("catch1", "*************idjvr******" + jvr);
                // if(tItems.size()!=0){
                if (tItems.size() == 0) {
                    mDListRec.setVisibility(View.GONE);
                    ((RelativeLayout) findViewById(R.id.textts)).setVisibility(View.VISIBLE);
//                    mDListAdapter = new ViewOrderadapter(MainMenuBaseActivity.this, tItems, 0);
//                    mDListRec.setAdapter(mDListAdapter);
                } else {
                    mDListRec.setVisibility(View.VISIBLE);
                    ((RelativeLayout) findViewById(R.id.textts)).setVisibility(View.GONE);
                    mDListAdapter = new ViewOrderadapter(MainMenuBaseActivity.this, tItems, 0);
                    mDListRec.setAdapter(mDListAdapter);
                }

                //}


            } catch (Exception jsd) {

            }

        }
    }

    private TotalClass tIMethtems(ArrayList<NamesTopup> mItems, String[] lsTing, Bitmap bitmap) {
        TotalClass ttlspecies = new TotalClass();
        ttlspecies.setitemId(lsTing[0]);
        ttlspecies.setitemNo(lsTing[1]);
        ttlspecies.setreedem(lsTing[2]);
        Double value = Double.parseDouble(lsTing[3]);
        ttlspecies.setprice(String.format("%.2f", value));
        ttlspecies.setQty(lsTing[4]);
        ttlspecies.setremark(lsTing[5]);
        ttlspecies.setistopUp(mItems);
        //ttlspecies.setdatatopUp(uItm);
        ttlspecies.setnpCover(lsTing[8]);
        ttlspecies.setThumbnail(bitmap);
        ttlspecies.setName(lsTing[6]);
        ttlspecies.sethasVar(lsTing[7]);
        ttlspecies.settAway(lsTing[9]);
        Log.d("catch1", "*******mm******id******" + mItems.size());
        return ttlspecies;


    }

    public void Intialick() {
        //&whid=13&docdate=2016/08/02&tableno=00001&nfcno=1234&regcode=TP3
        String strUrl = Utils.INTIALSTRI + Utils.GETOrder + Utils.COMPCODE + "&whid=" + Utils.getString(MainMenuBaseActivity.this, "wh_id") + "&docdate=" + Utils.currenDate() + "&tableno=" + Utils.getString(MainMenuBaseActivity.this, "Group_no") + "&nfcno=" + Utils.getString(MainMenuBaseActivity.this, "buzNo") + "&regcode=" + Utils.readList(getApplicationContext(), Utils._ARRYLISTPREF).get(4);
        Log.d("here this +++++", "" + strUrl);
        ttlItems = new HashMap<String, ArrayList<TotalClass>>();
        if (!Utils.isConnected(MainMenuBaseActivity.this)) {
            MainGetOrd = new JSONArray();
            //MainGetOrd = timeline;
            new DownloadFilesTask().execute();

            Log.d("Main Array", "" + "");
        } else {
            //httpCall(strUrl, null);
            MainGetOrd = new JSONArray();
            new DownloadFilesTask().execute();
        }
    }

    public void btnFirstClick() {
        finish = "";
        // tknMethod(Utils.MAINTAG + Utils.tknNumber);
        if (!Utils.isConnected(MainMenuBaseActivity.this)) {
            alT("");
        } else {
            try {
                xMuti = new ArrayList<String>();
                tyArry = new ArrayList<String>();
                Iterator it = ttlItems.entrySet().iterator();

                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();

                    JSONObject objT = new JSONObject();
                    System.out.println(pair.getKey() + " = " + pair.getValue());
                    tItems = new ArrayList<TotalClass>();
                    tItems = ttlItems.get(pair.getKey());
                    List<String> tkAway = new ArrayList<String>();
                    JSONArray mDataset = new JSONArray();
                    Log.d("RRRRtkAway", "" + tkAway.size() + "  txttt" + tItems.size());
//                        for (int k = 0; k < tkAway.size(); k++) {
//                            Log.d("RRRRtkAway", "" + tkAway.get(k));
//                        }

                    try {
                        if (!Utils.loadJSONArray(MainMenuBaseActivity.this, "SAVED", "" + pair.getKey()).toString().equalsIgnoreCase("[]")) {
                            mDataset = Utils.loadJSONArray(MainMenuBaseActivity.this, "SAVED", "" + pair.getKey());
                            ArrayList<String> list = new ArrayList<String>();

                            if (mDataset != null) {
                                int len = mDataset.length();
                                for (int i = 0; i < len; i++) {
                                    tkAway.add(mDataset.get(i).toString());
                                }
                            }
                        }
                    } catch (Exception e) {

                    }
                    seqVariable = 0;
                    for (int i = 0; i < tItems.size(); i++) {

                        xmlStrMai = "";


                        TotalClass nature = tItems.get(i);
                        String takeWe = "N";
                        if (tkAway.size() != tItems.size()) {
                            takeWe = "N";
                        } else {
                            takeWe = tkAway.get(i);
                        }

                        // Boolean takW =tkAway.get(i);
                        Log.d("RRRRRRR", "" + nature.getReddm);
                        if (nature.getReddm.equalsIgnoreCase("NO")) {
                            Utils.sNo += 1;
                            objT.put("order_no", "" + 0);
                            objT.put("s_no", "" + Utils.sNo);
                            objT.put("item_id", nature.getitemId());
                            objT.put("item_no", nature.getitemNo());
                            objT.put("qty", "" + nature.getQty().replace("$ 0.00", "1"));
                            objT.put("price", nature.getprice());
                            String xtrr = nature.getName() + " " + nature.getQty();
                            xMuti.add(xtrr);
                            if (nature.getremark().equalsIgnoreCase("NaN"))
                                objT.put("remarks", "");
                            else
                                objT.put("remarks", nature.getremark().replace(",", "\r"));
                            objT.put("takeaway", takeWe);
                            // objT.put("guest", pair.getKey());

                            Log.d("tkawasss", takeWe);
                            int cover = 1;
                            try {
                                cover = Utils.getint(MainMenuBaseActivity.this, "ccccints");
                            } catch (Exception e) {

                            }
                            objT.put("no_of_cover", "" + "1");
                            //Log.d("Log",""+ nature.getistopUp() );
                            if (nature.getistopUp() != null) {
                                ArrayList<NamesTopup> ts = new ArrayList<NamesTopup>();
                                insi = new ArrayList<String>();
                                ts = nature.getistopUp();
                                String total_versu = "";
                                String vars = "";

                                for (int iq = 0; iq < ts.size(); iq++) {
                                    JSONObject twobjT = new JSONObject();
                                    NamesTopup species = new NamesTopup();
                                    species = ts.get(iq);
                                    vars = "";
                                    twobjT.put("s_no", "" + Utils.sNo);

                                    Log.d("sssssss", "" + species.getName());
                                    Log.d("sssssaaaaaa", "" + species.getcdef_flag());
                                    if (!species.getcdef_flag().equalsIgnoreCase("M"))
                                        xMuti.add("   + " + species.getName());
                                    seqVariable = seqVariable + 1;
                                    Log.d("seqVariable", "" + seqVariable);
                                    twobjT.put("seq_no", seqVariable);
                                    twobjT.put("item_id", species.getcitem_no());
                                    twobjT.put("qty", "" + species.getQty().replace("$ 0.00", "1"));
                                    twobjT.put("uom", "" + species.getcuom());
                                    twobjT.put("price", species.getPrice());
                                    twobjT.put("remarks", "" + species.getcremarks().replace(",", "\r"));


                                    //  for (int q = 0; q < ts.size(); q++) {

                                    //


                                    // }
                                    // twobjT.put("uom", "" + species.getcuom());
                                    Iterator<String> iter = twobjT.keys();
                                    while (iter.hasNext()) {
                                        String key = iter.next();
                                        try {
                                            Object value = twobjT.get(key);

                                            vars += "<" + key + ">" + value.toString() +
                                                    "</" + key + ">";
                                        } catch (JSONException e) {
                                            // Something went wrong!
                                        }
                                    }
                                    vars = "<" + "v_data" + ">" + vars +
                                            "</" + "v_data" + ">";
                                    insi.add(vars);


                                    Log.d("**value", "*************id******" + insi);

                                }
                                String ttvars = "";
                                for (int oi = 0; oi < insi.size(); oi++) {
                                    ttvars += insi.get(oi);
                                }

//                                    vars = "<" + "variable_data" + ">" + vars +
//                                            "</" + "variable_data" + ">";

                                Log.d("**vars", "*************var******" + ttvars);
                                objT.put("variable_data", ttvars);
                            }

                            Log.d("RRRRRRR", "" + objT);
                            Iterator<String> iter = objT.keys();
                            while (iter.hasNext()) {
                                String key = iter.next();
                                try {
                                    Object value = objT.get(key);
                                    xmlStrMai += "<" + key + ">" + value.toString() +
                                            "</" + key + ">";
                                    Log.d("**vaeeee", "*************id******" + xmlStrMai);
                                } catch (JSONException e) {
                                    // Something went wrong!
                                }
                            }
                            xmlStrMai = "<" + "order" + ">" + xmlStrMai +
                                    "</" + "order" + ">";

                            tyArry.add(xmlStrMai);
                        } else {
                            Log.d("**value", "Came here for text");
                        }

                    }

                    //it.remove(); // avoids a ConcurrentModificationException
                }
                Log.d("**xmlStrMai", "" + xmlStrMai);
                if (xmlStrMai.matches("")) {
                    //  Utils.alertDialogShow(MainMenuBaseActivity.this, getString(R.string.noOrd), "Alert", 0);
                    return;
                } else {
                    Utils.d("VIewmyorder", xmlStrMai);
                    xmlMain = "";
                    Log.d("tyArrrr", "" + tyArry.size());
                    for (int i = 0; i < tyArry.size(); i++) {
                        xmlMain += tyArry.get(i);
                    }
                    try {
                        Log.d("tyArrrr111111", "" + tyArry.size() + "ddd" + xmlMain);
                        if (xmlMain.matches("")) {
                            Toast.makeText(MainMenuBaseActivity.this, "Please check the Network  and try again.", Toast.LENGTH_SHORT).show();
                            // backMethod();
                        } else {

                            el(xmlMain);
                            alT(xmlMain);
//
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

//                String xdc = getApplicationContext().getString(R.string.recptr);
//                xdc = xdc.replace("_", Utils.getString(MainMenuBaseActivity.this, "buzNo") + "\n");
//                xdc = xdc + Utils.currenDate() ;
//                for (int i = 0; i < xMuti.size(); i++) {
//                    xdc = xdc + "\n" + xMuti.get(i);
//                }
//                xdc = xdc + "\n" + ServerBy;
                Log.d("xxxxxxxxxxxx", "" + xMuti.size());


                // Log.d("***********", "" + jsObjArry);
            } catch (JSONException js) {

            }

        }


    }

    private void httpCall(String strUrl, RequestParams valu) {
        Utils.post(strUrl, valu, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                //progressDialog.show();
            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {

                // Pull out the first event on the public timeline
                try {
                    JSONArray jsAr = timeline;
                    MainGetOrd = new JSONArray();
                    Log.d("**@jsArryfor", "" + jsAr);
                    if (jsAr.length() == 0) {

                        xMainfor = 0;
                        postn = 0;

                        try {
                            tItems = new ArrayList<TotalClass>();
                            JSONArray jsArryfor = Utils.loadJSONArray(MainMenuBaseActivity.this, "totalArry", "" + 1);
                            Log.d("122222dddddd2", "" + jsArryfor);
                            if (timeline.length() == 0 && jsArryfor.length() == 0) {
                                tItems = new ArrayList<TotalClass>();
                                mDListAdapter = new ViewOrderadapter(MainMenuBaseActivity.this, tItems, 0);
                                mDListRec.setAdapter(mDListAdapter);

                            } else {

                                new DownloadFilesTask().execute();
                            }


                        } catch (Exception e) {
                            Log.d("came here", "ggggggg" + e.getMessage());
                        }


                        //Utils.alertDialogShow(MainMenuBaseActivity.this, "Currently No Items is ordered.", "Get Order", 0);


                        //return;
                    }
                    // else if()


                    else if (jsAr.toString().contains("@ll_orderno")) {

                        if (Integer.parseInt(jsAr.getJSONObject(0).getString("@ll_orderno").toString()) > 0) {
                            ttlItems = new HashMap<String, ArrayList<TotalClass>>();
                            // Utils.saveint(MainMenuBaseActivity.this, Integer.parseInt(jsAr.getJSONObject(0).getString("@ll_orderno").toString()), "orderNum");
                            txtMthd();
                            // Utils.alertDialogShow(MainMenuBaseActivity.this, getString(R.string.sOrder), "", 2);
                        } else {
                            //Utils.alertDialogShow(MainMenuBaseActivity.this, getString(R.string.failOrder) + "(" + jsAr.getJSONObject(0).getString("@ll_orderno").toString() + ")", "", 1);
                        }


                    } else if (jsAr.toString().contains("ReturnValue")) {
                        if (Integer.parseInt(jsAr.getJSONObject(0).getString("ReturnValue").toString()) > 0) {
                            finish = "ReturnValue";
                            Utils.removeJsonSharedPreferences(getApplicationContext(), "orderNum");
                            Utils.removeJsonSharedPreferences(getApplicationContext(), "SAVED");
                            Utils.removeJsonSharedPreferences(getApplicationContext(), "totalArry");
                            Utils.posTo = 0;
                            String xdc = getApplicationContext().getString(R.string.recptr);
                            xdc = xdc.replace("_", Utils.getString(MainMenuBaseActivity.this, "buzNo") + "\n");
                            xdc = xdc + Utils.currenDate() + " Order No:" + jsAr.getJSONObject(0).getString("ReturnValue");
                            for (int i = 0; i < xMuti.size(); i++) {
                                xdc = xdc + "\n" + xMuti.get(i);
                            }
                            xdc = xdc + "\n" + ServerBy;
                            ernl(xdc);
                            //Utils.alDialog(MainMenuBaseActivity.this, 1);

                            Intent i = new Intent(MainMenuBaseActivity.this, OrderSucess.class);
                            startActivity(i);


//                            new CountDownTimer(2800, 2800) {
//                                public void onTick(long ms) {
//                                }
//
//                                public void onFinish() {
//                                    Intent i = getBaseContext().getPackageManager()
//                                            .getLaunchIntentForPackage(getBaseContext().getPackageName());
//                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                    i.addCategory(Intent.CATEGORY_HOME);
//                                    startActivity(i);
//                                    android.os.Process.killProcess(android.os.Process.myPid());
//                                }
//                            }.start();
                        }
                    } else {
                        Utils.alrtDialog(MainMenuBaseActivity.this, getString(R.string.fil), getString(R.string.trgy), 2);

                    }


                } catch (Exception exception) {
                    Log.e("myappname", "exception", exception);
                    // handleError(exception);
                }

                // Do something with the response
                System.out.println(timeline);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("errror", "" + errorResponse);
                //Utils.alertDialogShow(MainMenuBaseActivity.this, getString(R.string.failOrder), " Fail Alert", 1);
            }

            @Override
            public void onFinish() {
                // progressDialog.dismiss();
//                if(finish.equalsIgnoreCase("ReturnValue")){
//                    Intent i = new Intent(MainMenuBaseActivity.this, OrderSucess.class);
//                    startActivity(i);
//                }
            }
        });

    }

    private void alT(final String xmlMain) {

        final Dialog dialog = new Dialog(MainMenuBaseActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.confrcutome_dilg);

        pos.sd.omakasa.Utils.TextViewPlus headerText = (pos.sd.omakasa.Utils.TextViewPlus) dialog.findViewById(R.id._hdr);
        pos.sd.omakasa.Utils.TextViewPlus msgText = (pos.sd.omakasa.Utils.TextViewPlus) dialog.findViewById(R.id._dtl);
        headerText.setText("" + getString(R.string.cfrcrt));
        msgText.setText("" + getString(R.string.fcd));


        Button dialogButton = (Button) dialog.findViewById(R.id._yesbtn);
        Button dialogNOButton = (Button) dialog.findViewById(R.id._nobtn);
        ImageButton digButton = (ImageButton) dialog.findViewById(R.id._btncln);
        digButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialogNOButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((LinearLayout) findViewById(R.id.footer)).getVisibility() == View.VISIBLE) {
                    ((LinearLayout) findViewById(R.id.footer)).setVisibility(View.GONE);
                }
                dialog.dismiss();
            }
        });
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    List<String> tsdArr = new ArrayList<String>();
                    tsdArr = Utils.readList(getApplicationContext(), Utils._ARRYLISTPREF);
                    //String[] xc = Utils.loadArray("serverName", MainMenuBaseActivity.this);
                    String url;
                    //Utils.getString(MainMenuBaseActivity.this, "Tableno")+"&printername=PRNK1"//Utils.getString(MainMenuBaseActivity.this, "Tableno")
                    url = URLEncoder.encode((getResources().getString(R.string.encdq)).toString() + xmlMain + (getResources().getString(R.string.encdtaiq)).toString(), "UTF-8");
                    String simpleUrl = Utils.INTIALSTRI + Utils.ExNT + Utils.COMPCODE + "&whid=" + Utils.getString(MainMenuBaseActivity.this, "wh_id") + "&whcd=" + tsdArr.get(6) + "&regcode=" + tsdArr.get(4) + "&shiftcode=SHIFT1&docdate=" + Utils.currenDate() + "&tableno=" + Utils.getString(MainMenuBaseActivity.this, "buzNo") + "&xmlfile="
                            + url + "&userid=" + "DBA";
                    Log.d("here this +++++", "" + simpleUrl);
                    if (!Utils.isConnected(MainMenuBaseActivity.this)) {

                        txtMthd();
                    } else {
                        httpCall(simpleUrl, null);
                    }
                } catch (Exception e) {

                }

                dialog.dismiss();
            }
        });


        dialog.show();


    }

    private void txtMthd() {


        new AlertDialog.Builder(MainMenuBaseActivity.this)
                .setTitle(getString(R.string.sOrder))
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Utils.removeJsonSharedPreferences(getApplicationContext(), "orderNum");
                        Utils.removeJsonSharedPreferences(getApplicationContext(), "SAVED");
                        Utils.removeJsonSharedPreferences(getApplicationContext(), "totalArry");
                        Utils.posTo = 0;
                        startActivity(new Intent(MainMenuBaseActivity.this, MainActivity.class));
//                        Intent i = getBaseContext().getPackageManager()
//                                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
//                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        i.addCategory(Intent.CATEGORY_HOME);
//                        startActivity(i);
//                        android.os.Process.killProcess(android.os.Process.myPid());


                    }
                })
                .show().getWindow().setLayout(1000, 300);
    }


    @Override
    public void onBackPressed() {

    }

    private void ernl(String sr) {
        // write text to file

        // add-write text into file
        try {
            File myFile = new File("/sdcard/Documents/RECEIPT.TXT");
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


    private void el(String sr) {
        // write text to file

        // add-write text into file
        try {
            File myFile = new File("/sdcard/Documents/OUTLOG.TXT");
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


    private ArrayList<NamesTopup> tsArryList(JSONArray jvribl) {
        ArrayList<NamesTopup> tsName = new ArrayList<NamesTopup>();
        NamesTopup species;
        try {
            for (int i = 0; i < jvribl.length(); i++) {
                JSONObject jsd = jvribl.getJSONObject(i);
                species = new NamesTopup();
                Double value = Double.parseDouble(jsd.getString("cprice"));
                species.setName(jsd.getString("citem_desc"));
                species.setcitem_id(jsd.getString("citem_id"));
                Log.d("citem id", jsd.getString("citem_id"));
                species.setThumbnail(null);
                species.setcseq_no(jsd.getString("cseq_no"));
                //species.setcseq_no(jsd.getString("cgseq_no"));
                species.setcdef_flag(jsd.getString("cdef_flag"));
                species.setcuom(jsd.getString("cuom"));
                species.setPrice(String.format("%.2f", value));
                species.setgrpName(jsd.getString("cgroup"));
                tsName.add(species);
            }
        } catch (Exception e) {

        }


        return tsName;
    }


    private void delet(String sr) {
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
