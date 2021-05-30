package com.example.hp.capstoneproject.adapters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hp.capstoneproject.R;
import com.example.hp.capstoneproject.fragments.PlayerDetailActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PlayerInfoAdapter extends RecyclerView.Adapter<PlayerInfoAdapter.PlayerHolder>
{
    private Activity context;
    private String[] id,names;

    public PlayerInfoAdapter(FragmentActivity activity, String[] id, String[] names)
    {
        this.context=activity;
        this.id=id;
        this.names=names;
    }

    @NonNull
    @Override
    public PlayerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(context).inflate(R.layout.playeritem,parent,false);
        return new PlayerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerHolder holder, int position) {
        holder.matchView.setText(id[position]);
        holder.matchDate.setText(names[position]);
    }

    @Override
    public int getItemCount() {
        return id.length;
    }

    public class PlayerHolder extends RecyclerView.ViewHolder
    {
        @InjectView(R.id.matchbw)
        TextView matchView;
        @InjectView(R.id.match_date)
        TextView matchDate;

        public PlayerHolder(View itemView) {
            super(itemView);

            ButterKnife.inject(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                int pos=getAdapterPosition();
                @Override
                public void onClick(View v) {
                    Intent bundle=new Intent();
                    bundle.putExtra("name",names[pos]);
                    context.startActivity(bundle);

                }
            });
        }
    }
}
