package com.example.saisankar.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Created by Sai Sankar on 05-06-2018.
 */

public class Widgets extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId:appWidgetIds){
            Intent i=new Intent(context,MainActivity.class);
            PendingIntent p=PendingIntent.getActivity(context,0,i,0);
            RemoteViews views=new RemoteViews(context.getPackageName(),R.layout.widgets);
            views.setOnClickPendingIntent(R.id.sai,p);
            appWidgetManager.updateAppWidget(appWidgetId,views);
        }
    }
}
