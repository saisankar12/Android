package com.example.saisankar.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText name,gmail,phone;
    DBhelper dBhelper;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name=findViewById(R.id.name);
        gmail=findViewById(R.id.gmail);
        phone=findViewById(R.id.phone);
        tv=findViewById(R.id.text);

        dBhelper=new DBhelper(this);
    }

    public void insert(View view) {
        String i_name=name.getText().toString();
        String i_gmail=gmail.getText().toString();
        String i_phone=phone.getText().toString();
        ContentValues values=new ContentValues();
        values.put(DBhelper.COL_Name,i_name);
        values.put(DBhelper.COL_Gmail,i_gmail);
        values.put(DBhelper.COL_Phone,i_phone);
        long i=dBhelper.insertData(values);
        if (i>0){
            Toast.makeText(this, "Data Saved Sucessfully", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Problem", Toast.LENGTH_SHORT).show();
        }
    }

    public void read(View view) {

        Cursor cursor=dBhelper.readData();
        StringBuilder sb=new StringBuilder();
        while (cursor.moveToNext()){
            sb.append(cursor.getString(0)+"\n"
            +cursor.getString(1)+"\n"
            +cursor.getString(2)+"\n"
            );
            //Toast.makeText(this, ""+sb, Toast.LENGTH_SHORT).show();
            tv.setText(sb);
        }


    }
}
