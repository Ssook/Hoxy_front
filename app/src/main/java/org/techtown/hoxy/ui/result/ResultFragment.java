package org.techtown.hoxy.ui.result;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.techtown.hoxy.R;

public class ResultFragment extends Fragment {

    String trash = "trash";

    public static ResultFragment newinstance() {
        return new ResultFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(getArguments() != null) {
            System.out.println("00");

            Bundle bundle = getArguments();
            trash = bundle.getString("Trash");
        }
        View root = inflater.inflate(R.layout.fragment_result, container, false);

        TextView textView = root.findViewById(R.id.textView2);

        textView.setText(trash);
        return root;
    }
}