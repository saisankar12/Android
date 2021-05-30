package com.example.saisankar.moviedb.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {

    MovieRepository movieRepository;

    LiveData<List<MovieInfo>> getMovieData;
    public MovieViewModel(@NonNull Application application) {
        super(application);

        movieRepository=new MovieRepository(application);
        getMovieData=movieRepository.getMovieData();
    }

    public void insert(MovieInfo movieInfo){
        movieRepository.insertmovie(movieInfo);
    }

    public  void delete(MovieInfo movieInfo){
        movieRepository.deletemovie(movieInfo);
    }

    public LiveData<List<MovieInfo>> getFavData(){
        return getMovieData;
    }

    public boolean checkFav(int s) {
        return movieRepository.checkFav(s);
    }
}
