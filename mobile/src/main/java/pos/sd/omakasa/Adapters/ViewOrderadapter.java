package pos.sd.omakasa.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import pos.sd.omakasa.Activity.MainMenuActivity;
import pos.sd.omakasa.Activity.MainMenuBaseActivity;
import pos.sd.omakasa.Modelclass.TotalClass;
import pos.sd.omakasa.R;
import pos.sd.omakasa.Utils.Utils;


/**
 * Created by jabbir on 8/6/15.
 */
public class ViewOrderadapter extends RecyclerView.Adapter<ViewOrderadapter.ViewHolder> {
    private ArrayList<String> mDataset;
    private Context context;
    private JSONArray layoutResourceId;
    private static final String TAG = ViewOrderadapter.class.getSimpleName();
    private List<TotalClass> mItems;
    private TotalClass species;
    private ArrayList<Double> txduble = new ArrayList<>();
    private String secondSubString = "";
    private HashMap<Integer, ArrayList<String>> getArrys = new HashMap<Integer, ArrayList<String>>();
    private ArrayList<Boolean> status = new ArrayList<Boolean>();
    private ArrayList<Integer> selectedItems;
    private ArrayList<String> insideItem;
    //private List<Data> selItems;
    private ArrayList<String> tAway;
    private double xctt = 0.00;
    private ArrayList<String> lisremrk;
    private int cusNum;
    private int xPodt = -1;
    int tx = 0;
    private HashMap<String, ArrayList<Integer>> addHash = new HashMap<String, ArrayList<Integer>>();
    private JSONArray ttl = new JSONArray();
    private String[] vcx = {};
    private Bitmap con;

    public ViewOrderadapter(Context context, List<TotalClass> nameItem, int cusTid) {
        super();
        this.layoutResourceId = layoutResourceId;
        this.cusNum = cusTid;
        con = Utils.decodeSampledBitmapFromResource(context.getResources(), R.drawable.nopic, 100, 100);

        status = new ArrayList<Boolean>();
        this.context = context;
//        bottomUp = AnimationUtils.loadAnimation(context,
//                R.anim.slide_in_up);

        this.mItems = nameItem;
        tAway = new ArrayList<String>();


        for (int i = 0; i < mItems.size(); i++) {
            tAway.add("N");
            status.add(false);

            TotalClass nature = mItems.get(i);
            Double qTy = Double.parseDouble(nature.getQty());

            txduble.add((Double.parseDouble(nature.getprice()) * qTy));
            xctt += (Double.parseDouble(nature.getprice()) * qTy);
        }
        MainMenuActivity.txtmrk(String.format("%.2f", xctt));

        Log.d("came1", "" + "cameeeee1111rrrrrrr");
        selectedItems = new ArrayList<Integer>();
        JSONArray mDataset = new JSONArray();
        JSONArray fir = new JSONArray();


        try {
            if (!Utils.loadJSONArray(context, "fire", "" + cusNum).toString().equalsIgnoreCase("[]")) {
                fir = Utils.loadJSONArray(context, "fire", "" + cusNum);
                ArrayList<Integer> cvTes = new ArrayList<Integer>();
                String strings[] = new String[fir.length()];
                for (int i = 0; i < strings.length; i++) {
                    strings[i] = fir.getString(i);
                    cvTes.add(Integer.parseInt(fir.get(i).toString()));
                    Log.d("catch1", "**strings" + Integer.parseInt(fir.get(i).toString()));
                }

                for (int e = 0; e < cvTes.size(); e++) {
                    status.set(cvTes.get(e), true);
                }

//                Log.d("catch1", "**" + fir.length());
//                if (fir != null) {
//                    int len = fir.length();
//                    for (int i = 0; i < len; i++) {
//                        if (fir.get(i).toString().equalsIgnoreCase("" + i)) {
//                            Log.d("catch1", "**" +);
//
//                        }
//
//                    }
//                }
            }
        } catch (Exception e) {

        }


        try {
            if (!Utils.loadJSONArray(context, "SAVED", "" + cusTid).toString().equalsIgnoreCase("[]")) {
                mDataset = Utils.loadJSONArray(context, "SAVED", "" + cusNum);
                Log.d("catch1", "*************cusNum******" + cusNum);
                if (mDataset != null) {
                    int len = mDataset.length();
                    for (int i = 0; i < len; i++) {
                        if (mDataset.get(i).toString().equalsIgnoreCase("Y"))
                            tAway.set(i, mDataset.get(i).toString());
                        else
                            tAway.set(i, "N");
                    }
                }
            }
        } catch (Exception e) {

        }

//        if (mDataset != null) {
//            Log.d("catch1", "*************nameItem******" + mDataset);
//            for (int ie = 0; ie < mDataset.size(); ie++) {
//
//            }
//
//        }
        //new DownloadFilesTask().execute();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.myorder_item, viewGroup, false);
        View rowView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.myorder_item, viewGroup, false);
        } else
            rowView = v;

        ViewHolder viewHolder = new ViewHolder(rowView);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Log.d("came2", "" + "cameeeee2");
        TotalClass nature = mItems.get(i);

        viewHolder.titleName.setText(nature.getName());

