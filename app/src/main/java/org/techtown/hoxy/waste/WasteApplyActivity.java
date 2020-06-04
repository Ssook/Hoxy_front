package org.techtown.hoxy.waste;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.kakao.util.helper.log.Logger;

import org.techtown.hoxy.CodeActivity;
import org.techtown.hoxy.MainActivity;
import org.techtown.hoxy.R;
import org.techtown.hoxy.community.CommentAllViewActivity;
import org.techtown.hoxy.login.LoginActivity;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class WasteApplyActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private ApplyInfo applyInfo;
    private EditText editText_user_name, editText_phone_num, editText_address, editText_address_detail;
    private TextView textView_count, textView_all_fee, textView_date, textView_time;
    private ListView listView_applied_waste;
    private Button button_cancle, button_next;
    private String receiveMsg;
    private WebView mWebView; // 웹뷰 선언
    private WebSettings mWebSettings; //웹뷰세팅

    EditText editText;
    ApplyInfo info_apply=new ApplyInfo();

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




    DatePickerDialog.OnDateSetListener setDateListener;
    TimePickerDialog.OnTimeSetListener setTimeListner;

    private AppBarConfiguration mAppBarConfiguration;
    private NavigationView navigationView;
    private SharedPreferences sp, sp2;
    private View nav_header_view;
    private TextView nav_header_id_text;
    private ImageView profile;
    private DrawerLayout drawer;
    ActionBarDrawerToggle toggle;

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
//        listView_applied_waste = findViewById(R.id.waste_list_view);
        textView_count = findViewById(R.id.tv_count);
        textView_all_fee = findViewById(R.id.tv_fee);

        //
        Toolbar toolbar = findViewById(R.id.toolbar5);
        sp = getSharedPreferences("profile", Activity.MODE_PRIVATE);
        sp2 = getSharedPreferences("apply", Activity.MODE_PRIVATE);
        setSupportActionBar(toolbar);
        setView_NavHeader();
        setView_Profile();
        setApplyInfo();


        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
// Passing each menu ID as a set of Ids because each
// menu should be considered as top level destinations.

        setView_Drawer(toolbar);


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_community, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
//NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(this);

//


        Intent intent_get = getIntent();
        waste_basket = (ArrayList<WasteInfoItem>) intent_get.getSerializableExtra("wastebasket");

        System.out.println("jy_test : " + waste_basket.get(0).getWaste_No());
        position = intent_get.getExtras().getInt("position");
        wasteInfoItem = waste_basket.get(position);
        System.out.println(waste_basket.size());
        System.out.println(wasteInfoItem.getWaste_name());
        System.out.println(position);


        //////////// RecyclerView ////////////////
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        int ITEM_SIZE = waste_basket.size();

        List<Item> items = new ArrayList<>();
        Item[] item = new Item[ITEM_SIZE];
        for(int i = 0; i < ITEM_SIZE; i++) {
//            Bitmap waste_bitmap = BitmapFactory.decodeByteArray(waste_basket.get(i).getWaste_bitmap(), 0, waste_basket.get(i).getWaste_bitmap().length);
            item[i] = new Item( waste_basket.get(i).getWaste_name(),waste_basket.get(i).getWaste_fee());
        }
        
        for (int i = 0; i < ITEM_SIZE; i++) {
            items.add(item[i]);
        }

        recyclerView.setAdapter(new RecyclerAdapter(getApplicationContext(), items, R.layout.activity_waste_apply));
        ////////////////////////////////////////////
        //리스트 뷰 만들기
        /*WasteListAdapter adapter;

        adapter = new WasteListAdapter(waste_basket);

        listView_applied_waste.setAdapter(adapter);
        */

        total_fee = 0;

        for (int i = 0; i < waste_basket.size(); i++) {
            total_fee += waste_basket.get(i).getWaste_fee();
        }
        textView_all_fee.setText(String.valueOf(total_fee));
        textView_count.setText(String.valueOf(waste_basket.size()));

