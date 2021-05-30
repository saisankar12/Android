package com.saisankar.smsfms.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.widget.TextView;

import com.saisankar.smsfms.MainActivity;
import com.saisankar.smsfms.R;
import com.saisankar.smsfms.database.studentdb.Student;

import java.util.List;

public class StudentHome extends AppCompatActivity {

    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        tv= findViewById(R.id.textView);

        MainActivity.viewModel.readStudentData().observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {
                tv.setText("No.of Students: "+students.size()+"\n");

                for (int i = 0; i < students.size(); i++) {
                    String smail = students.get(i).getMailid();
                    String sphone = students.get(i).getPhoneNumber();
                    tv.append(smail+"\n"+sphone+"\n");
                }
            }
        });
    }
}
