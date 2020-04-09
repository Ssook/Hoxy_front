package org.techtown.hoxy;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodSession;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends Activity {
    private SessionCallback callback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        callback= new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
        Session.getCurrentSession().checkAndImplicitOpen();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(Session.getCurrentSession().handleActivityResult(requestCode,resultCode,data)){
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }
    private class SessionCallback implements ISessionCallback{

        @Override
        public void onSessionOpened() {
            Log.e("kakao login result", "카카오 로그인 성공 ");
            requestMe();
            redirectMainActivity();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if(exception!=null){
                Logger.e(exception);
            }
        }
    }
    private void requestMe() {
        List<String> keys = new ArrayList<>();
        keys.add("properties.nickname");
        keys.add("properties.profile_image");
        keys.add("properties.thumbnail_image");
        keys.add("kakao_account.email");
        System.out.println(keys+"add");
        UserManagement.getInstance().me(keys, new MeV2ResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Logger.d(message);
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.e("onsuccess close", errorResult.getErrorMessage());
                redirectLoginActivity();
            }

            @Override
            public void onSuccess(MeV2Response response) {
                Log.e("kakao nickname is", response.getNickname());
                System.out.println("user id : " +response.getId());
                System.out.println("thumb"+response.getProperties().get("thumbnail_image"));


                saveShared(response.getId() + "", response.getNickname(), response.getProperties().get("thumbnail_image"));

                redirectMainActivity();
                //profile image 다운로드 안됨
            }
            //onNotSignedUp() 함수 삭제
        });
    }
    protected void redirectMainActivity(){
        final Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    protected void redirectLoginActivity(){
        final Intent intent=new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    private void onClickLogout(){
        UserManagement.getInstance().requestUnlink(new UnLinkResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.e("successclosed", "카카오 로그아웃 onSessionClosed");
            }

            @Override
            public void onNotSignedUp() {
                Log.e("session on not signedup", "카카오 로그아웃 onNotSignedUp");
            }

            @Override
            public void onSuccess(Long result) {
                Log.e("session success", "카카오 로그아웃 onSuccess");
            }
        });
    }
    private void saveShared(String id, String name, String profile_url) {
        SharedPreferences pref = getSharedPreferences("profile", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("token", id);
        editor.putString("name", name);
        editor.putString("image_url", profile_url);
        editor.apply();
    }

}


