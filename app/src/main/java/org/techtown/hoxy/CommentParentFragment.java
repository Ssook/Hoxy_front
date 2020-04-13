package org.techtown.hoxy;

import android.content.Context;
import android.os.Bundle;
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

        CommentParentFragment cpf = new CommentParentFragment();
        FragmentTransaction childFt = getChildFragmentManager().beginTransaction();

        if (!cpf.isAdded()) {
            childFt.replace(R.id.container, cpf);
            childFt.addToBackStack(null);
            childFt.commit();
        }


        return rootView;
    }
    public void onCommandFromMain(String command,String data){

    }
}
