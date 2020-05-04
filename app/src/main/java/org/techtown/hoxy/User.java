package org.techtown.hoxy;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private String user_Nickname;
    private String user_ProfileThumbnail;
    private String user_Id;
    private String user_Email;

    public User(String nickname, String thumbnail, String id, String email) {
        this.user_Nickname=nickname;
        this.user_ProfileThumbnail=thumbnail;
        this.user_Id=id;
        this.user_Email=email;
    }

    public void setUserNickname(String nickname){
        this.user_Nickname=nickname;
    }
    public void setUserId(String id){
        this.user_Id=id;
    }
    public void setUserEmail(String email){
        this.user_Email=email;
    }
    public void setUserProfileThumbnail(String thumbnail){
        this.user_ProfileThumbnail=thumbnail;
    }
    public String getUserNickname(){
        return this.user_Nickname;
    }
    public String getUserId(){
        return this.user_Id;
    }
    public String getUserEmail(){
        return this.user_Email;
    }
    public String getUserProfileThumbnail(){
        return this.user_ProfileThumbnail;
    }

    public JSONObject userToJSON(){
        JSONObject jsonobj=new JSONObject();
        try {
            jsonobj.put("id",this.getUserId());
            jsonobj.put("nickname",this.getUserNickname());
            jsonobj.put("email",this.getUserEmail());
            jsonobj.put("thumbnail",this.getUserProfileThumbnail());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonobj;
    }

}
