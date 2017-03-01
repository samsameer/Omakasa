package pos.sd.omakasa.Modelclass;

import android.graphics.Bitmap;

/**
 * Created by jabbir on 8/6/15.
 */
public class SetDataTopup {

//    "citem_id": 290,
//            "citem_no": "BVWBWW0002",
//            "citem_desc": "WINE - ILLUMINATI  MONTEPULCIANO D' ABRUZZO RIPAROSSO",
//            "cuom": "BOT",
//            "cqty": "1.000000",
//            "cf_qty": "1.000000"


    private String citem_id;
    private Bitmap mThumbnail;
    private String citem_no;
    private String cqty;
    private String cf_qty;
    private String cuom;
    private String citem_desc;
    private String cremarks;

    public String getcitem_id() {
        return citem_id;
    }

    public void setcitem_id(String citem_id) {
        this.citem_id = citem_id;
    }

    public Bitmap getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.mThumbnail = thumbnail;
    }

    public String getcqty() {
        return cqty;
    }

    public void setcqty(String cqty) {
        this.cqty = cqty;
    }

    public String getcf_qty() {
        return cf_qty;
    }

    public void setcf_qty(String cf_qty) {
        this.cf_qty = cf_qty;
    }

    public String getcuom() {
        return cuom;
    }

    public void setcuom(String cuom) {
        this.cuom = cuom;
    }

    public String getcitem_desc() {
        return citem_desc;
    }

    public void setcitem_desc(String citem_desc) {
        this.citem_desc = citem_desc;
    }

//    public String getQty() {
//        return Qty;
//    }
//
//    public void setQty(String Qty) {
//        this.Qty = Qty;
//    }
//
//    public String getgrpName() {
//        return grpName;
//    }
//
//    public void setgrpName(String grpName) {
//        this.grpName = grpName;
//    }

    public String getcitem_no() {
        return citem_no;
    }

    public void setcitem_no(String citem_no) {
        this.citem_no = citem_no;
    }

//
//    public String getcitem_id() {
//        return citem_id;
//    }
//
//    public void setcitem_id(String citem_id) {
//        this.citem_id = citem_id;
//    }


    public String getcremarks() {
        return cremarks;
    }

    public void setcremarks(String cremarks) {
        this.cremarks = cremarks;
    }
}
