package org.techtown.hoxy.waste;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.hoxy.MainActivity;
import org.techtown.hoxy.R;
import org.techtown.hoxy.TrashName;
import java.util.ArrayList;

public class WasteInfoActivity extends AppCompatActivity{
    private String wasteName = TrashName.getTrash();
    String select_name, select_size, select_fee;
    int select_no;
    ArrayList<String> spinnerArray = new ArrayList<String>();
    private Button next_button, cancle_button, again_button;
    private TextView waste_code_textView, waste_fee_textView;
  //  private String waste_code, waste_fee;
    private Spinner waste_size_spinner;
    private String intent_text;
    //추가
    private JSONArray wasteInfoItems;
    private int position;
    private ArrayList<Integer> waste_type_no = new ArrayList<>();
    private ArrayList<String> waste_name = new ArrayList<>(), waste_fee = new ArrayList<>(), waste_size= new ArrayList<>();

    private ArrayList<WasteInfoItem> waste_basket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wasteinfo);

        waste_size_spinner = (Spinner)findViewById(R.id.spinner);
        waste_code_textView =findViewById(R.id.textView2);
        waste_fee_textView = findViewById(R.id.textView4);
        cancle_button = findViewById(R.id.button3);
        again_button = findViewById(R.id.button6);
        next_button = findViewById(R.id.button5);



        //전 화면에서 받아오기
        Intent intent_get = getIntent();
        intent_text = intent_get.getExtras().getString("intent_text");
        String temp_wasteInfoItems = (String) intent_get.getSerializableExtra("wasteInfoItems");
        waste_basket = (ArrayList<WasteInfoItem>) intent_get.getSerializableExtra("wastebasket");
        try {
            wasteInfoItems = new JSONArray(temp_wasteInfoItems);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        position = intent_get.getExtras().getInt("position");
        //System.out.println(wasteInfoItem.getWaste_name());
        System.out.println(position);

        for(int i = 0 ; i < wasteInfoItems.length();i++)
        {
            try {
                JSONObject jo_data = wasteInfoItems.getJSONObject(i);
                waste_name.add(jo_data.getString("waste_type_kor_name"));
                waste_type_no.add(jo_data.getInt("waste_type_no"));
                waste_size.add(jo_data.getString("waste_type_size"));
                waste_fee.add(jo_data.getString("waste_type_fee"));
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


        //스피너 아이템 추가
       // spinnerArray.add(wasteInfoItem.getWaste_size());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, waste_size);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        waste_size_spinner.setAdapter(adapter);
        waste_size_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                waste_code_textView.setText(waste_name.get(position));
                waste_fee_textView.setText(waste_fee.get(position));
                select_fee = waste_fee.get(position);
                select_size = waste_size.get(position);
                select_name = waste_name.get(position);
                select_no = waste_type_no.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //textView.setText(trashName);
            }
        });


        //// 취소
        cancle_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                finish();
                Intent intent = new Intent(WasteInfoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        //// 다음
        next_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(WasteInfoActivity.this, WasteApplyActivity.class);
                intent.putExtra("position", position);
                addBasket(select_name,select_fee,select_size,select_no);
                intent.putExtra("wastebasket", waste_basket);
                startActivity(intent);
            }
        });



    }
    //한번 더
        public void OnClickHandler(View view) {
                final CharSequence[] items = {"카메라", "갤러리", "취소"};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("방법 선택")        // 제목 설정

                        .setItems(items, new DialogInterface.OnClickListener(){    // 목록 클릭시 설정

                            public void onClick(DialogInterface dialog, int index){
                                if(index == 0){
                                    finish();
                                    Intent intent = new Intent(WasteInfoActivity.this, ResultActivity.class);
                                    intent.putExtra("intent_text","camera");
                                    intent.putExtra("position", ++position);
                                    intent.putExtra("wasteInfoItems", wasteInfoItems.toString());
                                    addBasket(select_name,select_fee,select_size,select_no);
                                    intent.putExtra("wastebasket", waste_basket);
                                    startActivity(intent);
                                }
                                else if(index == 1){
                                    finish();
                                    Intent intent = new Intent(WasteInfoActivity.this, ResultActivity.class);
                                    intent.putExtra("intent_text","image");
                                    intent.putExtra("position", ++position);
                                    intent.putExtra("wasteInfoItems", wasteInfoItems.toString());
                                    addBasket(select_name,select_fee,select_size,select_no);
                                    intent.putExtra("wastebasket", waste_basket);
                                    startActivity(intent);
                                }
                                else
                                {
                                    dialog.cancel();
                                }
                            }
                        });

                AlertDialog dialog = builder.create();    // 알림창 객체 생성
                dialog.show();    // 알림창 띄우기
    }

    private  void addBasket(String name, String fee, String size, int no){
        WasteInfoItem wasteInfoItem = new WasteInfoItem(name, size, Integer.parseInt(fee), no, "");
        waste_basket.add(wasteInfoItem);
    }
}

