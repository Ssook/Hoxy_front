package org.techtown.hoxy.community;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.techtown.hoxy.MainActivity;
import org.techtown.hoxy.R;

import java.io.Serializable;
import java.util.ArrayList;

public class CommentAllViewActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Serializable {
    private CommentAdapter adapter;
    private Bundle data;
    private PostItem item;
    private ListView listView;
    private ArrayList<PostItem> items;
    DrawerLayout drawer;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    View nav_header_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayoutMapActivity();

        listView = (ListView) findViewById(R.id.listView);
        data = new Bundle();
        adapter = new CommentAdapter();
        adapter.addItem(new PostItem(R.drawable.user1,"앙기모","kss1218",1,"dndnd"));


        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item = (PostItem) adapter.getItem(position);
                Toast.makeText(getApplicationContext(),item.getUserId()+"선택",Toast.LENGTH_LONG).show();

                onCommand("showDetail",data);

            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showCommentWriteActivity();

                onCommand("writeComment",data);

            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        int check;
        if (requestCode == 101) {
            if (intent != null) {

                String contents = intent.getStringExtra("contents");
                String commentTitle = intent.getStringExtra("title");
                //System.out.print(commentTitle);
                Toast.makeText(getApplicationContext(),"메뉴화면으로부터 응답 : "+ commentTitle, Toast.LENGTH_LONG).show();
                adapter.addItem(new PostItem(R.drawable.user1, contents, "김성수", 1, commentTitle));
                adapter.notifyDataSetChanged();
            }
        }

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

        } else if (id == R.id.nav_community) {

        }else if (id == R.id.nav_slideshow) {

        }
        drawer = findViewById(R.id.drawer_layout);//??
        drawer.closeDrawer(GravityCompat.START);

        return false;
    }

    class CommentAdapter extends BaseAdapter {

        ArrayList<PostItem>items = new ArrayList<PostItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(PostItem item){
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
            PostItem item = items.get(position);
            view.setUserId(item.getUserId());
            view.setImage(item.getResId());
            view.setComment(item.getTitle());
            //view.setTime(item.getTime());

            return view;
        }
    }
    public void onCommand(String command,Bundle data){
        if (command.equals("writeComment")) {
            // 액티비티를 띄우는 경우
            Intent intent = new Intent(getApplicationContext(), CommentWriteActivity.class);

            startActivityForResult(intent, 101);
        }
        if (command.equals("showDetail")){
            Intent intent = new Intent(getApplicationContext(), CommentDetailActivity.class);

            intent.putExtra("item", item);
            startActivityForResult(intent, 102);
        }
    }
    /*public void removePost(String command){
        int check;
        if(command.equals("remove")) {
            check = listView.getCheckedItemPosition();
            if (check != listView.INVALID_POSITION) {
                items.remove(check);
                listView.clearChoices();
                adapter.notifyDataSetChanged();
            }
        }
    }*/
    public void initLayoutMapActivity() {           //레이아웃 정의
        setContentView(R.layout.activity_community_main);
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

}
