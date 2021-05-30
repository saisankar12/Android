package com.example.bookinfo;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;

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

public class Books extends AppCompatActivity {

    RecyclerView recyclerViewImages;
    GoogleProgressBar progressBar;
    String strUrl,strSearch,fullUrl;
    ArrayList<Model> arrayListModel;
    BookViewModel bookViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        progressBar=findViewById(R.id.progress);
        recyclerViewImages=findViewById(R.id.recycler);
        strUrl=getResources().getString(R.string.book_url);
        strSearch=getIntent().getStringExtra(getResources().getString(R.string.q));
        fullUrl=strUrl+strSearch;
        arrayListModel=new ArrayList<>();
        arrayListModel.clear();
        new BooksTask().execute(fullUrl);

        bookViewModel= ViewModelProviders.of(this).get(BookViewModel.class);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.fav:
                arrayListModel.clear();
                getFavData();
                break;
            case R.id.signOut:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public class BooksTask extends AsyncTask<String,Void,String>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url=new URL(strings[0]);
                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                InputStream inputStream=httpURLConnection.getInputStream();
                Scanner scanner=new Scanner(inputStream);
                scanner.useDelimiter("\\A");
                if(scanner.hasNext()){
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
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            if(s!=null){
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    JSONArray jsonArray=jsonObject.getJSONArray(getResources().getString(R.string.item));
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String bId = jsonObject1.getString(getResources().getString(R.string.id));
                        JSONObject volume = jsonObject1.getJSONObject(getResources().getString(R.string.v));
                        String bTitle = volume.getString(getResources().getString(R.string.t));
                        JSONArray author = volume.getJSONArray(getResources().getString(R.string.a));
                        String bAuthor = author.getString(0);
                        String date = volume.getString(getResources().getString(R.string.p));
                        JSONObject image = volume.getJSONObject(getResources().getString(R.string.im));
                        String thumbNail = image.getString(getResources().getString(R.string.th));
                        String preview = volume.getString(getResources().getString(R.string.pre));
                        arrayListModel.add(new Model(bId, bTitle, bAuthor, date, thumbNail, preview));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            recyclerViewImages.setLayoutManager(new GridLayoutManager(Books.this,2));
            recyclerViewImages.setAdapter(new ImagesAdapter(Books.this,arrayListModel));
        }
    }

    public void getFavData(){
        bookViewModel.getFavData().observe(Books.this, new Observer<List<Model>>() {
            @Override
            public void onChanged(@Nullable List<Model> models) {
                arrayListModel=(ArrayList<Model>) models;
                 ImagesAdapter imagesAdapter=new ImagesAdapter(Books.this,arrayListModel);
                recyclerViewImages.setAdapter(imagesAdapter);
                recyclerViewImages.setLayoutManager(new GridLayoutManager(Books.this,2));
            }
        });
    }
}
