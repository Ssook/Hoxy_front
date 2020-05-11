package org.techtown.hoxy;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.kakao.util.helper.log.Logger;


import org.techtown.hoxy.community.CommentAllViewActivity;
import org.techtown.hoxy.waste.ResultActivity;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    private NavigationView navigationView;
    private SharedPreferences sp;

    private View nav_header_view;
    private TextView nav_header_id_text;
    private ImageView profile;
    private DrawerLayout drawer;
    ActionBarDrawerToggle toggle;

    private String text_title;
    private String text_content1;
    private String text_content2;
    private String text_content3;
    private String text_content1_time;
    private String text_content2_time;
    private String text_content3_time;
    private TextView home_main_textView,content_textView,content2_textView ,content3_textView, content1_time_textView, content2_time_textView, content3_time_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        sp = getSharedPreferences("profile", Activity.MODE_PRIVATE);
        setSupportActionBar(toolbar);

        setView_NavHeader();

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

        if (sp == null) {
            System.out.println("??????");
        }
        setView_Profile();
       // setView_Navigationview();

        //

      /*  FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment,HomeFragment.newinstance()).commit();*/
        //

        //

        home_main_textView = findViewById(R.id.text_home); //혹시 어떻게 버리지??
        content_textView = findViewById(R.id.text_content1); // 첫번째 게시글
        content2_textView = findViewById(R.id.text_content2); // 두번째 게시글
        content3_textView = findViewById(R.id.text_content3); // 세번째 게시글
        content1_time_textView = findViewById(R.id.text_content1_time); // 첫번째 게시글 작성일
        content2_time_textView = findViewById(R.id.text_content2_time); // 첫번째 게시글 작성일
        content3_time_textView = findViewById(R.id.text_content3_time); // 첫번째 게시글 작성일

        text_title = "혹시 어떻게 버리지..?";

        text_content1 = "1번 게시글";
        text_content2 = "2번 게시글";
        text_content3 = "3번 게시글";

        text_content1_time = "2020-04-08";
        text_content2_time = "2020-04-09";
        text_content3_time = "2020-04-10";


        home_main_textView.setText(text_title);
        content_textView.setText(text_content1);
        content2_textView.setText(text_content2);
        content3_textView.setText(text_content3);
        content1_time_textView.setText(text_content1_time);
        content2_time_textView.setText(text_content2_time);
        content3_time_textView.setText(text_content3_time);




        ////////////////갤러리로 넘어가기
        ImageButton imageButton = findViewById(R.id.galleryButton);
        imageButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                System.out.println("ㅎㅇㅎㅇ");

                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra("intent_text", "image");
                startActivity(intent);
            }
        });
        //////////////////////
        //카메라 클릭시
        ImageButton cameraButton = findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra("intent_text", "camera");
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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




    //    public void onon(View view){
//        Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
//        //글쓰기 완료 후 전환 시 액티비티가 남지 않게 함
//        startActivity(intent);
//    }
    /*public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_comment) {
            Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
            startActivity(intent);

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }*/
   /* private void setView_Navigationview() {
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }*/

    //

   /* public void replaceFragment(Fragment fragment){//경록

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentManager.popBackStack();
        fragmentTransaction.replace(R.id.nav_host_fragment,fragment);
        fragmentTransaction.addToBackStack(null).commit();

    }*/

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
        } else if (id == R.id.nav_community) {
            Intent intent = new Intent(getApplicationContext(), CommentAllViewActivity.class);
            //글쓰기 완료 후 전환 시 액티비티가 남지 않게 함
            //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            //intent.putExtra("태그","전체");
            startActivity(intent);
            finish();
        }else if (id == R.id.nav_slideshow) {

        }
        drawer = findViewById(R.id.drawer_layout);//??
        drawer.closeDrawer(GravityCompat.START);
        return false;

    }
}
