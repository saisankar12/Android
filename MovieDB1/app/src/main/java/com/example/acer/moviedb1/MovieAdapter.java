package com.example.acer.moviedb1;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {
    Context context;
    List<MovieModel> arrayList;

    public MovieAdapter(Context context, List<MovieModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MovieAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(context).inflate(R.layout.design,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MyViewHolder holder, final int position) {
        MovieModel movieModel=arrayList.get(position);
        holder.textView.setText(movieModel.tit);
        Picasso.with(context).load("http://image.tmdb.org/t/p/w185/"+movieModel.img).into(holder.imageView);
        Log.i("movieurl",movieModel.img);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] str=new String [6];
                str[0]=arrayList.get(position).getImg();
                str[1]=arrayList.get(position).getTit();
                str[2]=arrayList.get(position).getId1();
                str[3]=arrayList.get(position).getAve();
                str[4]=arrayList.get(position).getOver();
                str[5]=arrayList.get(position).getReale();
                Intent i=new Intent(context,MovieDetailActivity.class);
                i.putExtra("hai",str);
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList==null?0:arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
            textView=itemView.findViewById(R.id.text);
        }
    }
}
