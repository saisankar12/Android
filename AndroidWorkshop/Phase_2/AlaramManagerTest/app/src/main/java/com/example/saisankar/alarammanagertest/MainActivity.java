package com.example.saisankar.alarammanagertest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Switch aSwitch;
public static final String MY_ACTION="com.example.saisankar.alarammanagertest.MYACTION";
    AlarmManager manager;
    PendingIntent pi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager= (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent i=new Intent(MY_ACTION);
        pi=PendingIntent.getBroadcast(this,2,i,PendingIntent.FLAG_UPDATE_CURRENT);

        aSwitch=findViewById(R.id.s);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(MainActivity.this, "ON", Toast.LENGTH_SHORT).show();
                    long triggerTime= SystemClock.elapsedRealtime()+2*60*1000;
                    long interVealTime=1*60*1000;
                    manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerTime,interVealTime,pi);
                }else {
                    Toast.makeText(MainActivity.this, "OFF", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
