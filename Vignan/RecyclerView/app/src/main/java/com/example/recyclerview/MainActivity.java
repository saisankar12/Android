package com.example.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv;
    int[] versions={R.drawable.cupcake,R.drawable.donut,
                    R.drawable.donut,R.drawable.gingerbread,
                    R.drawable.honeycomb,R.drawable.icecreamsandwich,
                    R.drawable.jellybean,R.drawable.kitkat,
                    R.drawable.lollipop,R.drawable.marshmallow,
                    R.drawable.nougat,R.drawable.oreo};
    String[] versionnames={"cupcake","donut","donut","gingerbread","honeycomb",
                            "icecreamsandwich","jellybean","kitkat","lollipop",
                        "marshmallow","nougat","oreo"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv=findViewById(R.id.recycler);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new VersionAdapter(this,versions,versionnames));

    }
}
