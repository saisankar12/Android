package com.example.hp.capstoneproject.fragments;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.capstoneproject.MainActivity;
import com.example.hp.capstoneproject.R;
import com.example.hp.capstoneproject.TechnologyFragment;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NewsDetailActivity extends AppCompatActivity
{
    @InjectView(R.id.detailImg)
    ImageView imgview;
    @InjectView(R.id.detail_title)
    TextView det_title;
    @InjectView(R.id.detail_overview)
    TextView detail_overview;
    @InjectView(R.id.detail_url)
    TextView detail_url;

    String poster,title,desc,url;
    public static final String Title="title";
    public static final String Image="image";
    public static final String Url="linkurl";
    public static final String Desc="description";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_news_detail);
        ButterKnife.inject(this);

        if (savedInstanceState != null) {
            poster = savedInstanceState.getString(Image);
            title = savedInstanceState.getString(Title);
            url = savedInstanceState.getString(Url);
            desc = savedInstanceState.getString(Desc);
        } else {
            Intent getIntentData = getIntent();
            if (getIntentData.hasExtra(Title)) {
                poster = getIntent().getStringExtra(Image);
                title = getIntent().getStringExtra(Title);
                url = getIntent().getStringExtra(Url);
                desc = getIntent().getStringExtra(Desc);

            }
            det_title.setText(title);
            detail_overview.setText(desc);
            detail_url.setText(url);
            //det_vote.setText(vote);
            Picasso.with(this).load(poster).placeholder(R.drawable.noprview).into(imgview);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id=item.getItemId();
        if (id==android.R.id.home){
        /*getSupportFragmentManager().beginTransaction().
                replace(R.id.containerId, new TechnologyFragment()).commit();*/
        navigateUpTo(new Intent(this, MainActivity.class));
        return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Title,title);
        outState.putString(Desc,desc);
        outState.putString(Image,poster);
        outState.putString(Url,url);
    }

    @Override
    protected void onResume() {
        super.onResume();
        det_title.setText(title);
        detail_overview.setText(desc);
        detail_url.setText(url);
        //det_vote.setText(vote);
        Picasso.with(this).load(poster).placeholder(R.drawable.noprview).into(imgview);
    }
}
