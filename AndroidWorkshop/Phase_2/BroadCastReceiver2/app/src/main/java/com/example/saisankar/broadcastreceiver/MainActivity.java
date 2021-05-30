package com.example.saisankar.broadcastreceiver;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity
{
    public static final String CBROADCAST=
            "com.example.saisankar." +
                    "broadcastreceiver.CUSTOM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyReceiver receive= new MyReceiver();

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(receive,
                        new IntentFilter(CBROADCAST));
    }

    public void sendCustom(View view)
    {
        Intent intent= new Intent(CBROADCAST);
        LocalBroadcastManager.getInstance(this)
                .sendBroadcast(intent);

    }
}
