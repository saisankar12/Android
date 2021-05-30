package com.example.saisankar.sqlitedatabase;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
  EditText et_name,et_branch;
  DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_name=(EditText) findViewById(R.id.et_name);
        et_branch=(EditText)findViewById(R.id.et_branch);
        helper=new DBHelper(this);
    }

    public void insertData(View view)
    {
        ContentValues values=new ContentValues();
        values.put(DBHelper.COL1,et_name.getText().toString());
        values.put(DBHelper.COL2,et_branch.getText().toString());
        long i=helper.saveData(values);
        if (i>0)
        {
            Toast.makeText(this,
                    "Data inserted and" +i + "rows are effected",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void readData(View view)
    {
        Cursor cursor=helper.readData();
        cursor.moveToFirst();
        do
        {
            String msg=cursor.getString(0)+"\n"
                    + cursor.getString(1);
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }while (cursor.moveToNext());

       /* Uri uri=Uri.parse("tel:"+et_name.getText().toString());
        Intent i=new Intent(Intent.ACTION_VIEW,uri);
        i.setAction(Intent.ACTION_DIAL);
        startActivity(i);*/

        /*ShareCompat.IntentBuilder
                .from(this)
                .setType("text/plain")
                .setChooserTitle("Share this text with: ")
                .setText(et_name.getText().toString())
                .startChooser();*/
    }
}
