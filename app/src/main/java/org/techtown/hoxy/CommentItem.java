package org.techtown.hoxy;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class CommentItem implements Serializable {
    private int resId;
    private String comment;
    private String userId;
    private int commentNum;
    //String time;


    public CommentItem(int resId, String comment,String userId,int commentNum) {
        this.resId = resId;
        this.comment = comment;
        this.userId=userId;
        this.commentNum=commentNum;
       // this.time=time;
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

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public JSONObject userToJSON(){
        JSONObject jsonobj=new JSONObject();
        try {
            jsonobj.put("id",this.getUserId());
            jsonobj.put("comment",this.getComment());
            jsonobj.put("resId",this.getResId());
            jsonobj.put("postNum",this.getCommentNum());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonobj;
    }

}
