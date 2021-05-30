package com.example.roomdemo.components;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {StudentModel.class},version = 1)
public abstract class StudentDatabase extends RoomDatabase {

public abstract StudentDAO studentDAO();

public static StudentDatabase databaseInstance;

public static synchronized StudentDatabase getDataBase(Context context){

    if (databaseInstance==null){
        databaseInstance= Room.databaseBuilder(context,StudentDatabase.class,"Student")
                .fallbackToDestructiveMigration().build();
    }
    return databaseInstance;
}

}

