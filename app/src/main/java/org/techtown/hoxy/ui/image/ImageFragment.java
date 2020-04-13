package org.techtown.hoxy.ui.image;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.techtown.hoxy.R;

public class ImageFragment extends Fragment {
    public static  ImageFragment newinstance(){
        return new ImageFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_image, container, false);

        TextView textView = root.findViewById(R.id.textView);
        textView.setText("히융..");

        return root;
    }

}
