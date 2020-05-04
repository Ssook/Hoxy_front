package org.techtown.hoxy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CommentDetailActivity extends AppCompatActivity{
    CommentItem item;
    CommentAdapter adapter;
    String comment;
    EditText othersComment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_detail);

        TextView userId = (TextView) findViewById(R.id.useridView);
        TextView title = (TextView) findViewById(R.id.title);
        TextView title2 = (TextView) findViewById(R.id.title2);
        ImageView userImage = (ImageView) findViewById(R.id.userImage);
        Button backButton = (Button) findViewById(R.id.goToAllViewButton);
        ImageButton writeButton = (ImageButton) findViewById(R.id.writeButton);
        othersComment = (EditText) findViewById(R.id.othersComment);




        item = (CommentItem) getIntent().getSerializableExtra("item");

        Toast.makeText(getApplicationContext(),"들어와쏭",Toast.LENGTH_LONG).show();

        userId.setText( item.getUserId());
        title.setText( item.getComment());
        userImage.setImageResource( item.getResId());

        title2.setText(item.getComment());
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ///////listview
        ListView listView = (ListView) findViewById(R.id.detailCommentList);

        adapter = new CommentAdapter();
        listView.setAdapter(adapter);

        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment = othersComment.getText().toString();
                adapter.addItem(new CommentItem(R.drawable.user1,comment,"김성수"));
                adapter.notifyDataSetChanged();
                othersComment.setText(null);

            }
        });


    }
    class CommentAdapter extends BaseAdapter {

        ArrayList<CommentItem> items = new ArrayList<CommentItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(CommentItem item){
            items.add(item);

        }
        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            CommentItemView view = null;
            if(convertView == null){
                view = new CommentItemView(getApplicationContext());

            }
            else{
                view = (CommentItemView) convertView;

            }
            CommentItem item = items.get(position);
            view.setUserId(item.getUserId());
            view.setImage(item.getResId());
            view.setComment(item.getComment());
            //view.setTime(item.getTime());

            return view;
        }
    }
}
