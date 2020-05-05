
package org.techtown.hoxy;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class PostItem  implements Serializable {
    private int resId;
    private String comment;
    private String userId;
    private String image;
    private int postNum;
    private String title;

    public PostItem(int resId, String title, String userId, int postNum, String reg_date) {
        this.resId = resId;
        //this.comment = comment;
        this.userId = userId;
        // this.image = image;
        this.postNum = postNum;
        this.title = title;
    }
    public PostItem(int resId, String comment, String userId, int postNum, String title,String reg_date) {
        this.resId = resId;
        this.comment = comment;
        this.userId = userId;
       // this.image = image;
        this.postNum = postNum;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPostNum() {
        return postNum;
    }

    public void setPostNum(int postNum) {
        this.postNum = postNum;
    }

    public JSONObject PostToJSON(){
        JSONObject jsonobj=new JSONObject();
        try {
            jsonobj.put("id",this.getUserId());
            jsonobj.put("comment",this.getComment());
            jsonobj.put("resId",this.getResId());
            jsonobj.put("image",this.getImage());
            jsonobj.put("postNum",this.getPostNum());
            jsonobj.put("title",this.getTitle());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonobj;
    }
}