//        setListViewHeightBasedOnChildren(listView_applied_waste);
        //// 신청 버튼 클릭시
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //여기서 널체크 해줘야댐

                if (!check_validate()){
                    createApplyInfo();
                saveShared(user_name, phone_num, address, address_detail);
                if(total_fee!=0) {
                    createApplyInfo();
                    Intent intent = new Intent(WasteApplyActivity.this, PaymentActivity.class);
                    sp = getSharedPreferences("profile", Activity.MODE_PRIVATE);
                    sp2 = getSharedPreferences("apply", Activity.MODE_PRIVATE);

                    System.out.println("rudfhr" + total_fee + waste_basket.size() + waste_basket.get(0).getWaste_name() + "Waste_No : " + waste_basket.get(0).getWaste_No());

                    intent.putExtra("apply_info", info_apply);
                    intent.putExtra("user", sp.getString("token", ""));
                    intent.putExtra("total_fee", String.valueOf(total_fee));
                    intent.putExtra("size", String.valueOf(waste_basket.size()));
                    intent.putExtra("name", waste_basket.get(0).getWaste_name());
                    intent.putExtra("wastebasket", waste_basket);

                    startActivity(intent);
                }
                else if (total_fee==0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(WasteApplyActivity.this);
                    builder.setTitle("알림").setMessage("수수료가 0원인 신청은 불가능합니다.");
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }

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

        //날짜 선택
        textView_date.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        WasteApplyActivity.this, setDateListener, year, month, day);
                Calendar minDate = Calendar.getInstance();
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
                if (hourOfDay <= 9) {
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
    public void createApplyInfo(){
        info_apply.setAddress(editText_address.getText()+" "+editText_address_detail.getText());
        info_apply.setApply_fee(total_fee);
        info_apply.setApply_date(textView_date.getText().toString());
        info_apply.setUser_No(sp.getString("token",""));
        info_apply.setPhone_No(editText_phone_num.getText().toString());
        info_apply.setUser_name(editText_user_name.getText().toString());

    }
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

    public boolean check_validate() {
        user_name = editText_user_name.getText().toString();
        phone_num = editText_phone_num.getText().toString();
        address = editText_address.getText().toString();
        address_detail = editText_address_detail.getText().toString();
        date = textView_date.getText().toString();
        time = textView_time.getText().toString();

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
            } else if(textView_date.equals("")){
                textView_date.requestFocus();
                toastMessage("배출 날짜를 작성해주세요.");
            }
            else{
                textView_time.requestFocus();
                toastMessage("배출 시각을 작성해주세요");
            }
        } //입력이 하나라도 비었을때
        else {
            return false;
        }

        return true;
    }


// }//onCreate

    private void toastMessage(String string) {
        Toast myToast = Toast.makeText(this.getApplicationContext(), string, Toast.LENGTH_SHORT);
        myToast.show();
    }


    //리스트뷰 사이즈 조정
  /*  public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);

        listView.requestLayout();
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_settings) {
            onClickLogout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickLogout() {
        UserManagement.getInstance().requestUnlink(new UnLinkResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.e("successclosed", "카카오 로그아웃 onSessionClosed");
                System.out.println(errorResult + "????");
            }

            @Override
            public void onNotSignedUp() {
                Log.e("session on not signedup", "카카오 로그아웃 onNotSignedUp");
            }

            @Override
            public void onSuccess(Long result) {
                Log.e("session success", "카카오 로그아웃 onSuccess");

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void setView_NavHeader() {//은석
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        nav_header_view = navigationView.getHeaderView(0);
        nav_header_id_text = (TextView) nav_header_view.findViewById(R.id.user_name);
        nav_header_id_text.setText(sp.getString("name", ""));


    }
    private  void setApplyInfo(){
        editText_user_name = findViewById(R.id.edit_user_name);
        editText_phone_num = findViewById(R.id.edit_phone_num);
        editText_address = findViewById(R.id.edit_address);
        editText_address_detail = findViewById(R.id.edit_address_detail);



        new Thread() {
            public void run() {
                System.out.println("!!!!test!!!!" + sp2);
                user_name = sp2.getString("name", "");
                phone_num = sp2.getString( "phone_num" , "");
                address = sp2.getString("address", "");
                address_detail = sp2.getString("address2", "");

                Handler mHandler = new Handler(Looper.getMainLooper());
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 사용하고자 하는 코드
                           editText_user_name.setText(user_name);
                           editText_phone_num.setText(phone_num);
                           editText_address.setText(address);
                           editText_address_detail.setText(address_detail);
                    }
                }, 0);


            }
        }.start();

    }

    private void setView_Profile() {//은석
        profile = nav_header_view.findViewById(R.id.profileimage);

        String urlStr;
        urlStr = sp.getString("image_url", "");
        new Thread() {
            public void run() {
                try {
                    System.out.println("test!" + sp);
                    String urlStr = sp.getString("image_url", "");
                    URL url = new URL(urlStr);
                    URLConnection conn = url.openConnection();
                    conn.connect();
                    BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                    final Bitmap bm = BitmapFactory.decodeStream(bis);
                    bis.close();
                    if (bm == null) {
                    }
                    Handler mHandler = new Handler(Looper.getMainLooper());
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // 사용하고자 하는 코드
                            if (bm != null) {
                                profile.setImageBitmap(bm);
                            } else return;
                        }
                    }, 0);


                } catch (IOException e) {
                    Logger.e("Androes", " " + e);
                }

            }
        }.start();


    }


    private void setView_Drawer(Toolbar toolbar) {
        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            //글쓰기 완료 후 전환 시 액티비티가 남지 않게 함
            //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            // intent.putExtra("태그","전체");
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_community) {
            Intent intent = new Intent(getApplicationContext(), CommentAllViewActivity.class);
            //글쓰기 완료 후 전환 시 액티비티가 남지 않게 함
            //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            //intent.putExtra("태그","전체");
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_slideshow) {

        }
        drawer = findViewById(R.id.drawer_layout);//??
        drawer.closeDrawer(GravityCompat.START);
        return false;

    }

    private void saveShared(String name, String phone_num, String address, String address_detail) {
        SharedPreferences pref = getSharedPreferences("apply", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("name", name);
        editor.putString("phone_num", phone_num);
        editor.putString("address", address);
        editor.putString("address2", address_detail);

        System.out.println( name + phone_num + address + address_detail);

        editor.apply();
    }

}


