package org.techtown.hoxy.community;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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


import org.techtown.hoxy.MainActivity;
import org.techtown.hoxy.R;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import org.techtown.hoxy.community.CommentItem;
import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;

public class CommentDetailActivity extends Activity implements Serializable, NavigationView.OnNavigationItemSelectedListener {
    PostItem item;
    private CommentAdapter adapter;
    private String comment;
    private EditText othersComment;
    private ArrayList<CommentItem> items = new ArrayList<CommentItem>();
    DrawerLayout drawer;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    View nav_header_view;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayoutPostWriteActivity();


        TextView userId = (TextView) findViewById(R.id.useridView);
        TextView title = (TextView) findViewById(R.id.title);
        TextView title2 = (TextView) findViewById(R.id.title2);
        ImageView userImage = (ImageView) findViewById(R.id.userImage);
        Button backButton = (Button) findViewById(R.id.goToAllViewButton);
        ImageButton writeButton = (ImageButton) findViewById(R.id.writeButton);
        othersComment = (EditText) findViewById(R.id.othersComment);

        item = (PostItem) getIntent().getSerializableExtra("item");
       /* if(item==null){
            System.out.println("tjdtntlqkf");}
        else {
            System.out.println(getIntent().getSerializableExtra("item")+"tlqkf");
        }*/

        Toast.makeText(getApplicationContext(),"들어와쏭",Toast.LENGTH_LONG).show();

        userId.setText( item.getUserId());
        title.setText( item.getTitle());
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
                adapter.addItem(new CommentItem(R.drawable.user1,comment,"김성수",1));
                adapter.notifyDataSetChanged();
                othersComment.setText(null);

            }
        });


    }
    public void initLayoutPostWriteActivity() {           //레이아웃 정의
        setContentView(R.layout.activity_comment_detail);
        setView_Toolbar();
        setView_NavHeader();
        setView_Drawer();

    }
    private void setView_Drawer() {
        drawer = findViewById(R.id.drawer_layout);

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open , R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setView_NavHeader() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        nav_header_view = navigationView.getHeaderView(0);
        //  nav_header_id_text = (TextView) nav_header_view.findViewById(R.id.user_name);

        //nav_header_id_text.setText(sp.getString("name", ""));
    }

    private void setView_Toolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Community");
        toolbar.setTitleMargin(5, 0, 5, 0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            //글쓰기 완료 후 전환 시 액티비티가 남지 않게 함
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            // intent.putExtra("태그","전체");
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_community) {

        }else if (id == R.id.nav_slideshow) {

        }
        drawer = findViewById(R.id.drawer_layout);//??
        drawer.closeDrawer(GravityCompat.START);

        return false;
    }

    class CommentAdapter extends BaseAdapter {



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

    //actionbar 관련 코드
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_main,menu);

        return true;
    }
    //actionbar 관련 코드
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int curId = item.getItemId();

        switch(curId){
            case R.id.menu_modify:
                Toast.makeText(this,"게시글 수정",Toast.LENGTH_LONG).show();

                break;
            case R.id.menu_delete:
                Toast.makeText(this,"게시글 삭제",Toast.LENGTH_LONG).show();


                finish();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
