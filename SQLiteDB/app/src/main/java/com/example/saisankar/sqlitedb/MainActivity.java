package com.example.saisankar.sqlitedb;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText et1,et2,et3,et4;
    DBhelper dBhelper;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         et1=findViewById(R.id.studentrollnumber);
         et2=findViewById(R.id.studentname);
         et3=findViewById(R.id.studentgmail);
         et4=findViewById(R.id.studentphone);

         tv=findViewById(R.id.textview);
         dBhelper=new DBhelper(this);
    }

    public void insert(View view) {

        String id=et1.getText().toString();
        String name=et2.getText().toString();
        String gmail=et3.getText().toString();
        String phone=et4.getText().toString();

        ContentValues values=new ContentValues();
        values.put(DBhelper.COL_RollNumber,id);
        values.put(DBhelper.COL_NAME,name);
        values.put(DBhelper.COL_GMAIL,gmail);
        values.put(DBhelper.COL_PHONE,phone);

        long i=dBhelper.insert(values);
        if (id.isEmpty()&&name.isEmpty()&&gmail.isEmpty()&&phone.isEmpty() ){
            Toast.makeText(this, "Please Fill All Details", Toast.LENGTH_SHORT).show();
        }else if (i>0){
            Toast.makeText(this, "Data Inserted", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Insertion Failed", Toast.LENGTH_SHORT).show();
        }
        et1.setText("");
        et2.setText("");
        et3.setText("");
        et4.setText("");

    }

    public void read(View view) {

        Cursor c=dBhelper.read();

        StringBuilder sb=new StringBuilder();

        while (c.moveToNext()){
            sb.append(c.getString(0)+"\n"+
                      c.getString(1)+"\n"+
                      c.getString(2)+"\n"+
                      c.getString(3)+"\n");
            tv.setText(sb);
            //Toast.makeText(this, sb, Toast.LENGTH_SHORT).show();
        }


    }

    public void update(View view) {
        String id=et1.getText().toString();
        String name=et2.getText().toString();
        String gmail=et3.getText().toString();
        String phone=et4.getText().toString();

        boolean i=dBhelper.update(id,name,gmail,phone);
        if(i == true) {
            Toast.makeText(MainActivity.this, "Data Update", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_LONG).show();
        }
    }

    public void delete(View view) {
        String id=et1.getText().toString();
        Integer deleterows=dBhelper.delete(id);
        if (deleterows>0){
            Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Data Not Deleted", Toast.LENGTH_SHORT).show();
        }


    }
}
