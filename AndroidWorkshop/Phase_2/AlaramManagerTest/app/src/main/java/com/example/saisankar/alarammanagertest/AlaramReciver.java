package com.example.saisankar.alarammanagertest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import static android.content.Context.NOTIFICATION_SERVICE;

public class AlaramReciver extends BroadcastReceiver {
    NotificationManager manager;
    @Override
    public void onReceive(Context context, Intent intent) {
        manager= (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
        Toast.makeText(context, "Alaram On", Toast.LENGTH_SHORT).show();
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("This is My FirstNotification");
        builder.setContentText("This mastan from APSSDC");
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        Intent i=new Intent(context,MainActivity.class);
        PendingIntent pi=PendingIntent.getActivity(context,2,i,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pi);
        builder.setAutoCancel(true);
        Bitmap bitmap= BitmapFactory.decodeResource( context.getResources(),R.mipmap.ic_launcher);
        builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap));
        builder.addAction(R.mipmap.ic_launcher,"Reply",pi);
        Notification notification=builder.build();
        manager.notify(2,notification);
    }
}
