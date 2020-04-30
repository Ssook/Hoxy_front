package org.techtown.hoxy;

import java.io.Serializable;

public class CommentItem implements Serializable {
    int resId;
    String comment;
    String userId;
    //String time;


    public CommentItem(int resId, String comment,String userId) {
        this.resId = resId;
        this.comment = comment;
        this.userId=userId;
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

  /*  public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
*/




    @Override
    public String toString() {
        return "CommentItem{" +
                "resId=" + resId +
                ", comment='" + comment + '\'' +
                '}';
    }
}
