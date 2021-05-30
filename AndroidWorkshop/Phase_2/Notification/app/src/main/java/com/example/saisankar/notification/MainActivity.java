package com.example.saisankar.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    NotificationManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*To Call Notification Manager into OnCreate Method*/
        manager= (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
    }

    public void sendNotification(View view) {
          /* Construct the Notification*/
        NotificationCompat.Builder builder=new
                           NotificationCompat.Builder(this);
        /* Adding ICON*/
        builder.setSmallIcon(R.mipmap.ic_launcher);
        /* Add Titile*/
        builder.setContentTitle("This is My FirstNotification");
        /*Add Context*/
        builder.setContentText("This mastan from APSSDC");


        /*To Add Default Notification Sound in Android Phone*/
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        /* To Set Priority */
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        /*When Click an Option ,Which Activity to Open*/
        Intent i=new Intent(this,MainActivity.class);
        /*To Link The Notification to Our Intent*/
        PendingIntent pi=PendingIntent.getActivity(this,2,
                         i,PendingIntent.FLAG_UPDATE_CURRENT);
        /*to Set the Conent Intent*/
        builder.setContentIntent(pi);
        /*When we tap the Notification to cancel that notification*/
        builder.setAutoCancel(true);
        /*to Convert Image to Bitmap*/
        Bitmap bitmap= BitmapFactory.decodeResource(getResources()
                ,R.mipmap.ic_launcher);
        /*To set Big Picture Style*/
        builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap));
       /*To add reply Button to Noptification*/
        builder.addAction(R.mipmap.ic_launcher,"Reply",pi);

        Notification notification=builder.build();
        /*To Send the Notificaton*/
        manager.notify(2,notification);
    }
}
