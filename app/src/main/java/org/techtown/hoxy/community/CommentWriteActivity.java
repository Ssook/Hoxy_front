package org.techtown.hoxy.community;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.techtown.hoxy.MainActivity;
import org.techtown.hoxy.R;
public class CommentWriteActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
    final String TAG = getClass().getSimpleName();
    final static int TAKE_PICTURE = 1;
    private ImageView picture;
    private EditText contentsInput;
    private EditText commentTitle;
    private Intent intent;
    Button saveButton;
    DrawerLayout drawer;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    View nav_header_view;
    JSONArray ja_title_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayoutPostWriteActivity();

        contentsInput = (EditText) findViewById(R.id.contentsInput);

        commentTitle = (EditText) findViewById(R.id.commentTitle);

        saveButton = (Button) findViewById(R.id.saveButton);
        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToMain();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked_CancelButton();
            }
        });
        call_the_camera();
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
    //모든 리스트뷰에 데이터 돌려보내기
    protected void returnToMain(){
        String contents = contentsInput.getText().toString();
        String title = commentTitle.getText().toString();
        intent = new Intent(getApplicationContext(),CommentAllViewActivity.class);

        intent.putExtra("title",title);
        intent.putExtra("contents",contents);

        startActivity(intent);
        finish();
        Toast.makeText(CommentWriteActivity.this, "게시글 등록 성공", Toast.LENGTH_SHORT).show();

    }
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
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, TAKE_PICTURE);
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

        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == RESULT_OK && intent.hasExtra("data")) {
                    Bitmap bitmap = (Bitmap) intent.getExtras().get("data");
                    if (bitmap != null) {
                        picture.setImageBitmap(bitmap);
                    }

                }
                break;
        }
    }


}
