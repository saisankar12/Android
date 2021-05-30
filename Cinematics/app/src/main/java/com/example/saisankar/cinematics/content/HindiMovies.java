package com.example.saisankar.cinematics.content;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.saisankar.cinematics.InternetUtilities;
import com.example.saisankar.cinematics.R;
import com.example.saisankar.cinematics.adapter.MovieAdapter;
import com.example.saisankar.cinematics.model.MovieInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HindiMovies extends Fragment {


    private static final String RESULTS = "results";
    private static final String id = "id";
    private static final String vote_count = "vote_count";
    private static final String video = "video";
    private static final String vote_average = "vote_average";
    private static final String title = "title";
    private static final String popularity = "popularity";
    private static final String poster_path = "poster_path";
    private static final String original_language = "original_language";
    private static final String original_title = "original_title";
    private static final String backdrop_path = "backdrop_path";
    private static final String adult = "adult";
    private static final String overview = "overview";
    private static final String release_date = "release_date";
    private RecyclerView recyclerView;
    private final String hindiurl = "https://api.themoviedb.org/3/discover/movie?api_key=2f8a6ebe46f318b2257843c5fc67f119&with_original_language=hi";
    private URL url;
    public HindiMovies() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hindi_movies, container, false);
        recyclerView = view.findViewById(R.id.recycler);
        recyclerView = view.findViewById(R.id.recycler);
        try {
            url = new URL(hindiurl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        new HindiAsynTask().execute(url);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        return view;
    }

    public void getMovies(String s) throws JSONException {
        ArrayList<MovieInfo> movieInfos = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(s);
        JSONArray jsonArray = jsonObject.getJSONArray(RESULTS);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject getJsonObject = jsonArray.getJSONObject(i);
            movieInfos.add(new MovieInfo(getJsonObject.getInt(id),
                    getJsonObject.getInt(vote_count),
                    getJsonObject.getString(video),
                    getJsonObject.getString(vote_average),
                    getJsonObject.getString(title),
                    getJsonObject.getString(popularity),
                    getJsonObject.getString(poster_path),
                    getJsonObject.getString(original_language),
                    getJsonObject.getString(original_title),
                    getJsonObject.getString(backdrop_path),
                    getJsonObject.getString(adult),
                    getJsonObject.getString(overview),
                    getJsonObject.getString(release_date)));

        }

        recyclerView.setAdapter(new MovieAdapter(getActivity(), movieInfos));
    }

    @SuppressLint("StaticFieldLeak")
    class HindiAsynTask extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... urls) {
            String s = "";
            try {
                s = InternetUtilities.getResponse(urls[0]);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                getMovies(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
