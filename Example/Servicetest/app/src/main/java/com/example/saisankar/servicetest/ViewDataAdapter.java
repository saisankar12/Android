package com.example.saisankar.servicetest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.saisankar.servicetest.database.UserModel;

import java.util.List;

public class ViewDataAdapter extends RecyclerView.Adapter<ViewDataAdapter.MyViewHolder> {
    Context ct;
    List<UserModel> models;
    public ViewDataAdapter(FragmentActivity activity, List<UserModel> userModels) {
    this.ct=activity;
    this.models=userModels;
    }

    @NonNull
    @Override
    public ViewDataAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(ct).inflate(R.layout.viewdesign,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewDataAdapter.MyViewHolder holder, int position) {

        UserModel model=models.get(position);
        holder.u_id.setText(model.getUid());
        holder.u_name.setText(model.getUname());
        holder.u_mail.setText(model.getUmail());
        holder.u_mobile.setText(model.getUmobile());

    }

    @Override
    public int getItemCount() {
        return (models==null) ?0:models.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView u_id,u_name,u_mail,u_mobile;
        public MyViewHolder(View itemView) {
            super(itemView);

            u_id=itemView.findViewById(R.id.viewuserid);
            u_name=itemView.findViewById(R.id.viewusername);
            u_mail=itemView.findViewById(R.id.viewusermail);
            u_mobile=itemView.findViewById(R.id.viewusermobile);
        }
    }
}
