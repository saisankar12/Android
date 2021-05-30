package com.example.saisankar.servicetest;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.saisankar.servicetest.database.UserModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class InsertFragment extends Fragment implements View.OnClickListener {


    public InsertFragment() {
        // Required empty public constructor
    }

EditText iid,iname,imail,imobile;
    Button isave;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_insert, container, false);
        iid=v.findViewById(R.id.adduserid);
        iname=v.findViewById(R.id.addusername);
        imail=v.findViewById(R.id.addusermail);
        imobile=v.findViewById(R.id.addusermobile);
        isave=v.findViewById(R.id.adduser);
        isave.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {

        if (v.getId()==R.id.adduser){

            String user_Id=iid.getText().toString();
            String user_Name=iname.getText().toString();
            String user_Mail=imail.getText().toString();
            String user_Mobile=imobile.getText().toString();

            UserModel userModel=new UserModel();
            userModel.setUid(user_Id);
            userModel.setUname(user_Name);
            userModel.setUmail(user_Mail);
            userModel.setUmobile(user_Mobile);

            MainActivity.userDataBase.userDao().insertuserdata(userModel);

            if (userModel!=null){
                Toast.makeText(getActivity(), "User Details Saved Sucessfully", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getActivity(), "Not Saved", Toast.LENGTH_SHORT).show();
            }

            iid.setText("");
            iname.setText("");
            imail.setText("");
            imobile.setText("");


        }
    }
}
