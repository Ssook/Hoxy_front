package org.techtown.hoxy.waste;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;


import org.techtown.hoxy.MainActivity;

import org.techtown.hoxy.R;
import org.techtown.hoxy.TrashName;
import org.techtown.hoxy.waste.WasteInfoActivity;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Objects;

public class ResultActivity extends AppCompatActivity {
    private final static int TAKE_PICTURE = 1;
    private static final int REQUEST_CODE = 0;

    private String intent_text;
    private ImageView waste_ImageView;
    private TextView waste_textView;
    private Button again_button, next_button;
    private Bitmap waste_bitmap;
    private String trashName ;
    ////추가
    private ArrayList<WasteInfoItem> wasteInfoItems;
    private WasteInfoItem wasteInfoItem;
    private int position = 0;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        //인텐트 받아오기
        Intent intent_get = getIntent();
        intent_text = Objects.requireNonNull(intent_get.getExtras()).getString("intent_text");
        wasteInfoItems = (ArrayList<WasteInfoItem>) intent_get.getSerializableExtra("wasteInfoItems");
        position = intent_get.getExtras().getInt("position");
        System.out.println(intent_text);


     //갤러리로 이동
        if(intent_text.equals("image")) {

            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, REQUEST_CODE);
        }
    //카메라로 이동
        else if(intent_text.equals("camera")) {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, TAKE_PICTURE);
        }
        waste_textView = findViewById(R.id.textView);


        waste_ImageView = (ImageView) findViewById(R.id.imageView);

        again_button = findViewById(R.id.button);
        next_button = findViewById(R.id.button2);

        //// 다시
        again_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

            finish();
            Intent intent = new Intent(ResultActivity.this, ResultActivity.class);
            intent.putExtra("intent_text",intent_text);
            intent.putExtra("position", position);
            intent.putExtra("wasteInfoItems", wasteInfoItems);
            startActivity(intent);
        }
    });

    //// 다음
        next_button.setOnClickListener(new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            if(wasteInfoItems == null)
               wasteInfoItems = new ArrayList<WasteInfoItem>();
            wasteInfoItems.add(wasteInfoItem);
            finish();
            Intent intent = new Intent(ResultActivity.this, WasteInfoActivity.class);
            intent.putExtra("intent_text",intent_text);
            intent.putExtra("wasteInfoItems", wasteInfoItems);
            intent.putExtra("position", position);
            startActivity(intent);
        }
    });


        //취소




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(intent_text.equals("image")) {
            // Check which request we're responding to
            if (requestCode == REQUEST_CODE) {
                System.out.println("1");
                // Make sure the request was successful
                if (resultCode == RESULT_OK) {
                    try {
                        System.out.println("2");
                        // 선택한 이미지에서 비트맵 생성
                        InputStream in = getContentResolver().openInputStream(data.getData());
                        waste_bitmap = BitmapFactory.decodeStream(in);
                        in.close();
                        TrashName.setTrash("쇼파일껄?");
                        trashName = TrashName.getTrash();
                        waste_textView.setText(trashName);
                        // 이미지 표시
                        Glide.with(this).load(waste_bitmap).into(waste_ImageView);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }// try, catch
                }// if result_ok
            }// if requestcode
        }// if image

        else if(intent_text.equals("camera"))
        {
            switch (requestCode) {
                case TAKE_PICTURE:
                    if (resultCode == RESULT_OK && data.hasExtra("data")) {
                        waste_bitmap = (Bitmap) data.getExtras().get("data");
                        if (waste_bitmap != null) {
                            TrashName.setTrash("쇼파아님");
                            trashName = TrashName.getTrash();
                            waste_textView.setText(trashName);
                            Glide.with(this).load(waste_bitmap).into(waste_ImageView);
                        }

                    }// if result code

            }// switch requestCode
        } // else if

        wasteInfoItem = new WasteInfoItem("쓰레기",String.valueOf(Math.random()) , 1200, 123, "asd123" ); //딥러닝 결과 테스트

    } // onActivityResult



    //딥러닝 결과 테스트
  //  private WasteInfoItem deepResult(){
 //      wasteInfoItem = new WasteInfoItem("쓰레기", "12,13,14", 1200, 123, "asd123" );
       // return wasteInfoItem;
  //  }// deppResult

}//ResultActivity
