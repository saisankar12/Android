package com.example.saisankar.cinematics.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.saisankar.cinematics.R;
import com.example.saisankar.cinematics.YouTubeTrailer;
import com.example.saisankar.cinematics.model.TrailersInfo;

import java.util.ArrayList;



public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {
    @SuppressLint("StaticFieldLeak")
    static Context c;
    static ArrayList<TrailersInfo> pogos;


    public TrailerAdapter(Context context, ArrayList<TrailersInfo>
            trailersInfos) {
        this.c = context;
        this.pogos = trailersInfos;

    }

    @NonNull
    @Override
    public TrailerAdapter.TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(c).inflate(R.layout.trailer, parent, false);
        return new TrailerViewHolder(v);


    }

    @Override
    public void onBindViewHolder(@NonNull TrailerAdapter.TrailerViewHolder holder, int position) {
        TrailersInfo trailerPOGO = pogos.get(position);
        holder.trmovie.setText(trailerPOGO.getName());


    }

    @Override
    public int getItemCount() {

        return pogos.size();
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder {

        TextView trmovie;

        public TrailerViewHolder(View itemView) {
            super(itemView);

            trmovie = itemView.findViewById(R.id.textview);
            trmovie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getLayoutPosition();

                    String s = pogos.get(pos).getKey();
                   Intent i = new Intent(c, YouTubeTrailer.class);
                    i.putExtra("youtubeurl", s);
                    c.startActivity(i);
                }
            });
        }
    }

}
