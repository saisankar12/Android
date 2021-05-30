package com.example.saisankar.sqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {


    public static final String DB_NAME="student";
    public static final String TABLE_NAME="student_info";
    public static final String COL_RollNumber="RollNumber";
    public static final String COL_NAME="Name";
    public static final String COL_GMAIL="Gmail";
    public static final String COL_PHONE="PhoneNumber";
    public static final int VERSION=2;

    public static final String QUERY="create table "+TABLE_NAME+"("+COL_RollNumber+" integer primary key, "+COL_NAME+" text, "+COL_GMAIL+" text, "+COL_PHONE+" text)";

    SQLiteDatabase database;

    public DBhelper(Context context) {
        super(context, DB_NAME, null,VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL(QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TABLE_NAME);
        onCreate(db);
    }

    public long insert(ContentValues values){

        database=getWritableDatabase();
        long i=database.insert(TABLE_NAME,null,values);
        return i;

    }

   public Cursor read(){
        database=getReadableDatabase();
        Cursor cursor=database.rawQuery("select * from "+TABLE_NAME,null);
        return cursor;
    }
    public boolean update(String id,String name,String gmail,String phone){
        database=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(DBhelper.COL_RollNumber,id);
        values.put(DBhelper.COL_NAME,name);
        values.put(DBhelper.COL_GMAIL,gmail);
        values.put(DBhelper.COL_PHONE,phone);

        database.update(TABLE_NAME, values, "RollNumber = ?",new String[] { id });
        return true;
    }

    public Integer delete(String id){
        database=getWritableDatabase();
        return database.delete(TABLE_NAME,"RollNumber = ?",new String[] {id});

    }

}
