package com.example.hp.capstoneproject.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hp.capstoneproject.R;
import com.example.hp.capstoneproject.adapters.PlayerInfoAdapter;
import com.example.hp.capstoneproject.adapters.UpcomingAdapter;
import com.example.hp.capstoneproject.model.UpcomingMatches;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PlayerInfo extends Fragment {
    /*@InjectView(R.id.playerInfo_recyler)
    RecyclerView player_recyclerView;*/

    //PlayerInfoAdapter adapter;
    @InjectView(R.id.player_edit)
    EditText editText;

    @InjectView(R.id.btn)
    Button findBtn;
    public static String link = null;
    private RequestQueue requestQueue;
    String name;
    String id;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_player_info, container, false);
        ButterKnife.inject(this, view);
        requestQueue = Volley.newRequestQueue(getActivity());
        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = editText.getText().toString();
                link = "http://cricapi.com/api/playerFinder?name=" + name + "&apikey=CUHZ98QHE6W3VHzEUMhYjAOxzHX2";

                parseJsonData();
                parsePlayer();
            }
        });
        /*String[] id = {"253802", "28235", "234675", "26421", "422108", "237095", "277916", "625371",
                        "30045", "236779", "376116", "481896", "398439", "475281", "559235", "325026",
                        "6683", "530011", "325012"};

        String[] names = {"Virat Kohli", "Shikhar Dhawan", "Ravindrasinh Anirudhsinh Jadeja",
                            "Ravichandran Ashwin", "Kannaur Lokesh Rahul", "Murali Vijay",
                "Ajinkya Madhukar Rahane", "Hardik Himanshu Pandya", "Krishnakumar Dinesh Karthik",
                "Ishant Sharma", "Umeshkumar Tilak Yadav", "Mohammad Imran Tahir",
                "Mohammed Shami Ahmed", "Karun Kaladharan Nair", "Shardul Narendra Thakur",
                "Kuldeep Yadav", "Glenn James Maxwell", "Marcus Peter Stoinis",
                "Aaron James Finch"};

        adapter = new PlayerInfoAdapter(getActivity(), id, names);
        player_recyclerView.setAdapter(adapter);
        player_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.notifyDataSetChanged();

*/

        return view;
    }

    public void parseJsonData() {

        StringRequest request = new StringRequest(Request.Method.GET,
                link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getActivity(), ""+response, Toast.LENGTH_SHORT).show();

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray articles = jsonObject.getJSONArray("data");
                    for (int i = 0; i < articles.length(); i++) {
                        JSONObject jp = articles.getJSONObject(i);
                        id = jp.optString("pid");
                        Toast.makeText(getActivity(), "" + id, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(request);
    }

    public void parsePlayer()
    {
        String li="http://cricapi.com/api/playerStats?pid="+id+"&apikey=CUHZ98QHE6W3VHzEUMhYjAOxzHX2";

        StringRequest request = new StringRequest(Request.Method.GET,
                li, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getActivity(), ""+response, Toast.LENGTH_SHORT).show();


                    String id;
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    String majorteams = jsonObject.getString("majorTeams");

                    Toast.makeText(getActivity(), "" + majorteams, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(request);
    }

}
