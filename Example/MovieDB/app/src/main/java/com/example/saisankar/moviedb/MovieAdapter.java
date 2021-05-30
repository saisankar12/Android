package com.example.saisankar.moviedb;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saisankar.moviedb.database.MovieInfo;
import com.example.saisankar.moviedb.utilities.InternetUtilities;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    Context moviecontext;
    List<MovieInfo> infos;
    public MovieAdapter(Context context, List<MovieInfo> movieInfos) {
        this.moviecontext=context;
        this.infos=  movieInfos;
    }

    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(moviecontext).inflate(R.layout.movieposterlayout,parent,false);
        return new MovieViewHolder(v);
    }
    @Override
    public void onBindViewHolder(MovieAdapter.MovieViewHolder holder, final int position) {
        //URL s= InternetUtilities.buildImageUrl(infos.get(position).getMposter_path().substring(1));
        String s=infos.get(position).getMposter_path();
        Picasso.with(moviecontext).load(s).into(holder.posterimage);
        MovieInfo movieInfo =infos.get(position);
        holder.movietitle.setText(movieInfo.getMtitle());
        Log.i("poster",s);
        holder.posterimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] movieData=new String[7];
                movieData[0]=infos.get(position).getMposter_path();
                movieData[1]=infos.get(position).getMoriginal_title();
                movieData[2]=infos.get(position).getMbackdrop_path();
                movieData[3]=infos.get(position).getMrelease_date();
                movieData[4]=infos.get(position).getMvote_average();
                movieData[5]=infos.get(position).getMoverview();
                movieData[6]= String.valueOf(infos.get(position).getMid());
                Intent i=new Intent(moviecontext,MovieDetails.class);
                i.putExtra("moviedetails",movieData);
                i.putExtra("id",infos.get(position).getMid());
                v.getContext().startActivity(i);
            }
        });
    }
    @Override
    public int getItemCount() {
        return infos.size();
    }
    public class MovieViewHolder extends RecyclerView.ViewHolder  {
        ImageView posterimage;
        TextView movietitle;
        public MovieViewHolder(View itemView) {
            super(itemView);
            posterimage=itemView.findViewById(R.id.poster_image);
            movietitle=itemView.findViewById(R.id.recyclertitle);
        }
    }
}
