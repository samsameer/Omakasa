package pos.sd.omakasa.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import pos.sd.omakasa.Activity.MainActivity;
import pos.sd.omakasa.Activity.MainMenuActivity;
import pos.sd.omakasa.Activity.SplashScreen;
import pos.sd.omakasa.Modelclass.EndangeredItem;
import pos.sd.omakasa.R;

/**
 * Created by jabbir on 30/8/16.
 */
public class Utils {

    public static final String _ARRYLISTPREF = "";
    public static int sNo = 0;
    public static int posTo = 0;
    public static String xTo = "";
    public static final String _NFCSTATUS = "/kpos/GetNFCStatus?compcode=";
    public static final String _SOLDTATUS = "/kpos/getItemSoldOutToday?compcode=";

    public static final String _MEALTIME = "/kpos/getMealTimeInfo?compcode=";
    public static final String _MEALGROUP = "/kpos/getMealGroup?compcode=";
    public static final String _POSCLIENT = "/kpos/getPosClientParam?compcode=";
    public static final String _WOIMG = "/kpos/getbuttonPage_woimage?compcode=";
    public static final String _FULLDETAILS = "/kpos/getItemsfulldetails?compcode=";
    public static String GETOrder = "/GetOrderItemByNFCByReg?compcode=";
    public static String ExNT = "/kpos/putorder?compcode=";
    public static int cusOn = 1;
    public static String mPar = "main";
    public static String subPar = "subcat";
    public static String qtyPar = "qty";
    public static String regCOde="";
    public static String remPar = "remark";
    public static String setitem = "setitem";
    public static String varPar = "variable";
    /* Start Looj android AsyncHttpClient this make http request and response */
    public static String userAuth = "DBA";
    public static String passAuth = "#DBA!";
    public static String INTIALSTRI;
    public static String COMPCODE;
    public static String URL_POSCLIENT = "POS";
    public static String URL_WOIMG = "WOIMG";
    public static HashMap<Drawable,int[]>tstMap=new HashMap<>();
    public static String URL_FULLDTL = "FULD";
    public static HashMap<String, ArrayList<EndangeredItem>> TtlItem = new HashMap<String, ArrayList<EndangeredItem>>();
    public static HashMap<String, Bitmap> tImages = new HashMap<String, Bitmap>();
    public static HashMap<Drawable, int[]> tmes = new HashMap<Drawable, int[]>();
    public static int FirstTime = 0;
    public static ArrayList<String>itemId=new ArrayList<String>();
    /*
    * SHARED PREFRENCE STRINGS
    *
    * */

    public static String keyARRY = "";
    private static AsyncHttpClient client = new AsyncHttpClient();
    private static SyncHttpClient cient = new SyncHttpClient();

