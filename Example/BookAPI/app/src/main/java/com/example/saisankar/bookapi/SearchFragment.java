package com.example.saisankar.bookapi;



import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements LoaderManager.LoaderCallbacks<String> {


    public SearchFragment() {
        // Required empty public constructor
    }

    EditText editbook;
    TextView bookresponse;
    Button getbook;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    final static int Loader_id=123;
    /*https://www.googleapis.com/books/v1/volumes?q=android&maxResults=40*/
    static String bookurl="https://www.googleapis.com/books/v1/volumes?";
    static String query="q=";
    ArrayList<BookModel> bookModels;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_search, container, false);
        editbook=v.findViewById(R.id.bookname);
       // bookresponse=v.findViewById(R.id.response);
        getbook=v.findViewById(R.id.getDetails);
        progressBar=v.findViewById(R.id.progressbar);
        recyclerView=v.findViewById(R.id.recycler);
        bookModels=new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        getbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookModels.clear();
                   //new BookSearchAsyn().execute();
                LoaderManager loaderManager=getLoaderManager();
                //loaderManager.restartLoader(Loader_id,null,getBaseContext());
            }
        });
       /* if (savedInstanceState!=null){
            if (isVisible()){
                editbook.setText(savedInstanceState.getString("value"));
                bookresponse.setText(savedInstanceState.getString("value"));
            }
        }*/

        return v;
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<String>(getActivity()) {
            @Nullable
            @Override
            public String loadInBackground() {
                try {
                    URL url=new URL(bookurl+query+editbook.getText().toString());
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
            protected void onStartLoading() {
                super.onStartLoading();
                forceLoad();
                progressBar.setVisibility(View.GONE);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {
            JSONObject jsonObject=new JSONObject(data);
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
        recyclerView.setAdapter(new BookAdapter(getActivity(),bookModels));

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }


    /*class BookSearchAsyn extends AsyncTask<URL,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {

            try {
                URL url=new URL(bookurl+query+editbook.getText().toString());
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
            String s1=editbook.getText().toString();
            //bookresponse.setText(s+s1);
           // Log.i("bookurl",bookresponse.toString());

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
            recyclerView.setAdapter(new BookAdapter(getActivity(),bookModels));

        }
    }*/

    /*@Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("value",editbook.getText().toString());
        outState.putString("value",bookresponse.getText().toString());
    }*/
}
