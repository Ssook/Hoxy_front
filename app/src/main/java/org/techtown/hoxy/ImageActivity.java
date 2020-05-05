package org.techtown.hoxy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import org.techtown.hoxy.waste.ResultActivity;
import org.techtown.hoxy.TrashName;
import java.io.InputStream;

public class ImageActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 0;
    private ImageView trash_ImageView;
    private TextView trash_textView;
    private Button again_button, next_button;


    private Bitmap trash_bitmap;
    private String trashName ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE);


        trash_textView = findViewById(R.id.textView);


        trash_ImageView = (ImageView) findViewById(R.id.imageView);

        again_button = findViewById(R.id.button);
        next_button = findViewById(R.id.button2);
        //// 다시
        again_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ImageActivity.this, ImageActivity.class);
                startActivity(intent);
            }
        });
        //// 다음
        next_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ImageActivity.this, ResultActivity.class);
                startActivity(intent);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == REQUEST_CODE) {
            System.out.println("1");
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    System.out.println("2");
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in =  getContentResolver().openInputStream(data.getData());
                    trash_bitmap = BitmapFactory.decodeStream(in);
                    in.close();
                    TrashName.setTrash("쇼파일껄?");
                    trashName = TrashName.getTrash();
                    trash_textView.setText(trashName);
                    // 이미지 표시
                    Glide.with(this).load(trash_bitmap).into(trash_ImageView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
