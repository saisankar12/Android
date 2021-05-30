package com.example.saisankar.roomdatabase;

import android.arch.persistence.room.Room;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

public static FragmentManager manager;
public static MyAppDatabase  myAppDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager=getSupportFragmentManager();

        myAppDatabase=Room.databaseBuilder(getApplicationContext(),
                MyAppDatabase.class,"userdb").allowMainThreadQueries().build();
        if (findViewById(R.id.fragment_container)!=null){
            if (savedInstanceState!=null){
                return;
            }
            manager.beginTransaction().replace(R.id.fragment_container,
                    new InsertFragment()).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.insertuser:
                MainActivity.manager.beginTransaction().replace(R.id.fragment_container,new InsertFragment())
                        .addToBackStack(null).commit();
                break;
            case R.id.viewuser:
                MainActivity.manager.beginTransaction().replace(R.id.fragment_container,new ReadFragment())
                        .addToBackStack(null).commit();
                break;
            case R.id.deleteuser:
                MainActivity.manager.beginTransaction().replace(R.id.fragment_container,new DeleteFragment())
                        .addToBackStack(null).commit();
                break;
            case R.id.updateuser:
                MainActivity.manager.beginTransaction().replace(R.id.fragment_container,new UpdateFragment())
                        .addToBackStack(null).commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
