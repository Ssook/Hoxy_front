package org.techtown.hoxy.waste;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import org.techtown.hoxy.CodeActivity;
import org.techtown.hoxy.MainActivity;
import org.techtown.hoxy.R;

import java.util.ArrayList;
import java.util.Calendar;


public class WasteApplyActivity extends AppCompatActivity {


    private ApplyInfo applyInfo;
    private EditText editText_user_name, editText_phone_num, editText_address, editText_address_detail;
    private TextView textView_count, textView_all_fee, textView_date, textView_time;;
    private ListView listView_applied_waste;
    private Button button_cancle, button_next;
    private String receiveMsg;
    private WebView mWebView; // 웹뷰 선언
    private WebSettings mWebSettings; //웹뷰세팅
    EditText editText;

    private String user_name, phone_num, address, address_detail;
    private int total_fee;

    //추가 05 14
    private ArrayList<String> LIST_MENU = new ArrayList<>();
    private ArrayList<WasteInfoItem> waste_basket;
    private WasteInfoItem wasteInfoItem;
    private int position;

    //추가 05 21
    private String date;//날짜 받아온 결과
    private String time;//시간 받아온 결과

    private int inputHour = -1;//사용자가 지정한 시간
    private int inputYear = -1;//사용자가 지정한 년도
    private int inputMonth = -1;//사용자가 지정한 월
    private int inputDay = -1;//사용자가 지정한 일

    Calendar calendar = Calendar.getInstance();
    private final int year = calendar.get(Calendar.YEAR);//안드로이드 에뮬레이터의 현재 시간
    private final int month = calendar.get(Calendar.MONTH);
    private final int day = calendar.get(Calendar.DAY_OF_MONTH);
    private final int hour = calendar.get(Calendar.HOUR_OF_DAY);//시간을 24시간으로
    private final int minute = calendar.get(Calendar.MINUTE);

    Calendar minDate = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener setDateListener;
    TimePickerDialog.OnTimeSetListener setTimeListner;

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waste_apply);
        //가져오기
        editText_address = findViewById(R.id.edit_address);
        editText_address_detail = findViewById(R.id.edit_address_detail);
        editText_user_name = findViewById(R.id.edit_user_name);
        editText_phone_num = findViewById(R.id.edit_phone_num);
        textView_date = findViewById(R.id.edit_date);
        textView_time = findViewById(R.id.edit_time);
        button_cancle = findViewById(R.id.button_cancle);
        button_next = findViewById(R.id.button_next);
        listView_applied_waste = findViewById(R.id.waste_list_view);
        textView_count = findViewById(R.id.tv_count);
        textView_all_fee = findViewById(R.id.tv_fee);

        //

        Intent intent_get = getIntent();
        waste_basket = (ArrayList<WasteInfoItem>) intent_get.getSerializableExtra("wastebasket");
        position = intent_get.getExtras().getInt("position");
        wasteInfoItem = waste_basket.get(position);
        System.out.println(waste_basket.size());
        System.out.println(wasteInfoItem.getWaste_name());
        System.out.println(position);


       /* for (int i = 0; i <= position; i++) {

            LIST_MENU.add(waste_basket.get(i).getWaste_size());
            System.out.println(waste_basket.get(i).getWaste_size());
            System.out.println(i);
            System.out.println(LIST_MENU.get(i));
        }

        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, LIST_MENU);
        listView_applied_waste.setAdapter(adapter);
        //*/

        //리스트 뷰 만들기
        WasteListAdapter adapter;

        adapter = new WasteListAdapter(waste_basket);

        listView_applied_waste.setAdapter(adapter);
        total_fee = 0;

        for (int i = 0; i < waste_basket.size(); i++) {
            total_fee += waste_basket.get(i).getWaste_fee();
        }
        textView_all_fee.setText(String.valueOf(total_fee));
        textView_count.setText(String.valueOf(waste_basket.size()));

        setListViewHeightBasedOnChildren(listView_applied_waste);
        //// 신청 버튼 클릭시
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WasteApplyActivity.this, PaymentActivity.class);
                sp = getSharedPreferences("profile", Activity.MODE_PRIVATE);
                System.out.println("rudfhr" + total_fee + waste_basket.size() + waste_basket.get(0).getWaste_name());
                intent.putExtra("user",sp.getString("token",""));
                intent.putExtra("total_fee", String.valueOf(total_fee));
                intent.putExtra("size", String.valueOf(waste_basket.size()));
                intent.putExtra("name", waste_basket.get(0).getWaste_name());
                startActivity(intent);

                //날짜 선택


