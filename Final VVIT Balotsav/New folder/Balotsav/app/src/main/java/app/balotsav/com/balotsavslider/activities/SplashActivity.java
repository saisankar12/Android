package app.balotsav.com.balotsavslider.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

import app.balotsav.com.balotsavslider.R;
import app.balotsav.com.balotsavslider.utils.EventAdapter;
import app.balotsav.com.balotsavslider.utils.GrantPermissions;


public class SplashActivity extends AppCompatActivity {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences pref = getSharedPreferences("Balotsav", 0);
        editor = pref.edit();
        databaseReference.child("Date").addValueEventListener(new ValueEventListener() {
                                                                  @Override
                                                                  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                      String result = dataSnapshot.getValue(String.class);
                                                                      editor.putString("expiryDay", result);
                                                                      editor.apply();
                                                                  }

                                                                  @Override
                                                                  public void onCancelled(@NonNull DatabaseError databaseError) {
                                                                  }
                                                              }

        );

        Animation animationUp = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.movedown);
        Animation animationDown = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move);
        LinearLayout l = (LinearLayout) findViewById(R.id.l1);
        LinearLayout l1 = (LinearLayout) findViewById(R.id.l2);
        l.setAnimation(animationUp);
        l1.setAnimation(animationDown);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                    new GrantPermissions(SplashActivity.this).checkAndRequestPermissions() ;
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();

            }
        }, 3000);

    }



    }

