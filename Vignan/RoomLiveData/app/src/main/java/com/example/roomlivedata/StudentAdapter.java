package com.example.roomlivedata;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    Context context;
    List<StudentModel> modelList;
    public StudentAdapter(MainActivity mainActivity, List<StudentModel> studentModels) {

        context=mainActivity;
        modelList=studentModels;
    }

    @NonNull
    @Override
    public StudentAdapter.StudentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(context).inflate(R.layout.rowdesign,
                                viewGroup,false);

        return new StudentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.StudentViewHolder
                                             studentViewHolder, int i) {

        StudentModel model=modelList.get(i);
        studentViewHolder.vroll.setText(model.getRollnumber());
        studentViewHolder.vname.setText(model.getName());
        studentViewHolder.vmail.setText(model.getGmail());
        studentViewHolder.vphone.setText(model.getMobile());

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {

        TextView vroll,vname,vmail,vphone;
        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);

            vroll=itemView.findViewById(R.id.readroll);
            vname=itemView.findViewById(R.id.readname);
            vmail=itemView.findViewById(R.id.readmail);
            vphone=itemView.findViewById(R.id.readPhone);

        }
    }
}
