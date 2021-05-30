package com.example.acer.moviedb1;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class MovieViewHolder extends AndroidViewModel {
    public MovieRep movieRep;
    LiveData<List<MovieModel>> getAllMovie;
    public MovieViewHolder(@NonNull Application application) {
        super(application);
    movieRep=new MovieRep(application);
    getAllMovie=movieRep.getAll();

    }
    public void insertData(MovieModel movieModel)
    {
        movieRep.insert(movieModel);
    }
    public void deleteModel(MovieModel movieModel)
    {
        movieRep.delete(movieModel);
    }
    public int favs(int md)
    {
        return movieRep.checkMovie(md);
    }
}
