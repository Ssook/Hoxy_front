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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import org.techtown.hoxy.R;
public class CommentWriteActivity extends Activity  {
    final String TAG = getClass().getSimpleName();
    final static int TAKE_PICTURE = 1;
    private ImageView picture;
    private EditText contentsInput;
    private EditText commentTitle;
    private Intent intent;
    Button saveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_write);

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
