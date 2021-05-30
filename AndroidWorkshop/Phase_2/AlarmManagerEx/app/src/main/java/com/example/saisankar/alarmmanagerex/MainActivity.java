package com.example.saisankar.alarmmanagerex;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity
{
    ToggleButton toggleButton;
    AlarmManager manager;
    public static final String BROAD=
            "com.example.saisankar.alarmmanagerex.BROAD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager= (AlarmManager) getSystemService(ALARM_SERVICE);
        final long trigger= SystemClock.elapsedRealtime()+3*60*1000;
        final long interval=2*60*1000;

        Intent intent= new Intent(BROAD);
        final PendingIntent pendingIntent=
                PendingIntent.getBroadcast(
                        this,
                        3,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        toggleButton=(ToggleButton) findViewById(R.id.toggle);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b)
            {
                if (b)
                {
                    manager.setInexactRepeating(
                            AlarmManager.ELAPSED_REALTIME_WAKEUP,
                            trigger,
                            interval,
                            pendingIntent
                    );
                    Toast.makeText(MainActivity.this,
                            "Alarm is on", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    manager.cancel(pendingIntent);
                    Toast.makeText(MainActivity.this,
                            "Alarm is off", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
