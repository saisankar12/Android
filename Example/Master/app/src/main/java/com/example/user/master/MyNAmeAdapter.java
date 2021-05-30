package com.example.user.master;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.master.Models.MyModel1;

import java.util.ArrayList;

public class MyNAmeAdapter extends RecyclerView.Adapter<MyNAmeAdapter.MyViewHolder> {

    Context context;
    ArrayList<MyModel1> model1s;
    MyModel1 myModel1;


    public MyNAmeAdapter(MainActivity mainActivity, ArrayList<MyModel1> model1ArrayList) {
        this.context = mainActivity;
        this.model1s = model1ArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.namedesign1,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {

        myModel1 = model1s.get(i);
        myViewHolder.textViewName.setText(myModel1.getNamee());
        myViewHolder.textViewServings.setText("Servings : "+myModel1.getServings());
        myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ItemListActivity.class);
                intent.putExtra("details", model1s.get(i));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return model1s.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName,textViewServings;
        LinearLayout linearLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewServings = itemView.findViewById(R.id.txtServings);
            textViewName = itemView.findViewById(R.id.txtName);
            linearLayout = itemView.findViewById(R.id.linear);
        }
    }
}
