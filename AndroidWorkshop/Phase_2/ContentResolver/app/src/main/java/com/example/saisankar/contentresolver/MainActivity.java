package com.example.saisankar.contentresolver;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity
{
    Uri  uri = Uri.parse("content://com.example.saisankar.sqlitedatabase.contentprovider");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Cursor cursor = getContentResolver().query(uri,null,null,
                null,null);
        StringBuffer sb = new StringBuffer();

        cursor.moveToFirst();
        do
        {
            String msg=cursor.getString(0)+"\n"
                    + cursor.getString(1);
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }while (cursor.moveToNext());


    }
}
