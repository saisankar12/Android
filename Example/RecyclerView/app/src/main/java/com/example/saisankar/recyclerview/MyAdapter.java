package com.example.saisankar.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    Context context;
    ArrayList<BookModel> books;


    public MyAdapter(MainActivity mainActivity, ArrayList<BookModel> bookModels) {
    this.context=mainActivity;
    this.books=bookModels;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v=LayoutInflater.from(context).inflate(R.layout.design,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, final int position) {

    final BookModel bookModel=books.get(position);
    holder.tv.setText(bookModel.title);
    holder.tv.setText(bookModel.bookid);
    Picasso.with(context).load(bookModel.thumbnail).into(holder.image);
    holder.image.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        String[] bookinfor=new String[4];
        bookinfor[0]=books.get(position).getBookid();
        bookinfor[1]=books.get(position).getThumbnail();
        bookinfor[2]=books.get(position).getTitle();
        bookinfor[3]=books.get(position).getDescription();
            Intent i =new Intent(context,DetailsActivity.class);
            i.putExtra("data",bookinfor);
            context.startActivity(i);

        }
    });

    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView tv,textview;
        public MyViewHolder(View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.imageview);
            tv=itemView.findViewById(R.id.textview);
            textview=itemView.findViewById(R.id.tv);
        }
    }
}
