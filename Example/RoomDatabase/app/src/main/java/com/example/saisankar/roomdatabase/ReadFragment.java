package com.example.saisankar.roomdatabase;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReadFragment extends Fragment {
    public ReadFragment() {
        // Required empty public constructor
    }
    TextView idtext,nametext,emailtext;
    RecyclerView rv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_read, container, false);
        idtext=v.findViewById(R.id.user_id);
        rv=v.findViewById(R.id.recycler);
        List<User> userList=MainActivity.myAppDatabase.myDao().getUser();
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(new MyDataAdapter(getActivity(),userList));


        String info="";
        for(User usr:userList){
            int id=usr.getId();
            String name=usr.getName();
            String email=usr.getEmail();

            info=info+"\n\n"+"ID :- "+id+"\t"+"Name :- "+name+"\t"+"Email :- "+email;
        }
       /* nametext=v.findViewById(R.id.user_name);
        emailtext=v.findViewById(R.id.user_email);*/
       idtext.setText(info);

        return v;
    }

}
