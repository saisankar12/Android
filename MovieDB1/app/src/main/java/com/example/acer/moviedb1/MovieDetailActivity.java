package com.example.acer.moviedb1;

import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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

public class MovieDetailActivity extends AppCompatActivity {
    ImageView imageView;
    TextView titl,id2,ava,over,rele;
    RecyclerView rrecycle,trecycle;
    ReviewModel reviewModel;
    TrailerModel trailerModel;
    MovieViewHolder movieViewHolder;
    String[] s;
    Button btn;
    static boolean status=false,st;
    ArrayList<ReviewModel> reviewModels;
    ArrayList<TrailerModel> trailerModels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        imageView=findViewById(R.id.image);
        titl=findViewById(R.id.title);
        id2=findViewById(R.id.id);
        ava=findViewById(R.id.vote);
        btn=findViewById(R.id.btn);
        over=findViewById(R.id.overview);
        rele=findViewById(R.id.release);
        rrecycle=findViewById(R.id.reviewrecycler);
        trecycle=findViewById(R.id.trailerrecycle);
        s=getIntent().getStringArrayExtra("hai");
        Picasso.with(this).load("http://image.tmdb.org/t/p/w185/"+s[0]).into(imageView);
        titl.setText(s[1]);
        id2.setText(s[2]);
        ava.setText(s[3]);
        over.setText(s[4]);
        rele.setText(s[5]);
        movieViewHolder=ViewModelProviders.of(this).get(MovieViewHolder.class);
        inFav(s[2]);
        String t="https://api.themoviedb.org/3/movie/"+s[2]+"/videos?api_key=72da03842b77664587304fa35d1a3de8";
        String r="https://api.themoviedb.org/3/movie/"+s[2]+"/reviews?api_key=72da03842b77664587304fa35d1a3de8";
        reviewModels=new ArrayList<>();
        new ReviewTask().execute(r);
        new TralierTask().execute(t);
        trailerModels=new ArrayList<>();
        rrecycle.setLayoutManager(new LinearLayoutManager(this));
        trecycle.setLayoutManager(new LinearLayoutManager(this));


    }

    public void fav(View view) {
        st=inFav(s[2]);
        MovieModel movieModel=new MovieModel();
        movieModel.setId1(s[2]);
        if(!st)
        {
            movieModel.setAve(s[3]);
            movieModel.setImg(s[0]);
            movieModel.setOver(s[4]);
            movieModel.setTit(s[1]);
            movieModel.setReale(s[5]);
            movieViewHolder.insertData(movieModel);
            setStatus(true);
            Toast.makeText(this, "Inserted Successfully", Toast.LENGTH_SHORT).show();
            btn.setText("Added to Fav");
        }
        else
        {
            movieViewHolder.deleteModel(movieModel);
            setStatus(false);
            Toast.makeText(this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
            btn.setText("Add to Fav");
        }

    }

    class ReviewTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                Log.i("review", String.valueOf(url));
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                Scanner scanner = new Scanner(inputStream);
                scanner.useDelimiter("\\A");
                if (scanner.hasNext()) {
                    return scanner.next();
                } else {
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
            if (s != null) {
                try {
                    JSONObject object = new JSONObject(s);
                    JSONArray array = object.getJSONArray("results");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object1 = array.getJSONObject(i);
                        String author = object1.getString("author");
                        String con = object1.getString("content");
                        reviewModel = new ReviewModel(author, con);
                        reviewModels.add(reviewModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                rrecycle.setAdapter(new ReviewAdapter(MovieDetailActivity.this,reviewModels));
            }
        }
    }
    class TralierTask extends AsyncTask<String ,Void,String>
    {

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url=new URL(strings[0]);
                Log.i("movieurl", String.valueOf(url));
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
            if (s!=null)
            {
                try {
                    JSONObject object=new JSONObject(s);
                    JSONArray array=object.getJSONArray("results");
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject object1=array.getJSONObject(i);
                        String key=object1.getString("key");
                        trailerModel=new TrailerModel(key);
                        trailerModels.add(trailerModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                trecycle.setAdapter(new TrailerAdapter(MovieDetailActivity.this,trailerModels));
            }
        }
    }
    public boolean inFav(String id)
    {
        int moid=movieViewHolder.favs(Integer.parseInt(id));
        if(moid>0)
        {
            setStatus(true);
            btn.setText("Added to Fav");

        }
        else
        {
            setStatus(false);
            btn.setText("Add to Fav");
        }
        return status;
    }
    public static void setStatus(boolean status)
    {
        MovieDetailActivity.status=status;
    }

}
