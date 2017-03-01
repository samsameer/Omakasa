package pos.sd.omakasa.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pos.sd.omakasa.Utils.Utils;

/**
 * Created by jabbir on 30/8/16.
 */
public class BackgrodBaseActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private List<String> tsdArr = new ArrayList<>();
    private String MealID = "";
    private String sd;
    private StringBuilder sBuild;
    private String whID = "12";
    private String groupCode = "";
    private JSONArray addObj = new JSONArray();
    private String MealGroup = "";
    private JSONArray jsBtnWoimg = new JSONArray();
    private JSONArray FullDetails = new JSONArray();
    private ArrayList<String> arrayListcat = new ArrayList<String>();
    private HashMap<Integer, String> mapper1 = new HashMap<Integer, String>();
    private ArrayList<ArrayList<String>> gcdI = new ArrayList<ArrayList<String>>();
    private HashMap<String, ArrayList<String>> arrayItemid = new HashMap<String, ArrayList<String>>();
    private ArrayList<String> cdI = new ArrayList<String>();
    private ArrayList<String> arr = new ArrayList<String>();
    private Map<String, String> xhaNames = new HashMap<String, String>();
    private JSONArray totlArray = new JSONArray();
    private JSONArray xyz;
    private JSONArray addArray = new JSONArray();
    private ArrayList<HashMap<Integer, ArrayList<JSONObject>>> cArryList = new ArrayList<HashMap<Integer, ArrayList<JSONObject>>>();
    private HashMap<String, ArrayList<String>> xczI = new HashMap<String, ArrayList<String>>();
    private ArrayList<String> cx = new ArrayList<String>();
    private ArrayList<String> timingHere = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Connecting to Server.....");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
    }


    void checktimer(int xyzz) {
        tsdArr = new ArrayList<>();
        tsdArr = Utils.readList(getApplicationContext(), Utils._ARRYLISTPREF);
        Utils.userAuth = tsdArr.get(2);
        Utils.passAuth = tsdArr.get(3);
        if (tsdArr.get(0).toString().contains("http://")) {
            tsdArr.set(0, "http://" + tsdArr.get(0));
        } else
            tsdArr.set(0, "http://" + tsdArr.get(0));
        Utils.INTIALSTRI = tsdArr.get(0) + ":" + tsdArr.get(1);
        Utils.COMPCODE = tsdArr.get(5);
        String Url = Utils.INTIALSTRI + Utils._MEALTIME + Utils.COMPCODE;
        SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
        String curTime = sdf.format(new Date());

        if (xyzz == 1)
            frstUrl(Url, "Meal Timing");
        else
            timingChker();


    }

    private void filMthd() {
        Toast toast = Toast.makeText(getApplicationContext(), "Fail to connect server. Please try again.", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void frstUrl(final String tcInt, final String keyVlue) {
        Utils.get(tcInt, null, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                progressDialog.show();
                progressDialog.setMessage("Checking   " + keyVlue + "...");

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                // Pull out the first event on the public timeline
                try {
                    if (timeline.length() == 0) {
                        filMthd();
                        //progressDialog.dismiss();
                        return;

                    } else {
                        if (keyVlue.equalsIgnoreCase("Meal Timing")) {
                            Calendar cal = Calendar.getInstance();
                            int hour = cal.HOUR_OF_DAY;
                            timingHere = new ArrayList<String>();
                            for (int i = 0; i < timeline.length(); i++) {
                                timingHere.add(timeline.getJSONObject(i).getString("start_time"));
                            }
                            Utils.writeList(BackgrodBaseActivity.this, timingHere, "timingarraylist");
                            if (Utils.getString(BackgrodBaseActivity.this, "Malid").equalsIgnoreCase("T2")) {
                                MealID = timeline.getJSONObject(1).getString("meal_id");
                            } else
                                MealID = timeline.getJSONObject(0).getString("meal_id");


                            String urlTr = Utils.INTIALSTRI + Utils._POSCLIENT + Utils.COMPCODE + "&regcode=" + tsdArr.get(4) + "&outlet=" + tsdArr.get(6);
                            // urlMethods(urlTr, "POS");
                        } else if (keyVlue.equalsIgnoreCase("Meal GROUP")) {
                            MealGroup = timeline.getJSONObject(0).getString("MealGroup");

                        } else if (keyVlue.equalsIgnoreCase("FULL DETAILS")) {
                            addObj = new JSONArray();
                            addObj = timeline;
                        }


                        //startActivity(new Intent(BackgrodBaseActivity.this, ServerConfig.class));
                    }


                } catch (Exception exception) {
                    //progressDialog.dismiss();
                    return;

                    // handleError(exception);
                }                   // Do something with the response
                System.out.println(timeline);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //  Utils.alertDialogShow(getApplicationContext(), "Error", " Connection  refused", 0);
            }


            @Override
            public void onFinish() {

                progressDialog.dismiss();
                if (keyVlue.equalsIgnoreCase("Meal Timing")) {
                    String urlTr = Utils.INTIALSTRI + Utils._POSCLIENT + Utils.COMPCODE + "&regcode=" + tsdArr.get(4) + "&outlet=" + tsdArr.get(6);
                    urlMethods(urlTr, "POS");
                    Log.d("urrrl1", urlTr);
                } else if (keyVlue.equalsIgnoreCase("Meal GROUP")) {

                    if (MealGroup.matches("")) {
                        if (Utils.getString(BackgrodBaseActivity.this, "Malid").equalsIgnoreCase("T2")) {
                            MealGroup = tsdArr.get(4) + "1PM";
                        } else
                            MealGroup = tsdArr.get(4) + "1AM";
                    }


                    String urlTr = Utils.INTIALSTRI + Utils._WOIMG + Utils.COMPCODE + "&paramgroup_code=" + MealGroup;
                    urlMethods(urlTr, "woImg");
                    Log.d("urrrl2", urlTr);
                } else if (keyVlue.equalsIgnoreCase("FULL DETAILS")) {
                    new DownloadWebPageTask().execute();


                }

            }


        });
    }

    private void urlMethods(String URL, final String intUrls) {
        // Utils.saveJSONArray(getApplicationContext(), "pages", "1", timeline);
        Utils.get(URL, null, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                progressDialog.show();
                progressDialog.setMessage("Connecting to Server ...");

            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                // Pull out the first event on the public timeline
                try {

                    if (timeline == null && timeline.length() <= 0) {
                        filMthd();
                        return;

                    } else if (intUrls.equalsIgnoreCase(Utils.URL_POSCLIENT)) {
                        whID = timeline.getJSONObject(0).getString("wh_id");
                        groupCode = timeline.getJSONObject(0).getString("group_code");
                        Utils.saveString(BackgrodBaseActivity.this, timeline.getJSONObject(0).getString("wh_id").toString(), "wh_id");

                        //  String[] xyz = {"company_name", "wh_name", "wh_id", "group_code", "tax_type","tax_per", "curr_code", "pymt_code", "header_info", "footer_info", "absorb_tax"};
                        Utils.saveJSONArray(BackgrodBaseActivity.this, "server", "key", timeline);

                        Utils.saveMap(getApplicationContext(), Utils.jsonToMap(timeline.getJSONObject(0)), "getPOSClient");
                        Log.d("saved", "" + whID);

                    } else if (intUrls.equalsIgnoreCase("woImg")) {
                        jsBtnWoimg = new JSONArray();
                        jsBtnWoimg = timeline;

                        //Utils.saveJSONArray(getApplicationContext(), "pages", "1", timeline);

                    }


                } catch (Exception exception) {
                    Log.d("this error", "" + exception);

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //  Utils.alertDialogShow(getApplicationContext(), "Error", " Connection  refused", 0);
            }


            @Override
            public void onFinish() {
                progressDialog.dismiss();
                if (intUrls.equalsIgnoreCase(Utils.URL_POSCLIENT)) {

                    String urlMtds = Utils.INTIALSTRI + Utils._MEALGROUP + Utils.COMPCODE + "&whid=" + Utils.getString(BackgrodBaseActivity.this, "wh_id") + "&mealid=" + MealID + "&regcode=" + tsdArr.get(4) + "&date=" + Utils.currenDate();
                    Log.d("urrrl2", urlMtds);
                    frstUrl(urlMtds, "Meal GROUP");
                } else if (intUrls.equalsIgnoreCase("woImg")) {
                    String urlMtds = Utils.INTIALSTRI + Utils._FULLDETAILS + Utils.COMPCODE + "&paramgroup_code=" + MealGroup;
                    frstUrl(urlMtds, "FULL DETAILS");
                    Log.d("urrrl3", urlMtds);
                }


            }
        });


    }


    private void timingChker() {

        if (Utils.getString(BackgrodBaseActivity.this, "Malid").equalsIgnoreCase("T2")) {
            MealGroup = tsdArr.get(4) + "1PM";
        } else
            MealGroup = tsdArr.get(4) + "1AM";


        String urlTr = Utils.INTIALSTRI + Utils._WOIMG + Utils.COMPCODE + "&paramgroup_code=" + MealGroup;
        Utils.get(urlTr, null, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                progressDialog.show();

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                // Pull out the first event on the public timeline
                try {
                    if (timeline.length() == 0) {
                        filMthd();
                        return;

                    } else {
                        jsBtnWoimg = new JSONArray();
                        jsBtnWoimg = timeline;
                    }

                } catch (Exception exception) {
                    return;
                }
                System.out.println(timeline);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            }


            @Override
            public void onFinish() {

                String urlMtds = Utils.INTIALSTRI + Utils._FULLDETAILS + Utils.COMPCODE + "&paramgroup_code=" + MealGroup;
                Utils.get(urlMtds, null, new JsonHttpResponseHandler() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                        // Pull out the first event on the public timeline
                        try {
                            if (timeline.length() == 0) {
                                filMthd();
                                //progressDialog.dismiss();
                                return;

                            } else {
                                addObj = timeline;
                                new DownloadWebPageTask().execute();
                            }


                        } catch (Exception exception) {
                            return;
                        }
                        System.out.println(timeline);
                    }


                    @Override
                    public void onFinish() {
                        progressDialog.dismiss();
                    }
                });
            }
        });


    }


    private class DownloadWebPageTask extends AsyncTask<String, Void, JSONArray> {

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Download and generating data messages...");
            progressDialog.show();
            super.onPreExecute();
        }


        @Override
        protected JSONArray doInBackground(String... urls) {

            try {
                // arrayItemid = new HashMap<String, String>();
                Map<String, String> xdcs = new HashMap<String, String>();

                for (int kw = 0; kw < jsBtnWoimg.length(); kw++) {

                    if (jsBtnWoimg.getJSONObject(kw).isNull("page_type")) {
//                        Log.d("wwwwjabbir", "" + jsBtnWoimg.getJSONObject(kw).getString("page_type"));
//                        getApplicationContext().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Utils.alertDialogShow(getApplicationContext(), "Json Error", "Json Error", 3);
//                            }
//                        });
                    } else {
                        if (jsBtnWoimg.getJSONObject(kw).getString("page_type").equals("I")) {
                            JSONObject actor = jsBtnWoimg.getJSONObject(kw);
                            mapper1.put(Integer.parseInt(actor.getString("page_seq")), actor.getString("page_desc"));
                        }
                    }

                }

                //Log.d("**********", Utils.createMap(arrayItemid) + "");
                arrayListcat = new ArrayList<String>();
                ArrayList<Integer> aListcat = new ArrayList<Integer>();
                for (Map.Entry<Integer, String> entry : mapper1.entrySet()) {
                    System.out.println("Item is:" + entry.getKey() + " with value:" +
                            entry.getValue());
                    aListcat.add(entry.getKey());
                }
                Set<Integer> hes = new HashSet<>();
                hes.addAll(aListcat);
                aListcat.clear();
                aListcat.addAll(hes);
                Collections.sort(aListcat);
                for (int i = 0; i < aListcat.size(); i++) {
                    arrayListcat.add(mapper1.get(aListcat.get(i)));
                }

                Log.d("**********", arrayListcat + "" + arrayListcat.size());
                Utils.writeList(getApplicationContext(), arrayListcat, "Main_Cat");
                //Utils.writeList(getApplicationContext(), arrayItemid, "pages");
                xczI = new HashMap<String, ArrayList<String>>();
                gcdI = new ArrayList<ArrayList<String>>();


                arrayItemid = new HashMap<String, ArrayList<String>>();
                for (int k = 0; k < arrayListcat.size(); k++) {
                    String[] xyz = {};
                    Log.d("**", "" + k);
                    String no = arrayListcat.get(k).toString();
                    cdI = new ArrayList<String>();
                    arr = new ArrayList<String>();
                    for (int kw = 0; kw < jsBtnWoimg.length(); kw++) {
                        JSONObject actor = jsBtnWoimg.getJSONObject(kw);
                        if (!actor.getString("page_type").equals("R")) {
                            String name = actor.getString("page_desc");
                            if (name != null && !name.isEmpty()) {
                                if (no.equalsIgnoreCase(name)) {
                                    if (jsBtnWoimg.getJSONObject(kw).getString("userid").startsWith("PAGE_")) {

                                        String[] parts = (jsBtnWoimg.getJSONObject(kw).getString("userid")).split("_");
                                        System.out.println("example" + parts[1]);
                                        xhaNames.put(jsBtnWoimg.getJSONObject(kw).getString("page_id"), jsBtnWoimg.getJSONObject(kw).getString("page_desc"));
                                        arr.add(jsBtnWoimg.getJSONObject(kw).getString("userid"));
                                        Log.d(jsBtnWoimg.getJSONObject(kw).getString("userid") + "------", jsBtnWoimg.getJSONObject(kw).getString("item_id"));
                                        xdcs.put(jsBtnWoimg.getJSONObject(kw).getString("userid"), jsBtnWoimg.getJSONObject(kw).getString("item_id"));
                                    }
                                    cdI.add(actor.getString("item_id"));
                                }
                            }
                        }
                    }
                    if (!arr.isEmpty()) {
                        arrayItemid.put("" + k, arr);
//                        Log.d("kkkkArraylength " + k, "" + arr);
//                        Log.d("**********", arrayItemid + "");
                    }
                    gcdI.add(cdI);

                    // Log.d("kkkkArraylength " + k, "" + arr);
                    xczI.put("" + k, cdI);
                    //Log.d("Arraylength", "" + gcdI);
                    //Log.d("@@@@@arrang", k + "" + cdI);
                }

                xdcs = Utils.createMap(xdcs);
                Log.d("Arraylength", "" + xdcs);
                Utils.saveMap(getApplicationContext(), xdcs, "xdcs");

                int total_s = -1;
                totlArray = new JSONArray();
                xyz = addObj;
                int x = 0;
                Map<String, String> cvx = new HashMap<String, String>();
                Map<String, String> Imrgcvx = new HashMap<String, String>();

                Map<String, String> cvrrx = new HashMap<String, String>();
                for (int o = 0; o < gcdI.size(); o++) {
                    System.out.println(gcdI.get(o));
                    cx = new ArrayList<String>();
                    cx = gcdI.get(o);
                    addArray = new JSONArray();
                    for (int w = 0; w < cx.size(); w++) {
                        x = w;
                        String nme = cx.get(w).toString();
                        //  Log.d("@@@@@arrang", "" + "" + nme);
                        total_s++;

                        for (int i = 0; i < xyz.length(); i++) {
                            JSONObject actor1 = xyz.getJSONObject(i);
                            String name = actor1.getString("item_id");
                            String price = actor1.getString("price");
                            String imgurl = actor1.getString("image");
                            Double value = Double.parseDouble(price);


                            if (nme.equalsIgnoreCase(name) && (!(String.format("%.2f", value)).equalsIgnoreCase("0.00"))) {
                                Log.d("***xxxxxxxxnn", "" + name);
                                addArray.put(xyz.getJSONObject(i));

                                if (!Utils.isConnected(getApplicationContext()))
                                    cvx.put(name, offlineString(imgurl));
                                else
                                    cvx.put(name, sBuildxc(imgurl));
                                if (actor1.has("variable_item")) {
                                    for (int vr = 0; vr < actor1.getJSONArray("variable_item").length(); vr++) {

//                                            if(!nme.equalsIgnoreCase(actor1.getJSONArray("variable_item").getJSONObject(vr).getString("citem_id")))
//                                            {
                                        if (!Imrgcvx.containsKey(actor1.getJSONArray("variable_item").getJSONObject(vr).getString("citem_id"))) {
                                            if (!Utils.isConnected(getApplicationContext()))
                                                Imrgcvx.put(actor1.getJSONArray("variable_item").getJSONObject(vr).getString("citem_id"), offlineString(actor1.getJSONArray("variable_item").getJSONObject(vr).getString("c_image")));
                                            else
                                                Imrgcvx.put(actor1.getJSONArray("variable_item").getJSONObject(vr).getString("citem_id"), sBuildxc(actor1.getJSONArray("variable_item").getJSONObject(vr).getString("c_image")));

                                        }
                                        //}

                                        Log.d("getJSONObject(i)", "" + actor1.getJSONArray("variable_item").getJSONObject(vr).getString("citem_id"));

                                    }

                                }

                            } else if (nme.equalsIgnoreCase(name) && ((String.format("%.2f", value)).equalsIgnoreCase("0.00"))) {
                                if (xdcs.containsValue(name)) {
                                    Log.d("xcDatata", name + " ___ Image " + imgurl);
                                    cvrrx.put(name, (imgurl));

                                } else {
                                    addArray.put(xyz.getJSONObject(i));
                                    if (!Utils.isConnected(getApplicationContext())) {
                                        cvx.put(name, offlineString(imgurl));
                                    } else
                                        cvx.put(name, sBuildxc(imgurl));
                                    //cvx.put(name, sBuildxc(imgurl));

                                }


                            }


//
                        }


                    }
                    Log.d("***xxxxxxxxnnddddddd", "" + addArray);
                    Log.d("***xxnddddddd", "" + arrayItemid.keySet());
                    for (String key : arrayItemid.keySet()) {
                        if (Integer.toString(o).equals(key)) {
                            ArrayList item = new ArrayList<>();
                            item = arrayItemid.get(key);
                            for (int j = 0; j < item.size(); j++) {
                                JSONObject js = new JSONObject();
                                js.put("userid", item.get(j));
                                Log.d("***js", "" + js);
                                addArray.put(js);
                            }

                            break;
                        }
                    }


                    ArrayList<Integer> tsInt = new ArrayList<>();
                    for (int we = 0; we < addArray.length(); we++) {
                        if (addArray.getJSONObject(we).isNull("userid"))
                            if (addArray.getJSONObject(we).has("button_no"))
                                tsInt.add(Integer.parseInt(addArray.getJSONObject(we).getString("button_no")));

                    }

// add elements to al, including duplicates
                    Set<Integer> hs = new HashSet<>();
                    hs.addAll(tsInt);
                    tsInt.clear();
                    tsInt.addAll(hs);
                    Collections.sort(tsInt);
                    Log.d("***tsInt", "" + tsInt);


                    JSONArray jsAry = new JSONArray();

                    if (tsInt.size() <= 0) {
                        jsAry = addArray;
                    } else {
                        for (int fd = 0; fd < tsInt.size(); fd++) {
                            for (int w = 0; w < addArray.length(); w++) {
                                if (addArray.getJSONObject(w).isNull("userid")) {
                                    if (Integer.parseInt(addArray.getJSONObject(w).getString("button_no")) == tsInt.get(fd))

                                    {
                                        jsAry.put((addArray.getJSONObject(w)));
                                        //break;
                                    }
                                }


                            }

                        }
                    }


                    Log.d("***xxxxxxxx", "" + jsAry.length());
                    totlArray.put(jsAry);
                    Log.d("***xxxxxxxx", "" + totlArray);

                }

                cvrrx = Utils.createMap(cvrrx);
                Map<String, String> cf = new HashMap<String, String>();
                for (String key : cvrrx.keySet()) {
                    System.out.println(key);
                    if (!Utils.isConnected(getApplicationContext())) {
                        cf.put(key, offlineString(cvrrx.get(key)));
                    } else
                        cf.put(key, (sBuildxc(cvrrx.get(key))));


                }
                xhaNames = Utils.createMap(xhaNames);
                Log.d("xhaNames", "" + xhaNames);
                Utils.saveMap(getApplicationContext(), Imrgcvx, "Imrgcvx");
                Utils.saveMap(getApplicationContext(), xhaNames, "xhaNames");
                Utils.saveMap(getApplicationContext(), cf, "pImages");

                //Log.d("**************", "" + ":" + cvx);
                Utils.saveMap(getApplicationContext(), cvx, "pageImages");

                Log.d("catIndxxyz", "" + ":" + arrayItemid);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return totlArray;
        }

        @Override
        protected void onPostExecute(JSONArray result) {
            // progressDialog.dismiss();
            progressDialog.dismiss();
//            Utils.MAINTAG = serConfig[0] + ":" + serConfig[1];
//
//
            try {
                Utils.saveJSONArray(getApplicationContext(), "basha", "1", result);
                ArrayList<ArrayList<Integer>> combiArray = new ArrayList<ArrayList<Integer>>();

            } catch (Exception je) {

            }
            Intent i = new Intent(getApplicationContext(), MainMenuActivity.class);
            i.putExtra("nextLev", 0);
            startActivity(i);
            finish();
        }
    }

    private String sBuildxc(String cd) {

        try {
            Log.i("read al giles", cd);
            StringBuilder everything = new StringBuilder();
            URL url = new URL(cd);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String sResponse;
            sBuild = new StringBuilder();

            while ((sResponse = in.readLine()) != null) {
                sBuild = sBuild.append(sResponse);
            }
            in.close();
            Log.i("sd", sBuild.toString());


        } catch (MalformedURLException e) {
        } catch (Exception e) {
            Log.i("sd444", e.getMessage());


        }
//        Bitmap bs = Utils.decodeBase64(sBuild.toString());

        return sBuild.toString();
    }


    private String offlineString(String cd) {

        String json = "";
        int rawt;
        try {

            String[] parts = (cd.split("resources/"));
            String first = parts[0];
            String second = parts[1];
            Log.d("first", first);
            Log.d("second", (second.replace("_", "d")).replace(".txt", ""));

            sd = "a" + (second.replace("_", "d")).replace(".txt", "");
            //int xcz=Integer.parseInt();
//
//            sd = cd.replace("a", "");
//            sd = sd.replace();

            int id = this.getResources().getIdentifier(sd, "raw", this.getPackageName());

            try {
                BufferedInputStream resourceStream = new BufferedInputStream(getResources().openRawResource(id));
                BufferedReader reader = new BufferedReader(new InputStreamReader(resourceStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    json += line;
                }
                reader.close();
                resourceStream.close();
            } catch (Exception ex) {
                Log.e("myApp", ex.getMessage());
            }
        } catch (Exception e) {
        }


        return json;
    }


    private JSONArray myOfflineJson(int nmeUrl) {
        JSONArray jsonobject = new JSONArray();


        try {
            String json = "";
            try {
                BufferedInputStream resourceStream = new BufferedInputStream(getResources().openRawResource(nmeUrl));
                BufferedReader reader = new BufferedReader(new InputStreamReader(resourceStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    json += line;
                }
                reader.close();
                resourceStream.close();
            } catch (Exception ex) {
                Log.e("myApp", ex.getMessage());
            }

            jsonobject = new JSONArray(json);
            //adap(jsonobject);
//
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonobject;
    }

}

