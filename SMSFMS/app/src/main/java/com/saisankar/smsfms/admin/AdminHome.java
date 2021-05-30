package com.saisankar.smsfms.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.saisankar.smsfms.MainActivity;
import com.saisankar.smsfms.R;
import com.saisankar.smsfms.database.studentdb.Student;

public class AdminHome extends AppCompatActivity {


    RadioButton r_faculty, r_student;
    LinearLayout l_student, l_faculty;
    EditText s_roll, s_name, s_mailid, s_phone;
    Spinner s_dept;
    String s1;
    TextView status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        l_faculty = findViewById(R.id.facultyview);
        l_student = findViewById(R.id.studetview);

        r_faculty = findViewById(R.id.faculty);
        r_student = findViewById(R.id.student);
        status = findViewById(R.id.status);


        r_faculty.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    l_faculty.setVisibility(View.VISIBLE);
                } else {
                    l_faculty.setVisibility(View.GONE);
                }
            }
        });

        r_student.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    l_student.setVisibility(View.VISIBLE);
                    s_roll = findViewById(R.id.student_roll_number);
                    s_name = findViewById(R.id.student_name);
                    s_mailid = findViewById(R.id.student_mail);
                    s_phone = findViewById(R.id.student_phone_number);
                    s_dept = findViewById(R.id.student_department);

                    s_dept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                            s1 = parent.getSelectedItem().toString();
                            //Toast.makeText(AdminHome.this, "You are Selected " + "\t" + s1 + "\t" + "Department", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    Button savestudent = findViewById(R.id.save_student);
                    savestudent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String roll = s_roll.getText().toString();
                            String name = s_name.getText().toString();
                            String mail = s_mailid.getText().toString();
                            String phone = s_phone.getText().toString();

                            if (roll.isEmpty() || name.isEmpty() || mail.isEmpty() || phone.isEmpty() || s1.equals("Select Your Department")) {
                                status.setText("Please Enter All Details");
                                status.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                            } else {
                                Student student = new Student();
                                student.setRollnumber(roll);
                                student.setName(name);
                                student.setMailid(mail);
                                student.setPhoneNumber(phone);
                                student.setDepartment(s1);
                                String find = MainActivity.viewModel.findStudent(student.getMailid());
                                Log.i("mailid",find);
                                if (mail.equals(find)) {
                                    status.setText("Student Mail Id already avaliable");
                                    Log.i("mailid",find);
                                } else {
                                    MainActivity.viewModel.inserStudentData(student);
                                    status.setText("Student Details Saved Sucessfully");
                                    status.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                                }
                            }
                        }
                    });
                } else {
                    l_student.setVisibility(View.GONE);
                }
            }
        });


    }
}
