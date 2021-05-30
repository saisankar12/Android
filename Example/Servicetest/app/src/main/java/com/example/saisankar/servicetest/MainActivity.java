package com.example.saisankar.servicetest;

import android.arch.persistence.room.Room;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.saisankar.servicetest.database.UserDataBase;

public class MainActivity extends AppCompatActivity {

    FragmentManager manager;
    static UserDataBase userDataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager=getSupportFragmentManager();
        userDataBase=Room.databaseBuilder
                (this,UserDataBase.class,"user_info")
                .allowMainThreadQueries().build();

        manager.beginTransaction().replace(R.id.fragment,new InsertFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.operatons,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.adduser:
                manager.beginTransaction().replace(R.id.fragment,new InsertFragment()).commit();
                break;
            case R.id.viewuser:
                manager.beginTransaction().replace(R.id.fragment,new ViewFragment()).commit();
                break;
            case R.id.updateuser:
                manager.beginTransaction().replace(R.id.fragment,new UpdateFragment()).commit();
                break;
            case R.id.deleteuser:
                manager.beginTransaction().replace(R.id.fragment,new DeleteFragment()).commit();
                break;


        }
        return super.onOptionsItemSelected(item);
    }
}
