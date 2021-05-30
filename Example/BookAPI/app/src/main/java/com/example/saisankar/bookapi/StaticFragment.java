package com.example.saisankar.bookapi;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class StaticFragment extends Fragment {


    public StaticFragment() {
        // Required empty public constructor
    }

    TextView tv;
    String[] data={"sai","sankar","college","Qis","Prakasam"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_static, container, false);

        tv=v.findViewById(R.id.staticid);
        for (String staticda:data){
            tv.append(staticda+"\n \n");
        }
        return v;
    }

}
