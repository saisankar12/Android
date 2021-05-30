package com.example.asyntask;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<String> {

    String imageurl="https://pixabay.com/api/?key=8907574-f2ba82f0d1e5cef1d06a114e6&q=rose";
    ProgressBar progressBar;

    ArrayList<ImageModel> models;

    RecyclerView rv;

    int LoaderID=34;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar=findViewById(R.id.progress);
        rv=findViewById(R.id.recycler);

        models=new ArrayList<>();

        //new ImageTask().execute();

        getSupportLoaderManager().initLoader(LoaderID,null,this);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new ImagesAdapter(this,models));
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new AsyncTaskLoader<String>(this) {
            @Nullable
            @Override
            public String loadInBackground() {
                try {
                    URL url=new URL(imageurl);
                    HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
                    InputStream inputStream = urlConnection.getInputStream();
                    Scanner scanner=new Scanner(inputStream);
                    scanner.useDelimiter("\\A");
                    if (scanner.hasNext()){
                        return scanner.next();
                    }else {
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
                forceLoad();
                progressBar.setVisibility(View.VISIBLE);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s) {

        progressBar.setVisibility(View.GONE);
        //Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
        try {
            JSONObject jsonObject=new JSONObject(s);
            JSONArray imagearray=jsonObject.getJSONArray("hits");
            for (int i=0;i<imagearray.length();i++){
                JSONObject imageobject=imagearray.getJSONObject(i);
                String image=imageobject.getString("largeImageURL");
                String likes=imageobject.getString("likes");
                models.add(new ImageModel(image,likes));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }


    /*class ImageTask extends AsyncTask<String,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url=new URL(imageurl);
                HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                Scanner scanner=new Scanner(inputStream);
                scanner.useDelimiter("\\A");
                if (scanner.hasNext()){
                    return scanner.next();
                }else {
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
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            //Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();

            try {
                JSONObject jsonObject=new JSONObject(s);
                JSONArray imagearray=jsonObject.getJSONArray("hits");
                for (int i=0;i<imagearray.length();i++){
                    JSONObject imageobject=imagearray.getJSONObject(i);
                    String image=imageobject.getString("largeImageURL");
                    String likes=imageobject.getString("likes");
                    models.add(new ImageModel(image,likes));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

*/

}
