package com.example.acer.moviedb1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.MyViewHolder> {
    Context context;
    ArrayList<TrailerModel> arrayList;

    public TrailerAdapter(Context context, ArrayList<TrailerModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public TrailerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(context).inflate(R.layout.trailerdesign,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerAdapter.MyViewHolder holder, int position) {
        final TrailerModel model=arrayList.get(position);
        holder.txtlink.setText(model.getKey());
        holder.txtlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vdlink=model.getKey();
                Intent vdi=new Intent(Intent.ACTION_VIEW);
                vdi.setData(Uri.parse("https://www.youtube.com/watch?v="+vdlink));
                //vdi.setPackage("com.google.android.youtube");
                context.startActivity(vdi);
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtlink;
        public MyViewHolder(View itemView) {
            super(itemView);
            txtlink=itemView.findViewById(R.id.trailer);
        }
    }
}
