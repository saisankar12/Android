package com.example.saisankar.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    ImageView bookimage;
    TextView booktitle,bookid,bookdec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        bookimage=findViewById(R.id.bookimage);
        booktitle=findViewById(R.id.booktitle);
        bookdec=findViewById(R.id.bookdescription);
        bookid=findViewById(R.id.bookid);

        String[] s=getIntent().getStringArrayExtra("data");

        bookid.setText(s[0]);
        //bookimage.setImageResource(s[1]);
        Picasso.with(this).load(s[1]).into(bookimage);
        booktitle.setText(s[2]);
        bookdec.setText(s[3]);
    }
}
