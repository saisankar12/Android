package com.example.saisankar.backingapp;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saisankar.backingapp.model.Receipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    final String receipeurl="https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
   RecyclerView rv;
    ArrayList<Receipe> receipes;

    ProgressBar progressBar;

    final int Receipe_ID= 23;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv=findViewById(R.id.recycler);
        progressBar=findViewById(R.id.progress);
        receipes=new ArrayList<>();
        receipes.clear();
        getSupportLoaderManager().initLoader(Receipe_ID,null,this);

    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new AsyncTaskLoader<String>(this) {
            @Nullable
            @Override
            public String loadInBackground() {

                try {
                    URL url=new URL(receipeurl);
                    HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
                    InputStream inputStream=urlConnection.getInputStream();
                    Scanner scanner=new Scanner(inputStream);
                    scanner.useDelimiter("\\A");
                    if (scanner.hasNext()){
                        return scanner.next();
                    }
                    else {
                        return null;
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                progressBar.setVisibility(View.VISIBLE);
                forceLoad();
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s) {
progressBar.setVisibility(View.GONE);
        try {
            JSONArray jsonArray = new JSONArray(s);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String receipename = object.optString("name");
                String receipeservings = object.optString("servings");

                String ingreArray = object.getJSONArray("ingredients").toString();
                String stepsArray = object.getJSONArray("steps").toString();
                receipes.add(new Receipe(receipename, receipeservings, ingreArray, stepsArray));
            }

            rv.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
            rv.setAdapter(new ReceipeAdapter(MainActivity.this,receipes));

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }



    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }


    private void getReceipeData(String s) throws JSONException {

           }
}