//                user_name = editText_user_name.getText().toString();
//                phone_num = editText_phone_num.getText().toString();
//                address = editText_address.getText().toString();
//                address_detail = editText_address_detail.getText().toString();
//                date = editText_date.getText().toString();
//
//                if (user_name.equals("") || phone_num.equals("") || address.equals("") || address_detail.equals("") || date.equals("")) {
//                    if (user_name.equals("")) {
//                        editText_user_name.requestFocus();
//                        toastMessage("이름을 작성해주세요.");
//                    } else if (phone_num.equals("")) {
//                        editText_phone_num.requestFocus();
//                        toastMessage("휴대폰 번호를 작성해주세요.");
//                    } else if (address.equals("")) {
//                        editText_address.requestFocus();
//                        toastMessage("배출 주소를 작성해주세요.");
//                    } else if (address_detail.equals("")) {
//                        editText_address_detail.requestFocus();
//                        toastMessage("상세 주소를 작성해주세요.");
//                    } else {
//                        editText_date.requestFocus();
//                        toastMessage("배출 날짜를 작성해주세요.");
//                    }
//                } //입력이 하나라도 비었을때
//                else {
//                    System.out.println(user_name);
//                    finish();
//                    Intent intent = new Intent(WasteApplyActivity.this, MainActivity.class);
//                    startActivity(intent);
//                }
//

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

        textView_date.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        WasteApplyActivity.this, setDateListener, year, month, day);
                minDate.add(Calendar.DATE, 1); //최소 날짜가 하루 다음
                datePickerDialog.getDatePicker().setMinDate(minDate.getTime().getTime()); // 최소 날짜 설정
                datePickerDialog.show();
            }
        });

        setDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                if (month < 10 && dayOfMonth < 10) //파싱하기 위한 조건과 맞게 변경한 날짜 (ex)2019-11-10
                    date = year + "-0" + month + "-0" + dayOfMonth;
                else if (dayOfMonth < 10)
                    date = year + "-" + month + "-0" + dayOfMonth;
                else if (month < 10)
                    date = year + "-0" + month + "-" + dayOfMonth;
                else
                    date = year + "-" + month + "-" + dayOfMonth;

                inputYear = year;//사용자가 지정한 년,월,일 삽입
                inputMonth = month;
                inputDay = dayOfMonth;
                textView_date.setText(date);

            }
        };

        //시간 선택
        textView_time.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        WasteApplyActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        , setTimeListner, hour, minute, false);//true로 하면 24시간 //false로 하면 오전/오후 선택
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                timePickerDialog.show();
            }
        });

        setTimeListner = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if(hourOfDay <=9) {
                    hourOfDay = 9;
                    minute = 0;
                    toastMessage("9시 이후에 배출 가능합니다.");
                }

                if (hourOfDay < 10 && minute < 10) //파싱하기 위한 조건과 맞게 변경한 시간 (ex)22:10:00
                    time = "0" + hourOfDay + ":0" + minute + ":" + "00";
                 else if (hourOfDay < 10)
                    time = "0" + hourOfDay + ":" + minute + ":" + "00";
                 else if (minute < 10)
                    time = hourOfDay + ":0" + minute + ":" + "00";
                    else
                    time = hourOfDay + ":" + minute + ":" + "00";

                textView_time.setText(time);
                inputHour = hourOfDay;//사용자가 지정한 시간 삽입

            }
        };
    }



