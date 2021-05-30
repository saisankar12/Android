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
import com.example.hp.capstoneproject.model.ScheduleMatches;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleHolder>
{
    Context context;
    ArrayList<ScheduleMatches> scheduleMatches;

    public ScheduleAdapter(FragmentActivity activity, ArrayList<ScheduleMatches> arrayList)
    {
        this.context=activity;
        this.scheduleMatches=arrayList;
    }

    @NonNull
    @Override
    public ScheduleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(context).inflate(R.layout.schedulelay,parent,false);
        return new ScheduleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleHolder holder, int position) {
        holder.matchView.setText(scheduleMatches.get(position).getName());
        holder.matchDate.setText(scheduleMatches.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return scheduleMatches.size();
    }

    public class ScheduleHolder extends RecyclerView.ViewHolder
    {
        @InjectView(R.id.matchbw)
        TextView matchView;
        @InjectView(R.id.match_date)
        TextView matchDate;

        public ScheduleHolder(View itemView) {
            super(itemView);

            ButterKnife.inject(this,itemView);
        }
    }
}
