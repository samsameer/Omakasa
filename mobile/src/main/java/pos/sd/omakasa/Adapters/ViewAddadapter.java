package pos.sd.omakasa.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pos.sd.omakasa.Modelclass.NamesTopup;
import pos.sd.omakasa.R;


/**
 * Created by jabbir on 14/7/15.
 */
public class ViewAddadapter extends RecyclerView.Adapter<ViewAddadapter.ViewHolder> {
    private Context context;
    private List<NamesTopup> neItems;

    public ViewAddadapter(Context context, ArrayList<NamesTopup> posList) {
        super();
        this.context = context;
        this.neItems = posList;
        //notifyItemRangeChanged(0, mItems.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.add_view, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        NamesTopup nature = neItems.get(i);
        viewHolder.chkBox.setText(nature.getName());
        viewHolder.lPrice.setText(nature.getPrice());
        viewHolder.imgRemrk.setText(nature.getcremarks());
        viewHolder.nQty.setText(nature.getQty().replace("$ 0.00","1"));
    }

    @Override
    public int getItemCount() {

        return neItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        // public TextView titleName;
        public TextView lPrice;
        private EditText nQty;
        private TextView chkBox;
        public TextView imgRemrk;

        public ViewHolder(final View itemView) {
            super(itemView);
            imgRemrk = (TextView) itemView.findViewById(R.id.addrenrk);
            chkBox = (TextView) itemView.findViewById(R.id.addch1k);
            lPrice = (TextView) itemView.findViewById(R.id.addtxt2);
            nQty = (EditText) itemView.findViewById(R.id.addnpe1);
           // itemView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.left_right));


//            View.OnClickListener clickListener = new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    switch (v.getId()) {
//
//                        case R.id.addrenrk:
//                            delTetItem(getPosition());
//                            break;
//                    }
//
//                    notifyItemRangeChanged(0, neItems.size());
//                }
//            };

            //lPrice.setOnClickListener(clickListener);

          //  imgRemrk.setOnClickListener(clickListener);

        }
    }

    private void delTetItem(final int postion) {
        NamesTopup nature = neItems.get(postion);
        AlertDialog.Builder alertDel = new AlertDialog.Builder(context);
        alertDel.setMessage(nature.getcremarks());
        alertDel.setTitle("Remark");
        alertDel.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });


        alertDel.show();


    }
}
