package com.example.saisankar.recyclerview;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv;
    EditText editbook;
    Button getbook;
    ProgressBar progressBar;
    static String bookurl="https://www.googleapis.com/books/v1/volumes?q=";
    ArrayList<BookModel> bookModels;
    /*String[] names={"Android-1","Android-2","Android-3","Android-4",
            "Android-5","Android-6","Android-7","Android-8"};*/
    /*int[] images={R.mipmap.ic_launcher,R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher,R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher,R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher,R.mipmap.ic_launcher_round};*/
TextView tl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv=findViewById(R.id.recycler);
        progressBar=findViewById(R.id.progressbar);
        editbook=findViewById(R.id.bookname);
        getbook=findViewById(R.id.getbookData);
        tl=findViewById(R.id.life);
        tl.append("onCreate()"+"\n");
        bookModels=new ArrayList<>();
        getbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BookTask().execute();
            }
        });
       /* if (savedInstanceState!=null){
            tl.append("savedinstance");
        }*/

        rv.setLayoutManager(new GridLayoutManager(this,2));
        rv.setAdapter(new MyAdapter(this,bookModels));

    }

   /* @Override
    protected void onStart() {
        super.onStart();
        tl.append("onStart()"+"\n");
    }

    @Override
    protected void onPause() {
        super.onPause();
        tl.append("onPause()"+"\n");
    }

    @Override
    protected void onResume() {
        super.onResume();
        tl.append("onResume()"+"\n");
    }

    @Override
    protected void onStop() {
        super.onStop();
        tl.append("onStop()"+"\n");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        tl.append("onRestart()"+"\n");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tl.append("onDestroy()"+"\n");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("key",tl.getText().toString());
    }
*/
    class BookTask extends AsyncTask<String,Void,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url=new URL(bookurl+editbook.getText().toString());
                Log.i("bookurl",url.toString());
                HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
                InputStream inputStream=urlConnection.getInputStream();
                Scanner scanner=new Scanner(inputStream);
                scanner.useDelimiter("\\A");
                if (scanner.hasNext()){
                    return scanner.next();
                }else {
                    return null;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);

            try {
                JSONObject jsonObject=new JSONObject(s);
                JSONArray jsonArray=jsonObject.getJSONArray("items");
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject bookObject=jsonArray.getJSONObject(i);
                    String id=bookObject.getString("id");
                    JSONObject volumeArray=bookObject.getJSONObject("volumeInfo");
                    String title=volumeArray.getString("title");
                    JSONArray autherinfo=volumeArray.getJSONArray("authors");
                    String auther=autherinfo.getString(0);
                    JSONObject imageObject=volumeArray.getJSONObject("imageLinks");
                    String image=imageObject.getString("thumbnail");
                    bookModels.add(new BookModel(id,title,auther,image));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
