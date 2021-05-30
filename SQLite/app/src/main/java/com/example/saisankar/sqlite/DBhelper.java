package com.example.saisankar.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sai Sankar on 17-03-2018.
 */

public class DBhelper extends SQLiteOpenHelper {


    public static final String DB_Name="details";
    public static final String Table_Name="student";
    public static final String COL_Name="stu_name";
    public static final String COL_Gmail="stu_gmail";
    public static final String COL_Phone="stu_phone";

    public static final int version=2;

    public static final String QUERY
            ="create table "+Table_Name+"("+COL_Name+" text,"+COL_Gmail+" text,"+COL_Phone+" text)";

    SQLiteDatabase database;

    public DBhelper(Context context) {
        super(context, DB_Name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        /*db.execSQL("drop table if exists "+Table_Name);
        onCreate(db);*/

        db.execSQL("drop table if exists "+Table_Name);
        onCreate(db);

    }
    public long insertData(ContentValues values) {
        database=getWritableDatabase();
        long i=database.insert(Table_Name,
                null,values);
        return i;
    }

    public Cursor readData(){
        database=getReadableDatabase();
        Cursor cursor=database.rawQuery("select * from "
                +Table_Name,null);
        return cursor;
    }
}
