package app.balotsav.com.balotsavslider;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    ImageView er,reg,anounce,res;
    private SliderLayout sliderLayout;
    private HashMap<String, String> Hash_file_maps;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ActionBar actionBar = ((DashBoard)getActivity()).getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.vvit_balotsav));
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        View rootview=inflater.inflate(R.layout.fragment_home, container,false);
        // Inflate the layout for this fragment
        sliderLayout = rootview.findViewById(R.id.slider);
        Hash_file_maps = new HashMap<>();
        er=rootview.findViewById(R.id.e_register);
        reg=rootview.findViewById(R.id.e_Registered);
        anounce=rootview.findViewById(R.id.announce);
        res=rootview.findViewById(R.id.results);
        er.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment =new EventDisplayFragment();
                loadFragment(fragment);

            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new RegisteredEventsFragment();
                loadFragment(fragment);
            }
        });
        anounce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new AnnouncementFragment();
                loadFragment(fragment);
            }
        });
        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ResultsFragment();
                loadFragment(fragment);
            }
        });

        Hash_file_maps.put("0", "https://www.balotsav.in/images/Gallery2017/DSC_5074_compressed.jpg");
        Hash_file_maps.put("1", "https://www.balotsav.in/images/Gallery2017/DSC_5118_compressed.jpg");
        Hash_file_maps.put("2", "https://www.balotsav.in/images/Gallery2017/DSC_5174_compressed.jpg");
        Hash_file_maps.put("3", "https://www.balotsav.in/images/Gallery2017/DSC_5185_compressed.jpg");
        Hash_file_maps.put("4", "https://www.balotsav.in/images/Gallery2017/KNK_1655_compressed.jpg");


        Hash_file_maps.put("5", "https://www.balotsav.in/images/Gallery2017/KNK_1657_compressed.jpg");
        Hash_file_maps.put("6", "https://www.balotsav.in/images/Gallery2017/KNK_1684_compressed.jpg");
        Hash_file_maps.put("7", "https://www.balotsav.in/images/Gallery2017/KNK_1706_compressed.jpg");
        Hash_file_maps.put("8", "https://www.balotsav.in/images/Gallery2017/_DSC1896_-_Copy_compressed.jpg");
        Hash_file_maps.put("9", "https://www.balotsav.in/images/Gallery2017/_MG_0112_compressed.jpg");

        Hash_file_maps.put("10", "https://www.balotsav.in/images/Gallery2017/_MG_0194_compressed.jpg");
        Hash_file_maps.put("11","https://www.balotsav.in/images/Gallery2017/_MG_2660_compressed.jpg");
        Hash_file_maps.put("12", "https://www.balotsav.in/images/Gallery2017/_MG_0259_compressed.jpg");
        Hash_file_maps.put("13", "https://www.balotsav.in/images/Gallery2017/_MG_0276_compressed.jpg");


        Hash_file_maps.put("14", "https://www.balotsav.in/images/Gallery2017/_MG_0390_compressed.jpg");
        Hash_file_maps.put("15", "https://www.balotsav.in/images/Gallery2017/_MG_0413_compressed.jpg");
        Hash_file_maps.put("16", "https://www.balotsav.in/images/Gallery2017/_MG_0420_compressed.jpg");
        Hash_file_maps.put("17", "https://www.balotsav.in/images/Gallery2017/_MG_0467_compressed.jpg");

        for (String name : Hash_file_maps.keySet()) {

            TextSliderView textSliderView = new TextSliderView(getActivity());
            textSliderView
                    .description(name)
                    .image(Hash_file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Stack);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(3000);


        return rootview;
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getActivity().getSupportFragmentManager().popBackStack();
            getFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

}
