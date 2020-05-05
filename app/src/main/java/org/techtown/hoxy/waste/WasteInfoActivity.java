package org.techtown.hoxy.waste;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.techtown.hoxy.R;
import org.techtown.hoxy.TrashName;

public class WasteInfoActivity extends AppCompatActivity {
    private String wasteName = TrashName.getTrash();
    private String[] wasteSizeArray = new String[]{"Test1" ,"Test2" ,"Test3" ,"Test4"};
    private Button next_button, again_button, howto_button;
    private TextView waste_code_textView, waste_fee_textView;
    private String waste_code, waste_fee;
    private Spinner waste_size_spinner;
    private String intent_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wasteinfo);

        waste_size_spinner = (Spinner)findViewById(R.id.spinner);

        waste_code_textView =findViewById(R.id.textView2);
        waste_fee_textView = findViewById(R.id.textView4);
        again_button = findViewById(R.id.button3);
        howto_button = findViewById(R.id.button6);
        next_button = findViewById(R.id.button5);

        Intent intent_get = getIntent();

        intent_text = intent_get.getExtras().getString("intent_text");
     /*   List<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("item1");
        spinnerArray.add("item2");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, trashSizeArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        trash_size_spinner.setAdapter(adapter);*/
        waste_size_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //textView1.setText(parent.getItemAtPosition(position).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //textView.setText(trashName);
            }
        });


        //// 다시
        again_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {


                Intent intent = new Intent(WasteInfoActivity.this, ResultActivity.class);
                intent.putExtra("intent_text", intent_text);
                startActivity(intent);
            }
        });
        //// 다음
        next_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(WasteInfoActivity.this, WasteInfoActivity.class);
                startActivity(intent);
            }
        });

        howto_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
