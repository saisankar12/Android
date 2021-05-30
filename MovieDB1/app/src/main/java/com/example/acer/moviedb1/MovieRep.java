package com.example.acer.moviedb1;


import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public class MovieRep {
    LiveData<List<MovieModel>> getAll;
    public static MovieDao movieDao;
    public MovieRep(Context context)
    {
        MovieDatabase movieDatabase=MovieDatabase.getMovieDatabase(context);
        movieDao=movieDatabase.movieDao();
        getAll=movieDao.getAllMovies();
    }

    LiveData<List<MovieModel>> getAll() {
        return getAll;
    }
    public void insert(MovieModel movieModel)
    {
        new InsertTask().execute(movieModel);
    }
    public void delete(MovieModel movieModel)
    {
        new DeleteTask().execute(movieModel);
    }
    public class InsertTask extends AsyncTask<MovieModel,Void,Void>
    {

        @Override
        protected Void doInBackground(MovieModel... movieModels) {
            movieDao.inserting(movieModels[0]);
            return null;
        }
    }
    public class DeleteTask extends AsyncTask<MovieModel,Void,Void>
    {

        @Override
        protected Void doInBackground(MovieModel... movieModels) {
            movieDao.deleting(movieModels[0]);
            return null;
        }
    }
    public int checkMovie(int mid)
    {

        return movieDao.isFav(mid);
    }
}

