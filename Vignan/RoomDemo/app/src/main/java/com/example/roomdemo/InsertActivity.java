package com.example.roomdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.roomdemo.components.StudentModel;

public class InsertActivity extends AppCompatActivity {

    EditText ename,ephone,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        ename=findViewById(R.id.username);
        email=findViewById(R.id.usermail);
        ephone=findViewById(R.id.usernumber);
    }

    public void save(View view) {

        String name=ename.getText().toString();
        String phone=ephone.getText().toString();
        String mail=email.getText().toString();
        StudentModel model=new StudentModel(name,phone,mail);

        model.setName(name);
        model.setPhone(phone);
        model.setEmail(mail);

        if (model.equals("")){
            Toast.makeText(this, "Data Not Inserted", Toast.LENGTH_SHORT).show();
        }else {
            MainActivity.viewModel.insertdata(model);
            finish();
        }
    }
}
