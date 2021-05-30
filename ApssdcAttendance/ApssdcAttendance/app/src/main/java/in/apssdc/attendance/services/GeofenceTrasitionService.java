package in.apssdc.attendance.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingEvent;

import java.util.ArrayList;
import java.util.List;

import in.apssdc.attendance.R;
import in.apssdc.attendance.activities.GeofenceLocationActivity;

public class GeofenceTrasitionService extends IntentService {

    private static final String TAG = GeofenceTrasitionService.class.getSimpleName();
    public static final int GEOFENCE_ENTERNOTIFICATION_ID = 0;
    public static final int GEOFENCE_EXITNOTIFICATION_ID = 1;
    int geoFenceTransition;
    Handler mHandler;

    public GeofenceTrasitionService() {
        super(TAG);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new Handler();
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        /* mHandler.post(new Runnable() {
             @Override
             public void run() {
              Toast.makeText(GeofenceTrasitionService.this,"onHandleIntent",Toast.LENGTH_LONG).show();
             }
         });*/
        // Retrieve the Geofencing intent
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        // Handling errors
        if(geofencingEvent.hasError()) {
            String errorMsg = getErrorString(geofencingEvent.getErrorCode() );
            Log.e( TAG, errorMsg );
            return;
        }
        // Retrieve GeofenceTrasition
        geoFenceTransition = geofencingEvent.getGeofenceTransition();
        // Check if the transition type
        if (geoFenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER || geoFenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
            // Get the geofence that were triggered
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
            // Create a detail message with Geofences received
            String geofenceTransitionDetails = getGeofenceTrasitionDetails(geoFenceTransition, triggeringGeofences );
            // Send notification details as a String
            sendNotification( geofenceTransitionDetails );
        }else{
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(GeofenceTrasitionService.this,"Out Of Genfence",Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    // Create a detail message with Geofences received
    private String getGeofenceTrasitionDetails(int geoFenceTransition, List<Geofence> triggeringGeofences) {
        // get the ID of each geofence triggered
        ArrayList<String> triggeringGeofencesList = new ArrayList<>();
        for (Geofence geofence : triggeringGeofences ) {
            triggeringGeofencesList.add(geofence.getRequestId() );
        }
        String status = null;
        if (geoFenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER )
            status = "Entering ";
        else if ( geoFenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT )
            status = "Exiting ";
        return status + TextUtils.join( ", ", triggeringGeofencesList);
    }
    // Send a notification
    private void sendNotification( String msg ) {
        //Intent to start the main Activity
        Intent notificationIntent = GeofenceLocationActivity.makeNotificationIntent(getApplicationContext(), msg);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(GeofenceLocationActivity.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent notificationPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        // Creating and sending Notification
        NotificationManager notificatioMng =(NotificationManager)getSystemService( Context.NOTIFICATION_SERVICE );
        if(msg.equalsIgnoreCase("Entering My Geofence"))
        {
            String message="Entered into Work location";
            notificatioMng.notify(GEOFENCE_ENTERNOTIFICATION_ID,createEnterNotification(message, notificationPendingIntent));
        }else if(msg.equalsIgnoreCase("Exiting My Geofence"))
        {
            String message="Exited from Work location";
            notificatioMng.notify(GEOFENCE_EXITNOTIFICATION_ID,createExitNotification(message, notificationPendingIntent));
        }
    }
    // Create a notification
    private Notification createEnterNotification(String msg, PendingIntent notificationPendingIntent) {
        /*mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(GeofenceTrasitionService.this,"Enter in",Toast.LENGTH_LONG).show();
            }
        });*/
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Notification", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("key","You just enter into Geofencing location");
        editor.commit();
        Bitmap bm = ((BitmapDrawable) getResources().getDrawable(R.drawable.user_icon)).getBitmap();
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder
                .setSmallIcon(R.drawable.ok)
                .setColor(Color.GREEN)
                .setContentTitle(msg)
                .setContentText("You just enter into Geofencing location")
                .setContentIntent(notificationPendingIntent)
                .setLargeIcon(bm)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
                .setAutoCancel(true);
        return notificationBuilder.build();
    }
    private Notification createExitNotification(String msg, PendingIntent notificationPendingIntent) {
        /*mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(GeofenceTrasitionService.this,"Exit Notification",Toast.LENGTH_LONG).show();
            }
        });*/
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Notification", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("key","You just exit from Geofencing location");
        editor.commit();
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder
                .setSmallIcon(R.drawable.red_geofence)
                .setColor(Color.RED)
                .setContentTitle(msg)
                .setContentText("You just exit from Geofencing location")
                .setContentIntent(notificationPendingIntent)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
                .setAutoCancel(true);
        return notificationBuilder.build();
    }
    // Handle errors
    private static String getErrorString(int errorCode) {
        switch (errorCode) {
            case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE:
                return "GeoFence not available";
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES:
                return "Too many GeoFences";
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
                return "Too many pending intents";
            default:
                return "Unknown error.";
        }
    }
}
