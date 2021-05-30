package com.example.saisankar.servicetest;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.saisankar.servicetest.database.UserModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewFragment extends Fragment {


    public ViewFragment() {
        // Required empty public constructor
    }
     RecyclerView rv;

    List<UserModel> userModels;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_view, container, false);
        rv=v.findViewById(R.id.recycler);
        userModels=MainActivity.userDataBase.userDao().getUserData();
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(new ViewDataAdapter(getActivity(),userModels));

        return v;
    }

}
