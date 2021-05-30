package app.balotsav.com.balotsavslider.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import app.balotsav.com.balotsavslider.R;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        LinearLayout lv=findViewById(R.id.linear);
        Animation animationup= AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.text);
        animationup.setRepeatCount(Animation.INFINITE);
        //animationup.setRepeatMode();
        lv.setAnimation(animationup);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(getApplicationContext(),DashBoardActivity.class);
                startActivity(i);
                finish();
            }
        },20000);
    }

}
