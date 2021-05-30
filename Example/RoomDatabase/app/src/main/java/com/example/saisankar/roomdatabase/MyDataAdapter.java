package com.example.saisankar.roomdatabase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyDataAdapter extends RecyclerView.Adapter<MyDataAdapter.MyViewHolder> {

    Context context;
    List<User> users;
    public MyDataAdapter(FragmentActivity activity, List<User> userList) {
        this.context=activity;
        this.users=userList;
    }

    @NonNull
    @Override
    public MyDataAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(context).inflate(R.layout.datadesign,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyDataAdapter.MyViewHolder holder, int position) {


        holder.tid.setText(String.valueOf(users.get(position).getId()));
        holder.tname.setText(users.get(position).getName());
        holder.temail.setText(users.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tid,tname,temail;
        public MyViewHolder(View itemView) {
            super(itemView);

            tid=itemView.findViewById(R.id.getuserid);
            tname=itemView.findViewById(R.id.getusername);
            temail=itemView.findViewById(R.id.getuseremail);

        }
    }
}
