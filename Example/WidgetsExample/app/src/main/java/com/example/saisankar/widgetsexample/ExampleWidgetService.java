package com.example.saisankar.widgetsexample;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

public class ExampleWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ExampleWidgetFactory(getApplicationContext(),intent);
    }


    public class ExampleWidgetFactory implements RemoteViewsFactory{


        private Context context;
        private int appWidgetIds;
        private String[] exampleData={"One","Two","Three","Four"};


        public ExampleWidgetFactory(Context context,Intent intent) {
        this.context=context;
        this.appWidgetIds=intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override
        public void onCreate() {
                //Connect to Data Source

            SystemClock.sleep(3000);
        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {
            //Close Data Source
        }

        @Override
        public int getCount() {
            return exampleData.length;
        }

        @Override
        public RemoteViews getViewAt(int i) {
            RemoteViews views=
                    new RemoteViews(context.getPackageName(), R.layout.rowdesign);
            views.setTextViewText(R.id.exampleWidgetitem,exampleData[i]);
            SystemClock.sleep(500);
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
