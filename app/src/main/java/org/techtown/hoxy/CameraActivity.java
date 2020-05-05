package org.techtown.hoxy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.techtown.hoxy.MainActivity;
import org.techtown.hoxy.R;
import org.techtown.hoxy.waste.ResultActivity;

public class CameraActivity extends AppCompatActivity {
    final static int TAKE_PICTURE = 1;

    private ImageView trash_ImageView;
    private TextView trash_textView;
    private Button again_button, next_button;

    private Bitmap trash_bitmap;
    private String trashName ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        trash_textView = findViewById(R.id.textView);


        ////////////////////////////////////////////////////////
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, TAKE_PICTURE);

        ///////////////////////////////////////////////////
        trash_ImageView = (ImageView) findViewById(R.id.imageView);

        again_button = findViewById(R.id.button);
        next_button = findViewById(R.id.button2);
        //// 다시
        again_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CameraActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });
        //// 다음
        next_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CameraActivity.this, ResultActivity.class);
                startActivity(intent);
            }
        });


        //////////////////////////////////////////////////////


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == RESULT_OK && intent.hasExtra("data")) {
                    trash_bitmap = (Bitmap) intent.getExtras().get("data");
                    if (trash_bitmap != null) {
                        TrashName.setTrash("쇼파아님");
                        trashName = TrashName.getTrash();
                        trash_textView.setText(trashName);
                        Glide.with(this).load(trash_bitmap).into(trash_ImageView);
                    }

                }

        }
    }
}
