package com.example.hp.capstoneproject.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hp.capstoneproject.R;
import com.example.hp.capstoneproject.adapters.ScheduleAdapter;
import com.example.hp.capstoneproject.adapters.UpcomingAdapter;
import com.example.hp.capstoneproject.model.ScheduleMatches;
import com.example.hp.capstoneproject.model.UpcomingMatches;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingMatchesFragment extends Fragment
{
    @InjectView(R.id.upcomingrecycler)
    RecyclerView upcomingRecycler;
    private RequestQueue requestQueue;
    public static String link="http://cricapi.com/api/matches?apikey=CUHZ98QHE6W3VHzEUMhYjAOxzHX2";
    ArrayList<UpcomingMatches> arrayList;
    UpcomingAdapter adapter;
    ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_upcoming_matches, container, false);
        ButterKnife.inject(this, view);
        requestQueue = Volley.newRequestQueue(getActivity());
        parseJsonData();
        showProgress();
        return view;
    }
    public void showProgress() {
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...plz wait...");
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
                    String team1,team2,type,time,matchstarted ,date = null;
                    arrayList=new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray articles = jsonObject.getJSONArray("matches");
                    for (int i = 0; i < articles.length(); i++) {
                        JSONObject jp = articles.getJSONObject(i);
                        team1 = jp.optString("team-1");
                        team2 = jp.optString("team-2");
                        type=jp.optString("type");
                        date=jp.optString("date");
                        time=jp.optString("dateTimeGMT");
                        matchstarted=jp.optString("matchStarted");

                        //Toast.makeText(getActivity(), ""+title, Toast.LENGTH_SHORT).show();
                        UpcomingMatches upcomingMatches = new UpcomingMatches(team1,team2,type,date,time,matchstarted);
                        //Toast.makeText(getActivity(), "" + article.getDescription(), Toast.LENGTH_SHORT).show();
                        arrayList.add(upcomingMatches);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                setRecyclerView();
                dialog.dismiss();
                //Toast.makeText(getActivity(), ""+arrayList.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(request);
    }

    public void setRecyclerView() {
        adapter = new UpcomingAdapter(getActivity(), arrayList);
        upcomingRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        upcomingRecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}
