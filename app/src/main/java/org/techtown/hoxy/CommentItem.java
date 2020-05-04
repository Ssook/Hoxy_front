package org.techtown.hoxy;

import java.io.Serializable;

public class CommentItem implements Serializable {
    int resId;
    String comment;
    String userId;
    int commentNum;
    //String time;


    public CommentItem(int resId, String comment,String userId) {
        this.resId = resId;
        this.comment = comment;
        this.userId=userId;
       // this.time=time;
<<<<<<< HEAD

=======
>>>>>>> 38eee6c6719116e84006524e3c81dbb79439cb34
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

    public int getCommnetNum() {
        return commentNum;
    }

    public void setCodeNum(int commentNum) {
        this.commentNum = commentNum;
    }


}
