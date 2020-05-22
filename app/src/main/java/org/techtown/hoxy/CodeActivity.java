package org.techtown.hoxy;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.kakao.util.helper.log.Logger;

import org.techtown.hoxy.community.CommentAllViewActivity;
import org.techtown.hoxy.login.LoginActivity;
import org.techtown.hoxy.waste.ResultActivity;
import org.techtown.hoxy.waste.WasteApplyActivity;
import org.techtown.hoxy.waste.WasteInfoItem;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class CodeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private String code;
    private TextView codeView;
    private Button btn_clip;
    private Button btn_finish;

    private AppBarConfiguration mAppBarConfiguration;
    private NavigationView navigationView;
    private SharedPreferences sp;
    private View nav_header_view;
    private TextView nav_header_id_text;
    private ImageView profile;
    private DrawerLayout drawer;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

        codeView = findViewById(R.id.tv_codeview);
        btn_clip = findViewById(R.id.bt_clip);
        btn_finish = findViewById(R.id.bt_finish);
        Intent intent = getIntent();
        code = intent.getExtras().getString("code");

        Toolbar toolbar = findViewById(R.id.toolbar7);
        sp = getSharedPreferences("profile", Activity.MODE_PRIVATE);
        setSupportActionBar(toolbar);
        setView_NavHeader();
        setView_Profile();


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


        code = "asdasd";

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
        }
        else if (id == R.id.nav_community) {
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


}

