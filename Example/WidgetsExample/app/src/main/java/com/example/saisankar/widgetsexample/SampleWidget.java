package com.example.saisankar.widgetsexample;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.RemoteViews;

public class SampleWidget extends AppWidgetProvider {

    SharedPreferences sp;
    String NAME="com.example.saisankar.widgetsexample";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        for (int appwidget:appWidgetIds){
            sp=context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
            String s=sp.getString("sai","Data Not Avalibale");
            Intent i=new Intent(context,MainActivity.class);
            PendingIntent pi=PendingIntent.getActivity(context,0,i,0);
            Intent i1=new Intent(context,ExampleWidgetService.class);
            i1.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appwidget);
            i1.setData(Uri.parse(i1.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews  views=new RemoteViews(context.getPackageName(),
                                                R.layout.widget_design);
            views.setTextViewText(R.id.widget,s);
            views.setRemoteAdapter(R.id.listview,i1);
            views.setEmptyView(R.id.listview,R.id.emptytext);
            //views.setOnClickPendingIntent(R.id.listview,pi);
            views.setOnClickPendingIntent(R.id.widget,pi);
            appWidgetManager.updateAppWidget(appwidget,views);
        }

    }
}
