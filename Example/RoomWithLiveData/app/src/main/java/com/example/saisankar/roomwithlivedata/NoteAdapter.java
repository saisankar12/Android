package com.example.saisankar.roomwithlivedata;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    Context context;
    List<User> list;
    public NoteAdapter(MainActivity mainActivity, List<User> userList) {
        this.context=mainActivity;
        this.list=userList;
    }

    @NonNull
    @Override
    public NoteAdapter.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(context).inflate(R.layout.design,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final NoteAdapter.NoteViewHolder holder, int position) {
        final User user=list.get(position);
        //holder.roll.setText(list.get(position).getRollNum());
        holder.roll.setText(user.getRollNum());
        holder.vname.setText(user.getName());
        holder.vemail.setText(user.getEmail());
        /*holder.vemail.setText(list.get(position).getEmail());
        holder.vname.setText(list.get(position).getName());*/

        holder.udelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //UserViewModel userViewModel=ViewModelProviders.of(v).get(UserViewModel.class);
                MainActivity.userViewModel.delete(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView roll,vname,vemail;
        ImageView udelete,uedit;
        public NoteViewHolder(View itemView) {
            super(itemView);
            roll=itemView.findViewById(R.id.viewroll);
            vname=itemView.findViewById(R.id.viewname);
            vemail=itemView.findViewById(R.id.viewemail);
            udelete=itemView.findViewById(R.id.userdelete);
            uedit=itemView.findViewById(R.id.useredit);
            udelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* //UserViewModel viewModel=ViewModelProviders.get(UserViewModel.class);
                        viewModel.delete(list.get(getAdapterPosition()));
                    //MainActivity.userDataBase.noteDao().deleteData(list.get(getAdapterPosition()));
*/

                   User user=new User();

                }
            });

            uedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 Intent i=new Intent(context,EditNote.class);
                    i.putExtra("roll",roll.getText().toString());
                    i.putExtra("name",vname.getText().toString());
                    i.putExtra("email",vemail.getText().toString());
                 context.startActivity(i);
                }
            });
        }
    }
}
