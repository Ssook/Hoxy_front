package org.techtown.hoxy.community;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.kakao.network.NetworkTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.hoxy.MainActivity;
import org.techtown.hoxy.R;
import org.techtown.hoxy.RequestHttpURLConnection;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.util.Base64.NO_WRAP;
import static android.util.Base64.encodeToString;

//import static org.techtown.hoxy.waste.ResultActivity.encodeTobase64;

public class CommentWriteActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
    final String TAG = getClass().getSimpleName();
    final static int TAKE_PICTURE = 1;
    private ImageView picture;
    private EditText contentsInput;
    private EditText commentTitle;
    private Intent intent;
    private Bitmap waste_bitmap;

    private Button saveButton;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    private View nav_header_view;
    private Button cancelButton;
    private String files;
    private String file_name;
    JSONArray ja_title_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayoutPostWriteActivity();

        set_inflate();

        call_the_camera();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertPostData();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked_CancelButton();
            }
        });

    }
    public void initLayoutPostWriteActivity() {           //레이아웃 정의
        setContentView(R.layout.activity_comment_write);

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

    // 권한 요청
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult");
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED ) {
            Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
        }
    }
 /*   //모든 리스트뷰에 데이터 돌려보내기
    protected void returnToMain(){


    }*/
    public void clicked_CancelButton(){
        intent = new Intent(getApplicationContext(),CommentAllViewActivity.class);
        startActivity(intent);
        finish();
        Toast.makeText(CommentWriteActivity.this, "게시글 등록 취소", Toast.LENGTH_SHORT).show();
    }

    public void call_the_camera(){

        picture = (ImageView)findViewById(R.id.pictureView);
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.pictureView:
                        // 카메라 앱을 여는 소스
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, TAKE_PICTURE);
                        System.out.println("사진창띄우기 성공");
                        break;
                }
            }
        });

        // 6.0 마쉬멜로우 이상일 경우에는 권한 체크 후 권한 요청
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ) {
                Log.d(TAG, "권한 설정 완료");
            } else {
                Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    // 카메라로 촬영한 영상을 가져오는 부분
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode == TAKE_PICTURE){
            System.out.println("코드확인");
                if (resultCode == RESULT_OK && intent.hasExtra("data")) {
                    waste_bitmap = (Bitmap) intent.getExtras().get("data");

                    System.out.println("사진데이터 갖고 오기 성공");
                    if (waste_bitmap != null) {
                        picture.setImageBitmap(waste_bitmap);
                        System.out.println("사진등록완료");
                    }
                }
        }

        files = encodeTobase64(waste_bitmap);

    }
    public static String encodeTobase64(Bitmap image)
    {
        Bitmap immagex=image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = encodeToString(b, NO_WRAP);
        return imageEncoded;
    }
    public void set_inflate(){
        contentsInput = (EditText) findViewById(R.id.contentsInput);

        commentTitle = (EditText) findViewById(R.id.commentTitle);

        saveButton = (Button) findViewById(R.id.saveButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
    }

    /*
    입력된 게시글의 정보를 서버에 보냄
    * */
    public void insertPostData(){
        System.out.println("insert");
        SharedPreferences sp=getSharedPreferences("profile", Activity.MODE_PRIVATE);
        String user_id = sp.getString("token","");

        String contents = contentsInput.getText().toString();
        String title = commentTitle.getText().toString();
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String board_reg_date = simpleDate.format(mDate);

        String file_name = board_reg_date + user_id+".jpg";
        System.out.println("review_reg_date = "+ board_reg_date);
        //서버로 보내기
        // URL 설정.
        //String url = "192.168.1.238:8080/select_board_title";
        //JSONObject에 서버로 보낼 게시글 정보를 담음
        JSONObject board_data = new JSONObject();
        try {
            board_data.put("board_title", title);
            board_data.put("board_ctnt", contents);
            board_data.put("board_reg_user_no", user_id);
            board_data.put("board_area_no", 1);
            board_data.put("board_reg_date",board_reg_date);
            board_data.put("files",files);
            board_data.put("file_name",file_name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //--------------------------------
        /* 게시글 정보를 서버에 보냄   Part*/
        //--------------------------------
        NetworkTask networkTask = new NetworkTask(board_data.toString());
        networkTask.execute();

        intent = new Intent(getApplicationContext(),CommentAllViewActivity.class);
/*
        intent.putExtra("title",title);
        intent.putExtra("contents",contents);*/
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        Toast.makeText(CommentWriteActivity.this, "게시글 등록 성공", Toast.LENGTH_SHORT).show();
    }
    //--------------------------------
    /* 게시글 정보를 서버에 보내는 Class*/
    //--------------------------------
    public class NetworkTask extends AsyncTask<Void, Void, String> {

        String values;

        NetworkTask(String values) {
            this.values = values;
        }//생성자

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progress bar를 보여주는 등등의 행위
        }//실행 이전 작업 정의 함수

        @Override
        protected String doInBackground(Void... params) {
            String result = "";

            try {
                //서버에 게시글 정보를 입력하는 함수 호출
                result = sendCommentWrite(values);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result; // 결과가 여기에 담깁니다. 아래 onPostExecute()의 파라미터로 전달됩니다.
        } // 백그라운드 작업 함수

        @Override
        protected void onPostExecute(String result) {
            // 통신이 완료되면 호출됩니다.
            // 결과에 따른 UI 수정 등은 여기서 합니다.
        }
    }
    public String sendCommentWrite(String values) throws JSONException {

        String result = "";
        try {
            //--------------------------
            //   URL 설정하고 접속하기
            //--------------------------
            String str_URL = "http://" + RequestHttpURLConnection.server_ip + ":" + RequestHttpURLConnection.server_port + "/insert_board/";

            URL url = new URL(str_URL);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();   // 접속
            //Log.d("eee", values);

            //--------------------------
            //   전송 모드 설정 - 기본적인 설정이다
            //--------------------------
            http.setDefaultUseCaches(false);
            http.setDoInput(true);                         // 서버에서 읽기 모드 지정
            http.setDoOutput(true);                       // 서버로 쓰기 모드 지정
            http.setRequestMethod("POST");         // 전송 방식은 POST

            // 서버에게 웹에서 <Form>으로 값이 넘어온 것과 같은 방식으로 처리하라는 걸 알려준다
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");            //--------------------------
            //   서버로 값 전송
            //--------------------------
            StringBuffer buffer = new StringBuffer();
            String regdata = "data=" + values;
            Log.d("board_data", regdata);
            System.out.println("regdata : "+ regdata);
            buffer.append(regdata);                 // php 변수에 값 대입

            OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
            PrintWriter writer = new PrintWriter(outStream);
            writer.write(buffer.toString());
            writer.flush();

            //--------------------------
            //   서버에서 전송받기
            //--------------------------
            InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "UTF-8");
            BufferedReader reader = new BufferedReader(tmp);
            StringBuilder builder = new StringBuilder();
            String str;
            System.out.println("Builder ; "+builder);


            while ((str = reader.readLine()) != null) {       // 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
                builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
            }
            result = builder.toString();
            System.out.println("result in commentWriteActivity : " + result);
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        System.out.println(result);
        return result;
    } // HttpPostDat
}
