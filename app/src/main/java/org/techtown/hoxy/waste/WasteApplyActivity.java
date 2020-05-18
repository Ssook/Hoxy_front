package org.techtown.hoxy.waste;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.hoxy.CodeActivity;
import org.techtown.hoxy.MainActivity;
import org.techtown.hoxy.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class WasteApplyActivity extends AppCompatActivity {


    private ApplyInfo applyInfo;
    private EditText editText_user_name, editText_phone_num, editText_address, editText_address_detail, editText_date;
    private ListView listView_applied_waste;
    private Button button_cancle, button_next;
    private String receiveMsg;
    private WebView mWebView;
    EditText editText;

    private String user_name, phone_num, address, address_detail, date;
    //추가 05 14
    private ArrayList<String> LIST_MENU = new ArrayList<>();
    private ArrayList<WasteInfoItem> waste_basket;
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
        waste_basket = (ArrayList<WasteInfoItem>) intent_get.getSerializableExtra("wastebasket");
        position = intent_get.getExtras().getInt("position");
        wasteInfoItem = waste_basket.get(position);
        System.out.println(waste_basket.size());
        System.out.println(wasteInfoItem.getWaste_name());
        System.out.println(position);


        for (int i = 0; i <= position; i++) {

            LIST_MENU.add(waste_basket.get(i).getWaste_size());
            System.out.println(waste_basket.get(i).getWaste_size());
            System.out.println(i);
            System.out.println(LIST_MENU.get(i));
        }

        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, LIST_MENU);
        listView_applied_waste.setAdapter(adapter);
        //
        setListViewHeightBasedOnChildren(listView_applied_waste);
        //// 신청 버튼 클릭시
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_name = editText_user_name.getText().toString();
                phone_num = editText_phone_num.getText().toString();
                address = editText_address.getText().toString();
                address_detail = editText_address_detail.getText().toString();
                date = editText_date.getText().toString();

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
                        editText_date.requestFocus();
                        toastMessage("배출 날짜를 작성해주세요.");
                    }
                } //입력이 하나라도 비었을때
                else {
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

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(WasteApplyActivity.this, CodeActivity.class);
                startActivity(intent);
              //  payThread t1 = new payThread();
               // t1.start();
                //Intent intent=new Intent();
//                intent.setAction(Intent.ACTION_VIEW);
//                intent.addCategory(Intent.CATEGORY_BROWSABLE);
//                intent.addCategory(Intent.CATEGORY_DEFAULT);
//                intent.setData(Uri.parse("https://mockup-pg-web.kakao.com/v1/b49fde0597270442a94aeafee0244e934455c82eccf26582d7570440572ca98a/aInfo"));
//                startActivity(intent);

            }
        });
/*

        mWebView = (WebView) findViewById(R.id.webView); //레이아웃에서 웹뷰를 가져온다
        mWebView.setWebViewClient(new WebClient()); //액티비티 내부에서 웹브라우저가 띄워지도록 설정
        WebSettings webSettings = mWebView.getSettings(); //getSettings를 사용하면 웹에대헤 다양한 설정을 할 수 있는 WebSettings타입을 가져올 수 있다.
        webSettings.setJavaScriptEnabled(true); //자바스크립트가 사용가능 하도록 설정
*/


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
           try {
//            OkHttpClient client = new OkHttpClient();
//            Request request = new Request.Builder()
//                    .addHeader("x-api-key", RestTestCommon.API_KEY)
//                    .url(requestURL)
//                    .post(RequestBody.create(MediaType.parse("application/json"), jsonMessage)) //POST로 전달할 내용 설정
//                    .build();

               //
               OkHttpClient client = new OkHttpClient().newBuilder()
                       .build();
               MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded,application/x-www-form-urlencoded");
               RequestBody body = RequestBody.create(mediaType, "cid=TC0ONETIME&partner_order_id=1001&partner_user_id=gorany&item_name=test&quantity=1&total_amount=1500&tax_free_amount=0&approval_url=http://172.16.46.22:8000/test/&cancel_url=http://172.16.46.22:8000/test/&fail_url=http://172.16.46.22:8000/test/");

               Request request = new Request.Builder()
                       .url("https://kapi.kakao.com/v1/payment/ready")
                       .method("POST", body)
                       .addHeader("Authorization", "KakaoAK 07bd56b63267b53895005b8792088d79")
                       .addHeader("Content-Type", "application/x-www-form-urlencoded")
                       .addHeader("Content-Type", "application/x-www-form-urlencoded")
                       .build();


               ///
               //동기 처리시 execute함수 사용
               Response response = client.newCall(request).execute();

               //출력
               String message = response.body().string();
               JSONObject jo1 = new JSONObject(message);
               jo1.getString("tms_result");
               System.out.println(jo1.getString("android_app_scheme") + "111ssook");
               System.out.println(message + "ssook");
               Intent intent = new Intent();
               intent.setAction(Intent.ACTION_VIEW);
               intent.addCategory(Intent.CATEGORY_BROWSABLE);
               intent.addCategory(Intent.CATEGORY_DEFAULT);
               intent.setData(Uri.parse(jo1.getString("android_app_scheme")));
               startActivity(intent);


           } catch (Exception e) {
               System.err.println(e.toString());
           }
       }

       public void check_validate() {
           user_name = editText_user_name.getText().toString();
           phone_num = editText_phone_num.getText().toString();
           address = editText_address.getText().toString();
           address_detail = editText_address_detail.getText().toString();
           date = editText_date.getText().toString();

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
                   editText_date.requestFocus();
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
