package com.example.saisankar.cinematics.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saisankar.cinematics.InternetUtilities;
import com.example.saisankar.cinematics.R;
import com.example.saisankar.cinematics.content.MovieDetails;
import com.example.saisankar.cinematics.model.MovieInfo;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyMovieHolder> {
    Context context;
    ArrayList<MovieInfo> infos;

    public MovieAdapter(FragmentActivity activity, ArrayList<MovieInfo> s) {
        this.context = activity;
        this.infos = s;
    }

    @NonNull
    @Override
    public MovieAdapter.MyMovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.recycler_row_design, parent, false);

        return new MyMovieHolder(v);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MyMovieHolder holder, final int position) {


        URL s = InternetUtilities.buildImageUrl(infos.get(position).getMposter_path());

        Picasso.with(context).load(s.toString()).error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher).into(holder.posterimage);
        MovieInfo movieInfo = infos.get(position);
        holder.movietitle.setText(movieInfo.getMtitle());
        holder.posterimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] movieData = new String[7];
                movieData[0] = infos.get(position).getMposter_path();
                movieData[1] = infos.get(position).getMoriginal_title();
                movieData[2] = infos.get(position).getMbackdrop_path();
                movieData[3] = infos.get(position).getMrelease_date();
                movieData[4] = infos.get(position).getMvote_average();
                movieData[5] = infos.get(position).getMoverview();
                movieData[6] = infos.get(position).getMtitle();
                Intent i = new Intent(context, MovieDetails.class);
                i.putExtra("movieDetails", movieData);
                i.putExtra("id", infos.get(position).getMid());
                v.getContext().startActivity(i);


            }
        });
    }

    @Override
    public int getItemCount() {
        return infos.size();
    }

    public class MyMovieHolder extends RecyclerView.ViewHolder {
        ImageView posterimage;
        TextView movietitle;

        public MyMovieHolder(View itemView) {
            super(itemView);
            posterimage = itemView.findViewById(R.id.poster_image);
            movietitle = itemView.findViewById(R.id.recyclertitle);
        }
    }
}
