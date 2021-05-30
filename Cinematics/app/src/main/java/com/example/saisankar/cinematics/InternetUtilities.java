package com.example.saisankar.cinematics;

import android.net.Uri;

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



    private final static String IMAGE_URL = "http://image.tmdb.org/t/p/w300";

    private static URL imageUrl = null;



    public static URL buildImageUrl(String path) {
        Uri uri = Uri.parse(IMAGE_URL).buildUpon()
                .appendEncodedPath(path)
                .build();
        try {
            imageUrl = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return imageUrl;
    }

    public static String getResponse(URL url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
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
