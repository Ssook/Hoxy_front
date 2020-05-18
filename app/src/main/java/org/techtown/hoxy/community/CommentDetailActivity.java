package org.techtown.hoxy.community;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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


import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
import com.kakao.network.NetworkTask;

import org.techtown.hoxy.RequestHttpURLConnection;
import org.techtown.hoxy.community.CommentItem;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CommentDetailActivity extends Activity implements Serializable, NavigationView.OnNavigationItemSelectedListener {
   // private PostItem item;
    private int post_List_post_no;
    private int post_detail_post_no;
    private CommentAdapter adapter;
    private String comment;
    private EditText othersComment;
    private ArrayList<CommentItem> items = new ArrayList<CommentItem>();
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    private View nav_header_view;
    private TextView userId;
    private TextView title;
    private TextView content;
    private ImageView userImage;
    private TextView post_reg_date;

    private Button backButton;
    private ImageButton writeButton;
    private JSONArray ja_array;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayoutPostWriteActivity();//init
        findView();//View들과 연결


        //postListActivity로부터 선택된 게시판의 post-no을 받아 서버에서 추가 적인 정보들을 가져옴
        post_List_post_no = getIntent().getIntExtra("post_no", 0);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("board_no",post_List_post_no);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //System.out.println(post_List_post_no);
       // item = (PostItem) getIntent().getSerializableExtra("item");

        //Toast.makeText(getApplicationContext(),"들어와쏭",Toast.LENGTH_LONG).show();
        CommentDetailActivity.NetworkTask networkTask = new CommentDetailActivity.NetworkTask(this, jsonObject.toString());
        //System.out.println("network_task1");
        networkTask.execute();
        //System.out.println("network_tast2");

        get_comment();

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

    public void findView(){

        userId = (TextView) findViewById(R.id.useridView);
        title = (TextView) findViewById(R.id.title);
        content = (TextView) findViewById(R.id.title2);
        userImage = (ImageView) findViewById(R.id.userImage);
        backButton = (Button) findViewById(R.id.goToAllViewButton);
        writeButton = (ImageButton) findViewById(R.id.writeButton);
        othersComment = (EditText) findViewById(R.id.othersComment);
        post_reg_date = (TextView) findViewById(R.id.reg_date);
    }

    public String request_post_data(String value) throws JSONException {
        //postAdapter = new PostAdapter();
        System.out.println("request_post_Data");
        int TIMEOUT_VALUE = 1000;
        String result = "";
        try {
            //--------------------------
            //   URL 설정하고 접속하기
            //--------------------------
            URL url = new URL("http://172.16.5.240:8000/select_board/");
            System.out.println("URL_connect");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();   // 접속
            //--------------------------
            //   전송 모드 설정 - 기본적인 설정이다
            //--------------------------

            http.setDefaultUseCaches(false);
            http.setDoInput(true);                         // 서버에서 읽기 모드 지정
            http.setDoOutput(true);                       // 서버로 쓰기 모드 지정

            //http.setConnectTimeout();
            http.setConnectTimeout(TIMEOUT_VALUE);

            http.setRequestMethod("POST");         // 전송 방식은 POST

            // 서버에게 웹에서 <Form>으로 값이 넘어온 것과 같은 방식으로 처리하라는 걸 알려준다
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");            //--------------------------
            System.out.println("set_Property");
            //   서버로 값 전송
            //--------------------------
            StringBuffer buffer = new StringBuffer();
            Log.e("tlqkf","tlqkf");
            String currentlocationsend = "data=" + value;

            buffer.append(currentlocationsend);                 // php 변수에 값 대입
            System.out.println("buffer.append");
            OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
            System.out.println("outStream");
            PrintWriter writer = new PrintWriter(outStream);
            System.out.println("PrintWriter");
            writer.write(buffer.toString());
            writer.flush();


            //--------------------------
            //   서버에서 전송받기
            //--------------------------
            InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "UTF-8");
            BufferedReader reader = new BufferedReader(tmp);
            StringBuilder builder = new StringBuilder();
            String str;

            while ((str = reader.readLine()) != null) {       // 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
                System.out.println("str"+str.toString());
                builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
            }
            result = builder.toString();
            result = result.replace("&#39;","\"");
            System.out.println("result"+result);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(result);
        return result;
    }

       /* public void set_component(int _area_no, String _title, String _user_name,String _content, String _reg_date){
        userId.setText( _user_name);
        title.setText( _title);
        userImage.setImageResource(R.drawable.user1);

        content.setText( _content);
        reg_date.setText(_reg_date);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }*/
    /*
    * 안드로이드에서 Post형식으로 서버에 데이터를 보내는 코드
    *
    * */



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

    //---------------------------------------
    /* 해당 게시글의 모든 정보를 받아오는 클래스*/
    //---------------------------------------
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public class NetworkTask extends AsyncTask<Void, Void, String> {

        String values;
        Context mcontext;

        NetworkTask(Context mcontext, String values) {
            this.mcontext = mcontext;
            this.values = values;
        }//생성자

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progress bar를 보여주는 등등의 행위
        }//실행 이전에 작업되는 것들을 정의하는 함수

        @Override
        protected String doInBackground(Void... params) {
            String result = "";

            try {
                //서버로 게시글 번호를 주고 게시글 댓글 데이타를 받아옴.
                result = request_post_data(values);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result; // 결과가 여기에 담깁니다. 아래 onPostExecute()의 파라미터로 전달됩니다.
        }// 백그라운드 작업 함수

        //---------------------------------------------
        /* 서버로 부터 받아온 게시글 댓글로 댓글 UI 작업  */
        //---------------------------------------------
        @Override
        protected void onPostExecute(String result) {
            // 통신이 완료되면 호출됩니다.
            // 결과에 따른 UI 수정 등은 여기서 합니다.
            System.out.println("result : " +result);
            if (result != "") {
                try {
                    ja_array= new JSONArray(result);
                } catch (JSONException e) {
                    //TODO Auto-generated catch block
                    e.printStackTrace();
                }
                //for (int i = 0; i < ja_array.length(); i++) {
                    try {
                        JSONObject jsonObject = ja_array.getJSONObject(0);
                        // array에 해당 값들을 넣어줌.
                        //Time Setting
                        jsonObject.getString("board_no");
                        String time = jsonObject.getString("board_reg_date");
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        Date date = null;
                        //try {
                            date = simpleDateFormat.parse(time);
                        /*} catch (ParseException e) {
                            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
                            try {
                                date = simpleDateFormat1.parse(time);
                            } catch (ParseException ex) {
                                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    date = simpleDateFormat2.parse(time);
                                } catch (ParseException exc) {
                                    exc.printStackTrace();
                                }
                                ex.printStackTrace();
                            }
                            e.printStackTrace();
                        }*/
                        /*Long longDate = date.getTime();
                        arrayregDate.add(TimeString.formatTimeString(longDate));*/
                        userId.setText(jsonObject.getString("board_user_name"));
                        title.setText(jsonObject.getString("board_title"));
                        content.setText(jsonObject.getString("board_ctnt"));//content로 변경해야됨(주용이와 대화 필요)
                        userImage.setImageResource(R.drawable.user1);
                        post_reg_date.setText(jsonObject.getString("board_reg_date"));
                    } catch (JSONException | ParseException e) {
                        e.printStackTrace();
                    }
                //}

            }//result not null
            else {
               Toast.makeText(getApplicationContext(), "댓글 없음.", Toast.LENGTH_SHORT).show();
            }
        }//onPostExecute func()
    }//NetWorkTask Class

    public void get_comment(){


    }
}
