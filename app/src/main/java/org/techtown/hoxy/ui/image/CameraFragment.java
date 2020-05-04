package org.techtown.hoxy.ui.image;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import org.techtown.hoxy.MainActivity;
import org.techtown.hoxy.R;
import org.techtown.hoxy.ui.result.ResultFragment;
import static android.app.Activity.RESULT_OK;

public class CameraFragment extends Fragment {

    final static int TAKE_PICTURE = 1;

    public static CameraFragment newinstance() {
        return new CameraFragment();
    }

    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_image, container, false);

        TextView textView = root.findViewById(R.id.textView);
        textView.setText("trash");
        ////////////////////////////////////////////////////////
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, TAKE_PICTURE);
        ///////////////////////////////////////////////////
        imageView = (ImageView) root.findViewById(R.id.imageView);

        Button button = root.findViewById(R.id.button);
        Button button2 = root.findViewById(R.id.button2);
        //// 다시
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                ((MainActivity)getActivity()).replaceFragment(CameraFragment.newinstance());
            }
        });
        //// 다음
        button2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                ((MainActivity)getActivity()).replaceFragment(ResultFragment.newinstance());
            }
        });


        //////////////////////////////////////////////////////



        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == RESULT_OK && intent.hasExtra("data")) {
                    Bitmap bitmap = (Bitmap) intent.getExtras().get("data");
                    if (bitmap != null) {
                        Glide.with(this).load(bitmap).into(imageView);
                    }
                }
        }
    }
}