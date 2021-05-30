package com.example.saisankar.moviedb;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.saisankar.moviedb.adapter.MovieAdapter;
import com.example.saisankar.moviedb.model.MovieInfo;
import com.example.saisankar.moviedb.utilities.InternetUtilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private static final int POPULAR_MOVIES_ID = 23;
    private static final int TOP_RATED_MOVIES_ID = 24;
    private static final int FAVOURITE_LOADER = 25;
    private static final int TELUGU_LOADER = 26;
    private static final String key = "BUNDLE";
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
    private static final String SEARCH = "1";
    static String value = "popular";
    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    private MovieAdapter movieAdapter;
    private Snackbar snackbar;
    private ConnectivityManager conMgr;
    private NetworkInfo netInfo;
    private ArrayList<MovieInfo> moviecursor = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = conMgr.getActiveNetworkInfo();

        if (internetConnection()) {
            if (savedInstanceState != null) {
                if (savedInstanceState.containsKey(key)) {
                    switch (savedInstanceState.getString(key)) {
                        case "popular":
                            value = "popular";
                            saveInstanceitem(value);
                            break;
                        case "top_rated":
                            value = "top_rated";
                            saveInstanceitem(value);
                            break;
                        case "favorites":
                            value = "favorites";
                            saveInstanceitem(value);
                            break;
                    }
                } else {
                    //value="popular";
                    saveInstanceitem(value);
                }
            } else {
                saveInstanceitem(value);
            }

        } else {
            snackbarsetting();
        }
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }
    }

    private Boolean internetConnection() {
        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            return false;
        }
        return true;
    }

    private void snackbarsetting() {
        snackbar = Snackbar.make(linearLayout, R.string.No_Internet, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    private Activity getActivity() {
        Context context = this;
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }


    public void saveInstanceitem(String savevalue) {
        if (savevalue == "popular") {
            if (internetConnection()) {
                Bundle movieBundle = new Bundle();
                value = "popular";
                movieBundle.putString(SEARCH, "popular");
                /*recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
                    @Override
                    public void onLoadMore() {
                        getSupportLoaderManager().initLoader(POPULAR_MOVIES_ID, movieBundle,MainActivity.this);
                    }
                });*/
                getSupportLoaderManager().initLoader(POPULAR_MOVIES_ID, movieBundle, this);
                onSaveInstanceState(movieBundle);

            } else {
                snackbarsetting();
            }

        } else if (savevalue == "top_rated")

        {

            if (internetConnection()) {
                Bundle movieBundle = new Bundle();
                value = "top_rated";
                movieBundle.putString(SEARCH, getString(R.string.top_rated_movie));
                getSupportLoaderManager().restartLoader(TOP_RATED_MOVIES_ID, movieBundle, this);
            } else {
                snackbarsetting();
            }

        } else if (savevalue == "favorites") {
            Bundle movieBundle = new Bundle();
            movieBundle.putString(SEARCH, "favourite");
            getSupportLoaderManager().restartLoader(FAVOURITE_LOADER, movieBundle, this);
        }
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case POPULAR_MOVIES_ID:
                return new AsyncTaskLoader<String>(this) {
                    @Override
                    public String loadInBackground() {
                        String s = "";
                        try {
                            //s = InternetUtilities.getResponse(InternetUtilities.moviebuildUrl(getResources().getString(R.string.popular_movie)));
                            s = InternetUtilities.getResponse(InternetUtilities.moviebuildUrl(getResources().getString(R.string.telugu)));
                            Log.i("final url", s.toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return s;
                    }

                    @Override
                    protected void onStartLoading() {
                        super.onStartLoading();
                        snackbar = Snackbar.make(linearLayout, R.string.loading, Snackbar.LENGTH_LONG);
                        View sbView = snackbar.getView();
                        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(Color.YELLOW);
                        snackbar.show();
                        forceLoad();
                    }

                    @Override
                    protected void onStopLoading() {
                        super.onStopLoading();

                    }
                };
            case TOP_RATED_MOVIES_ID:
                return new AsyncTaskLoader<String>(this) {
                    @Override
                    public String loadInBackground() {
                        String s = "";
                        try {
                            //s = InternetUtilities.getResponse(InternetUtilities.moviebuildUrl(getResources().getString(R.string.top_rated_movie)));
                            s = InternetUtilities.getResponse(InternetUtilities.moviebuildUrl(getResources().getString(R.string.hindi)));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return s;
                    }

                    @Override
                    protected void onStartLoading() {
                        super.onStartLoading();
                        snackbar = Snackbar.make(linearLayout, R.string.loading, Snackbar.LENGTH_LONG);
                        View sbView = snackbar.getView();
                        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(Color.YELLOW);
                        snackbar.show();
                        forceLoad();
                    }
                };
            case FAVOURITE_LOADER:
                return new AsyncTaskLoader<String>(this) {
                    @Override
                    public String loadInBackground() {

                        Cursor cursor = getContentResolver()
                                .query(MovieContract.MovieContractEntry.CONTENT_URI,
                                        null,
                                        null,
                                        null,
                                        null);
                        moviecursor = getFavoriteMoviesDataFromCursor(cursor);
                        return String.valueOf(cursor);
                    }

                    @Override
                    protected void onStartLoading() {
                        super.onStartLoading();
                        forceLoad();
                    }
                };
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        switch (loader.getId()) {
            case POPULAR_MOVIES_ID:
                movieparse(data);
                snackbar = Snackbar.make(linearLayout, "Loading Compleate..!", Snackbar.LENGTH_LONG);
                View sbView = snackbar.getView();
                TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.GREEN);
                snackbar.show();
                break;
            case TOP_RATED_MOVIES_ID:
                movieparse(data);
                snackbar = Snackbar.make(linearLayout, "Loading Compleate..!", Snackbar.LENGTH_LONG);
                View rsbView = snackbar.getView();
                TextView rtextView = rsbView.findViewById(android.support.design.R.id.snackbar_text);
                rtextView.setTextColor(Color.GREEN);
                snackbar.show();
                break;
            case FAVOURITE_LOADER:
                if (!moviecursor.isEmpty()) {
                    movieAdapter = new MovieAdapter(MainActivity.this, moviecursor);
                    recyclerView.setAdapter(movieAdapter);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Movie App");
                    builder.setMessage("There is No Favourites");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    builder.show();
                }
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
    }

    private ArrayList<MovieInfo> getFavoriteMoviesDataFromCursor(Cursor cursor) {
        ArrayList<MovieInfo> results = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                MovieInfo movie = new MovieInfo(cursor);
                results.add(movie);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return results;
    }

    private void movieparse(String s) {
        ArrayList<MovieInfo> movieInfos = new ArrayList<>();
        try {
            JSONObject moviejsonObject = new JSONObject(s);
            JSONArray jsonArrayresults = moviejsonObject.getJSONArray(RESULTS);
            for (int i = 0; i < jsonArrayresults.length(); i++) {
                JSONObject jsonObject = jsonArrayresults.getJSONObject(i);
                movieInfos.add(new MovieInfo(jsonObject.getInt(id),
                        jsonObject.getInt(vote_count),
                        jsonObject.getString(video),
                        jsonObject.getString(vote_average),
                        jsonObject.getString(title),
                        jsonObject.getString(popularity),
                        jsonObject.getString(poster_path),
                        jsonObject.getString(original_language),
                        jsonObject.getString(original_title),
                        jsonObject.getString(backdrop_path),
                        jsonObject.getString(adult),
                        jsonObject.getString(overview),
                        jsonObject.getString(release_date)));
                Log.i("movieinfo", jsonObject.getString("id"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        movieAdapter = new MovieAdapter(MainActivity.this, movieInfos);
        recyclerView.setAdapter(movieAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(key, value);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.toprated) {
            value = "with_original_language=te";
            conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

            netInfo = conMgr.getActiveNetworkInfo();
            if (netInfo == null) {
                snackbar = Snackbar.make(linearLayout, "No internet connection!", Snackbar.LENGTH_LONG);
                snackbar.setActionTextColor(Color.RED);
                // Changing action button text color
                View sbView = snackbar.getView();
                TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);
                snackbar.show();
            } else {
                Bundle movieBundle = new Bundle();
                movieBundle.putString(SEARCH, getString(R.string.telugu));
                getSupportLoaderManager().restartLoader(TOP_RATED_MOVIES_ID, null, this);
                onSaveInstanceState(movieBundle);
            }
            return true;
        } else if (item.getItemId() == R.id.popular) {
            value = "popular";
            conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            assert conMgr != null;
            netInfo = conMgr.getActiveNetworkInfo();
            if (netInfo == null) {
                snackbar = Snackbar.make(linearLayout, "No internet connection!", Snackbar.LENGTH_LONG);
                snackbar.setActionTextColor(Color.RED);
                // Changing action button text color
                View sbView = snackbar.getView();
                TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);
                snackbar.show();
            } else {
                Bundle movieBundle = new Bundle();
                movieBundle.putString(SEARCH, value);
                onSaveInstanceState(movieBundle);
                getSupportLoaderManager().restartLoader(POPULAR_MOVIES_ID, null, this);

            }
            return true;
        } else if (item.getItemId() == R.id.favourite) {
            value = "favorites";
            Bundle movieBundle = new Bundle();
            movieBundle.putString(SEARCH, "favourite");
            getSupportLoaderManager().restartLoader(FAVOURITE_LOADER, movieBundle, this);
            onSaveInstanceState(movieBundle);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}