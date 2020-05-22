package org.techtown.hoxy.waste;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.techtown.hoxy.R;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class WasteListAdapter  extends BaseAdapter {

    private ArrayList<WasteInfoItem> wasteInfoItems = new ArrayList<>();

    public WasteListAdapter(ArrayList<WasteInfoItem> wasteInfoItems){
        this.wasteInfoItems = wasteInfoItems;

    }

    @Override
    public int getCount() {
        return wasteInfoItems.size();
    }

    @Override
    public Object getItem(int position) {
        return  wasteInfoItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        final Context context = parent.getContext();

        if(convertView == null)
        {
            LayoutInflater inflater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.waste_item_view, parent, false);
        }

        TextView tv_name = convertView.findViewById(R.id.tv_waste_name);
        TextView tv_size = convertView.findViewById(R.id.tv_waste_size);
        TextView tv_fee = convertView.findViewById(R.id.tv_waste_fee);

       WasteInfoItem wasteInfoItem = wasteInfoItems.get(position);

       tv_name.setText(wasteInfoItem.getWaste_name());
       tv_size.setText(wasteInfoItem.getWaste_size());
       tv_fee.setText(String.valueOf(wasteInfoItem.getWaste_fee()));

        return convertView;
    }
}
