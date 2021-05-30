package com.example.saisankar.sharedpreferences;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
    }

    public void stop(View view) {
    stopService(new Intent(this,MyService.class));
    }

    public void start(View view) {
        startService(new Intent(this,MyService.class));
    }
}
