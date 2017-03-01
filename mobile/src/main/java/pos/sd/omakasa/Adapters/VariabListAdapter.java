package pos.sd.omakasa.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.CheckedTextView;

import java.util.ArrayList;
import java.util.List;

import pos.sd.omakasa.Modelclass.NamesTopup;
import pos.sd.omakasa.R;

/**
 * Created by jabbir on 29/5/15.
 */
public class VariabListAdapter extends RecyclerView.Adapter<VariabListAdapter.ViewHolder> {
    Context context;

    private Animation bottomUp;
    private int xPostu;
    List<NamesTopup> mItems;
    private Boolean TX = false;
    private ArrayList<Boolean> status = new ArrayList<Boolean>();
    static ArrayList<Integer> selectedItems = new ArrayList<Integer>();

    public VariabListAdapter(Context context, List<NamesTopup> layoutResourceId, int post) {
        super();
        this.mItems = new ArrayList<NamesTopup>();
        this.context = context;
        this.xPostu = post;
        mItems = layoutResourceId;

        for (int i = 0; i < mItems.size(); i++) {
            status.add(false);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.variable, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        NamesTopup nature = mItems.get(i);

        if (nature.getcdef_flag().equalsIgnoreCase("M")) {
            selectedItems = new ArrayList<Integer>();
            selectedItems.add(Integer.parseInt(nature.getcitem_id()));
            TX = true;
            viewHolder.ovrLay.setVisibility(View.GONE);
        } else {
            Typeface face = Typeface.createFromAsset(context.getAssets(),
                    "avenri_bold.ttc");
            viewHolder.ovrLay.setTypeface(face);



            viewHolder.ovrLay.setVisibility(View.VISIBLE);
            String price = nature.getPrice();
            if (price.startsWith("0.0")) {
                viewHolder.ovrLay.setText(nature.getName());
            } else {
                Double txy = Double.parseDouble(price);
                viewHolder.ovrLay.setText(nature.getName() + "+ $" + String.format("%.2f", txy));
            }

            viewHolder.ovrLay.setSelected(status.get(i));
            viewHolder.ovrLay.setChecked(status.get(i));
        }


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
        public CheckedTextView ovrLay;
        public CardView cardView;
        // public ImageView imgrrr;

        public ViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.card);
            ovrLay = (CheckedTextView) itemView.findViewById(R.id.vrableName);
            //imgrrr = (ImageView) itemView.findViewById(R.id.imgrrr);
            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.cname:
//


                            NamesTopup nature = mItems.get(getPosition());
                            if (nature.getgrpName().startsWith("SINGLE")) {
                                if (ovrLay.isSelected()) {
                                    for (int i = 0; i < status.size(); i++) {
                                        ovrLay.setSelected(false);
                                        ovrLay.setTextColor(context.getResources().getColor(R.color.tgraddy));
                                        status.set(i, false);
                                    }
                                } else {
                                    for (int i = 0; i < status.size(); i++) {
                                        if (getPosition() == i) {
                                            ovrLay.setSelected(true);
                                            ovrLay.setTextColor(context.getResources().getColor(R.color.gold));
                                            status.set(getPosition(), true);
                                        }
                                        else{
                                            ovrLay.setSelected(false);
                                            ovrLay.setTextColor(context.getResources().getColor(R.color.tgraddy));
                                            status.set(i, false);
                                        }
                                    }
                                }
//

                            }
                            else {
//                                if (ovrLay.isSelected()) {
//                                    ovrLay.setSelected(false);
//                                    ovrLay.setTextColor(context.getResources().getColor(R.color.tgraddy));
//                                    for (int i = 0; i < status.size(); i++) {
//
//                                        if (getPosition() == i) {
//
//                                        }
//
//                                    }
//                                } else {
                                    if (ovrLay.isSelected()) {
                                        ovrLay.setSelected(false);
                                        ovrLay.setTextColor(context.getResources().getColor(R.color.tgraddy));
                                        for (int i = 0; i < status.size(); i++) {
                                            if (getPosition() == i) {
                                                status.set(getPosition(), false);
                                            } else
                                                status.set(i, false);
                                        }
                                    } else {
                                        ovrLay.setSelected(true);
                                        ovrLay.setTextColor(context.getResources().getColor(R.color.gold));
                                        for (int i = 0; i < status.size(); i++) {
                                            if (getPosition() == i) {
                                                status.set(getPosition(), true);
                                            }
                                        }
                                    }

                                }


                            //nQty.setText("1");
                            //vcx.set(getPosition(), "1");
                            //Log.d("edit text", "" + vcx);
                            //rTextV.setText("");

//                                } else {
//


//                            if (!TX) {
//                                if (ovrLay.isSelected()) {
//                                    ovrLay.setSelected(false);
//                                    ovrLay.setTextColor(context.getResources().getColor(R.color.tgraddy));
//                                    for (int i = 0; i < status.size(); i++) {
//                                        if (getPosition() == i) {
//                                            status.set(getPosition(), false);
//                                        } else
//                                            status.set(i, false);
//
//                                    }
//
//                                    //nQty.setText("1");
//                                    //vcx.set(getPosition(), "1");
//                                    //Log.d("edit text", "" + vcx);
//                                    //rTextV.setText("");
//
//                                } else {
//
//                                    // vcx.set(getPosition(), "1");
//                                    //Log.d("edit2222 text", "" + vcx);
//                                    if (nature.getgrpName().startsWith("SINGLE")) {
//                                        for (int i = 0; i < status.size(); i++) {
//                                            if (getPosition() == i) {
//                                                ovrLay.setSelected(true);
//                                                ovrLay.setTextColor(context.getResources().getColor(R.color.gold));
//                                                status.set(getPosition(), true);
//                                            } else
//                                            {
//                                                status.set(getPosition(), false);
//                                                ovrLay.setSelected(false);
//                                                ovrLay.setTextColor(context.getResources().getColor(android.R.color.black));
//                                            }
//
//                                        }
//                                    } else {
//                                        for (int i = 0; i < status.size(); i++) {
//                                            if (getPosition() == i) {
//                                                status.set(getPosition(), true);
//                                            }
//                                        }
//
//
//                                    }
//                                }
//                            } else {
//
//                                if (nature.getgrpName().startsWith("SINGLE")) {
//                                    for (int i = 0; i < status.size(); i++) {
//                                        if (getPosition() == i) {
//                                            ovrLay.setSelected(true);
//                                            ovrLay.setTextColor(context.getResources().getColor(R.color.gold));
//                                            status.set(getPosition(), true);
//                                        } else {
//                                            status.set(getPosition(), false);
//                                            ovrLay.setSelected(false);
//                                            ovrLay.setTextColor(context.getResources().getColor(android.R.color.black));
//                                        }
//
//                                    }
//
//
//                                }
//
//
//                                ovrLay.setSelected(true);
//                                ovrLay.setTextColor(context.getResources().getColor(R.color.gold));
//
//                                if (nature.getgrpName().startsWith("SINGLE") || nature.getgrpName().startsWith("single")) {
//                                    for (int i = 0; i < status.size(); i++) {
//                                        if (getPosition() == i) {
//                                            status.set(getPosition(), true);
//                                        } else
//                                            status.set(getPosition(), false);
//                                    }
//                                } else {
//
//                                    for (int i = 0; i < status.size(); i++) {
//                                        if (getPosition() == i) {
//                                            status.set(getPosition(), true);
//                                        }
//
//                                    }
//                                }
//                                // vcx.set(getPosition(), "1");
//                                //Log.d("edit2222 text", "" + vcx);
//                            }
//
//                            Log.d("cmeeee5555", "ggggg11");
                            //chkItem();
                            notifyDataSetChanged();
                            break;


                    }

                }
            };
            ovrLay.setOnClickListener(clickListener);
            // cardView.setOnClickListener(clickListener);

        }
    }

    public static ArrayList<Integer> tsInt() {

        return selectedItems;
    }


}
