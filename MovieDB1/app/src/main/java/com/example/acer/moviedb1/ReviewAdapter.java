package com.example.acer.moviedb1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {
    Context context;
    ArrayList<ReviewModel> arrayList;

    public ReviewAdapter(Context context, ArrayList<ReviewModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ReviewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(context).inflate(R.layout.rowdesign,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.MyViewHolder holder, int position) {
        ReviewModel model=arrayList.get(position);
        holder.author1.setText(model.getAuthor());
        holder.content1.setText(model.getContent());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView author1,content1;
        public MyViewHolder(View itemView) {
            super(itemView);
            author1=itemView.findViewById(R.id.author);
            content1=itemView.findViewById(R.id.content);
        }
    }
}
