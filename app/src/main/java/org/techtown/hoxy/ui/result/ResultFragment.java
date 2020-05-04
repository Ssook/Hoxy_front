package org.techtown.hoxy.ui.result;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.techtown.hoxy.MainActivity;
import org.techtown.hoxy.R;
import org.techtown.hoxy.ui.home.HomeFragment;
import org.techtown.hoxy.ui.image.ImageFragment;
import org.techtown.hoxy.ui.image.TrashName;

public class ResultFragment extends Fragment {

    private String trashName = TrashName.getTrash();
    private String[] trashSizeArray = new String[]{"Test1" ,"Test2" ,"Test3" ,"Test4"};
    private Button next_button, again_button, howto_button;
    private TextView trash_code_textView, trash_fee_textView;
    private String trash_code, trash_fee;
    public static ResultFragment newinstance() {
        return new ResultFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View root = inflater.inflate(R.layout.fragment_result, container, false);
        Spinner spinner = (Spinner)root.findViewById(R.id.spinner);

        trash_code_textView = root.findViewById(R.id.textView2);
        trash_fee_textView  = root.findViewById(R.id.textView4);
        again_button = root.findViewById(R.id.button3);
        howto_button = root.findViewById(R.id.button6);
        next_button = root.findViewById(R.id.button5);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //textView1.setText(parent.getItemAtPosition(position).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //textView.setText(trashName);
            }
        });


        //// 다시
        again_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                ((MainActivity)getActivity()).replaceFragment(HomeFragment.newinstance());
            }
        });
        //// 다음
        next_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                ((MainActivity)getActivity()).replaceFragment(ResultFragment.newinstance());
            }
        });

        howto_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        return root;
    }
}