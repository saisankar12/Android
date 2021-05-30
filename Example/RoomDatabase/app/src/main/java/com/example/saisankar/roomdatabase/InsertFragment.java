package com.example.saisankar.roomdatabase;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class InsertFragment extends Fragment implements View.OnClickListener{

    public InsertFragment() {
        // Required empty public constructor
    }
    EditText userId,userName,userEmail;
    Button save;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_insert, container, false);
        userId=v.findViewById(R.id.userid);
        userName=v.findViewById(R.id.username);
        userEmail=v.findViewById(R.id.useremail);
        save=v.findViewById(R.id.submit);
        save.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit:
                int user_id= Integer.parseInt(userId.getText().toString());
                String user_name=userName.getText().toString();
                String user_email=userEmail.getText().toString();
                User user=new User();
                user.setId(user_id);
                user.setEmail(user_email);
                user.setName(user_name);
               MainActivity.myAppDatabase.myDao().addUser(user);
                Toast.makeText(getActivity(), "Data Save Sucessfully", Toast.LENGTH_SHORT).show();

                userId.setText("");
                userName.setText("");
                userEmail.setText("");

                MainActivity.manager.beginTransaction().replace(R.id.fragment_container,
                        new ReadFragment()).addToBackStack(null).commit();
        }
    }
}
