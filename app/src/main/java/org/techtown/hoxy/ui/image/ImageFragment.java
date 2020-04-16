package org.techtown.hoxy.ui.image;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.content.ContentResolver;



import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import org.techtown.hoxy.MainActivity;
import org.techtown.hoxy.R;

import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

public class ImageFragment extends Fragment {




    public static  ImageFragment newinstance(){
        return new ImageFragment();
    }

    private static final int REQUEST_CODE = 0;
    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_image, container, false);
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE);

        TextView textView = root.findViewById(R.id.textView);
        textView.setText("히융..");

       imageView = (ImageView) root.findViewById(R.id.imageView);





        return root;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == REQUEST_CODE) {
            System.out.println("1");
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    System.out.println("2");
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in =  ((MainActivity)getActivity()).getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    // 이미지 표시
                    Glide.with(this).load(img).into(imageView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
