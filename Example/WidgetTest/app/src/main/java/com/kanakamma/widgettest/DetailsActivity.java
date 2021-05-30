package com.kanakamma.widgettest;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        tv=findViewById(R.id.mytxt);
        int position=getIntent().getIntExtra("pos",0);
        String[] detailsarra=getResources()
                .getStringArray(R.array.details);
        String data=detailsarra[position];
        tv.setText(data);

        SharedPreferences sharedPreferences=
                getSharedPreferences("mypref",MODE_PRIVATE);
        SharedPreferences.Editor editor=
                sharedPreferences.edit();
        editor.putString("mydata",data);
        editor.apply();

        Intent i=new Intent(this,NewAppWidget.class);
        i.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        int[] widgets= AppWidgetManager.getInstance(this).getAppWidgetIds(new ComponentName(this,NewAppWidget.class));
        i.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,widgets);
        sendBroadcast(i);




    }
}
