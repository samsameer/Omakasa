package pos.sd.omakasa.Modelclass;

import android.graphics.Bitmap;

/**
 * Created by jabbir on 8/6/15.
 */

//"cseq_no": 3,
//        "citem_id": 78,
//        "citem_no": "FCSIN003L",
//        "citem_desc": "TRUFFLE FRIES",
//        "cuom": "SRV",
//        "cqty": "1.000000",
//        "cf_qty": "1.000000",
//        "cprice": "0.000000",
//        "cdef_flag": "N",
//        "cgseq_no": 3,
//        "cgroup": "SELECTION",
//        "c_image": "http://192.168.0.174:80/resources/01_78.txt"
public class NamesTopup {
    private String citem_desc;
    private Bitmap c_image;
    private String cprice;
    private String cqty;
    private String grpName;
    private String cdef_flag;
    private String cseq_no;
    private String citem_no;
    private String citem_id;
    private String cuom;
    private String cremarks;

    public String getName() {
        return citem_desc;
    }

    public void setName(String name) {
        this.citem_desc = name;
    }

    public Bitmap getThumbnail() {
        return c_image;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.c_image = thumbnail;
    }

    public String getPrice() {
        return cprice;
    }

    public void setPrice(String price) {
        this.cprice = price;
    }

    public String getcdef_flag() {
        return cdef_flag;
    }

    public void setcdef_flag(String cdef_flag) {
        this.cdef_flag = cdef_flag;
    }

    public String getcuom() {
        return cuom;
    }

    public void setcuom(String cuom) {
        this.cuom = cuom;
    }

    public String getcseq_no() {
        return cseq_no;
    }

    public void setcseq_no(String cseq_no) {
        this.cseq_no = cseq_no;
    }

    public String getQty() {
        return cqty;
    }

    public void setQty(String Qty) {
        this.cqty = Qty;
    }

    public String getgrpName() {
        return grpName;
    }

    public void setgrpName(String grpName) {
        this.grpName = grpName;
    }

    public String getcitem_no() {
        return citem_no;
    }

    public void setcitem_no(String citem_no) {
        this.citem_no = citem_no;
    }


    public String getcitem_id() {
        return citem_id;
    }

    public void setcitem_id(String citem_id) {
        this.citem_id = citem_id;
    }


    public String getcremarks() {
        return cremarks;
    }

    public void setcremarks(String cremarks) {
        this.cremarks = cremarks;
    }
}