    public static void getsyc(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        cient = new SyncHttpClient();
        cient.setBasicAuth(Utils.userAuth, Utils.passAuth);
        cient.setTimeout(500 * 1000);
        cient.get(getAbsoluteUrl(url), params, responseHandler);
    }


    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client = new AsyncHttpClient();
        client.setBasicAuth(Utils.userAuth, Utils.passAuth);
        client.setTimeout(500 * 1000);
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client = new AsyncHttpClient();
        client.setBasicAuth(Utils.userAuth, Utils.passAuth);
        client.setTimeout(500 * 1000);
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return relativeUrl;
    }
 /* End of Looj android AsyncHttpClient this make http request and response */

    /* Save String */
    public static void saveString(Context context, String text, String key) {
        SharedPreferences prefs = context.getSharedPreferences("preferenceName", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, text);
        editor.apply();
    }

    public static JSONArray RemoveJSONArray(JSONArray jarray, int pos) {

        JSONArray Njarray = new JSONArray();
        try {
            for (int i = 0; i < jarray.length(); i++) {
                if (i != pos)
                    Njarray.put(jarray.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Njarray;
    }

    public static String getString(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences("preferenceName", 0);
        return prefs.getString(key, null);
    }
    /* Load String */

    /* Load & Save JSONOBJECT */
    private static final String PREFIX = "json";

    public static void saveJSONObject(Context c, String prefName, String key, JSONObject object) {
        SharedPreferences settings = c.getSharedPreferences(prefName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(Utils.PREFIX + key, object.toString());
        editor.apply();
    }

    public static void saveint(Context context, int text, String key) {
        SharedPreferences prefs = context.getSharedPreferences("preferenceName", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, text); //3
        editor.commit(); //4
    }

    public static int getint(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences("preferenceName", 0);
        return prefs.getInt(key, 0);
    }

    public static JSONObject loadJSONObject(Context c, String prefName, String key) throws JSONException {
        SharedPreferences settings = c.getSharedPreferences(prefName, 0);
        return new JSONObject(settings.getString(Utils.PREFIX + key, "{}"));
    }

    /* Load & Save JSONArray */
    public static void saveJSONArray(Context c, String prefName, String key, JSONArray array) {
        SharedPreferences settings = c.getSharedPreferences(prefName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(Utils.PREFIX + key, array.toString());
        editor.apply();
    }

    public static JSONArray loadJSONArray(Context c, String prefName, String key) throws JSONException {
        SharedPreferences settings = c.getSharedPreferences(prefName, 0);
        return new JSONArray(settings.getString(Utils.PREFIX + key, "[]"));
    }
   /* Exit Load & Save JSONOBJECT */

    public static void postBuzzer(Context txt, String url, RequestParams params, StringEntity xyz, AsyncHttpResponseHandler responseHandler) {
        client = new AsyncHttpClient();




        //asycnHttpClient = new AsyncHttpClient();
        try {



            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
// We load the KeyStore
            trustStore.load(null, null);
// We initialize a new SSLSocketFacrory
            MySSLSocketFactory socketFactory = new MySSLSocketFactory(trustStore);
// We set that all host names are allowed in the socket factory
            socketFactory.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
// We initialize the Async Client
            client = new AsyncHttpClient();
// We set the timeout to 30 seconds
            client.setTimeout(30 * 1000);
// We set the SSL Factory
            client.setSSLSocketFactory(socketFactory);


//
//            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
//            trustStore.load(null, null);
//            testf = new MySSLSocketFactory(trustStore);
//            testf.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//            client.setSSLSocketFactory(testf);
            client.addHeader("appID", "273b319c40f5c9d6");
            client.addHeader("appSecret", "RxditLIwU9QFo1rHS8mU/caALI4AAA==");
            client.setTimeout(500 * 1000);
            Log.d("cameee ", "came here ");
            xyz.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            // client.post(getAbsoluteUrl(url), params, responseHandler);
            client.post(txt, getAbsoluteUrl(url), xyz, "application/json", responseHandler);

        } catch (Exception e) {
        }


        //client.post(getAbsoluteUrl(url), params, responseHandler);


    }
    public static void writeList(Context context, ArrayList<String> list, String prefix) {
        SharedPreferences prefs = context.getSharedPreferences("YourApp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        int size = prefs.getInt(prefix + "_size", 0);

        // clear the previous data if exists
        for (int i = 0; i < size; i++)
            editor.remove(prefix + "_" + i);

        // write the current list
        for (int i = 0; i < list.size(); i++)
            editor.putString(prefix + "_" + i, list.get(i));

        editor.putInt(prefix + "_size", list.size());
        editor.commit();
    }


    public static String mealds(ArrayList<String> tsd) {
        String urlMtds = "/kpos/getMealGroup?compcode=" + tsd.get(0) + "&whid=" + tsd.get(1) + "&mealid=" + tsd.get(2) + "&regcode=" + tsd.get(3) + "&date=" + tsd.get(4);
        return urlMtds;
    }

    public static JSONArray UpdateremJSONArray(JSONArray jarray, int pos, String xMan) {
        try {
            JSONObject js = new JSONObject();
            js = jarray.getJSONObject(pos);
            js.put("remark", xMan);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("rrrrrrrrr", "" + jarray);
        return jarray;
    }

    public static List<String> readList(Context context, String prefix) {
        SharedPreferences prefs = context.getSharedPreferences("YourApp", Context.MODE_PRIVATE);
        int size = prefs.getInt(prefix + "_size", 0);
        List<String> data = new ArrayList<String>(size);
        for (int i = 0; i < size; i++)
            data.add(prefs.getString(prefix + "_" + i, null));

        return data;
    }


    public static void saveMap(final Context context, Map<String, String> inputMap, String key) {
        SharedPreferences pSharedPref = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pSharedPref.edit();
        if (pSharedPref != null) {
            JSONObject jsonObject = new JSONObject(inputMap);
            String jsonString = jsonObject.toString();
            editor.remove(key).commit();
            editor.putString(key, jsonString);
            editor.commit();
        }
    }

    public static Map<String, String> loadMap(final Context context, String keys) {
        Map<String, String> outputMap = new HashMap<String, String>();
        SharedPreferences pSharedPref = context.getSharedPreferences(keys, Context.MODE_PRIVATE);
        try {
            if (pSharedPref != null) {
                String jsonString = pSharedPref.getString(keys, (new JSONObject()).toString());
                JSONObject jsonObject = new JSONObject(jsonString);
                Iterator<String> keysItr = jsonObject.keys();
                while (keysItr.hasNext()) {
                    String key = keysItr.next();
                    String value = (String) jsonObject.get(key);
                    outputMap.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputMap;
    }


    public static Map<String, String> jsonToMap(JSONObject json) throws JSONException {
        Map<String, String> retMap = new HashMap<String, String>();

        if (json != JSONObject.NULL) {
            retMap = toMap(json);
        }
        return retMap;
    }

    public static void d(String TAG, String message) {
        int maxLogSize = 15000;
        for (int i = 0; i <= message.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i + 1) * maxLogSize;
            end = end > message.length() ? message.length() : end;
            android.util.Log.d(TAG, message.substring(start, end));
        }
    }

    public final static Map<String, String> createMap(Map<String, String> m) {
        Map<String, String> map = new HashMap<String, String>();
        Map<String, String> tmpMap = new HashMap<String, String>();
        for (Map.Entry<String, String> entry : m.entrySet()) {
            if (!tmpMap.containsKey(entry.getValue())) {
                tmpMap.put(entry.getValue(), entry.getKey());
            }
        }
        for (Map.Entry<String, String> entry : tmpMap.entrySet()) {
            map.put(entry.getValue(), entry.getKey());
        }
        return map;
    }

    public static void removeJsonSharedPreferences(Context c, String prefName) {
        if (c != null) {
            SharedPreferences mSharedPreferences = c.getSharedPreferences(prefName, 0);
            if (mSharedPreferences != null)
                mSharedPreferences.edit().clear().commit();
            //mSharedPreferences.edit().clear().commit();
        }

    }

    public static Map<String, String> toMap(JSONObject object) throws JSONException {
        Map<String, String> map = new HashMap<String, String>();

        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value.toString());
        }
        return map;
    }

    public static String currenDate() {
        String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        return date;

    }

    public static boolean isConnected(Context context) {
        ConnectivityManager connMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = connMan.getActiveNetworkInfo();
        if (network == null || !network.isConnected()) {

            return false;
        }
        return true;
    }

    public static JSONArray js(Context cx) {

        JSONArray jst = new JSONArray();
        try {

            jst = Utils.loadJSONArray(cx, "basha", "1");

        } catch (Exception e) {


        }
        return jst;
    }


    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static void alertDialogShow(final Context context, String message, String title, final int xyz) {

        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);

        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setMessage(message);
        int x = xyz;
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                if (xyz == 0) {
                    Intent i = new Intent(context, MainActivity.class);
                    context.startActivity(i);
                    ((Activity) (context)).finish();
                }


                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }


    public static void alrtDialog(final Context contx, String Header, String body, final int xyInt) {
        final Dialog dialog = new Dialog(contx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.cutome_dilg);

        pos.sd.omakasa.Utils.TextViewPlus headerText = (pos.sd.omakasa.Utils.TextViewPlus) dialog.findViewById(R.id.hdr);
        pos.sd.omakasa.Utils.TextViewPlus msgText = (pos.sd.omakasa.Utils.TextViewPlus) dialog.findViewById(R.id.dtl);
        headerText.setText("" + Header);
        msgText.setText("" + body);

        Typeface face = Typeface.createFromAsset(contx.getAssets(),
                "avenri_bold.ttc");
        Button dialogButton = (Button) dialog.findViewById(R.id.okbtn);
        dialogButton.setTypeface(face);
        if (xyInt == 2) {
            dialogButton.setBackground(contx.getDrawable(R.drawable.r_corner));
            dialogButton.setText(contx.getString(R.string.cnlt));
        }

        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (xyInt == 0) {

                    dialog.dismiss();
                } else if (xyInt == 1) {
                    Intent i = new Intent(contx, SplashScreen.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addCategory(Intent.CATEGORY_HOME);
                    contx.startActivity(i);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    dialog.dismiss();
                } else if (xyInt == 2) {
                    Intent i = new Intent(contx, MainMenuActivity.class);
                    contx.startActivity(i);
                    dialog.dismiss();
                }

            }
        });


        ImageButton digButton = (ImageButton) dialog.findViewById(R.id.btncln);
        // if button is clicked, close the custom dialog
        digButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }


    public static void alDialog(final Context contx, final int xyDs) {
        final Dialog dialog = new Dialog(contx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.thnk_dilg);
        TextView headerText = (TextView) dialog.findViewById(R.id.hdr);
        TextView msgText = (TextView) dialog.findViewById(R.id.dtl);
        if (xyDs == 0) {

            Intent i = new Intent(contx, SplashScreen.class);
            contx.startActivity(i);
            dialog.dismiss();
        }


        dialog.show();
    }


    public static Bitmap decodeBase64(Context con, String input) {


        try {

            Bitmap bitmap = null;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPurgeable = true;
            byte[] decodedByte = Base64.decode(input, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
            return bitmap;

        } catch (Exception ee) {
            Log.w("ImageView", "OOM with sampleSize ", ee);
            System.gc();
            Bitmap bitmap = Utils.decodeSampledBitmapFromResource(con.getResources(), R.drawable.nopic, 100, 100);
            return bitmap;
        }


    }
}
