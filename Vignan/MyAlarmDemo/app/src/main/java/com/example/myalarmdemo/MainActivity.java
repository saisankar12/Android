package com.example.myalarmdemo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
ToggleButton toggle;
AlarmManager am;
PendingIntent pi;
Intent myintent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        am= (AlarmManager) getSystemService(ALARM_SERVICE);
        myintent=new Intent(this,MyAlarmReceiver.class);
        pi=PendingIntent.getBroadcast(this,432,myintent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        toggle=findViewById(R.id.tb_button);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                if (b){
                    long tiggerTime= SystemClock.elapsedRealtime()+2*60*1000;
                    long intervelTime=2*60*1000;
                    am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                            tiggerTime,intervelTime,pi);
                    Toast.makeText(MainActivity.this, "on state", Toast.LENGTH_SHORT).show();
                }
                else {
                    am.cancel(pi);
                    Toast.makeText(MainActivity.this, "off state", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
