package com.example.saisankar.moviedb;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Sai Sankar on 23-05-2018.
 */

public class MovieSQLiteDb extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movie.db";
    public static final int VERSION = 4;

    public MovieSQLiteDb(Context context) {
        super(context, MovieContract.MovieContractEntry.TABLE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String Movie_Table = "CREATE TABLE " + MovieContract.MovieContractEntry.TABLE_NAME + "("
                + MovieContract.MovieContractEntry.COLUMN_MOVIE_ID + " INTEGER PRIMARY KEY" + ","
                + MovieContract.MovieContractEntry.COLUMN_AVERAGE + " TEXT" + ","
                + MovieContract.MovieContractEntry.COLUMN_TITLE + " TEXT" + ","
                + MovieContract.MovieContractEntry.COLUMN_IMAGEPOSTER + " TEXT" + ","
                + MovieContract.MovieContractEntry.COLUMN_ORIGINALTITLE + " TEXT" + ","
                + MovieContract.MovieContractEntry.COLUMN_IMAGEBACKDROP + " TEXT" + ","
                + MovieContract.MovieContractEntry.COLUMN_OVERVIEW + " TEXT" + ","
                + MovieContract.MovieContractEntry.COLUMN_RELEASEDATE + " TEXT" + ")";

        db.execSQL(Movie_Table);
        Log.i("DataBase", db.toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieContractEntry.TABLE_NAME);
        onCreate(db);
    }
    public boolean isFavorite(int fav){
        SQLiteDatabase database=getWritableDatabase();
        String s="SELECT * FROM "+MovieContract.MovieContractEntry.TABLE_NAME + " WHERE " + MovieContract.MovieContractEntry.COLUMN_MOVIE_ID + " =?";
        Cursor cursor=database.rawQuery(s,new String[]{String.valueOf(fav)});
        boolean isFavorite =false;
        if (cursor.moveToFirst()){
            isFavorite=true;
            int count=0;
            while (cursor.moveToNext()){
                count++;
            }
        }
        cursor.close();
        database.close();
        return isFavorite;
    }
}
