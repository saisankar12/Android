package com.example.saisankar.hillfestival;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private SliderLayout sliderLayout;
    private HashMap<String, String> Hash_file_maps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sliderLayout = findViewById(R.id.slider);
        Hash_file_maps = new HashMap<>();

        Hash_file_maps.put("1", "https://kotappakondablog.files.wordpress.com/2018/11/08-11-2019-1.jpg?w=1000&h=");
        Hash_file_maps.put("2", "https://kotappakondablog.files.wordpress.com/2018/11/08-11-2019-5.jpg?w=1000&h=");
        Hash_file_maps.put("3", "https://kotappakondablog.files.wordpress.com/2018/11/08-11-2019-6.jpg?w=1000&h=");
        Hash_file_maps.put("4", "https://kotappakondablog.files.wordpress.com/2018/11/08-11-2019-10.jpg?w=1000&h=");
        Hash_file_maps.put("5", "https://kotappakondablog.files.wordpress.com/2018/11/08-11-2019-12.jpg?w=1000&h=");
        Hash_file_maps.put("6", "https://kotappakondablog.files.wordpress.com/2018/11/08-11-2019-16.jpg?w=1000&h=");
        Hash_file_maps.put("7", "https://kotappakondablog.files.wordpress.com/2018/11/08-11-2019-22.jpg?w=1000&h=");
        Hash_file_maps.put("8", "https://kotappakondablog.files.wordpress.com/2016/03/pics-25-dec-2015.jpg?w=1000&h=");
        Hash_file_maps.put("9", "https://kotappakondablog.files.wordpress.com/2016/11/pics_29-mar_2016-2.jpg?w=1000&h=");
        Hash_file_maps.put("10", "https://kotappakondablog.files.wordpress.com/2016/11/pics_29-mar_2016.jpg?w=1000&h=");
        Hash_file_maps.put("11", "https://kotappakondablog.files.wordpress.com/2018/11/08-11-2019-21.jpg?w=1000&h=");
        Hash_file_maps.put("12", "https://gotirupati.com/wp-content/uploads/2016/06/Kotappakonda-temple-timings-Copy.jpg");
        Hash_file_maps.put("13", "https://www.rvatemples.com/wp-content/uploads/2018/02/chilkur-balaji-temple-hyderabad.jpg");
        Hash_file_maps.put("14", "https://2.bp.blogspot.com/-oyxMsIY0EA0/WKAXND17r4I/AAAAAAAACHs/TqZkRzLkWcwLyD4wzU01tt4YiNFLjlPwQCLcB/s1600/Kotappakonda%2BSri%2BTrikoteswara%2BSwamy%2BTemple.JPG");

        for (String name : Hash_file_maps.keySet()) {

            TextSliderView textSliderView = new TextSliderView(this);
            textSliderView
                    .description(name)
                    .image(Hash_file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Stack);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(3000);
    }

    public void gallery(View view) {
        startActivity(new Intent(MainActivity.this,GalleryActivity.class));
    }

    public void gettinghere(View view) {
        startActivity(new Intent(MainActivity.this,GettingHere.class));
    }
}
