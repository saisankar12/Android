package com.example.saisankar.stampcollecter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    String stampnames[]={"Indian Post","Gandhi Post",
            "Vivekanand Post","Bismillah Post","APJ Abdul Kalam Post",
            "Mother Teresa Post"};
    int stampimages[]={R.drawable.indian,R.drawable.gandhi,R.drawable.apj,
            R.drawable.bismillah,R.drawable.mother,R.drawable.vivek};

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new StampAdapter(this,stampimages,stampnames,stampcount,plus,minus));

    }

    private class StampAdapter extends RecyclerView.Adapter<StampAdapter.StampHolder> {

        Context ct;
        int[] simages;
        String[] snames;
        int[] scount;
        String[] splus;
        String[] sminus;

        public StampAdapter(MainActivity mainActivity, int[] stampimages, String[] stampnames, int[] stampcount, String[] plus, String[] minus) {

            this.ct=mainActivity;
            this.simages=stampimages;
            this.snames=stampnames;
            this.scount=stampcount;
            this.splus=plus;
            this.sminus=minus;

        }

        @Override
        public StampAdapter.StampHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v= LayoutInflater.from(ct).inflate(R.layout.row,parent,false);
            return new StampHolder(v);
        }

        @Override
        public void onBindViewHolder(StampAdapter.StampHolder holder, int position) {

            holder.iv.setImageResource(simages[position]);
            holder.ntv.setText(snames[position]);
            holder.ctv.setText(scount[position]);
            holder.p.setText(splus[position]);
            holder.m.setText(sminus[position]);
        }

        @Override
        public int getItemCount() {
            return stampimages.length;
        }

        public class StampHolder extends RecyclerView.ViewHolder {
            ImageView iv;
            TextView ntv,ctv;
            Button p,m;
            public StampHolder(View itemView) {
                super(itemView);
                iv=itemView.findViewById(R.id.image);
                ntv=itemView.findViewById(R.id.text);
                ctv=itemView.findViewById(R.id.count);
                p=itemView.findViewById(R.id.plus);
                m=itemView.findViewById(R.id.minus);
            }
        }
    }
}
