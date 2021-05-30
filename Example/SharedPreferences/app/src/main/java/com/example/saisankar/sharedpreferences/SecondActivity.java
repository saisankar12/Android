package com.example.saisankar.sharedpreferences;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    Button bcountup,bcountdown;
    int acolor,acount=0;
    TextView tv;
    final static String COLOR_KEY="COLOR";
    final static String COUNT_KEY="COUNT";
    final static String KEY="com.example.saisankar.sharedpreferences";
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        bcountup=findViewById(R.id.countup);
        bcountdown=findViewById(R.id.countdown);
        tv=findViewById(R.id.textc);
        acolor=ContextCompat.getColor(this,R.color.colorPrimary);
        preferences=getSharedPreferences(KEY,MODE_PRIVATE);
        bcountup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acount++;
                tv.setText(Integer.toString(acount));
            }
        });
        bcountdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acount--;
                tv.setText(Integer.toString(acount));
            }
        });
        acount=preferences.getInt(COUNT_KEY,acount);
        acolor=preferences.getInt(COLOR_KEY,acolor);
        tv.setText(""+acount);
        tv.setBackgroundColor(acolor);
    }
    public void next(View view) {
        startActivity(new Intent(this,SettingsActivity.class));
    }
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor=preferences.edit();
        editor.putInt(COUNT_KEY,acount);
        editor.putInt(COLOR_KEY,acolor);
        editor.commit();
    }
    public void changeback(View v){
        int tvback=((ColorDrawable)v.getBackground()).getColor();
        tv.setBackgroundColor(tvback);
        acolor=tvback;
    }
}
