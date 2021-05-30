package com.example.user.master;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.user.master.Models.MyIngredientModel;
import com.example.user.master.Models.MyModel1;
import com.example.user.master.Models.MyStepsModel;
import com.example.user.master.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    String bakingUrl = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    RecyclerView recyclerView;
    ArrayList<MyModel1> model1ArrayList;
    ArrayList<MyIngredientModel> myIngredientModels;
    ArrayList<MyStepsModel> myStepsModels;
    ShimmerFrameLayout shimmerFrameLayout;
    String q,m,idd,shortDesc,desc,videoUrl,thumb,ing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        model1ArrayList = new ArrayList<>();

        shimmerFrameLayout = findViewById(R.id.ShimmerLayout);

        recyclerView = findViewById(R.id.myNameRecycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyNAmeAdapter(this,model1ArrayList));
        new NameAsyc().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmerAnimation();
    }

    @Override
    protected void onPause() {
        shimmerFrameLayout.stopShimmerAnimation();
        super.onPause();
    }


    private class NameAsyc extends AsyncTask<Void,Void,String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(bakingUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                InputStream inputStream = httpURLConnection.getInputStream();
                Scanner scanner = new Scanner(inputStream);
                scanner.useDelimiter("\\A");
                if (scanner.hasNext()){
                    return scanner.next();
                }else {
                    return null;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            shimmerFrameLayout.stopShimmerAnimation();
            shimmerFrameLayout.setVisibility(View.GONE);
            super.onPostExecute(s);
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(s);
                for (int i = 0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String ingArray = String.valueOf(jsonObject.getJSONArray("ingredients"));
                    String stepsArray = String.valueOf(jsonObject.getJSONArray("steps"));
                    String namee = jsonObject.getString("name");
                    String servings = jsonObject.getString("servings");
                    model1ArrayList.add(new MyModel1(namee,servings,ingArray,stepsArray));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
