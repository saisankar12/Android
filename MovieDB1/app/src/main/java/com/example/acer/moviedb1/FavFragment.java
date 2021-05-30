package com.example.acer.moviedb1;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<MovieModel> arrayList;
    LinearLayout linearLayout;
    MovieViewHolder movieViewHolder;
    public FavFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v=inflater.inflate(R.layout.fragment_fav, container, false);
        recyclerView=v.findViewById(R.id.recylerfav);
        linearLayout=v.findViewById(R.id.linear);
        movieViewHolder=ViewModelProviders.of(getActivity()).get(MovieViewHolder.class);
        movieViewHolder.getAllMovie.observe(getActivity(), new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(@Nullable List<MovieModel> movieModels) {
                if((movieModels==null)|| (movieModels.size()==0))
                {
                   // snackBar("No Favourites",v);

                    Snackbar snackBar = Snackbar.make(getActivity().findViewById(android.R.id.content),
                            "No Fav", Snackbar.LENGTH_LONG);
                    snackBar.show();
                }
                else {
                    recyclerView.setAdapter(new MovieAdapter(getActivity(), movieModels));
                }
            }
        });
        arrayList=new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        return v;
    }

    public void snackBar(String msg, View v) {
        Snackbar.make(v, msg, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
    /*  if ((superModels == null) || (superModels.size() == 0)) {
        Snackbar.make(linearLayout,getString(R.string.data),Snackbar.LENGTH_LONG).show();
        //Toast.makeText(MainActivity.this, getString(R.string.data), Toast.LENGTH_SHORT).show();
    } else {
        recyclerView.setAdapter(new SuperAdapter(getApplication(), superModels));
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
    }*/

}
