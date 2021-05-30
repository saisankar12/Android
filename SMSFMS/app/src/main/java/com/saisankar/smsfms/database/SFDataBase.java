package com.saisankar.smsfms.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.saisankar.smsfms.database.facultydb.Faculty;
import com.saisankar.smsfms.database.studentdb.Student;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Student.class, Faculty.class},version = 1,exportSchema = false)
public abstract class SFDataBase extends RoomDatabase {

    public abstract SFDao myDao();

    static final ExecutorService databaseWriteExecuter = Executors.newFixedThreadPool(4);
    static  SFDataBase dataBase;
    public static synchronized SFDataBase getDataBase(Context context){
        if (dataBase == null){
            dataBase = Room.databaseBuilder(context,
                    SFDataBase.class,"MyDb")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration().build();
        }
        return dataBase;
    }
}
