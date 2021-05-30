package com.google.developer.bugmaster.reminders;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.developer.bugmaster.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = AlarmReceiver.class.getSimpleName();
    private static final String CUSTOM_ALARM_BROADCAST = "com.google.developer.bugmaster.ALARM_BROADCAST";

    @Override
    public void onReceive(Context context, Intent intent) {
        //Schedule alarm on BOOT_COMPLETED
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            scheduleAlarm(context);
        }
        switch (intent.getAction())
        {
            case Intent.ACTION_BOOT_COMPLETED:
                scheduleAlarm(context);
                break;
            case CUSTOM_ALARM_BROADCAST:
                scheduleAlarm(context);
                break;
        }
    }

    /* Schedule the alarm based on user preferences */
    public static void scheduleAlarm(Context context) {
        AlarmManager manager = AlarmManagerProvider.getAlarmManager(context);

        String keyReminder = context.getString(R.string.pref_key_reminder);
        String keyAlarm = context.getString(R.string.pref_key_alarm);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        boolean enabled = preferences.getBoolean(keyReminder, false);

        //Intent to trigger
        Intent intent = new Intent(context, ReminderService.class);
        PendingIntent operation = PendingIntent
                .getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (enabled) {
            //Gather the time preference
            Calendar startTime = Calendar.getInstance();
            try {
                String alarmPref = preferences.getString(keyAlarm, "12:00");
                Log.d("ALARM_PREF",alarmPref);
                SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
                Date t=format.parse(alarmPref);
                startTime.setTime(format.parse(alarmPref));
                startTime.set(Calendar.HOUR_OF_DAY,t.getHours());
                startTime.set(Calendar.MINUTE,t.getMinutes());
                Log.d("START TIME",""+startTime);

            } catch (ParseException e) {
                Log.w(TAG, "Unable to determine alarm start time", e);
                return;
            }

            //Start at the preferred time
            //If that time has passed today, set for tomorrow
            if (Calendar.getInstance().after(startTime)) {
                startTime.add(Calendar.DATE, 1);
            }

            Log.d(TAG, "Scheduling quiz reminder alarm");
            manager.setExact(AlarmManager.RTC, startTime.getTimeInMillis(), operation);
        } else {
            Log.d(TAG, "Disabling quiz reminder alarm");
            manager.cancel(operation);
        }
    }

}
