package org.techtown.hoxy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class CommentDetailActivity extends AppCompatActivity{
    CommentItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_detail);

        TextView userId = (TextView) findViewById(R.id.useridView);
        TextView title = (TextView) findViewById(R.id.title);
        TextView title2 = (TextView) findViewById(R.id.title2);
        ImageView userImage = (ImageView) findViewById(R.id.userImage);
        Button button = (Button) findViewById(R.id.goToAllViewButton);

        item = (CommentItem) getIntent().getSerializableExtra("item");

        Toast.makeText(getApplicationContext(),"들어와쏭",Toast.LENGTH_LONG).show();

        userId.setText( item.getUserId());
        title.setText( item.getComment());
        userImage.setImageResource( item.getResId());

        title2.setText(item.getComment());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
