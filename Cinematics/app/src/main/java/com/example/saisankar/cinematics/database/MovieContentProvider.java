package com.example.saisankar.cinematics.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

public class MovieContentProvider extends ContentProvider {
    private static final int MOVIE = 50;

    private static final UriMatcher sUriMatcher = uriMatcher();
    private MovieSQLiteDb movieSQLiteDb;

    public MovieContentProvider() {
    }

    static UriMatcher uriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.MOVIE_AUTHORITY;
        matcher.addURI(authority, MovieContract.MOVIE_PATH, MOVIE);
        Log.i("matcher", matcher.toString());
        return matcher;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        final SQLiteDatabase database = movieSQLiteDb.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int moviefavdeleted;
        if (null == selection) selection = "1";
        switch (match) {
            case MOVIE:
                //String id = uri.getPathSegments().get(1);
                moviefavdeleted = database.delete(MovieContract.MovieContractEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri:" + uri);
        }
        if (moviefavdeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return moviefavdeleted;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.

        final SQLiteDatabase database = movieSQLiteDb.getWritableDatabase();
        int match = sUriMatcher.match(uri);

        Uri returnUri = null;
        switch (match) {
            case MOVIE:
                long id = database.insert(MovieContract.MovieContractEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(MovieContract.MovieContractEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unkonown Uri" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        movieSQLiteDb = new MovieSQLiteDb(getContext());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.

        final SQLiteDatabase database = movieSQLiteDb.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor movieCursor;
        switch (match) {
            case MOVIE:
                movieCursor = database.query(MovieContract.MovieContractEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unkonown Uri" + uri);

        }

        movieCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return movieCursor;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
