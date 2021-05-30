package com.google.developer.bugmaster.reminders;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.developer.bugmaster.QuizActivity;
import com.google.developer.bugmaster.R;
import com.google.developer.bugmaster.data.DatabaseManager;
import com.google.developer.bugmaster.data.Insect;

import java.util.ArrayList;
import java.util.Random;

public class ReminderService extends IntentService {

    private static final String TAG = ReminderService.class.getSimpleName();

    private static final int NOTIFICATION_ID = 42;

    public ReminderService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "Quiz reminder event triggered");

        //Present a notification to the user
        NotificationManager manager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //Create action intent
        Intent action = new Intent(this, QuizActivity.class);
        //TODO: Add data elements to quiz launch
        ArrayList<Insect> insects = new ArrayList<>();
        DatabaseManager databaseManager = new DatabaseManager(this);
        Cursor cursor = databaseManager.queryRandomInsects();
        while(cursor.moveToNext())
        {
            insects.add(new Insect("",cursor.getString(0),"","",0));
        }
        cursor.close();
        Random random = new Random();
        int index = random.nextInt(4);
        String sci_name = insects.get(index).scientificName;
        Cursor curs = databaseManager.queryNameOfInsects(sci_name);
        String nameOfInsect = null;
        String sci_nameOfInsect = null;
        while(curs.moveToNext())
        {
            nameOfInsect = curs.getString(0);
            sci_nameOfInsect = curs.getString(1);
        }
        Insect insect = new Insect(nameOfInsect,sci_nameOfInsect,"","",0);
        action.putParcelableArrayListExtra(QuizActivity.EXTRA_INSECTS,insects);
        action.putExtra(QuizActivity.EXTRA_ANSWER,insect);

        PendingIntent operation =
                PendingIntent.getActivity(this, 0, action, PendingIntent.FLAG_CANCEL_CURRENT);

        Notification note = new NotificationCompat.Builder(this)
                .setContentTitle(getString(R.string.notification_title))
                .setContentText(getString(R.string.notification_text))
                .setSmallIcon(R.drawable.ic_bug_empty)
                .setContentIntent(operation)
                .setAutoCancel(true)
                .build();

        manager.notify(NOTIFICATION_ID, note);

    }
}
