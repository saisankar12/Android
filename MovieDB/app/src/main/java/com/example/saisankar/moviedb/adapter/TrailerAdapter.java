package com.example.saisankar.moviedb.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.saisankar.moviedb.R;
import com.example.saisankar.moviedb.YouTubeClass;
import com.example.saisankar.moviedb.model.TrailersInfo;

import java.util.ArrayList;

/**
 * Created by Sai Sankar
 * on 16-05-2018.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {
    static Context c;
    static ArrayList<TrailersInfo> pogos;


 public TrailerAdapter(Context context, ArrayList<TrailersInfo>
            trailersInfos) {
    this.c=context;
    this.pogos=trailersInfos;

    }
    @Override
    public TrailerAdapter.TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(c).inflate(R.layout.trailer, parent, false);
        return new TrailerViewHolder(v);


    }
    @Override
    public void onBindViewHolder(TrailerAdapter.TrailerViewHolder holder, int position) {
        TrailersInfo trailerPOGO =pogos.get(position);
        holder.trmovie.setText(trailerPOGO.getName());
        Log.i("movietitle",holder.trmovie.toString());

    }
    @Override
    public int getItemCount() {

     return pogos.size();
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder {

        TextView trmovie;

        public TrailerViewHolder(View itemView) {
            super(itemView);

            trmovie=itemView.findViewById(R.id.textview);
            trmovie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=getLayoutPosition();
                    //Uri uri=Uri.parse("https://www.youtube.com/watch?v="+pogos.get(pos).getKey());
                    String s=pogos.get(pos).getKey();
                    Uri uri=Uri.parse(s);
                    //Intent i=new Intent(Intent.ACTION_VIEW, uri);
                    Log.i("traileryoutubeurl",uri.toString());
                    Intent i=new Intent(c, YouTubeClass.class);
                    i.putExtra("youtubeurl",uri);
                    c.startActivity(i);
                }
            });
        }
    }

}