/*    class WebClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    public void goingWeb(String src) {
        mWebView.loadUrl(src); //loadUrl메서드를 사용하면 웹브라우저를 띄운다.
    }*/

    //뒤로가기 앞으로 가기 기능
//    public void forwardAndBack(View v){
//        if(v.getId() == R.id.goForward){
//            mWebView.goForward();
//        }else if(v.getId() == R.id.goBack){
//            mWebView.goBack();
//        }
//    }

    public class payThread extends Thread {
        @Override
        public void run() {
//            try {
//            OkHttpClient client = new OkHttpClient();
//            Request request = new Request.Builder()
//                    .addHeader("x-api-key", RestTestCommon.API_KEY)
//                    .url(requestURL)
//                    .post(RequestBody.create(MediaType.parse("application/json"), jsonMessage)) //POST로 전달할 내용 설정
//                    .build();


            //
//                String url= "http://"+RequestHttpURLConnection.server_ip+":"+RequestHttpURLConnection.server_port+"/KakaoPay/";
//                OkHttpClient client = new OkHttpClient().newBuilder()
//                        .build();
//                MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded,application/x-www-form-urlencoded");
//                RequestBody body = RequestBody.create(mediaType, "cid=TC0ONETIME&partner_order_id=1001&partner_user_id=gorany&item_name=test&quantity=1&total_amount=22230&tax_free_amount=0&approval_url=http://localhost:8000&cancel_url=http://localhost:8000&fail_url=http://localhost:8000");
//                Request request = new Request.Builder()
//                        .url(url)
//                        .method("POST", body)
//                        .addHeader("Authorization", "KakaoAK 07bd56b63267b53895005b8792088d79")
//                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
//                        .build();
//                ///
//                System.out.println("err1");
//                //동기 처리시 execute함수 사용
//                Response response = client.newCall(request).execute();
//                //출력
//                String message = response.body().toString();
//                System.out.println("ssook"+message);
//                //JSONObject jo1 = new JSONObject(message);
//                System.out.println("err2");
//                //System.out.println(jo1.getString("android_app_scheme") + "111ssook");
//                System.out.println(message + "ssook");
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_VIEW);
//                intent.addCategory(Intent.CATEGORY_BROWSABLE);
//                intent.addCategory(Intent.CATEGORY_DEFAULT);
//                //intent.setData(Uri.parse(jo1.getString("android_app_scheme")));
//                startActivity(intent);
//            } catch (Exception e) {
//                System.out.println(e+"여기서에러");
        }
    }

    public void check_validate() {
        user_name = editText_user_name.getText().toString();
        phone_num = editText_phone_num.getText().toString();
        address = editText_address.getText().toString();
        address_detail = editText_address_detail.getText().toString();
        date = textView_date.getText().toString();

        if (user_name.equals("") || phone_num.equals("") || address.equals("") || address_detail.equals("") || date.equals("")) {
            if (user_name.equals("")) {
                editText_user_name.requestFocus();
                toastMessage("이름을 작성해주세요.");
            } else if (phone_num.equals("")) {
                editText_phone_num.requestFocus();
                toastMessage("휴대폰 번호를 작성해주세요.");
            } else if (address.equals("")) {
                editText_address.requestFocus();
                toastMessage("배출 주소를 작성해주세요.");
            } else if (address_detail.equals("")) {
                editText_address_detail.requestFocus();
                toastMessage("상세 주소를 작성해주세요.");
            } else {
                textView_date.requestFocus();
                toastMessage("배출 날짜를 작성해주세요.");
            }
        } //입력이 하나라도 비었을때
        else {
            System.out.println(user_name);
            finish();
            Intent intent = new Intent(WasteApplyActivity.this, MainActivity.class);
            startActivity(intent);
        }


    }


// }//onCreate

    private void toastMessage(String string) {
        Toast myToast = Toast.makeText(this.getApplicationContext(), string, Toast.LENGTH_SHORT);
        myToast.show();
    }


    //리스트뷰 사이즈 조정
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            //listItem.measure(0, 0);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight;
        listView.setLayoutParams(params);

        listView.requestLayout();
    }
    
}


