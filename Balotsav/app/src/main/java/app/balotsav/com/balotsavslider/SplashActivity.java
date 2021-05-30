package app.balotsav.com.balotsavslider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
//import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import app.balotsav.com.balotsavslider.Login;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Animation animationup= AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.movedown);
        Animation animationdown= AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move);
        LinearLayout l=(LinearLayout)findViewById(R.id.l1);
        LinearLayout l1=(LinearLayout)findViewById(R.id.l2);
        l.setAnimation(animationup);
        l1.setAnimation(animationdown);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(SplashActivity.this,Login.class);
                startActivity(i);
                finish();
            }
        },3000);

    }
}
