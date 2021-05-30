package com.example.getdevicelocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    FusedLocationProviderClient client;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.textView);
        client = LocationServices.getFusedLocationProviderClient(this);
        client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),
                                                            location.getLongitude(),1);

                    String latitude = String.valueOf(addresses.get(0).getLatitude());
                    String longitude = String.valueOf(addresses.get(0).getLongitude());
                    String country = addresses.get(0).getCountryName();
                    String locality = addresses.get(0).getLocality();
                    String postalcode = addresses.get(0).getPostalCode();
                    String add = addresses.get(0).getAddressLine(0);

                    tv.setText("Latitude :" +latitude+"\n"+"Longitude"+longitude+"\n"+"Country :"+country+
                            "\n"+"Locality :"+locality+"\n"+"Postal Code :"+postalcode+"\n"+"Address :"+add);

                    Log.i("saisankar",latitude+","+longitude);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
