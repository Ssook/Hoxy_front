package org.techtown.hoxy.community;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.hoxy.MainActivity;
import org.techtown.hoxy.R;
import org.techtown.hoxy.RequestHttpURLConnection;
import org.techtown.hoxy.TrashName;
import org.techtown.hoxy.waste.ResultActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CommentAllViewActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Serializable {
    private PostAdapter adapter;
    private Bundle data;
    private PostItem item;
    private ListView listView;
    private ArrayList<PostItem> items;
    private CommentItemView view;

    //postItem의 객체정보 값을 갖는 array들
    ArrayList<String> arrayregDate = new ArrayList<String>();
    ArrayList<String> arrayregUser = new ArrayList<String>();
    //ArrayList<String> arraytag = new ArrayList<String>();
    ArrayList<String> arraytitle = new ArrayList<String>();
    //ArrayList<String> arrayctnt = new ArrayList<String>();
    ArrayList<Integer> arrayPostNo = new ArrayList<Integer>();
    ArrayList<Integer> arrayimage = new ArrayList<Integer>();
    ArrayList<String> arrayContetnt = new ArrayList<String>();
    //PostItem 클래스 타입의 ArrayList
    ArrayList<PostItem> postList = new ArrayList<PostItem>();


    DrawerLayout drawer;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    View nav_header_view;
    JSONArray ja_title_data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayoutPostListActivity();


        listView = (ListView) findViewById(R.id.listView);
        data = new Bundle();
        //----------------------------
        /*      게시글을 전부 가져옴  */
        //----------------------------
        Intent intent = getIntent();
        System.out.println("allViewIntent");
        /*try {
            if(!(intent.getExtras().isEmpty()))
            {
               // tag=intent.getExtras().getString("태그");
            }
        } catch (NullPointerException e) {
            //tag="전체";
            e.printStackTrace();
        }*/
        //adapter = new PostAdapter();
        //adapter.addItem(new PostItem(R.drawable.user1,"앙기모","kss1218",1,"dndnd"));
        //listView.setAdapter(adapter);
        http_task http_task = new http_task("select_board_title");
        http_task.execute();

        //connect_http();
        set_button_action();

        //  NetworkTask networkTask = new NetworkTask( this, "");
        //  networkTask.execute();
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
                //adapter.addItem(new PostItem(R.drawable.user1, contents, "김성수", 1, commentTitle));
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
            //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
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
    public void set_data(String data){
        try {
            String str_res = data;
            JSONArray ja_res = new JSONArray(str_res);
            System.out.println("data : " + ja_res);
            System.out.println("ja_res.length(): " + ja_res.length());
            System.out.println("ja_res.getJSONObject(0): " + ja_res.getJSONObject(0));



            if(ja_res != null) {
                for (int i = 0; i < ja_res.length(); i++) {
                    try {
                        JSONObject jo_data = ja_res.getJSONObject(i);
                        arrayPostNo.add(jo_data.getInt("board_no"));
                        arraytitle.add( jo_data.getString("board_title"));
                        arrayregUser.add(jo_data.getString("board_user_name"));
                        //int area_no = jo_data.getInt("board_waste_area_no"));
                        arrayregDate.add(jo_data.getString("board_reg_date"));

                        //adapter.addItem(new PostItem(R.drawable.user1, title, user_name, post_no/*, reg_date*/));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("arraytitle.Size()"+arraytitle.size());
            for (int i = 0; i < arraytitle.size(); i++) {
                PostItem postItem = new PostItem(R.drawable.user1, arraytitle.get(i), arrayregUser.get(i), arrayPostNo.get(i),arrayregDate.get(i));
                //bind all strings in an array
                postList.add(postItem);
                System.out.println("postList.Size(): "+i+" "+postList.size());

            }
            adapter = new PostAdapter(postList);
            listView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void set_button_action(){
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

    public class PostAdapter extends BaseAdapter {

        ArrayList<PostItem> postItems; // main으로부터 modellist들을 전달 받을 객체
        //ArrayList<PostItem> postList; // modellist로부터 받은 모델을 array형으로 받을 객체
        //postList = postItems;
        public PostAdapter(ArrayList<PostItem> postItems)
        {
            this.postItems = postItems;
        }

        // ArrayList<PostItem>items = new ArrayList<PostItem>();
        public class ViewHolder{

        }

        @Override
        public int getCount() {
            return postItems.size();
        }

        public void addItem(PostItem item){
            postItems.add(item);

        }
        @Override
        public Object getItem(int position) {
            return postItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            view = null;
            if(convertView == null){
                view = new CommentItemView(getApplicationContext());

            }
            else{
                view = (CommentItemView) convertView;

            }
            PostItem item = postItems.get(position);
            view.setUserId(item.getUserId());
            view.setImage(item.getResId());
            view.setComment(item.getTitle());
            view.setReg_date(item.getReg_date());

            return view;
        }
    }

    public void onCommand(String command,Bundle data){
        /*
         *postList 혹은 fab버튼을 click시 화면 전환을 위한 함수
         * 화면 전환을 위한
         * */
        if (command.equals("writeComment")) {
            // 액티비티를 띄우는 경우
            Intent intent = new Intent(getApplicationContext(), CommentWriteActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);

        }
        if (command.equals("showDetail")){
            Intent intent = new Intent(getApplicationContext(), CommentDetailActivity.class);
            intent.putExtra("post_no",item.getPost_no());
            //intent.putExtra("")
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
    public void initLayoutPostListActivity() {           //레이아웃 정의
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
    public class http_task extends AsyncTask<String, String, String> {
        String sub_url = "";
        http_task(String sub_url){
            this.sub_url = sub_url;
        }
        @Override
        protected String doInBackground(String... params) {
            String res = "";
            try {
                String str = "";
                String str_URL = "http://" + RequestHttpURLConnection.server_ip + ":" + RequestHttpURLConnection.server_port + "/" + sub_url + "/";
                System.out.println("str_URL : " + str_URL);
                URL url = new URL(str_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                //--------------------------
                //   전송 모드 설정 - 기본적인 설정이다
                //--------------------------
                conn.setDefaultUseCaches(false);
                conn.setDoInput(true);                         // 서버에서 읽기 모드 지정
                conn.setDoOutput(true);                       // 서버로 쓰기 모드 지정
                conn.setRequestMethod("POST");         // 전송 방식은 POST

                // 서버에게 웹에서 <Form>으로 값이 넘어온 것과 같은 방식으로 처리하라는 걸 알려준다
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                System.out.println("setRequestProperty");
                //--------------------------
                //   서버로 값 전송
                //--------------------------
                StringBuffer buffer = new StringBuffer();
                String data = "data=" + "";
                //System.out.println("data = "+data);
                buffer.append(data);

                OutputStreamWriter outStream = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
                PrintWriter writer = new PrintWriter(outStream);
                writer.write(buffer.toString());
                writer.flush();

                //--------------------------
                //   서버에서 전송받기
                //--------------------------
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuilder builder = new StringBuilder();
                while ((str = reader.readLine()) != null) {       // 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
                    builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
                }

                res = builder.toString();
                res = res.replace("&#39;","\"");
                System.out.println("res : " + res);
                } catch (MalformedURLException ex) {
                ex.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return res;
        }
        @Override
        protected void onPostExecute(String result) {
            set_data(result);
        }
    }

}