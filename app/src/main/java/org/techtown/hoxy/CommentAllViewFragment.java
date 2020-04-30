package org.techtown.hoxy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CommentAllViewFragment extends Fragment {

    CommentAdapter adapter;
    FragmentCallback callback;
    Bundle data;
    CommentItem item;
    public static CommentAllViewFragment newInstance(){
        return new CommentAllViewFragment();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof FragmentCallback){
            callback = (FragmentCallback) context;
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        if (callback != null) callback = null;

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_comment_all_view,container,false);

        ListView listView = (ListView) rootView.findViewById(R.id.listView);

        data = new Bundle();


        adapter = new CommentAdapter();

        adapter.addItem(new CommentItem(R.drawable.user1,"앙기모","kss1218"));


        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item = (CommentItem) adapter.getItem(position);
                Toast.makeText(getContext(),item.getUserId()+"선택",Toast.LENGTH_LONG).show();

                onCommand("showDetail",data);

            }
        });

        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showCommentWriteActivity();

                onCommand("show",data);

            }
        });

        return rootView;
    }
    ////////이 아래 부분 건들여야 될듯

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode == 101){
            if(intent!=null){

                String contents = intent.getStringExtra("contents");
                System.out.print(contents);
                adapter.addItem(new CommentItem(R.drawable.user1 ,contents,"김성수"));
                adapter.notifyDataSetChanged();
            }
        }
    }
    public void onCommandToDetail(String command) {
        //textView.setText(data);
    }

    ////////////////////////////////////////////////////////////////////////////////

    /*public void showCommentWriteActivity() {
        //float rating = ratingBar.getRating();

        Intent intent = new Intent(getContext(),CommentWriteActivity.class);
        //intent.putExtra("rating",rating);

        startActivityForResult(intent, 101);
    }
     */
    class CommentAdapter extends BaseAdapter {

        ArrayList<CommentItem> items = new ArrayList<CommentItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(CommentItem item){
            items.add(item);

        }
        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            CommentItemView view = null;
            if(convertView == null){
                view = new CommentItemView(getContext());

            }
            else{
                view = (CommentItemView) convertView;

            }
            CommentItem item = items.get(position);
            view.setUserId(item.getUserId());
            view.setImage(item.getResId());
            view.setComment(item.getComment());
            //view.setTime(item.getTime());

            return view;
        }
    }
    public void onCommand(String command,Bundle data){
        if (command.equals("show")) {
            // 액티비티를 띄우는 경우
            Intent intent = new Intent(getContext(), CommentWriteActivity.class);
            startActivityForResult(intent, 101);
        }
        if (command.equals("showDetail")){
            Intent intent = new Intent(getContext(), CommentDetailActivity.class);

            intent.putExtra("item", item);
            startActivityForResult(intent, 102);
        }
    }
}
