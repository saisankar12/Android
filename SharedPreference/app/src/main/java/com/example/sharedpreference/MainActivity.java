package com.example.sharedpreference;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button et1,et2;

    SharedPreferences preferences;

    boolean verify = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1=findViewById(R.id.editText);
        et2=findViewById(R.id.editText2);
        preferences = getSharedPreferences("Sai",MODE_PRIVATE);
        verify = preferences.getBoolean("verify",false);
        if (verify){
            String name = preferences.getString("name","");
            String pass = preferences.getString("pass","");
            et1.setText(name);
            et2.setText(pass);
            startActivity(new Intent(MainActivity.this,SecondActivity.class));
            finish();
        }
    }

    public void save(View view) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("name",et1.getText().toString());
        editor.putString("pass",et2.getText().toString());
        editor.putBoolean("verify",true);
        editor.apply();

        Intent i =new Intent(this,SecondActivity.class);
        startActivity(i);
        finish();

    }


}
