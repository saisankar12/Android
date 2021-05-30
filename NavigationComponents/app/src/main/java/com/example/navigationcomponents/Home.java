package com.example.navigationcomponents;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    public Home() {
        // Required empty public constructor
    }

    Button b1,b2,b3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        b1=v.findViewById(R.id.chats);
        b2=v.findViewById(R.id.calls);
        b3=v.findViewById(R.id.status);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            NavController controller=Navigation.findNavController(v);
            controller.navigate(R.id.action_home2_to_chats);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller =Navigation.findNavController(v);
                controller.navigate(R.id.action_home2_to_calls);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    NavController controller=Navigation.findNavController(v);
                    controller.navigate(R.id.action_home2_to_status);
            }
        });


        return v;
    }
}
