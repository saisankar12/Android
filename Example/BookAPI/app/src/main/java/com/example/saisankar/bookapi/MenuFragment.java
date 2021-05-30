package com.example.saisankar.bookapi;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {


    public MenuFragment() {
        // Required empty public constructor
    }
    String bookurl="https://www.googleapis.com/books/v1/volumes?q=";

    RecyclerView rv;

    ProgressBar progressBar;
    ArrayList<BookMenuDesign> bookMenuDesigns;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_menu, container, false);
        rv=v.findViewById(R.id.recyclerview);
        progressBar=v.findViewById(R.id.progress);

        setHasOptionsMenu(true);
        //new BookTask().execute();
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));


        return v;
    }

    class BookTask extends AsyncTask<String,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            String urls=strings[0];
            try {
                URL url=new URL(urls);
                Log.i("bookurl",url.toString());
                HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream inputStream=urlConnection.getInputStream();
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
            //tv.setText(s);
            progressBar.setVisibility(View.GONE);
            try {
                JSONObject jsonObject=new JSONObject(s);
                Log.i("bookurl",jsonObject.toString());
                JSONArray jsonArray=jsonObject.getJSONArray("items");
                Log.i("bookurl",jsonArray.toString());
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject volumeInfo=jsonArray.getJSONObject(i);
                    JSONObject object=volumeInfo.getJSONObject("volumeInfo");
                    String title=object.getString("title");
                    Log.i("booktitle",title);
                    JSONArray autherArray=object.getJSONArray("authors");
                    String auther=autherArray.getString(0);
                    Log.i("bookauther",auther);
                    String description=object.getString("description");
                    Log.i("bookdescription",description);
                    JSONObject imageArry=object.getJSONObject("imageLinks");
                    String bookthubnil=imageArry.getString("thumbnail");
                    Log.i("bookthumbnil",bookthubnil);
                    JSONObject saleInfo=volumeInfo.getJSONObject("saleInfo");
                    String country=saleInfo.getString("country");
                    Log.i("bookcountry",country);

                    bookMenuDesigns.add(new BookMenuDesign(title,auther,description,bookthubnil,country));

                }
                Log.i("bookurl", String.valueOf(bookMenuDesigns.size()));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            rv.setAdapter(new MenuAdapter(getActivity(),bookMenuDesigns));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.android:
                bookMenuDesigns=new ArrayList<>();

                new BookTask().execute(bookurl+"android");

                break;
            case R.id.java:
                bookMenuDesigns=new ArrayList<>();

                new BookTask().execute(bookurl+"java");
                ///rv.setAdapter(new MenuAdapter(getActivity(),bookMenuDesigns));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
