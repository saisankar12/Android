package com.example.saisankar.roomwithlivedata;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditNote extends AppCompatActivity {
    EditText rollnumber,uemail,uname;
    UserViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        rollnumber=findViewById(R.id.userrollnumber);
        rollnumber.setKeyListener(null);
        uemail=findViewById(R.id.usermail);
        uname=findViewById(R.id.username);

        String rollnum=getIntent().getStringExtra("roll");
        String ename=getIntent().getStringExtra("name");
        String email=getIntent().getStringExtra("email");
        rollnumber.setText(rollnum);
        uname.setText(ename);
        uemail.setText(email);

        viewModel=ViewModelProviders.of(this).get(UserViewModel.class);

    }
    public void update(View view) {

        User user=new User();
        user.setName(uname.getText().toString());
        user.setRollNum(rollnumber.getText().toString());
        user.setEmail(uemail.getText().toString());

        //MainActivity.userDataBase.noteDao().updateData(user);
        viewModel.update(user);
        finish();
       // startActivity(new Intent(this,MainActivity.class));
    }
}
