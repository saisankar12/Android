package com.example.mysharedpreferences;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText et1,et2;
    SharedPreferences sp;
    String s1;
    String s2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1=findViewById(R.id.user);
        et2=findViewById(R.id.pass);
        sp=getSharedPreferences("myfile",MODE_PRIVATE);

    }

    public void submit(View view) {
          s1=et1.getText().toString();
          s2=et2.getText().toString();
        Toast.makeText(this, ""+s1+" "+s2, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("username",s1);
        editor.putString("password",s2);
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String result1=sp.getString("username","");
        String result2=sp.getString("password","");
        et1.setText(result1);
        et2.setText(result2);
    }
}
