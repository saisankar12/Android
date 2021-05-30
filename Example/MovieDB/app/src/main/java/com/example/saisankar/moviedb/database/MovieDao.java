package com.example.saisankar.moviedb.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert
    public void movieInsert(MovieInfo movieInfo);

    @Query("select * from Movie_Data")
    LiveData<List<MovieInfo>> getFavMovie();

    @Delete
    public void deleteFavMovie(MovieInfo movieInfo);

    @Query("SELECT * FROM Movie_Data WHERE movie_id = :poster")
    public boolean checkFav(int poster);
}
