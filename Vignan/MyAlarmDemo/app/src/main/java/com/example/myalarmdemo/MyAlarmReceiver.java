package com.example.myalarmdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

public class MyAlarmReceiver extends BroadcastReceiver {
MediaPlayer mp;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        mp=MediaPlayer.create(context,R.raw.mysong);
        mp.start();
    }
}
