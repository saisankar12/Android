package com.example.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String s=intent.getAction();
        switch (s)
        {
            case Intent.ACTION_AIRPLANE_MODE_CHANGED:
                Toast.makeText(context, "Airplane mode changed",
                        Toast.LENGTH_SHORT).show();
                break;
            case Intent.ACTION_POWER_CONNECTED:
                Toast.makeText(context, "Power connected",
                        Toast.LENGTH_SHORT).show();
                break;
            case Intent.ACTION_POWER_DISCONNECTED:
                Toast.makeText(context, "Power disconnected",
                        Toast.LENGTH_SHORT).show();
                break;
            case MainActivity.str:
                Toast.makeText(context, "This is a custom broadcast",
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
