package com.example.saisankar.moviedb.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Sai Sankar on 05-05-2018.
 */

public class InternetUtilities {

    private static String TAG = InternetUtilities.class.getSimpleName();

    final static  String BASE_URL = "http://api.themoviedb.org/3/";
    final static  String API_KEY = "2f8a6ebe46f318b2257843c5fc67f119";
    final static  String IMAGE_URL = "http://image.tmdb.org/t/p/w300";
    static URL movieUrl = null;
    static URL imageUrl = null;


    public static URL moviebuildUrl(String choice) {
        Uri uri = Uri.parse(BASE_URL)
                .buildUpon()
                .appendPath("movie")
                .appendPath(choice)
                .appendQueryParameter("api_key", API_KEY)
                .build();
        try {
            movieUrl = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }
        return movieUrl;
    }
    /*public static URL buildImageUrl(String path) {
        Uri uri = Uri.parse(IMAGE_URL).buildUpon()
                .appendPath(path)
                .build();
        try {
            imageUrl = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return imageUrl;
    }*/

    public static String getResponse(URL url) throws IOException {
        HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
        try {
            InputStream in = httpURLConnection.getInputStream();

            Scanner scanner = new Scanner(in);
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
