package org.techtown.hoxy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.techtown.hoxy.waste.ResultActivity;
import org.techtown.hoxy.waste.WasteApplyActivity;
import org.techtown.hoxy.waste.WasteInfoItem;

public class CodeActivity extends AppCompatActivity {
    private String code;
    private TextView codeView;
    private Button btn1;
    private Button btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wasteinfo);

       // codeView=findViewById(R.id.tv_codeview);





        Intent intent_get = getIntent();

     /*   List<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("item1");
        spinnerArray.add("item2");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, trashSizeArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        trash_size_spinner.setAdapter(adapter);*/





    }
}
