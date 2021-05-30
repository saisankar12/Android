package com.example.bookinfo;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class BookWidget extends AppWidgetProvider {

    String string;
    SharedPreferences sharedPreferences;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for(int appWidgetId: appWidgetIds){
            sharedPreferences=context.getSharedPreferences(context.getString(R.string.details),Context.MODE_PRIVATE);
            string=sharedPreferences.getString(context.getString(R.string.data),context.getString(R.string.No));
            Intent intent=new Intent(context,Books.class);
            PendingIntent pendingIntent=PendingIntent.getActivity(context,1,intent,0);

            RemoteViews remoteViews=new RemoteViews(context.getPackageName(),R.layout.book_widget);
            remoteViews.setTextViewText(R.id.appwidget_text,string);
            remoteViews.setOnClickPendingIntent(R.id.appwidget_text,pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId,remoteViews);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

