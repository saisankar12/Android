package com.example.saisankar.roomdatabase;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {


    public HomeFragment() {
        // Required empty public constructor
    }

    Button add,view,update,delete;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_home, container, false);
        add=v.findViewById(R.id.insertuser);
        view=v.findViewById(R.id.viewuser);
        delete=v.findViewById(R.id.deleteuser);
        update=v.findViewById(R.id.updateuser);
        add.setOnClickListener(this);
        view.setOnClickListener(this);
        delete.setOnClickListener(this);
        update.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {

      /*  switch (v.getId()){
            case R.id.insertuser:
                MainActivity.manager.beginTransaction().replace(R.id.fragment_container,new InsertFragment())
                        .addToBackStack(null).commit();
                break;
            case R.id.viewuser:
                MainActivity.manager.beginTransaction().replace(R.id.fragment_container,new ReadFragment()).addToBackStack(null).commit();
                break;

            case R.id.deleteuser:
                MainActivity.manager.beginTransaction().replace(R.id.fragment_container,new DeleteFragment()).addToBackStack(null).commit();
                break;
            case R.id.updateuser:
                MainActivity.manager.beginTransaction().replace(R.id.fragment_container,new UpdateFragment()).addToBackStack(null).commit();
                break;


        }*/

    }
}
