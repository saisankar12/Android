package com.example.saisankar.moviedb.database;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public class MovieRepository {

    private static MovieDao movieDao;

    LiveData<List<MovieInfo>> movieData;

    public MovieRepository(Context context) {
        MovieDataBase dataBase=MovieDataBase.getMovieInstance(context);
        movieDao=dataBase.movieDao();
        movieData=movieDao.getFavMovie();
    }



    public LiveData<List<MovieInfo>> getMovieData(){
        return movieData;
    }

    public void insertmovie(MovieInfo movieInfo){
        new MovieInsertTask(movieDao).execute(movieInfo);
    }

    public void deletemovie(MovieInfo movieInfo){
        new MovieDeleteTask(movieDao).execute(movieInfo);
    }

    public boolean checkFav(int s) {
        return movieDao.checkFav(s);
    }


    private static class MovieInsertTask extends AsyncTask<MovieInfo,Void,Void>{
        private MovieDao movieDao;
        public MovieInsertTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(MovieInfo... movieInfos) {
            movieDao.movieInsert(movieInfos[0]);
            return null;
        }
    }

    private static class MovieDeleteTask extends AsyncTask<MovieInfo,Void,Void>{
        private MovieDao movieDao;

        public MovieDeleteTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(MovieInfo... movieInfos) {
            movieDao.deleteFavMovie(movieInfos[0]);
            return null;
        }
    }
}
