package com.example.hp.capstoneproject.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hp.capstoneproject.R;
import com.example.hp.capstoneproject.model.OldMatches;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class OldmatchesAdapter extends RecyclerView.Adapter<OldmatchesAdapter.Oldholder>
{
    Context context;
    ArrayList<OldMatches> oldMatches;
    public OldmatchesAdapter(FragmentActivity activity, ArrayList<OldMatches> arrayList)
    {
        this.context=activity;
        this.oldMatches=arrayList;
    }

    @NonNull
    @Override
    public Oldholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(context).inflate(R.layout.olditem,parent,false);
        return new Oldholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Oldholder holder, int position) {
        holder.matchView.setText(oldMatches.get(position).getTitle());
        //holder.matchDate.setText(scheduleMatches.get(position).getDate());
    }


    @Override
    public int getItemCount() {
        return oldMatches.size();
    }

    public class Oldholder extends RecyclerView.ViewHolder
    {
        @InjectView(R.id.oldmb)
        TextView matchView;

        public Oldholder(View itemView) {
            super(itemView);

            ButterKnife.inject(this,itemView);
        }
    }
}
