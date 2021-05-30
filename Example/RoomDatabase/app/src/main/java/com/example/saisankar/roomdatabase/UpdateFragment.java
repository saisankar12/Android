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
public class UpdateFragment extends Fragment {


    public UpdateFragment() {
        // Required empty public constructor
    }
    EditText userId,userName,userEmail;
    Button save;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_update, container, false);

        userId=v.findViewById(R.id.userid);
        userName=v.findViewById(R.id.username);
        userEmail=v.findViewById(R.id.useremail);
        save=v.findViewById(R.id.update);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int user_id= Integer.parseInt(userId.getText().toString());
                String user_name=userName.getText().toString();
                String user_email=userEmail.getText().toString();

                User user=new User();
                user.setId(user_id);
                user.setName(user_name);
                user.setEmail(user_email);

                MainActivity.myAppDatabase.myDao().updateUser(user);
                Toast.makeText(getActivity(), "Update Sucessfully", Toast.LENGTH_SHORT).show();
                userId.setText("");
                userName.setText("");
                userEmail.setText("");
                MainActivity.manager.beginTransaction().replace(R.id.fragment_container,new ReadFragment())
                        .addToBackStack(null).commit();
            }
        });
        return v;
    }

}
