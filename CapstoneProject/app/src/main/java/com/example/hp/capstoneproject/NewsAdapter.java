package com.example.hp.capstoneproject;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.capstoneproject.fragments.NewsDetailActivity;
import com.example.hp.capstoneproject.model.Article;
import com.example.hp.capstoneproject.widget.CricketWidget;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {
    ArrayList<Article> exampleArrayList;
    //Context context;
    Activity context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public NewsAdapter(FragmentActivity activity, ArrayList<Article> arrayList) {
        this.context = activity;
        this.exampleArrayList = arrayList;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        Picasso.with(context).load(exampleArrayList.get(position).getUrlToImage())
                .placeholder(R.drawable.noprview).into(holder.imageView);
        holder.textView.setText(exampleArrayList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return exampleArrayList.size();
    }

    public class NewsHolder extends RecyclerView.ViewHolder {
         @InjectView(R.id.tview)
         TextView textView;
        @InjectView(R.id.imgview)
        ImageView imageView;

        public NewsHolder(final View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                    if (pos!=RecyclerView.NO_POSITION){
                        Intent bundle=new Intent(context, NewsDetailActivity.class);
                        bundle.putExtra("title",exampleArrayList.get(pos).getTitle());
                        bundle.putExtra("description",exampleArrayList.get(pos).getDescription());
                        bundle.putExtra("author",exampleArrayList.get(pos).getAuthor());
                        bundle.putExtra("publish",exampleArrayList.get(pos).getPublishedAt());
                        bundle.putExtra("image",exampleArrayList.get(pos).getUrlToImage());
                        bundle.putExtra("linkurl",exampleArrayList.get(pos).getUrl());

                        sharedPreferences=context.getSharedPreferences("mypref",Context.MODE_PRIVATE);
                        editor=sharedPreferences.edit();
                        editor.clear();
                        editor.putString("title",exampleArrayList.get(pos).getTitle());
                        editor.putString("description",exampleArrayList.get(pos).getDescription());
                        editor.commit();

                        Intent intent = new Intent(context, CricketWidget.class);
                        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
                        int ids[] = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context,
                                CricketWidget.class));
                        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
                        context.sendBroadcast(intent);


                        context.startActivity(bundle);

                    }
                }
            });
        }
    }
}
