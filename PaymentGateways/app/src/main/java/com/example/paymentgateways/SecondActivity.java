package com.example.paymentgateways;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    EditText et;
    static String key = "data1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView tv = findViewById(R.id.textView);
        tv.setText(getIntent().getStringExtra("data"));
        et = findViewById(R.id.editTextTextPersonName);
        findViewById(R.id.reply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = et.getText().toString();
                Intent i = new Intent(SecondActivity.this,MainActivity.class);
                i.putExtra(key,s1);
                setResult(RESULT_OK,i);
                finish();
            }
        });
    }
}