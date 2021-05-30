package com.example.hp.capstoneproject.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.hp.capstoneproject.R;
import com.example.hp.capstoneproject.TechnologyFragment;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class HomeFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener
{
    @InjectView(R.id.slider)
    SliderLayout sliderLayout;
    HashMap<String, Integer> hashMap;
    @InjectView(R.id.card_playerinfo)
    CardView playerinfo;
    @InjectView(R.id.card_sportnews)
    CardView sportsnews;
    @InjectView(R.id.card_timetable)
    CardView timetable;
    @InjectView(R.id.card_upcoming)
    CardView upcoming;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        //sliderLayout=view.findViewById(R.id.slider);
        ButterKnife.inject(this,view);
        file_maps.put("Information about Technology",R.drawable.mobiletechnology);
        file_maps.put("Infromation about Entertainmnt",R.drawable.entertainment);
        file_maps.put("Information about CrickInfo", R.drawable.cricket);
        file_maps.put("Information about Technolgy",R.drawable.technology);
        file_maps.put("Information about Genera News", R.drawable.portada);
        file_maps.put("Infromation about Entertainment",R.drawable.entertainmentndustry);
        file_maps.put("Information about all Sports",R.drawable.sportss);
        file_maps.put("Information about Cricket", R.drawable.cricket);
        file_maps.put("Information about General News", R.drawable.generalinf);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(3000);
        sliderLayout.addOnPageChangeListener(this);

        upcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().
                        replace(R.id.containerId, new UpcomingMatchesFragment()).commit();
            }
        });

        playerinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().
                        replace(R.id.containerId, new PlayerInfo()).commit();
            }
        });

        timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().
                        replace(R.id.containerId, new ScheduleMatchFragment()).commit();
            }
        });

        sportsnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().
                        replace(R.id.containerId, new TechnologyFragment()).commit();
            }
        });
        return view;
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


}
