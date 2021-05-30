package com.google.developer.bugmaster;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class InsectDetailsActivity extends AppCompatActivity {
    ImageView imageView;
    TextView name,scientific_name,classification;
    RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO: Implement layout and display insect details

        setContentView(R.layout.insect_details);
        imageView=(ImageView)findViewById(R.id.img);
        name=(TextView)findViewById(R.id.title);
        scientific_name=(TextView)findViewById(R.id.scientific_name);
        classification=(TextView)findViewById(R.id.classification);
        ratingBar=(RatingBar)findViewById(R.id.danger);
        Bundle bundle=getIntent().getExtras();
        String frnd_name=bundle.getString("name");
        String sci_name=bundle.getString("scientific_name");
        String image_asset=bundle.getString("image");
        String classi=bundle.getString("classification");
        int rating=bundle.getInt("rating");
        ratingBar.setRating(rating);
        try {
            InputStream inputStream=getAssets().open(image_asset);
            Drawable d=Drawable.createFromStream(inputStream,null);
            imageView.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }
        name.setText(frnd_name);
        classification.append(classi);
        scientific_name.setText(sci_name);


    }
}
