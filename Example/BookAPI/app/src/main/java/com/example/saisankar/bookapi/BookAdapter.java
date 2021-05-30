package com.example.saisankar.bookapi;

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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyBookHolder> {
    Context context;
    ArrayList<BookModel> bookModels;
    public BookAdapter(FragmentActivity activity, ArrayList<BookModel> bookModel) {
        this.context=activity;
        this.bookModels=bookModel;
    }

    @NonNull
    @Override
    public BookAdapter.MyBookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(context).inflate(R.layout.design,parent,false);
        return new MyBookHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.MyBookHolder holder, final int position) {

        final BookModel bookModel=bookModels.get(position);
        Picasso.with(context).load(bookModel.thumbnail).into(holder.iv);
        holder.tv.setText(bookModel.title);
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String[] bookinfo=new String[4];
              bookinfo[0]=bookModels.get(position).getBookid();
              bookinfo[1]=bookModels.get(position).getTitle();
              bookinfo[2]=bookModels.get(position).getThumbnail();
              bookinfo[3]=bookModels.get(position).getDescription();
                Intent i=new Intent(context,BookDetails.class);
                i.putExtra("details",bookinfo);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return bookModels.size();
    }

    public class MyBookHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv;
        public MyBookHolder(View itemView) {
            super(itemView);
            iv=itemView.findViewById(R.id.bookimage);
            tv=itemView.findViewById(R.id.booktitle);
        }
    }
}
