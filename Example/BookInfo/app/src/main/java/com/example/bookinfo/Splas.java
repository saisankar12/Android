package com.example.bookinfo;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;

public class Splas extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
   GoogleProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splas);
        progressBar=findViewById(R.id.progres);
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(Splas.this, SignIn.class);
                startActivity(i);
                progressBar.setVisibility(View.GONE);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}
