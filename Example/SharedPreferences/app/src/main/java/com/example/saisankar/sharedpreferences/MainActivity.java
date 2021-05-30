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

public class MainActivity extends AppCompatActivity {

    Button red,green,yellow,pink,countup,countdown;
    TextView textcount;
    public static final String STRING_KEY="com.example.saisankar.sharedpreferences";
    public static final String COLOR_KEY="color_pref";
    public static final String COUNT_KEY="count_pref";
    private int mCount = 0;
    private int mColor;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        red=findViewById(R.id.red);
        green=findViewById(R.id.green);
        yellow=findViewById(R.id.yellow);
        pink=findViewById(R.id.pink);
        countup=findViewById(R.id.countup);
        countdown=findViewById(R.id.countdown);
        textcount=findViewById(R.id.textc);
        mColor = ContextCompat.getColor(this, R.color.colorAccent);
        preferences = getSharedPreferences(STRING_KEY, MODE_PRIVATE);
        // Restore preferences
        mCount = preferences.getInt(COUNT_KEY, 0);
        textcount.setText(String.format("%s", mCount));
        mColor = preferences.getInt(COLOR_KEY, mColor);
        textcount.setBackgroundColor(mColor);
        countdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCount--;
                textcount.setText(""+mCount);
            }
        });
        countup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mCount++;
                textcount.setText(""+mCount);
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor=preferences.edit();
        editor.putInt(COUNT_KEY, mCount);
        editor.putInt(COLOR_KEY, mColor);
        editor.apply();
    }
    public void changeback(View view) {
        int textbackcolor=((ColorDrawable)view.getBackground()).getColor();
        textcount.setBackgroundColor(textbackcolor);
        mColor=textbackcolor;
    }


}