//        Bitmap b = BitmapFactory.decodeByteArray(nature.getThumbnail(), 0, (nature.getThumbnail().));

        Bitmap b = Utils.decodeBase64(context, MainMenuBaseActivity.xyzHash.get(nature.getitemId()));
        if (b == null)
            viewHolder.thmy.setImageBitmap(con);
        else
            viewHolder.thmy.setImageBitmap(b);

        viewHolder.rsLay.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
        viewHolder.titleName.setTextColor(context.getResources().getColor(android.R.color.white));
        viewHolder.lPrice.setText("$ " + nature.getprice());
        viewHolder.npe1.setText(nature.getQty());
        viewHolder.selectItem.setSelected(false);
        viewHolder.mBthn.setVisibility(View.GONE);


//        try {
//            Log.d("came1", "" + "cameeeee22222rrrrrrr");
//            if (xPodt != -1) {
//
//                if (i == xPodt) {
//                    String xTrs = Utils.getPString(context, "rR", "rrrr");
//                    xPodt = -1;
//                    // JSONObject js = Utils.loadJSONObject(context, "mkeit", cmpAdd + "_" + custID);
//                    viewHolder.remrkview.setText(xTrs);
//
//                } else
//                    viewHolder.remrkview.setText("");
//            }
//
//        } catch (Exception e) {
//
//        }


        if (tAway.get(i).equals("Y") || nature.gettAway().equalsIgnoreCase("Y")) {
            viewHolder.togBut.setTextColor(context.getResources().getColor(R.color.primary));
            viewHolder.togBut.setText("TAKEAWAY");
            viewHolder.togBut.setChecked(true);
        } else {
            viewHolder.togBut.setTextColor(context.getResources().getColor(R.color.white));
            viewHolder.togBut.setText("EATIN");
            viewHolder.togBut.setChecked(false);
        }


        if (nature.getReddm.equalsIgnoreCase("NO")) {
            viewHolder.mDel.setVisibility(View.VISIBLE);
            viewHolder.npe1.setFocusable(true);
            status.set(i, false);
            viewHolder.mfired.setVisibility(View.GONE);
            // viewHolder.mRemarl.setVisibility(View.VISIBLE);
            viewHolder.maddd.setVisibility(View.VISIBLE);
            viewHolder.mminu.setVisibility(View.VISIBLE);
            viewHolder.selectItem.setVisibility(View.GONE);
            viewHolder.togBut.setClickable(true);
            viewHolder.mAdd.setVisibility(View.INVISIBLE);

        } else if (nature.getReddm.equalsIgnoreCase("DONE") || nature.getReddm.equalsIgnoreCase("Y")) {
            viewHolder.mfired.setVisibility(View.VISIBLE);
            status.set(i, false);
            viewHolder.rsLay.setBackgroundColor(context.getResources().getColor(android.R.color.white));
            viewHolder.titleName.setTextColor(context.getResources().getColor(android.R.color.black));
            viewHolder.selectItem.setVisibility(View.GONE);
            viewHolder.mDel.setVisibility(View.GONE);
            viewHolder.npe1.setFocusable(false);
            viewHolder.mminu.setVisibility(View.INVISIBLE);
            viewHolder.mAdd.setVisibility(View.INVISIBLE);
            viewHolder.maddd.setVisibility(View.INVISIBLE);
            viewHolder.togBut.setClickable(false);
        } else {
            viewHolder.rsLay.setBackgroundColor(context.getResources().getColor(android.R.color.white));
            viewHolder.titleName.setTextColor(context.getResources().getColor(android.R.color.white));
            viewHolder.mminu.setVisibility(View.INVISIBLE);
            viewHolder.mfired.setVisibility(View.GONE);
            viewHolder.selectItem.setVisibility(View.VISIBLE);
            viewHolder.selectItem.setSelected(status.get(i));
            viewHolder.selectItem.setChecked(status.get(i));
            viewHolder.mDel.setVisibility(View.GONE);
            viewHolder.mAdd.setVisibility(View.INVISIBLE);
            viewHolder.maddd.setVisibility(View.INVISIBLE);
            viewHolder.npe1.setFocusable(false);
            viewHolder.togBut.setClickable(false);

        }

        if (nature.gethasVar().equalsIgnoreCase("NO")) {
            viewHolder.linRecycle.setVisibility(View.GONE);


        } else {
            viewHolder.linRecycle.setVisibility(View.VISIBLE);
            Log.d("came", "" + nature.getistopUp().size());
            if (nature.getistopUp().size() <= 2)
                viewHolder.linRecycle.getLayoutParams().height = 160;
            else if (4 < nature.getistopUp().size() && nature.getistopUp().size() <= 5)
                viewHolder.linRecycle.getLayoutParams().height = 240;
            else
                viewHolder.linRecycle.getLayoutParams().height = 160;
            viewHolder.ChAdapter = new ViewAddadapter((Activity) context, nature.getistopUp());
            viewHolder.mChairList.setAdapter(viewHolder.ChAdapter);
            // viewHolder.thmy.getAppTaskThumbnailSize()
        }


        // notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

