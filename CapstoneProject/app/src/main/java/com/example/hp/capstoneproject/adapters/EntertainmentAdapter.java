package com.example.hp.capstoneproject.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.capstoneproject.R;
import com.example.hp.capstoneproject.model.Entertainment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class EntertainmentAdapter extends RecyclerView.Adapter<EntertainmentAdapter.EntertainmentHolder>
{
    Context context;
    ArrayList<Entertainment> arrayList;
    public EntertainmentAdapter(FragmentActivity activity, ArrayList<Entertainment> arrayList)
    {
        this.context=activity;
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public EntertainmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        EntertainmentHolder entertainmentHolder=new EntertainmentHolder(view);
        return entertainmentHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EntertainmentHolder holder, int position) {
        Picasso.with(context).load(arrayList.get(position).getEt_urlToImage())
                .placeholder(R.drawable.noprview).into(holder.imageView);
        holder.textView.setText(arrayList.get(position).getEt_title());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class EntertainmentHolder extends RecyclerView.ViewHolder
    {
        @InjectView(R.id.tview)
        TextView textView;
        @InjectView(R.id.imgview)
        ImageView imageView;

        public EntertainmentHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
