package com.example.saisankar.servicetest;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.saisankar.servicetest.database.UserDao;
import com.example.saisankar.servicetest.database.UserModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateFragment extends Fragment implements View.OnClickListener {


    public UpdateFragment() {
        // Required empty public constructor
    }
    EditText uid,uname,umail,umobile;
    Button uupdate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_update, container, false);

        uid=v.findViewById(R.id.updateuserid);
        uname=v.findViewById(R.id.updateusername);
        umail=v.findViewById(R.id.updateusermail);
        umobile=v.findViewById(R.id.updateusermobile);
        uupdate=v.findViewById(R.id.updateuser);

        uupdate.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {

        if (v.getId()==R.id.updateuser){

            String user_Id=uid.getText().toString();
            String user_Name=uname.getText().toString();
            String user_Mail=umail.getText().toString();
            String user_Mobile=umobile.getText().toString();

            UserModel userModel=new UserModel();
            userModel.setUid(user_Id);
            userModel.setUname(user_Name);
            userModel.setUmail(user_Mail);
            userModel.setUmobile(user_Mobile);

            MainActivity.userDataBase.userDao().updateuserdata(userModel);

            if (userModel!=null){
                Toast.makeText(getActivity(), "Update Sucessfully", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getActivity(), "Not Updated", Toast.LENGTH_SHORT).show();
            }

            uid.setText("");
            uname.setText("");
            umail.setText("");
            umobile.setText("");
        }
    }
}
