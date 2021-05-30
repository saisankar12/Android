package com.example.saisankar.cinematics.content;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saisankar.cinematics.BuildConfig;
import com.example.saisankar.cinematics.InternetUtilities;
import com.example.saisankar.cinematics.R;
import com.example.saisankar.cinematics.adapter.ReviewsAdapter;
import com.example.saisankar.cinematics.adapter.TrailerAdapter;
import com.example.saisankar.cinematics.database.MovieContract;
import com.example.saisankar.cinematics.database.MovieSQLiteDb;
import com.example.saisankar.cinematics.model.MovieInfo;
import com.example.saisankar.cinematics.model.ReviewsInfo;
import com.example.saisankar.cinematics.model.TrailersInfo;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetails extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private static final String SEARCH = "";
    private static final int TRAILER_LOADER = 23;
    private static final int REVIEWS_LOADER = 24;
    private static final int FAV_ADD_LOADER = 25;
    private static final int FAV_REMOVE_LOADER = 26;
    private static final String API_KEY = BuildConfig.API_KEY;
    public static String useremail;
    private String[] moviedata;
    private ContentValues contentValues;
    private int movieid;
    private MovieInfo movieInfo;
    private MovieSQLiteDb sqLite = new MovieSQLiteDb(this);
    @BindView(R.id.movie_poster)
    ImageView mposter;
    @BindView(R.id.movie_backdrop)
    ImageView mBackdrop;
    @BindView(R.id.movie_title)
    TextView mTitle;
    @BindView(R.id.movie_release_date)
    TextView mRelease;
    @BindView(R.id.movie_vote_average)
    TextView mVote;
    @BindView(R.id.movie_overview)
    TextView mOverview;
    @BindView(R.id.favourite)
    ImageView favourite;
    @BindView(R.id.recycler)
    RecyclerView trv;
    @BindView(R.id.reviewrecycler)
    RecyclerView rrv;
    @BindView(R.id.linear)
    LinearLayout linearLayout;
    private boolean favourites = false;
    MovieDetails movieDetails;
    private MainContentActivity mainContentActivity = new MainContentActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);

        moviedata = getIntent().getStringArrayExtra("movieDetails");
        movieid = getIntent().getIntExtra("id", 1);
        movieInfo = new MovieInfo();


        URL s = InternetUtilities.buildImageUrl(moviedata[0]);
        Picasso.with(this).load(s.toString()).placeholder(R.mipmap.ic_launcher).into(mposter);
        mTitle.setText(moviedata[1]);
        URL s1 = InternetUtilities.buildImageUrl(moviedata[2]);
        Picasso.with(this).load(s1.toString()).placeholder(R.mipmap.ic_launcher).into(mBackdrop);
        mRelease.setText(moviedata[3]);
        mVote.setText(moviedata[4]);
        mOverview.setText(moviedata[5]);
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if (netInfo == null) {
            Snackbar snackbar = Snackbar.make(linearLayout, R.string.no_internet, Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.WHITE);
            View sbView = snackbar.getView();
            TextView textView =  sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        } else {
            Bundle movieBundle = new Bundle();
            movieBundle.putString(SEARCH, "videos");
            getSupportLoaderManager().initLoader(TRAILER_LOADER, movieBundle, this);
            Bundle reviewBundle = new Bundle();
            movieBundle.putString(SEARCH, "reviews");
            getSupportLoaderManager().initLoader(REVIEWS_LOADER, reviewBundle, this);
        }


        if (savedInstanceState != null) {

        }
        favourites = sqLite.isFavorite(movieid);
        if (favourites) {
            favourite.setImageResource(R.drawable.fav_add);
        } else {
            favourite.setImageResource(R.drawable.fav_del);
        }
        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!favourites) {
                    getSupportLoaderManager().initLoader(FAV_ADD_LOADER, null, MovieDetails.this);
                    favourite.setImageResource(R.drawable.fav_add);
                    favourites = true;
                } else {
                    getSupportLoaderManager().initLoader(FAV_REMOVE_LOADER, null, MovieDetails.this);
                    favourite.setImageResource(R.drawable.fav_del);
                    favourites = false;
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("is_favourites", favourites);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        favourites = savedInstanceState.getBoolean("is_favourites");
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader<String> onCreateLoader(int id, final Bundle bundle) {
        switch (id) {
            case TRAILER_LOADER:
                return new AsyncTaskLoader<String>(this) {
                    @Override
                    public String loadInBackground() {
                        String query = bundle.getString(SEARCH);

                        String s = "";
                        try {
                            String trailers = "http://api.themoviedb.org/3/movie/" + "" + movieid + "" + "/videos?api_key=" + API_KEY;
                            URL url = new URL(trailers);

                            s = InternetUtilities.getResponse(url);

                        } catch (IOException e) {
                            e.printStackTrace();

                            return null;
                        }
                        return s;
                    }

                    @Override
                    protected void onStartLoading() {
                        super.onStartLoading();
                        if (bundle == null)
                            return;
                        forceLoad();
                    }
                };
            case REVIEWS_LOADER:
                return new AsyncTaskLoader<String>(this) {
                    @Override
                    public String loadInBackground() {
                        String query = bundle.getString(SEARCH);

                        String s = "";
                        try {
                            String reviews = "http://api.themoviedb.org/3/movie/" + "" + movieid + "" + "/reviews?api_key=" + API_KEY;

                            URL url = new URL(reviews);

                            s = InternetUtilities.getResponse(url);

                        } catch (IOException e) {
                            e.printStackTrace();

                            return null;
                        }
                        return s;
                    }

                    @Override
                    protected void onStartLoading() {
                        super.onStartLoading();
                        if (bundle == null)
                            return;
                        forceLoad();
                    }
                };
            case FAV_ADD_LOADER:
                return new AsyncTaskLoader<String>(this) {
                    @Override
                    public String loadInBackground() {


                        contentValues = new ContentValues();
                        contentValues.put(MovieContract.MovieContractEntry.COULUMN_USER_EMAIL, mainContentActivity.user_email);
                        contentValues.put(MovieContract.MovieContractEntry.COLUMN_MOVIE_ID, movieid);
                        contentValues.put(MovieContract.MovieContractEntry.COLUMN_IMAGEPOSTER, moviedata[0].substring(1));
                        contentValues.put(MovieContract.MovieContractEntry.COLUMN_ORIGINALTITLE, moviedata[1]);
                        contentValues.put(MovieContract.MovieContractEntry.COLUMN_IMAGEBACKDROP, moviedata[2].substring(1));
                        contentValues.put(MovieContract.MovieContractEntry.COLUMN_RELEASEDATE, moviedata[3]);
                        contentValues.put(MovieContract.MovieContractEntry.COLUMN_AVERAGE, moviedata[4]);
                        contentValues.put(MovieContract.MovieContractEntry.COLUMN_OVERVIEW, moviedata[5]);
                        contentValues.put(MovieContract.MovieContractEntry.COLUMN_TITLE, moviedata[6]);


                        //}
                        return "Added to Favourites";
                    }

                    @Override
                    protected void onStartLoading() {
                        super.onStartLoading();
                        forceLoad();
                    }
                };
            case FAV_REMOVE_LOADER:
                return new AsyncTaskLoader<String>(this) {
                    @Override
                    public String loadInBackground() {

                        getContentResolver().delete(MovieContract.MovieContractEntry.CONTENT_URI, MovieContract.MovieContractEntry.COLUMN_MOVIE_ID + " =? ", new String[]{String.valueOf(movieid)});

                        return null;
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
            case TRAILER_LOADER:
                try {
                    parseTrailer(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case REVIEWS_LOADER:
                try {
                    parseReviewer(data);
                } catch (JSONException e) {
                    e.printStackTrace();

                }
                break;
            case FAV_ADD_LOADER:
                getContentResolver().insert(MovieContract.MovieContractEntry.CONTENT_URI, contentValues);
                Toast.makeText(this, data, Toast.LENGTH_LONG).show();
                Snackbar snackbar = Snackbar.make(linearLayout, R.string.addfav, Snackbar.LENGTH_LONG);
                View sbView = snackbar.getView();
                TextView textView =  sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.GREEN);
                snackbar.show();
                break;
            case FAV_REMOVE_LOADER:
                //getContentResolver().delete(MovieContract.MovieContractEntry.CONTENT_URI, MovieContract.MovieContractEntry.COLUMN_MOVIE_ID + " =? ", new String[]{String.valueOf(movieid)});
                Toast.makeText(this, data, Toast.LENGTH_LONG).show();
                Snackbar snackbar1 = Snackbar.make(linearLayout, "Removed from Favorites", Snackbar.LENGTH_LONG);
                View sbView1 = snackbar1.getView();
                TextView textView1 = sbView1.findViewById(android.support.design.R.id.snackbar_text);
                textView1.setTextColor(Color.RED);
                snackbar1.show();
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    private void parseReviewer(String s) throws JSONException {
        JSONObject jsonObject = new JSONObject(s);
        JSONArray results = jsonObject.getJSONArray("results");

        ArrayList<ReviewsInfo> reviewslist = new ArrayList<>();

        for (int i = 0; i < results.length(); i++) {
            JSONObject jsonObject1 = results.getJSONObject(i);

            reviewslist.add(new ReviewsInfo(jsonObject1));

            rrv.setLayoutManager(new LinearLayoutManager(this));
            rrv.setAdapter(new ReviewsAdapter(this, reviewslist));
        }
    }

    private void parseTrailer(String s) throws JSONException {
        JSONObject jsonObject1 = new JSONObject(s);
        JSONArray results = jsonObject1.getJSONArray("results");

        final ArrayList<TrailersInfo> trailersInfos = new ArrayList<>();

        int c = 0;
        for (int i = 0; i < results.length(); i++) {

            JSONObject jsonObject = results.getJSONObject(i);


            trailersInfos.add(new TrailersInfo(jsonObject));

            c++;

        }
        String[] sample = new String[c];
        for (int i = 0; i < c; i++) {
            if (trailersInfos.get(i).getType().contentEquals("Trailer"))
                sample[i] = trailersInfos.get(i).getName();
        }

        trv.setLayoutManager(new LinearLayoutManager(this));
        trv.setAdapter(new TrailerAdapter(this, trailersInfos));

    }


}

