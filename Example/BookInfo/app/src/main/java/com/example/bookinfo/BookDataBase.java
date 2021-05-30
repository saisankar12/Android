package com.example.bookinfo;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.view.Display;

@Database(entities = {Model.class},version = 1,exportSchema = false)
public abstract class BookDataBase extends RoomDatabase {

    public abstract BookDao bookDao();

    public static BookDataBase bookDataBase;

    public static BookDataBase getInstance(Context context){
        if(bookDataBase==null){
            synchronized (BookDataBase.class){
                if(bookDataBase==null){
                bookDataBase= Room.databaseBuilder(context.getApplicationContext(),
                        BookDataBase.class,context.getString(R.string.bookdb)).allowMainThreadQueries().
                        fallbackToDestructiveMigration().build();
                }
        }}

        return bookDataBase;
    }


}
