package com.example.saisankar.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
// TODO(1) Create Receiver Class with extension BroadcastReceiver

// TODO(2) Add Action in Manifest file with in the receiver tag after adding a intent filter
public class MyReceiver extends BroadcastReceiver {
    public static final String MY_ACTION="com.example.saisankar.broadcastreceiver.MYACTION";


    @Override
    public void onReceive(Context context, Intent intent)

    {
        //TODO(3) To Get the system Actions
        String action=intent.getAction();
        //TODO(4) TO Write Cases
        switch (action){
            case Intent.ACTION_POWER_CONNECTED:
                Toast.makeText(context,
                        "POWER connected", Toast.LENGTH_SHORT).show();
                break;
            case Intent.ACTION_POWER_DISCONNECTED:
                Toast.makeText(context, "dis connected", Toast.LENGTH_SHORT).show();
                break;

            case MY_ACTION:
                Toast.makeText(context, "This is Custom", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
