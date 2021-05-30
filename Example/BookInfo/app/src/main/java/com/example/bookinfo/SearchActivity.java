package com.example.bookinfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.libizo.CustomEditText;

public class SearchActivity extends AppCompatActivity {


    CustomEditText editText;
    String  stringQuery;
    AdView adView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        editText=findViewById(R.id.edit);
        MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713");

        adView=findViewById(R.id.adView);
        AdRequest adRequest=new AdRequest.Builder().build();
        adView.loadAd(adRequest);

    }

    public void search(View view) {
        stringQuery=editText.getText().toString();
        Toast.makeText(this, stringQuery, Toast.LENGTH_SHORT).show();
        if(stringQuery.isEmpty()){
            Toast.makeText(this, "Enter Book Name", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent=new Intent(SearchActivity.this,Books.class);
            intent.putExtra("query",stringQuery);
            startActivity(intent);
        }

    }
}
