package com.example.saisankar.bookapi;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public class JavaFragment extends Fragment {
    String bookurl="https://www.googleapis.com/books/v1/volumes?q=c&maxResults=40";
    RecyclerView rv;
    ArrayList<BookModel> bookModel;
    public JavaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_java, container, false);
        rv=v.findViewById(R.id.recycler);
        bookModel=new ArrayList<>();
        new MyBookTask().execute();
        rv.setLayoutManager(new GridLayoutManager(getContext(),2));
        return v;
    }

    class MyBookTask extends AsyncTask<String,Void,String>{
        HttpURLConnection httpURLConnection;
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url=new URL(bookurl);
                Log.i("javabook",url.toString());
                httpURLConnection= (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                InputStream inputStream=httpURLConnection.getInputStream();
                Log.i("javabook",inputStream.toString());
                Scanner scanner=new Scanner(inputStream);
                scanner.useDelimiter("\\A");
                if (scanner.hasNext()){
                    String s=scanner.next();
                    return s;
                }
                else {
                    return null;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
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
                for (int a=0;a<jsonArray.length();a++){
                    JSONObject bookObject=jsonArray.getJSONObject(a);
                    //String is=bookObject.getString("id");
                    JSONObject voluminfo=bookObject.getJSONObject("volumeInfo");
                   // String volume=voluminfo.getString("title");
                    JSONObject imagelinks=voluminfo.getJSONObject("imageLinks");
                    //String imge=imagelinks.getString("thumbnail");
                    //bookModel.add(new BookModel(is,volume,imge));
                    bookModel.add(new BookModel(bookObject.getString("id"),
                                     voluminfo.getString("title"),
                            voluminfo.getString("description"),
                            imagelinks.getString("thumbnail")));

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            rv.setAdapter(new BookAdapter(getActivity(),bookModel));
        }
    }
}
