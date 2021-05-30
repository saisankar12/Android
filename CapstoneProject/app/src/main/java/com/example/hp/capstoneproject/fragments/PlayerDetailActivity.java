package com.example.hp.capstoneproject.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import com.android.volley.RequestQueue;
import com.example.hp.capstoneproject.R;

public class PlayerDetailActivity extends AppCompatActivity
{
    public static String link=null;
    private RequestQueue requestQueue;
    String name;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        name=getIntent().getStringExtra("name");
        link="http://cricapi.com/api/playerStats?pid=33757&apikey=CUHZ98QHE6W3VHzEUMhYjAOxzHX2";
    }



}
