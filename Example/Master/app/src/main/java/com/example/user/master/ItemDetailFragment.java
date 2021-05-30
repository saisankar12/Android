package com.example.user.master;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.user.master.Models.MyStepsModel;
import com.example.user.master.dummy.DummyContent;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import static com.example.user.master.R.id.exoplayerView;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */


public class ItemDetailFragment extends Fragment {


    ImageView textView;
    SimpleExoPlayer exoPlayer;
    SimpleExoPlayerView simpleExoPlayerView;
    MyStepsModel myStepsModel;
    long currentposition;
    boolean playwhenready;
    String url;
    Uri videoUri;


    public static final String ARG_ITEM_ID = "item_id";
    private DummyContent.DummyItem mItem;
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        textView = rootView.findViewById(R.id.txtDesc);
        simpleExoPlayerView = rootView.findViewById(R.id.exoplayerView);

       /* if (savedInstanceState == null){
            //startPlayer();
        }
        else  {
            currentposition = savedInstanceState.getLong("currentpos");
            playwhenready = savedInstanceState.getBoolean("playwhenready");

            exoPlayer.seekTo(currentposition);
            exoPlayer.setPlayWhenReady(playwhenready);
        }
        startPlayer();*/


        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);

        if (getArguments().getParcelable("steps") != null){
            myStepsModel = getArguments().getParcelable("steps");

            if (appBarLayout != null) {
                appBarLayout.setTitle(myStepsModel.getShortdes());
            }
            if (myStepsModel.getStepdesc() != null) {
                ((TextView) rootView.findViewById(R.id.item_detail))
                        .setText(myStepsModel.getStepdesc());
            }
            if (!myStepsModel.getStepsVideo().isEmpty()){
                url = myStepsModel.getStepsVideo();
                startPlayer();
                Toast.makeText(activity, ""+url, Toast.LENGTH_SHORT).show();
               Log.i("videourl",url);

            }else if (!myStepsModel.getStepThumbnil().isEmpty()){
                url = myStepsModel.getStepThumbnil();
                Toast.makeText(activity, ""+url, Toast.LENGTH_SHORT).show();
                startPlayer();
                 if (myStepsModel.getStepThumbnil().isEmpty()){
                    Glide.with(this).load(url).into(textView);
                }
            }
        }else {
            Toast.makeText(activity, "No Steps Found", Toast.LENGTH_SHORT).show();
        }

        /*if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

        }*/

        if (savedInstanceState!=null){
            currentposition=savedInstanceState.getLong("currentpos");
            exoPlayer.seekTo(currentposition);
            playwhenready=savedInstanceState.getBoolean("playwhenready");
            exoPlayer.setPlayWhenReady(playwhenready);
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (exoPlayer!=null){
            outState.putLong("currentpos",exoPlayer.getContentPosition());
            outState.putBoolean("playwhenready",exoPlayer.getPlayWhenReady());
        }
    }
    public void startPlayer(){
        if (exoPlayer == null && url != null){
            BandwidthMeter bandwidthMeter=new DefaultBandwidthMeter();
            TrackSelector trackSelector=new
                    DefaultTrackSelector(new
                    AdaptiveTrackSelection.Factory(bandwidthMeter));
            exoPlayer = ExoPlayerFactory
                    .newSimpleInstance(getActivity()
                            ,trackSelector);
            videoUri=Uri.parse(url);
            //videoUri = Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4");
            DefaultHttpDataSourceFactory sourceFactory=
                    new DefaultHttpDataSourceFactory("EXOPlayerDemo");
            ExtractorsFactory extractorsFactory= new DefaultExtractorsFactory();
            MediaSource mediaSource =new ExtractorMediaSource(videoUri,
                   sourceFactory,extractorsFactory,null,null);
            simpleExoPlayerView.setPlayer(exoPlayer);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
        }


       /* videoUri = Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4");
          //  videoUrl = Uri.parse(url);
            //Log.i("vidiourl",url);
        String useragent = Util.getUserAgent(getContext(),"EXOPlayerDemo");


        exoPlayer.seekTo(currentposition);*/


    }
    /*public void stopPlayer(){
        if (exoPlayer != null){
            currentposition = exoPlayer.getCurrentPosition();
            exoPlayer.release();
            exoPlayer.stop();
            exoPlayer = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }*/

    @Override
    public void onPause() {
        super.onPause();
        if (exoPlayer!=null){
            exoPlayer.stop();
            exoPlayer.release();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (exoPlayer!=null){
            exoPlayer.stop();
            exoPlayer.release();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (exoPlayer!=null){
            exoPlayer.stop();
            exoPlayer.release();
        }
    }
}
