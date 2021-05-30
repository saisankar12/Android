package com.example.saisankar.notificationex;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity
{
    NotificationCompat.Builder builder;
    NotificationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager= (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
    }

    public void sendNotification(View view)
    {
        Intent intent= new Intent(this,
                MainActivity.class);
        PendingIntent pi= PendingIntent.getActivity(this,
                2,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder=new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setContentTitle("Android Workshop");
        builder.setContentText("Welcome to Phase 2");
        builder.setContentIntent(pi);
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);

        Notification noti=builder.build();
        manager.notify(1,noti);
    }
}
