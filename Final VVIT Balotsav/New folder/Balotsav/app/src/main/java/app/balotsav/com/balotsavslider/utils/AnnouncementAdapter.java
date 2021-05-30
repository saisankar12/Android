package app.balotsav.com.balotsavslider.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import app.balotsav.com.balotsavslider.R;
import app.balotsav.com.balotsavslider.model.Announcement;


public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.Holder> {
    ArrayList<Announcement> arrayList;

    public AnnouncementAdapter(ArrayList<Announcement> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public AnnouncementAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View AnnounceView = LayoutInflater.from(parent.getContext()).inflate(R.layout.announcement, parent, false);
        return new Holder(AnnounceView);
    }

    @Override
    public void onBindViewHolder(AnnouncementAdapter.Holder holder, int position) {

        holder.announcements.setText(arrayList.get(position).getNotice());
        holder.date.setText(arrayList.get(position).getDate());
        holder.time.setText(arrayList.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView announcements, time, date;

        public Holder(View itemView) {
            super(itemView);
            announcements = itemView.findViewById(R.id.announcement_text);
            date = itemView.findViewById(R.id.id_date_stamp);
            time = itemView.findViewById(R.id.id_time_stamp);
        }
    }
}