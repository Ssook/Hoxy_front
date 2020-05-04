package org.techtown.hoxy.ui.result;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.techtown.hoxy.R;

public class ResultFragment extends Fragment {

    String trash = "trash";
    final String[] Example = new String[]{"Test1" ,"Test2" ,"Test3" ,"Test4"};

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
        final View root = inflater.inflate(R.layout.fragment_result, container, false);
        Spinner spinner = (Spinner)root.findViewById(R.id.spinner);

        final TextView textView = root.findViewById(R.id.textView2);
        final TextView textView1 = root.findViewById(R.id.textView4);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textView1.setText(parent.getItemAtPosition(position).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                textView.setText(trash);
            }
        });
        return root;
    }
}