package org.techtown.hoxy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CommentAllViewFragment extends Fragment {
    CommentAdapter adapter;
    FragmentCallback callback;

    public static CommentAllViewFragment newInstance(){
        return new CommentAllViewFragment();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_comment_all_view,container,false);

        ListView listView = (ListView) rootView.findViewById(R.id.listView);

        adapter = new CommentAdapter();

        adapter.addItem(new CommentItem(R.drawable.user1,"앙기모","kss1218","21시 10분"));


        listView.setAdapter(adapter);

        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showCommentWriteActivity();
                if(callback != null){
                    callback.onCommand("show");
                }
            }
        });

        return rootView;
    }

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

            return view;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void onCommandFromMain(String command,String data) {
        //textView.setText(data);
    }
}
