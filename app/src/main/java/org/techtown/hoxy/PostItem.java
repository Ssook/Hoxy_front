
package org.techtown.hoxy;

import java.io.Serializable;

public class PostItem  implements Serializable {
    int resId;
    String comment;
    String userId;
    String image;
    int postNum;
    String title;

    public PostItem(int resId, String title, String userId, int postNum) {
        this.resId = resId;
        //this.comment = comment;
        this.userId = userId;
        // this.image = image;
        this.postNum = postNum;
        this.title = title;
    }
    public PostItem(int resId, String comment, String userId, int postNum, String title) {
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
        title = title;
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
}