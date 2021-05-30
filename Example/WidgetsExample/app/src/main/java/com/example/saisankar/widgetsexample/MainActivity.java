package com.example.saisankar.widgetsexample;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    ListView lv;

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    public static final String NAME="com.example.saisankar.widgetsexample";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv=findViewById(R.id.list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sp=getSharedPreferences(NAME,MODE_PRIVATE);
                editor=sp.edit();
                StringBuffer  buffer=new StringBuffer();
                String in=adapterView.getAdapter().getItem(i).toString();
                buffer.append(in);
                Toast.makeText(MainActivity.this, in, Toast.LENGTH_SHORT).show();
                editor.putString("sai",buffer.toString());
                editor.apply();
                Intent intent=new Intent(MainActivity.this,SampleWidget.class);
                intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                int[] listid=AppWidgetManager.getInstance(MainActivity.this)
                        .getAppWidgetIds(new ComponentName(getApplicationContext()
                                ,SampleWidget.class));
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,listid);
                sendBroadcast(intent);
            }
        });
    }

}
