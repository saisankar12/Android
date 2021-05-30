package com.example.saisankar.roomwithlivedata;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddNote extends AppCompatActivity {

    EditText rollnumber,uemail,uname;

    UserViewModel userViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        rollnumber=findViewById(R.id.userrollnumber);
        uemail=findViewById(R.id.usermail);
        uname=findViewById(R.id.username);
        userViewModel=ViewModelProviders.of(this).get(UserViewModel.class);
    }

    public void save(View view) {

        User user=new User();
        user.setName(uname.getText().toString());
        user.setRollNum(rollnumber.getText().toString());
        user.setEmail(uemail.getText().toString());

        //MainActivity.userDataBase.noteDao().insertData(user);
        userViewModel.insert(user);
        finish();
        //startActivity(new Intent(this,MainActivity.class));
    }
}
