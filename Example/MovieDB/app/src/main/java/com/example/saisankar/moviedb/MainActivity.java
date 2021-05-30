package com.example.saisankar.moviedb;



import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saisankar.moviedb.database.MovieInfo;
import com.example.saisankar.moviedb.database.MovieViewModel;
import com.example.saisankar.moviedb.utilities.InternetUtilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    private static final String RESULTS="results";
    private static final String id="id";
    private static final String vote_count="vote_count";
    private static final String video="video";
    private static final String vote_average="vote_average";
    private static final String title="title";
    private static final String popularity="popularity";
    private static final String poster_path="poster_path";
    private static final String original_language="original_language";
    private static final String original_title="original_title";
    private static final String backdrop_path="backdrop_path";
    private static final String adult="adult";
    private static final String overview="overview";
    private static final String release_date="release_date";

    MovieAdapter movieAdapter;
    URL url;
    Snackbar snackbar;
    @BindView(R.id.recycler) RecyclerView recyclerView;
    @BindView(R.id.linearLayout) LinearLayout linearLayout;

    static MovieViewModel movieViewModel;
    static List<MovieInfo> movieInfos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieViewModel=ViewModelProviders.of(this).get(MovieViewModel.class);
        linearLayout=(LinearLayout)findViewById(R.id.linearLayout);
        ButterKnife.bind(this);
        ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null){
            snackbar = Snackbar.make(linearLayout, R.string.No_Internet, Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.WHITE);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        }else{
            url = InternetUtilities.moviebuildUrl(getString(R.string.popular_movie));
            new MovieAsynTask().execute(url);
        }

        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
        else{
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }
    }

    public Activity getActivity() {
        Context context = this;
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }
    public class MovieAsynTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            snackbar = Snackbar.make(linearLayout, R.string.loading, Snackbar.LENGTH_LONG);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        }

        @Override
        protected String doInBackground(URL... urls) {
            String s="";
            try {
                s=InternetUtilities.getResponse(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            snackbar = Snackbar.make(linearLayout, "Loading Compleate..!", Snackbar.LENGTH_LONG);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.GREEN);
            snackbar.show();
            movieInfos=new ArrayList<>();
            try {
                JSONObject  moviejsonObject = new JSONObject(s);
                JSONArray jsonArrayresults=moviejsonObject.getJSONArray(RESULTS);
                for (int i=0;i<jsonArrayresults.length();i++){
                    JSONObject jsonObject=jsonArrayresults.getJSONObject(i);



                    // MovieInfo info = new MovieInfo();

                    movieInfos.add(new MovieInfo(jsonObject.getInt(id),
                            jsonObject.getInt(vote_count),
                            jsonObject.getString(video),
                            jsonObject.getString(vote_average),
                            jsonObject.getString(title),
                            jsonObject.getString(popularity),
                            "http://image.tmdb.org/t/p/w300"+jsonObject.getString(poster_path),
                            jsonObject.getString(original_language),
                            jsonObject.getString(original_title),
                            "http://image.tmdb.org/t/p/w300"+jsonObject.getString(backdrop_path),
                            jsonObject.getString(adult),
                            jsonObject.getString(overview),
                            jsonObject.getString(release_date)));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            movieAdapter=new MovieAdapter(MainActivity.this,movieInfos);
            recyclerView.setAdapter(movieAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==R.id.toprated){
            ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
            if (netInfo == null){

                snackbar = Snackbar.make(linearLayout, R.string.No_Internet, Snackbar.LENGTH_LONG);
                snackbar.setActionTextColor(Color.WHITE);
                // Changing action button text color
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);
                snackbar.show();
            }else{
                URL url=InternetUtilities.moviebuildUrl("top_rated");
                new MovieAsynTask().execute(url);
            }
            return true;
        }else if (item.getItemId()==R.id.popular){
            ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
            if (netInfo == null){
                snackbar = Snackbar.make(linearLayout, R.string.No_Internet, Snackbar.LENGTH_LONG);
                snackbar.setActionTextColor(Color.WHITE);
                // Changing action button text color
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);
                snackbar.show();
            }else{
                URL url1=InternetUtilities.moviebuildUrl(getString(R.string.popular_movie));
                new MovieAsynTask().execute(url1);
            }
            return true;
        }else if (item.getItemId()==R.id.favorite){
            movieViewModel.getFavData().observe(MainActivity.this, new Observer<List<MovieInfo>>() {
                @Override
                public void onChanged(@Nullable List<MovieInfo> movieInfos) {
                    movieAdapter=new MovieAdapter(MainActivity.this,movieInfos);
                    recyclerView.setAdapter(movieAdapter);
                    Log.i("movidata", String.valueOf(movieInfos));
                }
            });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}