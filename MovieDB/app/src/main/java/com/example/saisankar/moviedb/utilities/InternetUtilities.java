package com.example.saisankar.moviedb.utilities;

import android.net.Uri;
import android.util.Log;

import com.example.saisankar.moviedb.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Sai Sankar on 05-05-2018.
 */

public class InternetUtilities {

    private static String TAG = InternetUtilities.class.getSimpleName();

    final static String BASE_URL = "http://api.themoviedb.org/3/";
    final static String IMAGE_URL = "http://image.tmdb.org/t/p/w300";
    final static String Telugu_URl="http://api.themoviedb.org/4/discover/movie?api_key=2f8a6ebe46f318b2257843c5fc67f119&with_original_language=te";

    final static String Base="http://api.themoviedb.org/3/discover/movie";
    final static String lan="te";
    private static final String API_KEY = BuildConfig.API_KEY;
    static URL movieUrl = null;
    static URL imageUrl = null;



    public static URL moviebuildUrl(String choice) {

        /*Uri uri = Uri.parse(BASE_URL)
                .buildUpon()
                .appendPath("movie")
                .appendPath(choice)
                .appendQueryParameter("api_key", API_KEY)
                .build();*/
            Uri uri=Uri.parse(Base)
                    .buildUpon()
                    .appendQueryParameter("api_key",API_KEY)
                    .appendQueryParameter("with_original_language",lan)
                    .build();

        try {
            movieUrl = new URL(uri.toString());
            Log.i("movieurl",movieUrl.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }
        return movieUrl;
    }


    public static URL buildImageUrl(String path) {
        Uri uri = Uri.parse(IMAGE_URL).buildUpon()
                .appendEncodedPath(""+path)
                .build();
        try {
            imageUrl = new URL(uri.toString());

            Log.i("ImageUrl",imageUrl.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return imageUrl;
    }

    public static String getResponse(URL url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = httpURLConnection.getInputStream();
            Log.i("inputstream",in.toString());
            Scanner scanner = new Scanner(in);
            Log.i("scanner",scanner.toString());
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            httpURLConnection.disconnect();
        }
    }
}
