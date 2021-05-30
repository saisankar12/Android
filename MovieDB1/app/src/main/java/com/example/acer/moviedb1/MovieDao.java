package com.example.acer.moviedb1;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert
    public void inserting(MovieModel movieModel);
    @Update
    public void Updating(MovieModel  movieModel);
    @Delete
    public void deleting(MovieModel movieModel);
    @Query("select * from MovieModel")
    LiveData<List<MovieModel>> getAllMovies();
    @Query("Select id1 from MovieModel where id1= :mid")
    int isFav(int mid);
}
