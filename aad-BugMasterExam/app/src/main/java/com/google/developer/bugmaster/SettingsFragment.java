package com.google.developer.bugmaster;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.google.developer.bugmaster.reminders.AlarmReceiver;

public class SettingsFragment extends PreferenceFragment {

    AlarmReceiver alarmReceiver;
    private static final String CUSTOM_ALARM_BROADCAST = "com.google.developer.bugmaster.ALARM_BROADCAST";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        alarmReceiver = new AlarmReceiver();

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(alarmReceiver,new IntentFilter(CUSTOM_ALARM_BROADCAST));
        Intent customBroadcast = new Intent(CUSTOM_ALARM_BROADCAST);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(customBroadcast);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sharedPreferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                String keyAlarm = getString(R.string.pref_key_alarm);
                String time = sharedPreferences.getString(keyAlarm,"12:00");
                Toast.makeText(getActivity(), "TRIGGERING ALARM AT: "+time, Toast.LENGTH_SHORT).show();
                Log.d("TRIGGERING ALARM AT",""+time);
                Intent customBroadcast = new Intent(CUSTOM_ALARM_BROADCAST);
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(customBroadcast);
            }
        });


    }
}
