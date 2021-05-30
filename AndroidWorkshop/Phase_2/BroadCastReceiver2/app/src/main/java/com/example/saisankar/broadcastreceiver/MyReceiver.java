package com.example.saisankar.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver
{
    public static final String CBROADCAST=
            "com.example.saisankar." +
                    "broadcastreceiver.CUSTOM";

    @Override
    public void onReceive(Context context,
                          Intent intent)
    {
        String act=intent.getAction();
        switch (act)
        {
            case Intent.ACTION_POWER_CONNECTED:
                Toast.makeText(context,
                        "Hi Power is connected",
                        Toast.LENGTH_SHORT).show();
                break;
            case Intent.ACTION_POWER_DISCONNECTED:
                Toast.makeText(context,
                        "Hi Power is disconnected",
                        Toast.LENGTH_SHORT).show();
                break;
            case Intent.ACTION_BATTERY_LOW:
                Toast.makeText(context,
                        "Battery is low",
                        Toast.LENGTH_SHORT).show();
                break;
            case CBROADCAST:
                Toast.makeText(context,
                        "Hi I'm Custom Broadcast" +
                                "" +
                                "",
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
