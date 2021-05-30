package com.kanakamma.widgettest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {


    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.mylist);

        String[] namesarray=getResources().getStringArray(R.array.names);
        ArrayAdapter adapter=new ArrayAdapter(this,
                android.R.layout.simple_dropdown_item_1line,
                namesarray);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i=new Intent(MainActivity.this,DetailsActivity.class);
                i.putExtra("pos",position);
                startActivity(i);
            }
        });
    }
}
