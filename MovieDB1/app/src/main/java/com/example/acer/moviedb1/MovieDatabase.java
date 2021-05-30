package com.example.acer.moviedb1;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import static android.arch.persistence.room.Room.databaseBuilder;

@Database(entities = {MovieModel.class},version = 1,exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
    public static MovieDatabase movieDatabase;
    public static MovieDatabase getMovieDatabase(Context context)
    {
        if (movieDatabase == null) {
            movieDatabase=Room.databaseBuilder(context,MovieDatabase.class,"fav")
                    .allowMainThreadQueries().fallbackToDestructiveMigration().build();

        }
        return movieDatabase;

    }
}
