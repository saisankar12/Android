package com.example.saisankar.sharedpreference;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

        EditText et1,et2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1=findViewById(R.id.luserName);
        et2=findViewById(R.id.lpassword);
    }

    public void signIn(View view) {


        SharedPreferences preferences=getSharedPreferences("myDetails",MODE_PRIVATE);
        String lname=preferences.getString("u",null);
        String lpass=preferences.getString("p",null);

        if (et1.getText().toString().equalsIgnoreCase(lname)
                &&et2.getText().toString().equalsIgnoreCase(lpass)){
            Toast.makeText(this, "Login Sucessfully", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Invalid Logins", Toast.LENGTH_SHORT).show();

        }

    }

    public void register(View view) {
        startActivity(new Intent(this,RegisterActivity.class));
    }
}
