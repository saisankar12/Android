package com.example.saisankar.alarmmanagerex;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder=
                new NotificationCompat.Builder(context);
        NotificationManager notificationManager=
                (NotificationManager) context.
                        getSystemService(Context.NOTIFICATION_SERVICE);
        builder.setSmallIcon(R.drawable.stand);
        builder.setContentTitle("Stand Up");
        builder.setContentText("Walk for 2 minutes");
        builder.setPriority(2);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        notificationManager.notify(2,builder.build());


    }
}
