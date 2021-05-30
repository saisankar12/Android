package com.example.saisankar.moviedb;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Sai Sankar on 24-05-2018.
 */

public class MovieContract {
    public static final String MOVIE_AUTHORITY = "com.example.saisankar.moviedb";
    public static final Uri MOVIE_CONTENT_URI = Uri.parse("content://" + MOVIE_AUTHORITY);
    public static final String MOVIE_PATH = "MovieData";


    public static final class MovieContractEntry implements BaseColumns {

        public static final Uri CONTENT_URI=MovieContract.MOVIE_CONTENT_URI.buildUpon().appendPath(MOVIE_PATH).build();

        public static final String TABLE_NAME = "MovieData";

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_IMAGEPOSTER = "poster_path";
        public static final String COLUMN_IMAGEBACKDROP = "backdrop_path";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_AVERAGE = "rating";
        public static final String COLUMN_RELEASEDATE = "release_date";
        public static final String COLUMN_ORIGINALTITLE = "original_title";


    }
}
