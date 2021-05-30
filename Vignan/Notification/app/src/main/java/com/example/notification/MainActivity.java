package com.example.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String s = "str";
    NotificationManager nm;
    PendingIntent pi;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        i=new Intent(this,SecondActivity.class);
        pi=PendingIntent.getActivity(this,1246,i,
                PendingIntent.FLAG_UPDATE_CURRENT);

    }

    public void send(View view) {

        NotificationCompat.Builder nb = new NotificationCompat.Builder(this);
        nb.setSmallIcon(R.mipmap.ic_launcher);
        nb.setContentTitle("Vignan's University");
        nb.setContentText("Vignans Foundation for Science,Research and Technology");
        nb.setDefaults(NotificationCompat.DEFAULT_ALL);
        nb.setPriority(NotificationCompat.PRIORITY_MAX);
        nb.addAction(R.drawable.reply,"reply",pi);
        nb.setContentIntent(pi);
        nm.notify(20, nb.build());
    }
}
