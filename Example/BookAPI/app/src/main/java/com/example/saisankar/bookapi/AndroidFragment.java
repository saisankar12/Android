package com.example.saisankar.bookapi;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class AndroidFragment extends Fragment {

    String bookurl="https://www.googleapis.com/books/v1/volumes?q=android&maxResults=40";
    String s;
    RecyclerView rv;
    ArrayList<BookModel> bookModel;
    //EditText ed;
    public AndroidFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_android, container, false);
        //ed=v.findViewById(R.id.ed);
        /*Button button=v.findViewById(R.id.getbook);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s=ed.getText().toString();
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
            }
        });*/
        bookModel=new ArrayList<>();
        rv=v.findViewById(R.id.recycler);
        new MyBookTask().execute();
        rv.setLayoutManager(new GridLayoutManager(getActivity(),2));
        return v;
    }

    class MyBookTask extends AsyncTask<String,Void,String>{

        HttpURLConnection httpURLConnection;
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url=new URL(bookurl);
                httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.connect();
                InputStream inputStream=httpURLConnection.getInputStream();
                Scanner scanner=new Scanner(inputStream);
                scanner.useDelimiter("\\A");
                boolean next=scanner.hasNext();
                if (next){
                    return scanner.next();
                }else {
                    return null;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                httpURLConnection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject=new JSONObject(s);
                JSONArray jsonArray=jsonObject.getJSONArray("items");
                //Log.i("booktitle", String.valueOf(jsonArray.length()));
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject bookObject=jsonArray.getJSONObject(i);
                    /*JSONObject volumeinfo=bookObject.getJSONObject("volumeInfo");

                    Log.i("booktitle",volumeinfo.getString("title"));
                    JSONObject thumbnail=bookObject.getJSONObject("thumbnail");
                    Log.i("booktitle",thumbnail.getString("thumbnail"));*/
                    String id=bookObject.getString("id");
                    JSONObject volumeinfo=bookObject.getJSONObject("volumeInfo");
                    String title=volumeinfo.getString("title");
                    JSONArray author=volumeinfo.getJSONArray("authors");
                    String autherinfo=author.getString(0);
                    Log.i("autherinfo",autherinfo);
                   // String desc=volumeinfo.getString("description");
                    JSONObject img = volumeinfo.getJSONObject("imageLinks");
                    String img_link = img.optString("thumbnail");

                    bookModel.add(new BookModel(id,title,autherinfo,img_link));


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            rv.setAdapter(new BookAdapter(getActivity(),bookModel));

        }
    }

}
