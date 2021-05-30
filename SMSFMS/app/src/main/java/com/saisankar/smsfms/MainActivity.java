package com.saisankar.smsfms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.saisankar.smsfms.admin.AdminHome;
import com.saisankar.smsfms.database.SFViewModel;
import com.saisankar.smsfms.database.studentdb.Student;
import com.saisankar.smsfms.faculty.FacultyHome;
import com.saisankar.smsfms.student.StudentHome;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText uname, upass;

    public static SFViewModel viewModel;

    String smail,sphone;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        uname = findViewById(R.id.username);
        upass = findViewById(R.id.password);

        viewModel = new ViewModelProvider(this).get(SFViewModel.class);


        viewModel.readStudentData().observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {
                for (int i = 0; i < students.size(); i++) {
                    smail = students.get(i).getMailid();
                    sphone = students.get(i).getPhoneNumber();
                }
            }
        });
    }

    public void login(View view) {
        String name = uname.getText().toString();
        String pass = upass.getText().toString();

        if (name.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Please fill details", Toast.LENGTH_SHORT).show();
        } else if (name.equals("admin") && pass.equals("admin")) {
            startActivity(new Intent(MainActivity.this, AdminHome.class));
        } else if (name.equals("faculty") && pass.equals("faculty")) {
            startActivity(new Intent(MainActivity.this, FacultyHome.class));
        } else if (name.equals("student") && pass.equals("student")) {
            startActivity(new Intent(MainActivity.this, StudentHome.class));
        } else if(name.equals(smail) && pass.equals(sphone)){
            startActivity(new Intent(MainActivity.this, StudentHome.class));
        } else{
            Toast.makeText(this, "Wrong Details", Toast.LENGTH_SHORT).show();
        }
    }
}
