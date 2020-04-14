package org.techtown.hoxy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class CommentWriteActivity extends AppCompatActivity {

    //RatingBar ratingBar;
    EditText contentsInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_write);


        contentsInput = (EditText) findViewById(R.id.contentsInput);

        Button saveButton = (Button) findViewById(R.id.saveButton);
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
                finish();
            }
        });
        Intent intent = getIntent();
        processIntent(intent);
    }
    protected void processIntent(Intent intent){
        if (intent !=null) {}
    }

    protected void returnToMain(){
        String contents = contentsInput.getText().toString();

        Intent intent = new Intent();
        Log.e("시발","빡치네");
        intent.putExtra("contents",contents);
        Log.e("정말","에러러러러");

        setResult(RESULT_OK,intent);

        finish();
    }
}
