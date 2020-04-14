package org.techtown.hoxy;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class CommentParentFragment extends Fragment {
    public static CommentParentFragment newInstance() {
        return new CommentParentFragment();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_comment_parent,container,false);


        //CommentParentFragment cpf = new CommentParentFragment();
        Log.e("부모 프레그먼트 로그","errrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");

        //FragmentTransaction childFt = getChildFragmentManager().beginTransaction();
        Log.e("부모 프레그먼트 트렌섹션 에러","errrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
/*
        if (!cpf.isAdded()) {
            childFt.add(R.id.container, CommentParentFragment.newInstance());
            Log.e("부모 에드 에러","errrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
            childFt.addToBackStack(null);
            Log.e("부모 에드에러 2","errrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
            childFt.commit();
            Log.e("부모 에드 에러 3","errrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");

        }
*/
        return rootView;
    }
    public void onCommandFromMain(String command,String data){

    }
}
