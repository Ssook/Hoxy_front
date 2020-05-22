package org.techtown.hoxy;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.techtown.hoxy.waste.ResultActivity;
import org.techtown.hoxy.waste.WasteApplyActivity;
import org.techtown.hoxy.waste.WasteInfoItem;

public class CodeActivity extends AppCompatActivity {
    private String code;
    private TextView codeView;
    private Button btn_clip;
    private Button btn_finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

        codeView = findViewById(R.id.tv_codeview);
        btn_clip = findViewById(R.id.bt_clip);
        btn_finish = findViewById(R.id.bt_finish);
        Intent intent = getIntent();
        code = intent.getExtras().getString("code");


        codeView.setText(code);

        btn_clip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clip_click(code);
            }
        });

        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(CodeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


     /*   List<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("item1");
        spinnerArray.add("item2");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, trashSizeArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        trash_size_spinner.setAdapter(adapter);*/


    }

    private void clip_click(String string) {
        String strLabel = "code";
        String strCopy = string;

        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        ClipData clipData = ClipData.newPlainText(strLabel, strCopy);

        clipboardManager.setPrimaryClip(clipData);

        toastMessage("code " + string + " 가 복사되었습니다.");
    }

    private void toastMessage(String string) {
        Toast myToast = Toast.makeText(this.getApplicationContext(), string, Toast.LENGTH_SHORT);
        myToast.show();
    }

}

