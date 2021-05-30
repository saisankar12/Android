package com.example.saisankar.backingapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.saisankar.backingapp.model.Receipe;

import java.util.ArrayList;

public class ReceipeAdapter extends RecyclerView.Adapter<ReceipeAdapter.MyViewHolder> {

    Context ct;
    ArrayList<Receipe> list;
    public ReceipeAdapter(MainActivity mainActivity, ArrayList<Receipe> receipes) {
        ct=mainActivity;
        list=receipes;
    }

    @NonNull
    @Override
    public ReceipeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(ct).inflate(R.layout.maindesign,viewGroup,false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ReceipeAdapter.MyViewHolder myViewHolder, int i) {

        Receipe receipe=list.get(i);
        myViewHolder.rename.setText(receipe.getName());
        myViewHolder.reservings.setText(receipe.getServings());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView rename,reservings;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rename=itemView.findViewById(R.id.receipename);
            reservings=itemView.findViewById(R.id.receipeservings);
        }
    }
}
