package com.example.roomlivedata;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class InsertActivity extends AppCompatActivity {

    EditText iroll,iname,imail,iphone;

    MyViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        iroll=findViewById(R.id.rollnumber);
        iname=findViewById(R.id.name);
        imail=findViewById(R.id.gmail);
        iphone=findViewById(R.id.phonenumber);

        viewModel= ViewModelProviders.of(this).get(MyViewModel.class);

    }
    public void save(View view) {
        String sroll=iroll.getText().toString();
        String sname=iname.getText().toString();
        String smail=imail.getText().toString();
        String sphone=iphone.getText().toString();

        StudentModel model=new StudentModel(sroll,sname,smail,sphone);

        viewModel.insertData(model);

        Toast.makeText(this, "Data Save Sucessfully", Toast.LENGTH_SHORT).show();

        finish();
    }
}
