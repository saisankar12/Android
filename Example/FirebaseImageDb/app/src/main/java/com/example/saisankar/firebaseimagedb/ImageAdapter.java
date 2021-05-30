package com.example.saisankar.firebaseimagedb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {

  Context ct;
  List<MyModel> teachers;;


    public ImageAdapter(Context context, List<MyModel> teachers) {
        ct = context;
        this.teachers = teachers;
    }

    @NonNull
    @Override
    public ImageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(ct).inflate(R.layout.roudesign,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.MyViewHolder myViewHolder, int i) {

        MyModel model=teachers.get(i);
        myViewHolder.nameTextView.setText(model.getName());
        myViewHolder.dateTextView.setText(getDateToday());
        myViewHolder.descriptionTextView.setText(model.getDescription());

        Log.i("imageupload",model.getImageURL());
        Picasso.with(ct)
                .load(model.getImageURL())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(myViewHolder.nameImageView);
    }

    @Override
    public int getItemCount() {
        return teachers.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView nameTextView,descriptionTextView,dateTextView;
        public ImageView nameImageView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView=itemView.findViewById(R.id.nameTextView);
            descriptionTextView=itemView.findViewById(R.id.descriptionTextView);
            dateTextView=itemView.findViewById(R.id.dateTextView);
            nameImageView=itemView.findViewById(R.id.imageView);

        }
    }
    private String getDateToday(){
        DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd");
        Date date=new Date();
        String today= dateFormat.format(date);
        return today;
    }
}
