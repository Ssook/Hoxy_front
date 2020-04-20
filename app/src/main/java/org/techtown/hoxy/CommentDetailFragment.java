package org.techtown.hoxy;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CommentDetailFragment extends Fragment {

    FragmentCallback callback;

    public static CommentDetailFragment newInstance(){
        return new CommentDetailFragment();
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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_comment_detail, container, false);

        return rootView;
    }

    void backToAllView(){

    }


}
