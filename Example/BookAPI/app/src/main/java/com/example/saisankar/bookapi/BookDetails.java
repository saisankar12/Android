package com.example.saisankar.bookapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class BookDetails extends AppCompatActivity {
    ImageView bookimage;
    TextView booktitle,bookid,bookdec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        bookimage=findViewById(R.id.bookimage);
        booktitle=findViewById(R.id.booktitle);
        bookdec=findViewById(R.id.bookdescription);
        bookid=findViewById(R.id.bookid);
        String[] i=getIntent().getStringArrayExtra("details");
        bookid.setText(i[0]);
        booktitle.setText(i[1]);
        Picasso.with(this).load(i[2]).into(bookimage);
        bookdec.setText(i[3]);


    }
}
