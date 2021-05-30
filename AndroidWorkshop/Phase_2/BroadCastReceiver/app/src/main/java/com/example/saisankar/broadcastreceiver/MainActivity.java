package com.example.saisankar.broadcastreceiver;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
public static final String MY_ACTION=
        "com.example.saisankar.broadcastreceiver.MYACTION";
    ComponentName mComponentname;
    PackageManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mComponentname=new ComponentName(this,MyReceiver.class);
        manager=getPackageManager();
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(new MyReceiver(),
                        new IntentFilter(MY_ACTION));

    }
    public void custom(View view) {
        Intent i=new Intent(MY_ACTION);
        LocalBroadcastManager.getInstance(this).sendBroadcast(i);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(new MyReceiver());
    }
    @Override
    protected void onStart() {
        super.onStart();
        manager.setComponentEnabledSetting(mComponentname,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

    }

    @Override
    protected void onStop() {
        super.onStop();
        manager.setComponentEnabledSetting(mComponentname,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }
}
