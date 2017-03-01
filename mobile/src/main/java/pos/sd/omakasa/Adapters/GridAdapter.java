package pos.sd.omakasa.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pos.sd.omakasa.Activity.MainMenuActivity;
import pos.sd.omakasa.Activity.MainMenuBaseActivity;
import pos.sd.omakasa.Modelclass.EndangeredItem;
import pos.sd.omakasa.Modelclass.NamesTopup;
import pos.sd.omakasa.R;
import pos.sd.omakasa.Utils.RecyclerItemClickListener;
import pos.sd.omakasa.Utils.Utils;

/**
 * Created by jabbir on 29/5/15.
 */
public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {
    Context context;
    private JSONObject js = new JSONObject();
    private Animation bottomUp;
    int xyzz;
    private int Supoist;
    private int xPostu;
    private List<EndangeredItem> mItems;
    private JSONObject jdObj;
    private ArrayList<Boolean> status = new ArrayList<Boolean>();
    private ArrayList<Boolean> statuTsts = new ArrayList<Boolean>();
    private Bitmap con;
    private EndangeredItem naturetst;
    private List<String> xyz = new ArrayList<>();
    private HashMap<String, ArrayList<String>> aHash = new HashMap<String, ArrayList<String>>();
    private ArrayList<String> editTextLi = new ArrayList<String>();
    private JSONArray jsdR = new JSONArray();
    private int xyzData = -1;
    private HolderClickListener mHolderClickListener;
    private HashMap<Integer,ArrayList<Boolean>> xcdArraylist=new HashMap<Integer, ArrayList<Boolean>>();
    public GridAdapter(Context context, ArrayList<EndangeredItem> layoutResourceId, int xPostu) {
        super();
        this.mItems = new ArrayList<EndangeredItem>();
        this.context = context;
        this.xPostu = xPostu;
        mItems = layoutResourceId;
        xcdArraylist=new HashMap<>();
        statuTsts = new ArrayList<Boolean>();
        for (int i = 0; i < mItems.size(); i++) {

            statuTsts.add(false);

        }


//        Collections.sort(mItems, new Comparator<EndangeredItem>() {
//            @Override
//            public int compare(EndangeredItem lhs, EndangeredItem rhs) {
//                return lhs.getName().compareTo(rhs.getbtnPage());
//            }
//        });


//        bottomUp = AnimationUtils.loadAnimation(context,
//                R.anim.slide_in_up);
        con = Utils.decodeSampledBitmapFromResource(context.getResources(), R.drawable.nopic, 100, 100);


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.new_home_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        EndangeredItem nature = mItems.get(i);


        if ((i & 1) != 0) {
            viewHolder.tfffrxt.setVisibility(View.GONE);
            viewHolder.trxt.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tfffrxt.setVisibility(View.VISIBLE);
            viewHolder.trxt.setVisibility(View.GONE);
        }

//        if (i == xyzData) {
//            viewHolder.detail.setClickable(true);
//            viewHolder.txxtt.setVisibility(View.GONE);
//            viewHolder.addOrd.setTextColor(context.getResources().getColor(R.color.selected_txt));
//            viewHolder.detail.setTextColor(context.getResources().getColor(R.color.white));
////            viewHolder.detail.setOnClickListener(clickListener);
//
//            //addvr.setOnClickListener(clickListener);
//
//        } else if (xyzData == -1) {
//            viewHolder.addOrd.setTextColor(context.getResources().getColor(R.color.white));
//            viewHolder.detail.setTextColor(context.getResources().getColor(R.color.white));
//            viewHolder.addOrd.setClickable(true);
//            viewHolder.txxtt.setVisibility(View.GONE);
//            //viewHolder.crdView.setCardBackgroundColor(Color.TRANSPARENT);
//            viewHolder.detail.setClickable(true);
//
//        } else {
//            viewHolder.detail.setTextColor(context.getResources().getColor(R.color.white));
//            viewHolder.addOrd.setTextColor(context.getResources().getColor(R.color.selected_txt));
//            viewHolder.addOrd.setClickable(false);
//            viewHolder.txxtt.setVisibility(View.VISIBLE);
//            viewHolder.detail.setClickable(false);
//           // viewHolder.crdView.setCardBackgroundColor(context.getResources().getColor(R.color.prry));
//        }



        if (nature.getuserId() != null) {
            viewHolder.imgThumbnail.setVisibility(View.VISIBLE);
            if (nature.getThumbnail() == null)
                viewHolder.imgThumbnail.setImageBitmap(con);
            else
                viewHolder.imgThumbnail.setImageBitmap(nature.getThumbnail());
            List<String> ts = new ArrayList<String>();
            ts = Utils.readList(context, "Main_Cat");
            String example = nature.getuserId();
            viewHolder.mtop.setVisibility(View.GONE);

        } else {


            if ((nature.getvName().equalsIgnoreCase("no"))) {
                viewHolder.addOrd.setVisibility(View.VISIBLE);
                viewHolder.detail.setVisibility(View.GONE);
            } else {
                viewHolder.detail.setVisibility(View.VISIBLE);
                viewHolder.addOrd.setVisibility(View.GONE);
            }
            viewHolder.imgThumbnail.setVisibility(View.VISIBLE);
            viewHolder.img_txtc.setVisibility(View.VISIBLE);
            viewHolder.mtop.setVisibility(View.VISIBLE);
            viewHolder.tvspecies.setText(nature.getName());
            viewHolder.desc.setText(nature.getDescr());
            Bitmap bitmap = Utils.decodeBase64(context, MainMenuBaseActivity.xyzHash.get(nature.getitemId()));
            if (bitmap == null)
                viewHolder.imgThumbnail.setImageBitmap(con);
            else
                viewHolder.imgThumbnail.setImageBitmap(bitmap);
            Double value = Double.parseDouble(nature.getPrice().toString());
            viewHolder.img_txtc.setText(String.format("%.2f", value));
        }
//        if(statuTsts.get(i)){
//
//        }
//        else{
//
//                viewHolder.detail.setVisibility(View.INVISIBLE);
//                viewHolder.addOrd.setVisibility(View.INVISIBLE);
//        }





//

    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgThumbnail;
        public TextView trxt, tfffrxt;
        public pos.sd.omakasa.Utils.TextViewPlus img_txtc;
        private pos.sd.omakasa.Utils.TextViewPlus tvspecies;
        private pos.sd.omakasa.Utils.TextViewPlus desc;
        private RecyclerView mChairList;
        private RecyclerView.Adapter ChAdapter;
        private RecyclerView.LayoutManager lmutManager;
        private LinearLayout btmlin;
        public Button addOrd, detail, addvr;
        public LinearLayout mtop;
        public  TextView txxtt;
        public CardView crdView;



        public ViewHolder(final View itemView) {
            super(itemView);
            imgThumbnail = (ImageView) itemView.findViewById(R.id.img);
            tvspecies = (pos.sd.omakasa.Utils.TextViewPlus) itemView.findViewById(R.id.tiitl);
            img_txtc = (pos.sd.omakasa.Utils.TextViewPlus) itemView.findViewById(R.id.pric);
            addOrd = (Button) itemView.findViewById(R.id.addiy);
            trxt = (TextView) itemView.findViewById(R.id.trxt);
            desc = (pos.sd.omakasa.Utils.TextViewPlus) itemView.findViewById(R.id.desc);
            tfffrxt = (TextView) itemView.findViewById(R.id.tfffrxt);
            detail = (Button) itemView.findViewById(R.id.select);
            addvr = (Button) itemView.findViewById(R.id.addy);
            mtop = (LinearLayout) itemView.findViewById(R.id.top);
            btmlin = (LinearLayout) itemView.findViewById(R.id.btmlin);
            mChairList = (RecyclerView) itemView.findViewById(R.id.itmvr);
            crdView = (CardView) itemView.findViewById(R.id.crd1);
            mChairList.setHasFixedSize(true);
            // txxtt=(TextView)itemView.findViewById(R.id.txxttx);
            lmutManager = new GridLayoutManager((Activity) context, 2);
            // mChairList.setLayoutManager(lmutManager);
            LinearLayoutManager layManager = new LinearLayoutManager((Activity) context);
            layManager.setOrientation(LinearLayoutManager.VERTICAL);
            mChairList.setLayoutManager(lmutManager);
            final Typeface face = Typeface.createFromAsset(context.getAssets(),
                    "avenri_bold.ttc");

            addvr.setTypeface(face);
            detail.setTypeface(face);
            addOrd.setTypeface(face);

            final Animation slide_down = AnimationUtils.loadAnimation(context,
                    R.anim.slide_down);

            final Animation slide_up = AnimationUtils.loadAnimation(context,
                    R.anim.slide_up);
            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.select:


                            if (btmlin.getVisibility() == View.VISIBLE) {
                                xcdArraylist.remove(getAdapterPosition());
                                //btmlin.startAnimation(slide_down);
                                btmlin.setVisibility(View.GONE);
                                detail.setBackground(context.getResources().getDrawable(R.drawable.r_corner_black));
                                detail.setTextColor(context.getResources().getColor(R.color.white));
                                xyzData = -1;
                                notifyDataSetChanged();
                            } else {
                               // ViewHolder holder = (ViewHolder )(itemView.getTag());
                                MainMenuActivity tst=new MainMenuActivity();
                                // MainMenuActivity.();
                                Supoist = getPosition();
                                xyzData = getAdapterPosition();

                                detail.setBackground(context.getResources().getDrawable(R.drawable.r_corner_black));
                                detail.setTextColor(context.getResources().getColor(R.color.selected_txt));
                                btmlin.setVisibility(View.VISIBLE);
                                // btmlin.startAnimation(slide_up);
                                naturetst = mItems.get(getAdapterPosition());
                                ChAdapter = new VariabListAdapter((Activity) context, naturetst.getvrblset(), getAdapterPosition());
                                mChairList.setAdapter(ChAdapter);
                                status = new ArrayList<Boolean>();
                                for (int i = 0; i < naturetst.getvrblset().size(); i++) {
                                    NamesTopup nare = naturetst.getvrblset().get(i);
                                    if (nare.getcdef_flag().equalsIgnoreCase("M"))
                                        status.add(true);
                                    else
                                        status.add(false);
                                }
                                xcdArraylist.put(xyzData,status);



                               chkItem(naturetst.getvrblset(),""+getAdapterPosition());

                                mChairList.setItemAnimator(new DefaultItemAnimator());
                                mChairList.addOnItemTouchListener(
                                        new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(final View view, final int position) {
                                                xyzz = position;

                                                final CheckedTextView xyBtn = ((CheckedTextView) view.findViewById(R.id.vrableName));
                                                xyBtn.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        Log.d("ggggg", naturetst.getvrblset().get(position).getgrpName());

                                                        if (naturetst.getvrblset().get(position).getgrpName().startsWith("SINGLE")) {
                                                            Log.d("cae in", "" + xyBtn.isSelected());
                                                            if (xyBtn.isSelected()) {
                                                                for (int k = 0; k < mChairList.getChildCount(); k++) {
                                                                    View vrr = mChairList.getChildAt(k);
                                                                    final CheckedTextView child = (CheckedTextView) vrr.findViewById(R.id.vrableName);
                                                                    child.setSelected(false);
                                                                    child.setChecked(false);
                                                                    status.set(k, false);
                                                                    NamesTopup nare = naturetst.getvrblset().get(k);
                                                                    if (nare.getcdef_flag().equalsIgnoreCase("M"))
                                                                        status.add(k, true);

                                                                }




//                                                                xyBtn.setSelected(false);
//                                                                xyBtn.setChecked(false);
//                                                                xyBtn.setTextColor(context.getResources().getColor(R.color.tgraddy));
//                                                                for (int i = 0; i < status.size(); i++) {
//                                                                        status.set(i, false);}
//
//                                                                for (int i = 0; i < mChairList.getChildCount(); i++) {
//                                                                 View  child = mChairList.getChildAt(i);
//                                                                    child.setSelected(false);
//                                                                    status.set(i, false);
//                                                                    //child.setChecked(false);
//                                                                }

                                                            } else {

                                                                // vcx.set(getPosition(), "1");
                                                                for (int k = 0; k < mChairList.getChildCount(); k++) {
                                                                    View vrr = mChairList.getChildAt(k);
                                                                    final CheckedTextView child = (CheckedTextView) vrr.findViewById(R.id.vrableName);
                                                                    child.setSelected(false);
                                                                    child.setChecked(false);
                                                                    status.set(k, false);
                                                                    NamesTopup nare = naturetst.getvrblset().get(k);
                                                                    if (nare.getcdef_flag().equalsIgnoreCase("M"))
                                                                        status.set(k, true);

                                                                }

                                                                final CheckedTextView child = (CheckedTextView) view.findViewById(R.id.vrableName);
                                                                child.setSelected(true);
                                                                child.setChecked(true);
                                                                status.set(xyzz, true);


//                                                                xyBtn.setSelected(true);
//                                                                xyBtn.setChecked(true);
//                                                                xyBtn.setTextColor(context.getResources().getColor(R.color.red));
//                                                                //Log.d("edit2222 text", "" + vcx);
//                                                                for (int i = 0; i < status.size(); i++) {
//                                                                    if(i==xyzz)
//                                                                    status.set(xyzz, true);
//                                                                   else
//                                                                        status.set(i,false);
//                                                            }
                                                            }
                                                            xcdArraylist.put(getAdapterPosition(),status);

                                                        } else {
                                                            if (xyBtn.isSelected()) {
                                                                xyBtn.setSelected(false);
                                                                xyBtn.setChecked(false);
                                                                xyBtn.setTextColor(context.getResources().getColor(R.color.tgraddy));
                                                                for (int i = 0; i < status.size(); i++) {
                                                                    if (xyzz == i) {
                                                                        status.set(xyzz, false);
                                                                    }

                                                                }
                                                                //xcdArraylist.put(""+getAdapterPosition(),status);

                                                            } else {
                                                                xyBtn.setSelected(true);
                                                                xyBtn.setChecked(true);
                                                                xyBtn.setTextColor(context.getResources().getColor(R.color.red));
                                                                // vcx.set(getPosition(), "1");
                                                                //Log.d("edit2222 text", "" + vcx);
                                                                for (int i = 0; i < status.size(); i++) {
                                                                    if (xyzz == i) {
                                                                        status.set(xyzz, true);
                                                                    }

                                                                }
                                                                //xcdArraylist.put(""+getAdapterPosition(),status);
                                                            }

                                                        }
                                                        xcdArraylist.put(getAdapterPosition(),status);
                                                       // xcdArraylist.put(""+getAdapterPosition(),status);
                                                      // chkItem(naturetst.getvrblset(),""+getAdapterPosition());
                                                        notifyDataSetChanged();

                                                    }


                                                });

                                            }
                                        }));

                            }
                            break;
                        case R.id.addy:
                            Utils.tstMap=new HashMap<>();
                            int[] start_location = new int[2];
                            imgThumbnail.getLocationInWindow(start_location);//获取点击商品图片的位置
                            Drawable drawable =  imgThumbnail.getDrawable();//复制一个新的商品图标

                            Log.d("Start Location",""+start_location[1]);
                            //Utils.tstMap.put(drawable,start_location);

                            ((MainMenuActivity)context).mthdanimation(drawable,start_location);

                            // if()

                            // mHolderClickListener.onHolderClick(drawable,start_location);


                            // Log.i("Varaiable seleted", "" + VariabListAdapter.tsInt().get(0) + " lenght" + VariabListAdapter.tsInt().size());
                            addvr.setBackground(context.getResources().getDrawable(R.drawable.r_corner));
                            addvr.setText(context.getString(R.string.added));
                            new CountDownTimer(1000, 1000) {
                                public void onTick(long ms) {
                                }

                                public void onFinish() {
                                    xyzData = -1;
                                    addvr.setBackground(context.getResources().getDrawable(R.drawable.r_corner_white_yes));
                                    addvr.setText(context.getString(R.string.additm));
                                    btmlin.setVisibility(View.GONE);
                                    detail.setTextColor(context.getResources().getColor(R.color.white));
                                    detail.setBackground(context.getResources().getDrawable(R.drawable.r_corner_black));
                                    notifyDataSetChanged();
                                }
                            }.start();


                            try {

//                                ArrayList<NamesTopup> neItm=new ArrayList<>();
//                                neItm = mItems.get(getAdapterPosition()).getvrblset();
//                                chkItem( neItm,""+ getAdapterPosition());

                                ArrayList<Boolean>statuBln=new ArrayList<>();
                                statuBln=xcdArraylist.get(getAdapterPosition());

                                ArrayList<NamesTopup> neItem=new ArrayList<>();
                                neItem = mItems.get(getAdapterPosition()).getvrblset();

                                jdObj = new JSONObject();
                                jsdR = new JSONArray();
                                try {
                                    aHash = new HashMap<String, ArrayList<String>>();
                                    for (int k = 0; k < statuBln.size(); k++) {
                                        if (statuBln.get(k)) {
                                            editTextLi = new ArrayList<String>();
                                            NamesTopup nature = neItem.get(k);
                                            editTextLi.add(nature.getName());
                                            editTextLi.add(nature.getPrice());
                                            editTextLi.add(nature.getcitem_id());
                                            editTextLi.add(nature.getcseq_no());
                                            editTextLi.add("" + 1);
                                            editTextLi.add(nature.getcuom());
                                            editTextLi.add("");
                                            editTextLi.add(nature.getcdef_flag());
                                            aHash.put("" + k, editTextLi);
                                            Log.d("keyseyf", "" + k);
                                        }
                                    }

                                    jdObj = new JSONObject(aHash);
                                }
                                catch (Exception e){

                                }


                                Log.d("adddddddddd",getAdapterPosition()+"rrrrr"+jdObj);




                                JSONArray jsN = new JSONArray();

                                JSONObject js = new JSONObject();
                                js.put(Utils.mPar, "" + 0);
                                js.put(Utils.subPar, "" + getAdapterPosition());
                                js.put(Utils.qtyPar, "" + 1);
                                js.put(Utils.remPar, "");
                                jsN.put(jdObj);
                                js.put(Utils.varPar, jsN);
                                addJsn(js);


                            } catch (Exception e) {

                            }


                            break;


                    }


                }
            };

            detail.setOnClickListener(clickListener);

            addvr.setOnClickListener(clickListener);

        }
    }

    private void chkItem(ArrayList<NamesTopup> neItems,String adptr) {
        JSONObject jh = new JSONObject();
        String xNu = "null";
        ArrayList<Boolean>statuBln=new ArrayList<>();
        statuBln=xcdArraylist.get(adptr);

        ArrayList<NamesTopup> neItem=new ArrayList<>();
        neItem = mItems.get(Integer.parseInt(adptr)).getvrblset();

        jdObj = new JSONObject();
        jsdR = new JSONArray();
        try {
            aHash = new HashMap<String, ArrayList<String>>();
            for (int k = 0; k < statuBln.size(); k++) {
                if (statuBln.get(k)) {
                    editTextLi = new ArrayList<String>();
                    NamesTopup nature = neItem.get(k);
                    editTextLi.add(nature.getName());
                    editTextLi.add(nature.getPrice());
                    editTextLi.add(nature.getcitem_id());
                    editTextLi.add(nature.getcseq_no());
                    editTextLi.add("" + 1);
                    editTextLi.add(nature.getcuom());
                    editTextLi.add("");
                    editTextLi.add(nature.getcdef_flag());
                    aHash.put("" + k, editTextLi);
                    Log.d("keyseyf", "" + k);
                }
            }

            jdObj = new JSONObject(aHash);
            Log.d("@jdObjVariable11111", "" + jdObj);
        } catch (Exception e) {

        }
    }
    public void SetOnSetHolderClickListener(HolderClickListener holderClickListener){
        this.mHolderClickListener = holderClickListener;
    }
    public interface HolderClickListener{
        public void onHolderClick(Drawable drawable,int[] start_location);
    }
    private void addJsn(JSONObject jADD) {
        try {
            if (Utils.loadJSONArray(context, "totalArry", "" + 1).equals("[]")) {
                JSONArray jsd = new JSONArray();
                jsd.put(jADD);
                Utils.saveJSONArray(context, "totalArry", "" + 1, jsd);
                Log.d("@@@@@@@@33", "" + Utils.loadJSONArray(context, "totalArry", "" + 1));
            } else {
                JSONArray jsw = new JSONArray();
                jsw = Utils.loadJSONArray(context, "totalArry", "" + 1);
                Log.d("@@@@@@@@33", "" + Utils.loadJSONArray(context, "totalArry", "" + 1));
                Log.d("@@@@@@@@33iiiii", "" + jADD);
                jsw.put(jADD);
                Utils.saveJSONArray(context, "totalArry", "" + 1, jsw);
                //}

                MainMenuActivity.finalCount(context);
                Log.d("@@@@@@@@test", "" + Utils.loadJSONArray(context, "totalArry", "" + 1));

            }
            //  animate(1);
        } catch (Exception e) {

        }
    }


}
