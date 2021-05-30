package com.example.asyntask;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImageViewHolder> {

    Context ct;
    ArrayList<ImageModel> imageModels;
    public ImagesAdapter(MainActivity mainActivity, ArrayList<ImageModel> models) {

        this.ct=mainActivity;
        this.imageModels=models;
    }

    @NonNull
    @Override
    public ImagesAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(ct)
                .inflate(R.layout.design,viewGroup,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesAdapter.ImageViewHolder imageViewHolder, int i) {
            ImageModel model=imageModels.get(i);
            imageViewHolder.tv.setText(model.getImagelikes());

             Picasso.with(ct).load(model.getImagedata()).into(imageViewHolder.iv);

    }

    @Override
    public int getItemCount() {
        return imageModels.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            iv=itemView.findViewById(R.id.rowimage);
            tv=itemView.findViewById(R.id.rowText);
        }
    }
}
