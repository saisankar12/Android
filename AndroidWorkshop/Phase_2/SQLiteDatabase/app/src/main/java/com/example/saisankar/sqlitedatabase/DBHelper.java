package com.example.saisankar.sqlitedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper
{
    public static final String DBNAME="kiet";
    public static final String TBNAME="students";
    public static final String COL1="name";
    public static final String COL2="branch";
    public static final int Version=1;

    SQLiteDatabase database;


    public DBHelper(Context context)
    {
        super(context, DBNAME, null, Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String QUERY="create table " + TBNAME + " ( "
                + COL1 + " text,"
                + COL2 + " text)";
        db.execSQL(QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("drop table if exists " + TBNAME);
        onCreate(db);
    }
    public long saveData(ContentValues values)
    {
        database=getWritableDatabase();
       long i= database.insert(TBNAME,null,values);
       return i;
    }
    public Cursor readData()
    {
        database=getReadableDatabase();
        Cursor cursor=database.rawQuery(
                "select * from " +TBNAME,null);
        return cursor;
    }
}
