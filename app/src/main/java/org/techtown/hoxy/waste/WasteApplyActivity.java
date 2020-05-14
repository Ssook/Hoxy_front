package org.techtown.hoxy.waste;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.techtown.hoxy.MainActivity;
import org.techtown.hoxy.R;

import java.util.ArrayList;

public class WasteApplyActivity extends AppCompatActivity {


    private ApplyInfo applyInfo;
    private EditText editText_user_name, editText_phone_num, editText_address, editText_address_detail, editText_date;
    private ListView listView_applied_waste;
    private Button button_cancle, button_next;

    private String user_name, phone_num, address, address_detail, date;
    //추가 05 14
    private ArrayList<String> LIST_MENU = new ArrayList<>();
    private ArrayList<WasteInfoItem> wasteInfoItems;
    private WasteInfoItem wasteInfoItem;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_waste_apply);
            //가져오기
            editText_address = findViewById(R.id.edit_address);
            editText_address_detail = findViewById(R.id.edit_address_detail);
            editText_user_name = findViewById(R.id.edit_user_name);
            editText_phone_num = findViewById(R.id.edit_phone_num);
            editText_date = findViewById(R.id.edit_date);
            button_cancle = findViewById(R.id.button_cancle);
            button_next = findViewById(R.id.button_next);
            listView_applied_waste = findViewById(R.id.waste_list_view);

            //

        Intent intent_get = getIntent();
        wasteInfoItems = (ArrayList<WasteInfoItem>) intent_get.getSerializableExtra("wasteInfoItems");
        position = intent_get.getExtras().getInt("position");
        wasteInfoItem = wasteInfoItems.get(position);
        System.out.println(wasteInfoItems.size());
        System.out.println(wasteInfoItem.getWaste_name());
        System.out.println(position);

        for(int i = 0 ; i<=position ; i++){

            LIST_MENU.add(wasteInfoItems.get(i).getWaste_size());
            System.out.println(wasteInfoItems.get(i).getWaste_size());
            System.out.println(i);
            System.out.println(LIST_MENU.get(i));
        }

        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1 , LIST_MENU) ;
        listView_applied_waste.setAdapter(adapter);
            //


        //// 신청 버튼 클릭시
            button_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    user_name = editText_user_name.getText().toString();
                    phone_num = editText_phone_num.getText().toString();
                    address = editText_address.getText().toString();
                    address_detail = editText_address_detail.getText().toString();
                    date = editText_date.getText().toString();

                  if(user_name.equals("") || phone_num.equals("") || address.equals("") || address_detail.equals("") || date.equals(""))
                  {
                      if(user_name.equals("")) {
                          editText_user_name.requestFocus();
                          toastMessage("이름을 작성해주세요.");
                      }
                      else if(phone_num.equals("")) {
                          editText_phone_num.requestFocus();
                          toastMessage("휴대폰 번호를 작성해주세요.");
                      }
                      else if(address.equals("")) {
                          editText_address.requestFocus();
                          toastMessage("배출 주소를 작성해주세요.");
                      }
                      else if(address_detail.equals("")) {
                          editText_address_detail.requestFocus();
                          toastMessage("상세 주소를 작성해주세요.");
                      }
                      else {
                          editText_date.requestFocus();
                          toastMessage("배출 날짜를 작성해주세요.");
                      }
                  } //입력이 하나라도 비었을때
                    else
                  {
                      System.out.println(user_name);
                      finish();
                      Intent intent = new Intent(WasteApplyActivity.this, MainActivity.class);
                      startActivity(intent);
                  }


                } //onClick
            }); // SetOnclickListner

            button_cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    Intent intent = new Intent(WasteApplyActivity.this, MainActivity.class);
                    startActivity(intent);
                }//onClick
            });//setOnClickListener

    }//onCreate

    private void toastMessage(String string){
        Toast myToast = Toast.makeText(this.getApplicationContext(),string,Toast.LENGTH_SHORT);
        myToast.show();
    }
}