//    protected boolean isSelected(int position) {
//        return selectedItems.contains(Integer.valueOf(position));
//    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private Switch togBut;
        public pos.sd.omakasa.Utils.TextViewPlus titleName;
        public pos.sd.omakasa.Utils.TextViewPlus lPrice;
        private ImageView thmy;
        private TextView mDel;
        private EditText npe1;
        private CheckedTextView selectItem;
        //public TextView mRemarl;
        // private TextView remrkview;
        public TextView mAdd;
        private TextView mfired, maddd, mminu;
        private LinearLayout mBthn;
        private RecyclerView mChairList;
        private RecyclerView.Adapter ChAdapter;
        // private HorizontalScrollView scrl_vie;
        private RecyclerView.LayoutManager lmutManager;
        private LinearLayout linRecycle;
        private RelativeLayout rsLay;
        private int previousTotal = 0;
        private boolean loading = true;
        private int visibleThreshold = 5;

        int firstVisibleItem, visibleItemCount, totalItemCount;

        public ViewHolder(final View itemView) {
            super(itemView);
            rsLay = (RelativeLayout) itemView.findViewById(R.id.res_lys);
            titleName = (pos.sd.omakasa.Utils.TextViewPlus) itemView.findViewById(R.id.oName);
            lPrice = (pos.sd.omakasa.Utils.TextViewPlus) itemView.findViewById(R.id.lprice);
            thmy = (ImageView) itemView.findViewById(R.id.img_thl);
            mDel = (TextView) itemView.findViewById(R.id.img_thnail);
            mBthn = (LinearLayout) itemView.findViewById(R.id.lir221);
            npe1 = (EditText) itemView.findViewById(R.id.edNum);
            selectItem = (CheckedTextView) itemView.findViewById(R.id.chkIt);
            mAdd = (TextView) itemView.findViewById(R.id.mAdd);
            mminu = (TextView) itemView.findViewById(R.id.view_var);
            maddd = (TextView) itemView.findViewById(R.id.newww);

            mfired = (TextView) itemView.findViewById(R.id.fired);
            linRecycle = (LinearLayout) itemView.findViewById(R.id.linrout);
            togBut = (Switch) itemView.findViewById(R.id.mySwitch);
            mChairList = (RecyclerView) itemView.findViewById(R.id.itmMode);
            mChairList.setHasFixedSize(true);
            lmutManager = new GridLayoutManager((Activity) context, 2);
            // mChairList.setLayoutManager(lmutManager);
            LinearLayoutManager layManager = new LinearLayoutManager((Activity) context);
            layManager.setOrientation(LinearLayoutManager.VERTICAL);
            mChairList.setLayoutManager(lmutManager);


            //itemView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.left_right));

            if (selectItem == null) {
                selectItem = new CheckedTextView(context);
            }


            npe1.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    Log.d("came11111111", "" + "cameeeee" + getPosition());

                    TotalClass nature = mItems.get(getPosition());
                    nature.setQty(npe1.getText().toString().replace("$", ""));
                    Double xt = Double.parseDouble(npe1.getText().toString());
                    Double txy = Double.parseDouble(lPrice.getText().toString().replace("$ ", ""));
                    if (tx == 1) {
                        xctt = xctt + Double.parseDouble(nature.getprice());
                        tx = 0;
                        MainMenuActivity.txtmrk(String.format("%.2f", xctt));
                    } else if (tx == 2) {
                        xctt = xctt - Double.parseDouble(nature.getprice());
                        tx = 0;
                        MainMenuActivity.txtmrk(String.format("%.2f", xctt));
                    }

                    //
                    txy = ((double) (txy * xt));
                    lPrice.setText("$ " + String.format("%.2f", txy));

//                    TotalClass nature = mItems.get(getPosition());
//                    nature.setQty(npe1.getText().toString().replace("$", ""));
                }
            });
            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    switch (v.getId()) {

                        case R.id.img_thnail:
                            alT(getPosition());
                            break;


                        case R.id.newww:


                            int xc = Integer.parseInt(npe1.getText().toString()) + 1;

                            tx = 1;

                            npe1.setText("" + xc);
                            addDelmethd(getPosition(), tx);
//                            int xc = Integer.parseInt(npe1.getText().toString()) + 1;
//                            npe1.setText("" + xc);


                            break;
                        case R.id.view_var:

                            int xct = Integer.parseInt(npe1.getText().toString()) - 1;
                            if (xct < 1) {
                                xct = 1;
                            } else if (xct == 1) {
                                tx = 2;
                                addDelmethd(getPosition(), tx);
                            } else {
                                tx = 2;
                                addDelmethd(getPosition(), tx);
                            }
                            npe1.setText("" + xct);

//                            int xct = Integer.parseInt(npe1.getText().toString()) - 1;
//                            if (xct <= 1) {
//                                xct = 1;
//                            }
//                            npe1.setText("" + xct);
                            break;


                        case R.id.mAdd:
//                            //ArrayList<NamesTopup> twz = new ArrayList<NamesTopup>();
//                            ArrayList<String> mittwz = new ArrayList<String>();
//                            ArrayList<ArrayList<String>> t2twz = new ArrayList<ArrayList<String>>();
//
//                            TotalClass nature = mItems.get(getPosition());
//                            // twz = nature.getistopUp();
//                            ArrayList<NamesTopup> object = new ArrayList<NamesTopup>();
//                            object = nature.getistopUp();
//                            for (int i = 0; i < object.size(); i++) {
//                                mittwz = new ArrayList<String>();
//                                NamesTopup species = object.get(i);
//                                mittwz.add(species.getName());
//                                mittwz.add(species.getPrice());
//                                mittwz.add(species.getcremarks());
//                                mittwz.add(species.getQty());
//                                Log.d("catch1", "*************mittwz******" + mittwz);
//                                t2twz.add(mittwz);
//
//                            }
//                            Log.d("catch1", "*************id******" + t2twz);
//                            Intent i = new Intent(context, AddonViewsDilog.class);
//                            i.putExtra("ARRAYLIST", t2twz);
//                            context.startActivity(i);
                            break;

                        case R.id.chkIt:
                            int index = selectedItems.indexOf(getPosition());


                            if (index != -1) {
                                selectItem.setSelected(false);
                                status.set(getPosition(), false);

                            } else {
//                                //txName.toggle();
                                if (selectItem.isSelected()) {
                                    status.set(getPosition(), false);
                                    selectItem.setSelected(status.get(getPosition()));


                                } else {
                                    status.set(getPosition(), true);
                                    selectItem.setSelected(status.get(getPosition()));

                                }
                                aledit();
                            }


                            break;
                    }

                    notifyDataSetChanged();
                }
            };

            maddd.setOnClickListener(clickListener);
            mminu.setOnClickListener(clickListener);
            selectItem.setOnClickListener(clickListener);
            mAdd.setOnClickListener(clickListener);
            mDel.setOnClickListener(clickListener);
