package pos.sd.omakasa.Modelclass;

import android.graphics.Bitmap;

import java.util.ArrayList;


/**
 * Created by jabbir on 26/5/15.
 */
public class EndangeredItem {
    private String mName;
    private String vName;
    private String setName;
    private String descr;
    private Bitmap mThumbnail;
    private String mPrice;
    private String userId;
    private String mil;
    private String itemId;
    private String Buttonid;
    private ArrayList<NamesTopup>vrTble;

    private String btnPage;

    public String getitemId() {
        return itemId;
    }

    public void setitemId(String itemId) {
        this.itemId = itemId;
    }

    public ArrayList<NamesTopup> getvrblset() {
        return vrTble;
    }

    public void setvrblset(ArrayList<NamesTopup> istopUp) {
        this.vrTble = istopUp;
    }


    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }


    public String getmil() {
        return mil;
    }



    public void setbtnPage(String btnPage) {
        this.btnPage = btnPage;
    }



    public String getbtnPage() {
        return btnPage;
    }



    public void setmil(String mil) {
        this.mil = mil;
    }

    public Bitmap getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.mThumbnail = thumbnail;
    }

    public String getuserId() {
        return userId;
    }

    public void setuserId(String userId) {
        this.userId = userId;
    }


    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        this.mPrice = price;
    }

    public String getvName() {
        return vName;
    }

    public void setvName(String vname) {
        this.vName = vname;
    }

    public String getsetName() {
        return setName;
    }

    public void setsetName(String setName) {
        this.setName = setName;
    }


    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }
}
