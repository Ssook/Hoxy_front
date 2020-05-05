/*
package org.techtown.hoxy.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import org.techtown.hoxy.MainActivity;
import org.techtown.hoxy.R;
import org.techtown.hoxy.ui.image.CameraFragment;
import org.techtown.hoxy.ui.image.ImageFragment;

public class HomeFragment extends Fragment {

    private String text_title;
    private String text_content1;
    private String text_content2;
    private String text_content3;
    private String text_content1_time;
    private String text_content2_time;
    private String text_content3_time;
    private TextView home_main_textView,content_textView,content2_textView ,content3_textView, content1_time_textView, content2_time_textView, content3_time_textView;

    public static HomeFragment newinstance(){
            return new HomeFragment();
        }

@Override
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
          home_main_textView = root.findViewById(R.id.text_home); //혹시 어떻게 버리지??
          content_textView = root.findViewById(R.id.text_content1); // 첫번째 게시글
          content2_textView = root.findViewById(R.id.text_content2); // 두번째 게시글
          content3_textView = root.findViewById(R.id.text_content3); // 세번째 게시글
          content1_time_textView = root.findViewById(R.id.text_content1_time); // 첫번째 게시글 작성일
          content2_time_textView = root.findViewById(R.id.text_content2_time); // 첫번째 게시글 작성일
          content3_time_textView = root.findViewById(R.id.text_content3_time); // 첫번째 게시글 작성일

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
    ImageButton imageButton = root.findViewById(R.id.galleryButton);
    imageButton.setOnClickListener(new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            System.out.println("ㅎㅇㅎㅇ");

            ((MainActivity)getActivity()).replaceFragment(ImageFragment.newinstance());
        }
    });
    //////////////////////
    //카메라 클릭시
    ImageButton cameraButton = root.findViewById(R.id.cameraButton);
    cameraButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((MainActivity)getActivity()).replaceFragment(CameraFragment.newinstance());
        }
    });
    return root;
    }



    */
/*public String getText_title() {
        return text_title;
    }

    public void setText_title(String text_title) {
        this.text_title = text_title;
    }

    public String getText_content1() {
        return text_content1;
    }

    public void setText_content1(String text_content1) {
        this.text_content1 = text_content1;
    }

    public String getText_content2() {
        return text_content2;
    }

    public void setText_content2(String text_content2) {
        this.text_content2 = text_content2;
    }

    public String getText_content3() {
        return text_content3;
    }

    public void setText_content3(String text_content3) {
        this.text_content3 = text_content3;
    }

    public String getText_content1_time() {
        return text_content1_time;
    }

    public void setText_content1_time(String text_content1_time) {
        this.text_content1_time = text_content1_time;
    }

    public String getText_content2_time() {
        return text_content2_time;
    }

    public void setText_content2_time(String text_content2_time) {
        this.text_content2_time = text_content2_time;
    }

    public String getText_content3_time() {
        return text_content3_time;
    }

    public void setText_content3_time(String text_content3_time) {
        this.text_content3_time = text_content3_time;
    }*//*


}
*/
