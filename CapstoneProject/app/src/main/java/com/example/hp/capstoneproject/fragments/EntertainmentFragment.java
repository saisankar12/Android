package com.example.hp.capstoneproject.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hp.capstoneproject.NewsAdapter;
import com.example.hp.capstoneproject.R;
import com.example.hp.capstoneproject.adapters.EntertainmentAdapter;
import com.example.hp.capstoneproject.model.Article;
import com.example.hp.capstoneproject.model.Entertainment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class EntertainmentFragment extends Fragment
{
    @InjectView(R.id.entertainment_recycler)
    RecyclerView enter_recycler;
    private RequestQueue requestQueue;
    public static String link="https://newsapi.org/v2/everything?sources=mashable&apiKey=5efbf669135442c9a316b376313f61fc";
    ArrayList<Entertainment> arrayList;
    EntertainmentAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_entertainment, container, false);
        ButterKnife.inject(this, view);
        requestQueue = Volley.newRequestQueue(getActivity());
        parseJsonData();
        return view;
    }
    public void parseJsonData() {

        StringRequest request = new StringRequest(Request.Method.GET,
                link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getActivity(), ""+response, Toast.LENGTH_SHORT).show();

                try {
                    String author = null, title, desc, url, urlImg, publish = null;
                    arrayList=new ArrayList<>();
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

                        //Toast.makeText(getActivity(), ""+title, Toast.LENGTH_SHORT).show();
                        Entertainment entertainment = new Entertainment(author, title, desc, url, urlImg, publish);
                        //Toast.makeText(getActivity(), "" + article.getDescription(), Toast.LENGTH_SHORT).show();
                        arrayList.add(entertainment);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                setRecyclerView();
                Toast.makeText(getActivity(), ""+arrayList.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(request);
    }

    public void setRecyclerView() {
        adapter = new EntertainmentAdapter(getActivity(), arrayList);
        enter_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        enter_recycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}
