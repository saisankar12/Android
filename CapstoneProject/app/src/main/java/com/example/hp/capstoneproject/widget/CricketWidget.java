package com.example.hp.capstoneproject.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.example.hp.capstoneproject.MainActivity;
import com.example.hp.capstoneproject.R;
import com.example.hp.capstoneproject.TechnologyFragment;
import com.example.hp.capstoneproject.fragments.NewsDetailActivity;

/**
 * Implementation of App Widget functionality.
 */
public class CricketWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
       SharedPreferences sharedPreferences=context.getSharedPreferences("mypref",Context.MODE_PRIVATE);
       String title=sharedPreferences.getString("title",null);
       String desc=sharedPreferences.getString("description",null);

       String txt="Title:\n\n"+title+"\n\nDescription:\n\n"+desc;
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.cricket_widget);
        views.setTextViewText(R.id.appwidget_text, txt);

        Intent intent=new Intent(context, MainActivity.class);
        sharedPreferences.edit().clear();
        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent,0);
        views.setOnClickPendingIntent(R.id.appwidget_text,pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
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

