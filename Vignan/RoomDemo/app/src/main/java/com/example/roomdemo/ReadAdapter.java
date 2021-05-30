package com.example.roomdemo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.roomdemo.components.StudentModel;

import java.util.List;

public class ReadAdapter extends RecyclerView.Adapter<ReadAdapter.ReadViewHolder> {
   Context ct;
   List<StudentModel> models;
    public ReadAdapter(MainActivity mainActivity, List<StudentModel> studentModels) {
        ct=mainActivity;
        models=studentModels;
    }

    @NonNull
    @Override
    public ReadAdapter.ReadViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ReadViewHolder(LayoutInflater.from(ct).inflate(R.layout.rowdesign,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ReadAdapter.ReadViewHolder readViewHolder, int i) {

        final StudentModel model=models.get(i);
        readViewHolder.rname.setText(model.getName());
        readViewHolder.rphone.setText(model.getPhone());
        readViewHolder.rmail.setText(model.getEmail());
        readViewHolder.rdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.viewModel.deletedata(model);
            }
        });
        readViewHolder.redit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ct,EditActivity.class);
                i.putExtra("Name",readViewHolder.rname.getText().toString());
                i.putExtra("Phone",readViewHolder.rphone.getText().toString());
                i.putExtra("Email",readViewHolder.rmail.getText().toString());
                ct.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ReadViewHolder extends RecyclerView.ViewHolder{

        TextView rname,rmail,rphone;
        ImageView rdelete,redit;
        public ReadViewHolder(@NonNull View itemView) {
            super(itemView);
            rname=itemView.findViewById(R.id.readname);
            rphone=itemView.findViewById(R.id.readmobile);
            rmail=itemView.findViewById(R.id.readmail);
            rdelete=itemView.findViewById(R.id.delete);
            redit=itemView.findViewById(R.id.edit);
        }
    }
}
