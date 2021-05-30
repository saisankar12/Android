package com.example.saisankar.hillfestival;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    LinearLayout l1;
    private static int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        l1 = findViewById(R.id.l1);


        Animation anim = AnimationUtils.loadAnimation(this, R.anim.downtoup);
        anim.reset();

        l1.clearAnimation();
        l1.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.downtoup);
        anim.reset();
        TextView tv = findViewById(R.id.textview);
        tv.clearAnimation();
        tv.startAnimation(anim);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
