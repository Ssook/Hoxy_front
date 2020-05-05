package org.techtown.hoxy.waste;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.techtown.hoxy.R;

import java.util.ArrayList;

public class WasteApplyActivity extends AppCompatActivity {

    private ArrayList<WasteInfoItem> apply_waste_list;
    private ApplyInfo applyInfo;
    private EditText editText_user_name, editText_phone_num, editText_address, editText_address_detail, editText_date;
    private ListView listView_applied_waste;
    private Button button_cancle, button_next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waste_apply);

    }
}
