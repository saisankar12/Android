package com.example.saisankar.bookapi;


import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookSearchFragment extends Fragment {


    public BookSearchFragment() {
        // Required empty public constructor
    }

    EditText searchbookname;
    Button getBookData;
    TextView display;
    ProgressBar progressBar;

    public static String bookurl="https://www.googleapis.com/books/v1/volumes?";
    public static String query="q=";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_book_search, container, false);

        searchbookname=view.findViewById(R.id.enterbookname);
        getBookData=view.findViewById(R.id.getBookDetails);
        display=view.findViewById(R.id.bookresponse);
        progressBar=view.findViewById(R.id.progressbar);


        getBookData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             new BookTask().execute();
                           }
        });

        return view;
    }

    class BookTask extends AsyncTask<URL,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {

            try {
                URL url=new URL(bookurl+query+searchbookname.getText().toString());
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
            //String s1=searchbookname.getText().toString();
            display.setText(s);
            Log.i("bookurl",s);
        }
    }
}
