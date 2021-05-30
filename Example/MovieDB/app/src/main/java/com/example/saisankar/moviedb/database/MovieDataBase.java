package com.example.saisankar.moviedb.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


@Database(entities = {MovieInfo.class},version = 1)
public abstract class MovieDataBase extends RoomDatabase {

    public abstract MovieDao movieDao();

    public static MovieDataBase movieInstance;

    public static synchronized MovieDataBase getMovieInstance(Context context){

        if (movieInstance==null){
            movieInstance=Room.databaseBuilder(context,MovieDataBase.class,"MovieFavDB")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }

        return movieInstance;
    }
}
