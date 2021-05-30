package com.example.sharedpreference;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    SharedPreferences preferences;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        tv=findViewById(R.id.textView);
        preferences=getSharedPreferences("Sai",MODE_PRIVATE);
        if (preferences!=null){
            String name = preferences.getString("name","");
            String pass = preferences.getString("pass","");
            /*et1.setText(name);
            et2.setText(pass);*/
            tv.setText(name +"\t \t" +pass);
            Toast.makeText(this, name+""+pass, Toast.LENGTH_SHORT).show();
        }
    }


    public void stop(View view) {

        Intent i =new Intent(this,MyService.class);
        stopService(i);

    }

    public void start(View view) {
        Intent i =new Intent(this,MyService.class);
        startService(i);
    }
}
