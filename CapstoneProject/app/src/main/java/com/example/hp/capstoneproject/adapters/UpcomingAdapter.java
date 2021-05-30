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
import com.example.hp.capstoneproject.model.UpcomingMatches;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class UpcomingAdapter extends RecyclerView.Adapter<UpcomingAdapter.UpcomingHolder> {
    Context context;
    ArrayList<UpcomingMatches> arrayList;

    public UpcomingAdapter(FragmentActivity activity, ArrayList<UpcomingMatches> arrayList) {
        this.context = activity;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public UpcomingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.upcomingitem, parent, false);
        return new UpcomingHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingHolder holder, int position) {
        holder.team1.setText(arrayList.get(position).getTeam_1());
        holder.team2.setText(arrayList.get(position).getTeam_2());
        holder.type.setText(arrayList.get(position).getType());
        //holder.time.setText(arrayList.get(position).getTime());
        holder.date.setText(arrayList.get(position).getDate());
        holder.matchstarted.setText(arrayList.get(position).getMatchStarted());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class UpcomingHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.team1)
        TextView team1;
        @InjectView(R.id.team2)
        TextView team2;
        @InjectView(R.id.type)
        TextView type;
        @InjectView(R.id.mdate)
        TextView date;
       /* @InjectView(R.id.mtime)
        TextView time;*/
        @InjectView(R.id.matchstarted)
        TextView matchstarted;

        public UpcomingHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
