package com.example.saisankar.moviedb;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saisankar.moviedb.database.MovieInfo;
import com.example.saisankar.moviedb.database.MovieViewModel;
import com.example.saisankar.moviedb.utilities.InternetUtilities;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetails extends AppCompatActivity {

    String[] moviedata;
    @BindView(R.id.movie_poster)
    ImageView mposter;
    @BindView(R.id.movie_backdrop)
    ImageView mBackdrop;
    @BindView(R.id.movie_title)
    TextView mTitle;
    @BindView(R.id.movie_release_date)
    TextView mRelease;
    @BindView(R.id.movie_vote_average)
    TextView mVote;
    @BindView(R.id.movie_overview)
    TextView mOverview;
    @BindView(R.id.favorite)
    ImageView addFav;

    boolean favorite;
    MovieViewModel viewModel;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    static final String NAME="com.example.saisankar.moviedb";
    ArrayList<MovieInfo> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);
        moviedata = getIntent().getStringArrayExtra("moviedetails");

        viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        Toast.makeText(this, "" + viewModel.checkFav(Integer.parseInt(moviedata[6])), Toast.LENGTH_SHORT).show();

        sp=getSharedPreferences(NAME,MODE_PRIVATE);
        editor=sp.edit();
        list=new ArrayList<>();

        if (viewModel.checkFav(Integer.parseInt(moviedata[6]))) {
            addFav.setImageResource(R.drawable.fav);
            favorite = true;
        }
        //URL s = InternetUtilities.buildImageUrl(moviedata[0].substring(1));
        Picasso.with(this).load( moviedata[0]).into(mposter);
        mTitle.setText(moviedata[1]);
        // URL s1 = InternetUtilities.buildImageUrl(moviedata[2].substring(1));
        Picasso.with(this).load( moviedata[2]).into(mBackdrop);
        mRelease.setText(moviedata[3]);
        mVote.setText(moviedata[4]);
        mOverview.setText(moviedata[5]);


        addFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(moviedata[6]);
                MovieInfo movieInfo = new MovieInfo();
                movieInfo.setMposter_path(moviedata[0]);
                movieInfo.setMtitle(moviedata[1]);
                movieInfo.setMbackdrop_path(moviedata[2]);
                movieInfo.setMrelease_date(moviedata[3]);
                movieInfo.setMoverview(moviedata[5]);
                movieInfo.setMvote_average(moviedata[4]);
                movieInfo.setMid(id);
                list.add(movieInfo);

                StringBuffer buffer=new StringBuffer();
                int counter=1;

                for (int i=0;i<list.size();i++){
                 buffer.append(counter+""+list.get(i).getMtitle()+"\n");
                 counter++;
                }

                String data=buffer.toString();

                editor.putString("sai",data);
                editor.apply();

                Intent wi=new Intent(MovieDetails.this,MovieWidget.class);
                wi.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

                int[] ids=AppWidgetManager.getInstance(MovieDetails.this).getAppWidgetIds(
                        new ComponentName(getApplication(),MovieWidget.class));
                wi.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
                sendBroadcast(wi);

                if (viewModel.checkFav(id)) {
                    MainActivity.movieViewModel.delete(movieInfo);
                    addFav.setImageResource(R.drawable.unfav);
                    // favorite=!favorite;
                } else {
                    MainActivity.movieViewModel.insert(movieInfo);
                    addFav.setImageResource(R.drawable.fav);
                    //favorite=!favorite;
                }
            }
        });
    }

}
