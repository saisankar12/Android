package com.example.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class VersionAdapter extends RecyclerView.Adapter<VersionAdapter.VersionViewHolder> {

    Context ct;
    int[] imagesnames;
    String[] stringnames;

    public VersionAdapter(MainActivity mainActivity, int[] versions, String[] versionnames) {

        this.ct=mainActivity;
        this.imagesnames=versions;
        this.stringnames=versionnames;

    }

    @NonNull
    @Override
    public VersionAdapter.VersionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(ct).inflate(R.layout.rowdesign,
                                        viewGroup,false);
        return new VersionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VersionAdapter.VersionViewHolder
                                             versionViewHolder, final int i) {

        versionViewHolder.iv.setImageResource(imagesnames[i]);
        versionViewHolder.tv.setText(stringnames[i]);

        versionViewHolder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ct, ""+i, Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return imagesnames.length;
    }

    public class VersionViewHolder extends RecyclerView.ViewHolder {

        ImageView iv;
        TextView tv;

        public VersionViewHolder(@NonNull View itemView) {
            super(itemView);

            iv=itemView.findViewById(R.id.rowimages);
            tv=itemView.findViewById(R.id.rowtext);

        }
    }
}
