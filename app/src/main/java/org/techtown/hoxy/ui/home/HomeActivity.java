package org.techtown.hoxy.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.techtown.hoxy.MainActivity;
import org.techtown.hoxy.R;
import org.techtown.hoxy.ui.image.CameraActivity;
import org.techtown.hoxy.ui.image.CameraFragment;
import org.techtown.hoxy.ui.image.ImageActivity;
import org.techtown.hoxy.ui.image.ImageFragment;

public class HomeActivity extends AppCompatActivity {

    private String text_title;
    private String text_content1;
    private String text_content2;
    private String text_content3;
    private String text_content1_time;
    private String text_content2_time;
    private String text_content3_time;
    private TextView home_main_textView,content_textView,content2_textView ,content3_textView, content1_time_textView, content2_time_textView, content3_time_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        home_main_textView = findViewById(R.id.text_home); //혹시 어떻게 버리지??
        content_textView = findViewById(R.id.text_content1); // 첫번째 게시글
        content2_textView = findViewById(R.id.text_content2); // 두번째 게시글
        content3_textView = findViewById(R.id.text_content3); // 세번째 게시글
        content1_time_textView = findViewById(R.id.text_content1_time); // 첫번째 게시글 작성일
        content2_time_textView = findViewById(R.id.text_content2_time); // 첫번째 게시글 작성일
        content3_time_textView = findViewById(R.id.text_content3_time); // 첫번째 게시글 작성일

        text_title = "혹시 어떻게 버리지..?";

        text_content1 = "1번 게시글";
        text_content2 = "2번 게시글";
        text_content3 = "3번 게시글";

        text_content1_time = "2020-04-08";
        text_content2_time = "2020-04-09";
        text_content3_time = "2020-04-10";


        home_main_textView.setText(text_title);
        content_textView.setText(text_content1);
        content2_textView.setText(text_content2);
        content3_textView.setText(text_content3);
        content1_time_textView.setText(text_content1_time);
        content2_time_textView.setText(text_content2_time);
        content3_time_textView.setText(text_content3_time);



        ////////////////갤러리로 넘어가기
        ImageButton imageButton = findViewById(R.id.galleryButton);
        imageButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                System.out.println("ㅎㅇㅎㅇ");

                Intent intent = new Intent(HomeActivity.this, ImageActivity.class);
                startActivity(intent);
            }
        });
        //////////////////////
        //카메라 클릭시
        ImageButton cameraButton = findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });
    }
}
