package com.example.acer.moviedb1;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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
import java.util.List;
import java.util.Scanner;


/**
 * A simple {@link Fragment} subclass.
 */
public class PopularFragment extends Fragment {
    RecyclerView recyclerView;
    FrameLayout frameLayout;
    static  ProgressBar progressBar;
    static List<MovieModel> arrayList=null;
    static MovieModel movieModels;
    String s="https://api.themoviedb.org/3/movie/popular?api_key=72da03842b77664587304fa35d1a3de8";
    public PopularFragment() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_popular, container, false);
        progressBar=v.findViewById(R.id.progress_circular);
        recyclerView=v.findViewById(R.id.recyler1);
        frameLayout=v.findViewById(R.id.net);
        check();

        return v;


    }

    /*@Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("My Movies App");
    }
*/
    protected boolean isOnline()
    {
        ConnectivityManager connectivityManager=(ConnectivityManager)
                getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null&&networkInfo.isConnectedOrConnecting())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public void check()
    {
        if(isOnline())
        {
            arrayList = new ArrayList<MovieModel>();
            new MovieTask().execute(s);
            arrayList.clear();
            recyclerView.setAdapter(new MovieAdapter(getActivity(), arrayList));
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        }
        else
        {
            progressBar.setVisibility(View.GONE);
            Snackbar.make(frameLayout,"Please check your network",Snackbar.LENGTH_LONG).show();
        }

    }

    @SuppressLint("StaticFieldLeak")
    public class MovieTask extends AsyncTask<String,Void,String> {
        //ArrayList<MovieModel> arrayList;
        //MovieModel movieModels;
        //ProgressBar progressBar;
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url=new URL(s);
                HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream inputStream=urlConnection.getInputStream();
                Scanner scanner=new Scanner(inputStream);
                scanner.useDelimiter("\\A");
                if(scanner.hasNext())
                {
                    return scanner.next();
                }
                else
                {
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
            if (s != null)
            {
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    Log.i("movie",s);
                    JSONArray results=jsonObject.getJSONArray("results");
                    for (int i=0;i<results.length();i++)
                    {
                        JSONObject object=results.getJSONObject(i);
                        String id=object.getString("id");
                        String title=object.getString("title");
                        String img=object.getString("poster_path");
                        String average=object.getString("vote_average");
                        String overview=object.getString("overview");
                        String releasedate=object.getString("release_date");
                        movieModels=new MovieModel(img,title,id,average,overview,releasedate);
                        arrayList.add(movieModels);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
