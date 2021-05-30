package com.blogspot.pavankreddytadi.storyboard;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blogspot.pavankreddytadi.storyboard.room_database.StoryBoard;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.StoryViewHolder> {

    Context context;
    List<StoryBoard> storyBoard;
    public RecyclerAdapter(MainActivity mainActivity, List<StoryBoard> storyBoards) {
        this.context=mainActivity;
        this.storyBoard=storyBoards;
    }

    @NonNull
    @Override
    public RecyclerAdapter.StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StoryViewHolder(LayoutInflater.from(context).inflate(R.layout.one_row_in_recyclerview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.StoryViewHolder holder, int position) {
        holder.story.setText(storyBoard.get(position).getStory());
        holder.auther.setText(storyBoard.get(position).getAuther());

    }

    @Override
    public int getItemCount() {
        return storyBoard.size();
    }

    public class StoryViewHolder extends RecyclerView.ViewHolder {
        TextView story,auther;
        public StoryViewHolder(View itemView) {
            super(itemView);
            story=itemView.findViewById(R.id.story);
            auther=itemView.findViewById(R.id.author);
        }
    }
}
