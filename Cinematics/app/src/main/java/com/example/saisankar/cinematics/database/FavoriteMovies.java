package com.example.saisankar.cinematics.database;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.saisankar.cinematics.R;
import com.example.saisankar.cinematics.WidgetActivity;
import com.example.saisankar.cinematics.adapter.MovieAdapter;
import com.example.saisankar.cinematics.content.MainContentActivity;
import com.example.saisankar.cinematics.model.MovieInfo;

import java.util.ArrayList;

import static com.google.firebase.analytics.FirebaseAnalytics.Event.SEARCH;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovies extends Fragment implements LoaderManager.LoaderCallbacks<String> {

    private static final int FAVOURITE_LOADER = 25;
    MovieAdapter movieAdapter;
    RecyclerView recyclerView;
    ArrayList<String> strings;
    MainContentActivity mainContentActivity = new MainContentActivity();
    FrameLayout frameLayout;
    private ArrayList<MovieInfo> moviecursor = new ArrayList<>();

    public FavoriteMovies() {
        // Required empty public constructor
        strings = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_movies, container, false);
        Bundle movieBundle = new Bundle();
        movieBundle.putString(SEARCH, "favourite");
        recyclerView = view.findViewById(R.id.recycler);
        frameLayout =  view.findViewById(R.id.frame);
        Snackbar.make(frameLayout, R.string.widget, Snackbar.LENGTH_SHORT).show();
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(new MovieAdapter(getActivity(), moviecursor));
        getLoaderManager().restartLoader(FAVOURITE_LOADER, movieBundle, this);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.widgetmenu, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.widgets:
                Toast.makeText(getActivity(), R.string.adwid, Toast.LENGTH_SHORT).show();
                String s = null;
                @SuppressLint("Recycle") Cursor cursor = getContext().getContentResolver().query(MovieContract.MovieContractEntry.CONTENT_URI, null, MovieContract.MovieContractEntry.COULUMN_USER_EMAIL + " LIKE ?", new String[]{mainContentActivity.user_email}, null);
                if (cursor.getCount() >= 1) {
                    if (moviecursor.size() > 0) {
                        for (int i = 0; i < moviecursor.size(); i++) {
                            String fav = moviecursor.get(i).getMtitle();

                            s += fav + "\n";
                        }
                    }
                }
                WidgetActivity.text = s;
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("StaticFieldLeak")
    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<String>(getActivity()) {
            @Nullable
            @Override
            public String loadInBackground() {

                Cursor cursor = getActivity().getContentResolver()
                        .query(MovieContract.MovieContractEntry.CONTENT_URI,
                                null,
                                MovieContract.MovieContractEntry.COULUMN_USER_EMAIL + "=?;",
                                new String[]{mainContentActivity.user_email},
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

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        if (!moviecursor.isEmpty()) {
            movieAdapter = new MovieAdapter(getActivity(), moviecursor);
            recyclerView.setAdapter(movieAdapter);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.movie_title);
            builder.setMessage(R.string.fav);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getActivity().finish();
                }
            });
            builder.show();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

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


}
