package org.techtown.hoxy;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentItemView extends LinearLayout {

    private TextView userIdView;
    private TextView commentView;
    private CircleImageView userImageView;
    private TextView timeView;

    public CommentItemView(Context context) {
        super(context);
        init(context);
    }

    public CommentItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public void init(Context context){

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.comment_item_view,this,true);

        userIdView = (TextView) findViewById(R.id.useridView);
        commentView = (TextView) findViewById(R.id.comment);
        userImageView = (CircleImageView) findViewById(R.id.userImage);
        timeView = (TextView) findViewById(R.id.time);

    }

    public void setUserId(String userId) {
        userIdView.setText(userId);
    }

    public void setComment(String comment){commentView.setText(comment);}
    public void setImage(int resId) {
        userImageView.setImageResource(resId);
    }
    //public void setTime(String time){}


}
