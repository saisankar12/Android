package com.example.saisankar.sharedpreference;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText et1,et2,et3,et4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

            et1=findViewById(R.id.username);
            et2=findViewById(R.id.password);
            et3=findViewById(R.id.age);
            et4=findViewById(R.id.gender);
    }

    public void doRegister(View view) {
        String uname,upass,uage,ugender;
        uname=et1.getText().toString();
        upass=et2.getText().toString();
        uage=et3.getText().toString();
        ugender=et4.getText().toString();

        SharedPreferences preferences=
                getSharedPreferences("myDetails",MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("u",uname);
        editor.putString("p",upass);
        editor.putString("a",uage);
        editor.putString("g",ugender);
        editor.apply();
        Toast.makeText(this, "Details Saved Sucessfully", Toast.LENGTH_SHORT).show();
        finish();

    }
}
