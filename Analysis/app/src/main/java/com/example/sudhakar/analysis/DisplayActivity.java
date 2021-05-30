package com.example.sudhakar.analysis;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private ProgressDialog progressDialog;
    private PieChart pieChart;
    ArrayList<Entry> yvalues;
    Current current;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        progressDialog=new ProgressDialog(this);
        pieChart = (PieChart) findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);
        pieChart.animateY(5000);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("data");
        progressDialog.setMessage("Loading Data.......");
        progressDialog.show();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot childSnapshot:dataSnapshot.getChildren()) {
                    progressDialog.dismiss();
                    current= childSnapshot.getValue(Current.class);
                    ((TextView)findViewById(R.id.humiditytv)).setText("Humidity : "+current.getHumidity());
                    ((TextView)findViewById(R.id.temparaturetv)).setText("Temparature : "+current.getTemparature());
                    //Toast.makeText(getApplicationContext(), current.toString(), Toast.LENGTH_LONG).show();
                    // IMPORTANT: In a PieChart, no values (Entry) should have the same
                    // xIndex (even if from different DataSets), since no values can be
                    // drawn above each other.

                    ArrayList<Entry> yvalues = new ArrayList<Entry>();
                    yvalues.add(new Entry(current.getPh(), 0));
                    yvalues.add(new Entry(100 - current.getPh(), 1));

                    ArrayList<String> xVals = new ArrayList<String>();
                    xVals.add("pH");
                    xVals.add("");

                    PieDataSet dataSet = new PieDataSet(yvalues, "PH Percentage");
                    dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
                    PieData data = new PieData(xVals, dataSet);

                    data.setValueTextSize(13f);
                    data.setValueTextColor(Color.DKGRAY);
                    data.setValueFormatter(new PercentFormatter());
                    pieChart.setData(data);
                    pieChart.saveToGallery("/sd/mychart.jpg", 85);
                    pieChart.invalidate();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                // Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // show menu when menu button is pressed
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // display a message when a button was pressed
        if (item.getItemId() == R.id.logout) {
            mAuth.signOut();
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //startActivity(new Intent(getApplicationContext(),ServiceActivity.class));
        finish();
        System.exit(1);
    }
}
