package com.example.saisankar.roomwithlivedata;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = User.class,version = 1)
public abstract class UserDataBase extends RoomDatabase {

    public abstract NoteDao noteDao();

    private static UserDataBase instance;

    public static synchronized UserDataBase getInstance(Context context){

        if (instance==null){
            instance=Room.databaseBuilder(context.getApplicationContext(),UserDataBase.class,
                    "userdb")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }


}
