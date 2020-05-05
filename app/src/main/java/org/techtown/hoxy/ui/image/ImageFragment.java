/*
package org.techtown.hoxy.ui.image;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

*/
/*
-경록
 메인 단에서 갤러리 호출을 위한 프레그먼트
* *//*

public class ImageFragment extends Fragment {



    public static  ImageFragment newinstance(){
        return new ImageFragment();
    }

    private static final int REQUEST_CODE = 0;
    private ImageView trash_ImageView;
    private TextView trash_textView;
    private Button again_button, next_button;


    private Bitmap trash_bitmap;
    private String trashName ;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_image, container, false);
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE);


        trash_textView = root.findViewById(R.id.textView);


        trash_ImageView = (ImageView) root.findViewById(R.id.imageView);

        again_button = root.findViewById(R.id.button);
        next_button = root.findViewById(R.id.button2);
    //// 다시
        again_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                ((MainActivity)getActivity()).replaceFragment(ImageFragment.newinstance());
            }
        });
    //// 다음
        next_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                ((MainActivity)getActivity()).replaceFragment(ResultFragment.newinstance());
            }
        });






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
                    trash_bitmap = BitmapFactory.decodeStream(in);
                    in.close();
                    TrashName.setTrash("쇼파일껄?");
                    trashName = TrashName.getTrash();
                    trash_textView.setText(trashName);
                    // 이미지 표시
                    Glide.with(this).load(trash_bitmap).into(trash_ImageView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
*/
