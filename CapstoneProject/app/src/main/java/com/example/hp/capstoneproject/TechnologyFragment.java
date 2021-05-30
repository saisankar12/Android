package com.example.hp.capstoneproject;


import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hp.capstoneproject.model.Article;
import com.example.hp.capstoneproject.model.Example;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TechnologyFragment extends Fragment {
    @InjectView(R.id.recycler)
    RecyclerView recyclerView;
    private RequestQueue requestQueue;
    public static String link = "https://newsapi.org/v2/everything?sources=espn-cric-info&apiKey=5efbf669135442c9a316b376313f61fc";
    ArrayList<Article> arrayList;
    NewsAdapter adapter;
    ProgressDialog dialog;
    LinearLayoutManager linearLayoutManager;
    Parcelable state;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_technology, container, false);
        ButterKnife.inject(this, view);
        requestQueue = Volley.newRequestQueue(getActivity());
        showProgress();
        parseJsonData();
        return view;
    }

    public void showProgress() {
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading ...plz wait...");
        dialog.setCancelable(false);
        dialog.show();
    }

    public void parseJsonData() {

        StringRequest request = new StringRequest(Request.Method.GET,
                link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getActivity(), ""+response, Toast.LENGTH_SHORT).show();

                try {
                    String author = null, title, desc, url, urlImg, publish = null;
                    arrayList = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray articles = jsonObject.getJSONArray("articles");
                    for (int i = 0; i < articles.length(); i++) {
                        JSONObject jp = articles.getJSONObject(i);
                        author = jp.optString("author");
                        title = jp.optString("title");
                        desc = jp.optString("description");
                        url = jp.optString("url");
                        urlImg = jp.optString("urlToImage");
                        publish = jp.optString("publishedAt");

                        Article article = new Article(author, title, desc, url, urlImg, publish);
                        arrayList.add(article);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                setRecyclerView();
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(request);
    }

    public void setRecyclerView() {
        adapter = new NewsAdapter(getActivity(), arrayList);

        linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.onRestoreInstanceState(state);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onPause() {
        super.onPause();
        state = linearLayoutManager.onSaveInstanceState();
    }
}
