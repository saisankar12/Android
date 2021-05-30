package com.google.developer.bugmaster;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.developer.bugmaster.data.BugsDbHelper;
import com.google.developer.bugmaster.data.DatabaseManager;
import com.google.developer.bugmaster.data.Insect;
import com.google.developer.bugmaster.data.InsectRecyclerAdapter;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener {

    RecyclerView recyclerView;
    int flag = 0;
    String colors[];
    String sortingOrder="COMMON_NAME";
    BugsDbHelper bugsDbHelper;
    InsectRecyclerAdapter insectRecyclerAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bugsDbHelper=new BugsDbHelper(this);
        bugsDbHelper.getData();
        colors=getResources().getStringArray(R.array.dangerColors);
        loadRecyclerView();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    private void loadRecyclerView() {

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        insectRecyclerAdapter = new InsectRecyclerAdapter(this,bugsDbHelper.sciti_name,colors,
                sortingOrder);
        recyclerView.setAdapter(insectRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort:
                //TODO: Implement the sort action
                if(flag==0)
                {
                    sortingOrder = "DANGER_LEVEL";
                    flag=1;
                    loadRecyclerView();
                }
                else if(flag==1)
                {
                    sortingOrder = "COMMON_NAME";
                    flag=0;
                    loadRecyclerView();
                }
                return true;
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* Click events in Floating Action Button */
    @Override
    public void onClick(View v) {
        //TODO: Launch the quiz activity
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
        Cursor c= databaseManager.queryNameOfInsects(sci_name);
        String nameOfInsect = null;
        String sci_nameOfInsect = null;
        while(c.moveToNext())
        {
            nameOfInsect = c.getString(0);
            sci_nameOfInsect = c.getString(1);
        }
        Insect insect = new Insect(nameOfInsect,sci_nameOfInsect,"","",0);

        Intent intent = new Intent(this,QuizActivity.class);
        intent.putParcelableArrayListExtra(QuizActivity.EXTRA_INSECTS,insects);
        intent.putExtra(QuizActivity.EXTRA_ANSWER,insect);
        startActivity(intent);
    }
}