//            mChairList.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//                @Override
//                public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
//                    int action = e.getAction();
//                    switch (action) {
//                        case MotionEvent.ACTION_MOVE:
//                            rv.getParent().requestDisallowInterceptTouchEvent(true);
//                            break;
//                    }
//                    return false;
//                }
//
//                @Override
//                public void onTouchEvent(RecyclerView rv, MotionEvent e) {
//
//                }
//
//
//            });
            // or get it from the layout by ToggleButton Btn=(ToggleButton) findViewById(R.id.IDofButton);
            togBut.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                    if (bChecked) {
                        togBut.setTextColor(context.getResources().getColor(R.color.primary));
                        togBut.setText("TAKEAWAY");
                        tAway.set(getPosition(), "Y");
                        //nature.settAway("Y");
                    } else {
                        togBut.setTextColor(context.getResources().getColor(R.color.white));
                        togBut.setText("EATIN");
                        tAway.set(getPosition(), "N");
                    }


                    JSONArray jsArray = new JSONArray(tAway);
                    Utils.saveJSONArray(context, "SAVED", "" + cusNum, jsArray);
                    try {
                        Log.d("&&&&&&&&", "" + cusNum + "" + Utils.loadJSONArray(context, "SAVED", "" + cusNum));
                    } catch (Exception e) {
                        Log.d("catch1", "*************json******" + ttl);
                    }

                }
            });


        }
    }


    public List<Integer> getSelectedItems() {
        return selectedItems;
    }


    private void aledit() {

        addHash = new HashMap<String, ArrayList<Integer>>();
        insideItem = new ArrayList<String>();
        for (int i = 0; i < status.size(); i++) {
            if (status.get(i)) {
                insideItem.add("" + i);
            }
        }

        // addHash.put("" + cusNum, insideItem);
        try {

            JSONArray jsArray = new JSONArray(insideItem);
            Utils.saveJSONArray(context, "fire", "" + cusNum, jsArray);

//            JSONObject jas = new JSONObject(addHash);
//            if (!jas.toString().equals("{}")) {
//                Utils.saveJSONObject(context, "fire", "" + cusNum, jas);
//            }
            Log.d("&&&&&&&&", "" + Utils.loadJSONArray(context, "fire", "" + cusNum));

        } catch (JSONException ks) {

        }


    }

    private void alert_edit(int pos) {
        final int x = pos;
        String firstSubString = "";

        TotalClass nature = mItems.get(pos);
        String remark = nature.getremark();
        if (!remark.matches("")) {
            if (remark.contains("_")) {
                String[] split = remark.split("_");
                firstSubString = split[0];
                secondSubString = split[1];
            }

        } else {
            firstSubString = "";
            secondSubString = "";
        }


        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        final EditText edittext = new EditText(context);
        edittext.setText(firstSubString);
        edittext.setHint("                        Please write your remarks.        ");
        alert.setMessage(secondSubString);

        alert.setTitle(R.string.remrk);
        alert.setView(edittext);

        alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                TotalClass nature = mItems.get(x);
                nature.setremark(edittext.getText() + secondSubString.replace("\n", ""));
                notifyDataSetChanged();

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alert.show();


    }

    private void delTetItem(final int postion) {
        AlertDialog.Builder alertDel = new AlertDialog.Builder(context);
        alertDel.setMessage("                    Are you sure you want to delete this Item ?                                               ");
        alertDel.setTitle("Delete Item");
        alertDel.setPositiveButton("         Delete              ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {


            }
        });

        alertDel.setNegativeButton("Cancel                          ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alertDel.show();


    }

    private void addDelmethd(final int postion, final int addDel) {
        try {
            int posTin = 0;

            if (MainMenuBaseActivity.xMainfor == -1 || MainMenuBaseActivity.xMainfor == 0) {
                posTin = postion;
            } else {
                posTin = (postion - MainMenuBaseActivity.xMainfor);
            }

            JSONArray jsArryfor = new JSONArray();
            jsArryfor = Utils.loadJSONArray(context, "totalArry", "" + Utils.cusOn);
            Log.d("jsttttt",""+jsArryfor);
            int qty = Integer.parseInt(jsArryfor.getJSONObject(posTin).getString("qty"));
            if (addDel == 2) {
                qty = qty - 1;
            } else {
                qty = qty + 1;
            }
            JSONObject js = new JSONObject();
            js = jsArryfor.getJSONObject(posTin);
            js.remove("qty");
            js.put("qty", "" + qty);
            jsArryfor.put(posTin, js);

            TotalClass nature = mItems.get(postion);

            JSONArray jsd = new JSONArray();
            Log.d("postion", "" + posTin);
            if ((jsArryfor.getJSONObject(posTin).toString().contains(Utils.varPar))) {

                double xsd = 0;
                jsd = jsArryfor.getJSONObject(posTin).getJSONArray(Utils.varPar);
                Log.d("jsd", "" + jsd.length());
                for (int j = 0; j < jsd.length(); j++) {
                    JSONObject ksw = jsd.getJSONObject(j);
                    Iterator<String> iter = ksw.keys();

                    Log.d("ksw.keys()", "" + ksw.keys());

                    while (iter.hasNext()) {
                        String key = iter.next();
                        try {
                            //nature.getistopUp().get(j).setQty("" + qty);
                            ksw.getJSONArray(key).put(4, "" + qty);

                        } catch (Exception e) {

                        }
                    }
                    jsd.put(j, ksw);
                }
                for (int i = 0; i <nature.getistopUp().size() ; i++) {
                    nature.getistopUp().get(i).setQty("" + qty);
                }





                jsArryfor.getJSONObject(posTin).put("variable", jsd);
                Utils.saveJSONArray(context, "totalArry", "" + Utils.cusOn, jsArryfor);
                notifyDataSetChanged();
                MainMenuActivity.finalCount(context);

            } else {
                Utils.saveJSONArray(context, "totalArry", "" + Utils.cusOn, jsArryfor);
                MainMenuActivity.finalCount(context);
            }


        } catch (Exception e) {

        }


    }

    public void mthod() {
        try {
            try {
                JSONArray jsArryfor = Utils.loadJSONArray(context, "totalArry", "" + 1);
                Utils.saveJSONArray(context, "totalArry", "" + 1, Utils.UpdateremJSONArray(jsArryfor, xPodt, Utils.xTo));
                Log.d("notifiy1222222", "" + Utils.loadJSONArray(context, "totalArry", "" + 1));
                MainMenuActivity.finalCount(context);
            } catch (JSONException je) {

            }

            TotalClass nature = mItems.get(xPodt);

            //   nature.setremark(Utils.xTo);
            notifyDataSetChanged();
        } catch (Exception e) {
        }

//        int tx=Utils.xpTo;
//        String xyz= Utils.xTo;
//        Log.d("same eeee",""+tx);
//        Log.d("string name eeee", "" +xyz);

    }

    private void alT(final int postion) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.confrcutome_dilg);

        TextView headerText = (TextView) dialog.findViewById(R.id._hdr);
        TextView msgText = (TextView) dialog.findViewById(R.id._dtl);
        headerText.setText("" + "Delete Item");
        msgText.setText("" + " Are you sure you want to delete this Item ?     ");


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
                dialog.dismiss();
            }
        });
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONArray jsArryfor = new JSONArray();
                int wxz = 0;

                try {
                    if (MainMenuBaseActivity.xMainfor == -1 || MainMenuBaseActivity.xMainfor == 0) {
                        Utils.posTo += -1;
                        jsArryfor = Utils.loadJSONArray(context, "totalArry", "" + 1);
                        Log.d("notifiy", "" + Utils.RemoveJSONArray(jsArryfor, postion));
                        Utils.saveJSONArray(context, "totalArry", "" + 1, Utils.RemoveJSONArray(jsArryfor, postion));
                        mItems.remove(postion);
                        MainMenuActivity.finalCount(context);
                        notifyItemRemoved(postion);
                        notifyItemRangeChanged(postion, mItems.size());
                    } else {
                        Log.d("notifiy", MainMenuBaseActivity.xMainfor + "poist" + postion);
                        int xPost = (postion - MainMenuBaseActivity.xMainfor);

                        Utils.posTo += -1;

                        jsArryfor = Utils.loadJSONArray(context, "totalArry", "" + 1);
                        Log.d("notifiy", "" + Utils.RemoveJSONArray(jsArryfor, xPost));
                        Utils.saveJSONArray(context, "totalArry", "" + 1, Utils.RemoveJSONArray(jsArryfor, xPost));
                        mItems.remove(postion);


                        MainMenuActivity.finalCount(context);

                        notifyItemRemoved(postion);
                        notifyItemRangeChanged(postion, mItems.size());
                    }
                    xctt = 0.00;
                    for (int i = 0; i < mItems.size(); i++) {
//
                        TotalClass nature = mItems.get(i);
                        Double qTy = Double.parseDouble(nature.getQty());
                        txduble.add((Double.parseDouble(nature.getprice()) * qTy));
                        xctt += (Double.parseDouble(nature.getprice()) * qTy);
                    }
                    MainMenuActivity.txtmrk(String.format("%.2f", xctt));

                } catch (Exception e) {
                    Utils.posTo += 1;

                }


                dialog.dismiss();
            }
        });


        dialog.show();


    }

}







