package com.example.saisankar.exoplayer;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.browse.MediaBrowser;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
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
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

public class MainActivity extends AppCompatActivity {

    SimpleExoPlayerView exoView;
    SimpleExoPlayer exoPlayer;
   // public static int flag;
    ConstraintLayout layout;
    private long currentPosition;
    private boolean playWhenReady;

    String v = "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //layout=findViewById(R.id.sai);
        exoView = findViewById(R.id.exoplayer);
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelector trakSelecter = new DefaultTrackSelector
                (new AdaptiveTrackSelection.Factory(bandwidthMeter));
        exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trakSelecter);
        Uri uri = Uri.parse(v);
        DefaultHttpDataSourceFactory
                dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        MediaSource mediaSource = new ExtractorMediaSource(uri
                , dataSourceFactory, extractorsFactory, null, null);
        exoView.setPlayer(exoPlayer);
        exoPlayer.prepare(mediaSource);
        exoPlayer.setPlayWhenReady(true);

        if (savedInstanceState!=null){
         //   flag=savedInstanceState.getInt("flag");
            currentPosition=savedInstanceState.getLong("current_Position");
            exoPlayer.seekTo(currentPosition);
            playWhenReady=savedInstanceState.getBoolean("play_back");
            exoPlayer.setPlayWhenReady(playWhenReady);
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)
                    exoView.getLayoutParams();
            params.width= Resources.getSystem().getDisplayMetrics().widthPixels;
            params.height=Resources.getSystem().getDisplayMetrics().heightPixels;
            exoView.setLayoutParams(params);
        }else if (newConfig.orientation==Configuration.ORIENTATION_PORTRAIT){
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) exoView.getLayoutParams();
            params.width= Resources.getSystem().getDisplayMetrics().widthPixels;
            params.height=600;
            exoView.setLayoutParams(params);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
       // outState.putInt("flag",flag);
        if (exoPlayer!=null){
        outState.putLong("current_Position",exoPlayer.getCurrentPosition());
        outState.putBoolean("play_back",exoPlayer.getPlayWhenReady());
            //Toast.makeText(this, ""+flag, Toast.LENGTH_SHORT).show();
        }
    }
}
