package com.example.saisankar.servicetest;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saisankar.servicetest.database.UserModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class DeleteFragment extends Fragment {


    public DeleteFragment() {
        // Required empty public constructor
    }

    EditText did;
    Button delete;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_delete, container, false);
        did=v.findViewById(R.id.deleteuserid);
        delete=v.findViewById(R.id.deleteuser);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user_id=did.getText().toString();
                UserModel userModel=new UserModel();
                userModel.setUid(user_id);

                MainActivity.userDataBase.userDao().deleteuserdata(userModel);
                Toast.makeText(getActivity(), userModel.getUid()+"is Deleted", Toast.LENGTH_SHORT).show();
                did.setText("");
            }
        });
        return v;
    }

}
