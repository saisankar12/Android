package com.example.roomlivedata;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = StudentModel.class,version = 1)
public abstract class StudentDataBase extends RoomDatabase {

    public abstract StudnetDAO studnetDAO();

    public static StudentDataBase dataBase;

    public static synchronized StudentDataBase createDatabase(Context context){

        if (dataBase==null){
            dataBase= Room.databaseBuilder(context,StudentDataBase.class,"sai")
                    .fallbackToDestructiveMigration().build();
        }
        return dataBase;
    }
}
