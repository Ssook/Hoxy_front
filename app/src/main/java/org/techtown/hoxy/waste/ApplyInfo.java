package org.techtown.hoxy.waste;

import org.json.JSONException;
import org.json.JSONObject;

public class ApplyInfo {


    private String user_No;
    private int apply_fee;
    private String address;
    private String phone_No;
    private int waste_No;
    private String img_Url;

    public ApplyInfo(String user_No, int apply_fee, String address, String phone_No, int waste_No, String img_Url) {
        this.user_No = user_No;
        this.apply_fee = apply_fee;
        this.address = address;
        this.phone_No = phone_No;
        this.waste_No = waste_No;
        this.img_Url = img_Url;
    }

    public String getUser_No() {
        return user_No;
    }

    public int getApply_fee() {
        return apply_fee;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone_No() {
        return phone_No;
    }

    public int getWaste_No() {
        return waste_No;
    }

    public String getImg_Url() {
        return img_Url;
    }

    public void setUser_No(String user_No) {
        this.user_No = user_No;
    }

    public void setApply_fee(int apply_fee) {
        this.apply_fee = apply_fee;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone_No(String phone_No) {
        this.phone_No = phone_No;
    }

    public void setWaste_No(int waste_No) {
        this.waste_No = waste_No;
    }

    public void setImg_Url(String img_Url) {
        this.img_Url = img_Url;
    }

    public JSONObject applyInfoToJSON(){
        JSONObject jsonobj=new JSONObject();

        try {
            jsonobj.put("user_no",this.getUser_No());
            jsonobj.put("apply_fee",this.getApply_fee());
            jsonobj.put("address",this.getAddress());
            jsonobj.put("phone_no",this.getPhone_No());
            jsonobj.put("waste_no",this.getWaste_No());
            jsonobj.put("img_url",this.getImg_Url());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonobj;
    }

}
