package com.example.saisankar.bookapi;

import android.content.Context;
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

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    Context c;
    ArrayList<BookMenuDesign> bookMenu;
    public MenuAdapter(FragmentActivity activity, ArrayList<BookMenuDesign> bookMenuDesigns) {
   this.c=activity;
   this.bookMenu=bookMenuDesigns;
    }

    @NonNull
    @Override
    public MenuAdapter.MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(c).inflate(R.layout.menudesign,parent,false);
        return new MenuViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.MenuViewHolder holder, int position) {

        BookMenuDesign bookMenuDesign=bookMenu.get(position);
        holder.titles.setText(bookMenuDesign.titles);
        Picasso.with(c).load(bookMenuDesign.getThumbnail()).placeholder(R.mipmap.ic_launcher_round).into(holder.bookImages);
        holder.countrys.setText(bookMenuDesign.country);



    }

    @Override
    public int getItemCount() {
        return bookMenu.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {
        TextView titles,countrys;
        ImageView bookImages;
        public MenuViewHolder(View itemView) {
            super(itemView);
            titles=itemView.findViewById(R.id.booktitle);
            countrys=itemView.findViewById(R.id.country);
            bookImages=itemView.findViewById(R.id.bookimage);
        }
    }
}
