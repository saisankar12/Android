package in.apssdc.attendance.activities;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import in.apssdc.attendance.R;
import in.apssdc.attendance.common.CheckInternetGps;
import in.apssdc.attendance.common.CheckNetworkConnection;
import in.apssdc.attendance.dialogs.MyCustomProgressDialog;
import in.apssdc.attendance.model.ContactModel;
import in.apssdc.attendance.model.EmployeeDetails;
import in.apssdc.attendance.model.Response;
import in.apssdc.attendance.receivers.MyScheduleReceiver;
import in.apssdc.attendance.services.GeofenceTrasitionService;

public class GeofenceLocationActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, OnMapReadyCallback,ResultCallback<Status> {

    //Component Declaration
    ImageView iv_marker;
    Button bt_next;
    Animation animation;
    ProgressBar progressBar;
    Geocoder geocoder;
    EmployeeDetails employeeDetails;
    //Google map components
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    SupportMapFragment sMapFragment;
    GoogleMap gmap;
    TextView tv_dateTime;
    Marker marker,geoFenceMarker;
    GeofencingRequest.Builder builder;
    PendingIntent geoFencePendingIntent;
    private Circle geoFenceLimits;
    CircleOptions circleOptions;
    CameraPosition INIT;
    //Variable Declaration
    String place_of_work, workLocation,current_address,mLastUpdateTime;
    StringBuilder stringBuilder;
    float GEOFENCE_RADIUS;
    double lat, lng,latitude,longitude;
    private static final long INTERVAL = 1000 * 10;//5 seconds
    private static final long FASTEST_INTERVAL = 1000 * 10;
    private static final int DISPLACEMENT=5;
    private final int GEOFENCE_REQ_CODE=0;
    private static final String NOTIFICATION_MSG = "NOTIFICATION MSG";
    private static final String GEOFENCE_REQ_ID = "My Geofence";
    //session Time
    BroadcastReceiver myReceiver;
    int requestCode=1;
    PendingIntent pIntent;
    AlarmManager aManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //check GooglePlay Services
        try{
            if(!isGooglePlayServicesAvailable()) {
                Toast.makeText(getApplicationContext(), "Dont have Google Play Services", Toast.LENGTH_LONG).show();
                finish();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
        setContentView(R.layout.activity_geolocation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        sMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        gmap = sMapFragment.getMap();
        sMapFragment.getMapAsync(this);
        gmap.getUiSettings().setZoomControlsEnabled(false);
        gmap.getUiSettings().setMapToolbarEnabled(false);
        gmap.getUiSettings().setMyLocationButtonEnabled(false);
        gmap.getUiSettings().setCompassEnabled(false);
        gmap.setMyLocationEnabled(false);
        geocoder = new Geocoder(GeofenceLocationActivity.this);
        stringBuilder = new StringBuilder();
        tv_dateTime=(TextView)findViewById(R.id.tv_date);
        iv_marker = (ImageView) findViewById(R.id.marker);
        iv_marker.setVisibility(View.VISIBLE);
        bt_next = (Button) findViewById(R.id.bt_next);
        bt_next.setEnabled(false);
        bt_next.setBackgroundColor(getResources().getColor(R.color.disable));
        animation = new TranslateAnimation(
                TranslateAnimation.ABSOLUTE, 0.1f,
                TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0.1f);
        animation.setDuration(400);
        animation.setRepeatCount(-1);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setInterpolator(new LinearInterpolator());
        iv_marker.startAnimation(animation);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.GONE);
        //session
        IntentFilter filter=new IntentFilter();
        filter.addAction("siva.ACTION_FINISH");
        myReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //return the result to MainActivity
                Intent resultIntent=new Intent();
                resultIntent.putExtra("result",requestCode);
                GeofenceLocationActivity.this.setResult(RESULT_OK,resultIntent);
                finish();
            }
        };
        registerReceiver(myReceiver,filter);
        //Data coming from previous activity
        try {
            employeeDetails = (EmployeeDetails) getIntent().getSerializableExtra("emp_object");
            place_of_work = employeeDetails.getPlaceofwork();
            if (place_of_work.equalsIgnoreCase("PMDC")) {
                workLocation = "4G Identity Solutions Pvt Ltd,330-331,Road Number 79, Film Nagar,Jubilee Hills,Hyderabad,Telangana 500033";
                GEOFENCE_RADIUS =300f;//in meters;
            }else if (place_of_work.equalsIgnoreCase("VijayawadaOffice")) {
                workLocation = "NTR administrative block,Pandit Nehru Bus Station,Vijayawada,Andhra Pradesh 520013";
                GEOFENCE_RADIUS =500f;//in meters;
            }else if (place_of_work.equalsIgnoreCase("SDC")) {
                workLocation = employeeDetails.getAssigned_to() + "," + "Andhra Pradesh";
                GEOFENCE_RADIUS =1000f;//in meters;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        if (CheckNetworkConnection.isConnectionAvailable(GeofenceLocationActivity.this)) {
            if (gmap != null) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                try{
                    new GetLocationTask().execute(workLocation);
                }catch (Exception e){e.printStackTrace();}
            }
        }else{
            new CheckInternetGps(GeofenceLocationActivity.this).callInternetAlert();
        }

        //Click event for continue button
        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                SharedPreferences pref = getApplicationContext().getSharedPreferences("Notification", Context.MODE_PRIVATE);
                String status = pref.getString("key", "");
                if (status.equalsIgnoreCase("You just enter into Geofencing location")) {
                    Intent i = new Intent();
                    i.setComponent(new ComponentName(getApplicationContext(), HomeActivity.class));
                    i.putExtra("emp_object", employeeDetails);
                    startActivity(i);
                } else if (status.equalsIgnoreCase("You just exit from Geofencing location") || status.equalsIgnoreCase("You are out of Geofence")) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(GeofenceLocationActivity.this);
                    alertDialog.setMessage("Sorry! You are out of geofencing..");
                    alertDialog.setCancelable(false);
                    alertDialog.setIcon(R.drawable.red_geofence);
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            progressBar.setVisibility(View.GONE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                finishAffinity();
                                moveTaskToBack(true);
                            }else{
                                finish();
                                moveTaskToBack(true);
                            }
                        }
                    });
                    AlertDialog dialog = alertDialog.create();
                    dialog.show();
                }
            }
        });
    }
    @Override
    public void onConnected(Bundle bundle) {
        try{
            startLocationUpdates();
        }catch(Exception e){e.printStackTrace();}
    }
    @Override
    public void onConnectionSuspended(int i) {

    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //Log.d("==onConnectionFailed==", "Connection failed:" + connectionResult.toString());
    }
    @Override
    protected void onStart() {
        super.onStart();
        try{
            if (mGoogleApiClient != null) {
                mGoogleApiClient.connect();
            }
        }catch(Exception e){e.printStackTrace();}
    }
    @Override
    protected void onResume() {
        super.onResume();
        try{
            if(aManager!=null){
                aManager.cancel(pIntent);
            }
            if (mGoogleApiClient.isConnected()) {
                startLocationUpdates();
            }
        }catch(Exception e){e.printStackTrace();}
    }
    @Override
    protected void onPause() {
        super.onPause();
        try{
            Intent i=new Intent(getBaseContext(),MyScheduleReceiver.class);
            Bundle b=new Bundle();
            b.putInt("val",8);
            i.putExtras(b);
            pIntent=PendingIntent.getBroadcast(getBaseContext(),0,i,0);
            aManager=(AlarmManager)getSystemService(ALARM_SERVICE);
            Calendar c=Calendar.getInstance();
            c.setTimeInMillis(System.currentTimeMillis());//device current time
            c.add(Calendar.MINUTE,3);
            aManager.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pIntent);
            stopLocationUpdates();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        try{
            if (mGoogleApiClient.isConnected()) {
                mGoogleApiClient.disconnect();
            }
        }catch(Exception e){e.printStackTrace();}
    }
    @Override
    public void onLocationChanged(Location location) {
        // Assign the new location and Displaying the new location on UI
        SharedPreferences pref=getSharedPreferences("Mock", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
        Bundle extras =location.getExtras();
        boolean isMockLocation = extras != null && extras.getBoolean(FusedLocationProviderApi.KEY_MOCK_LOCATION, false);
        if(isMockLocation==false){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    bt_next.setEnabled(true);
                    bt_next.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
            },13000);
            mCurrentLocation = location;
            mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
            tv_dateTime.setText(mLastUpdateTime);
            try{
                updateUI();
            }catch (Exception e){e.printStackTrace();}
        }else{
            try{
                bt_next.setEnabled(false);
                bt_next.setBackgroundColor(getResources().getColor(R.color.disable));
                android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(GeofenceLocationActivity.this).create();
                alertDialog.setTitle("Fake GPS Provider");
                alertDialog.setMessage("Goto Developer options to disable the fake GPS...");
                alertDialog.setCancelable(false);
                alertDialog.setIcon(R.mipmap.ic_alert);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences pref=getSharedPreferences("Mock", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("location","mock");
                        editor.commit();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            finishAffinity();
                            moveTaskToBack(true);
                        }else{
                            finish();
                            moveTaskToBack(true);
                        }
                    }
                });
                alertDialog.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void updateUI() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (mCurrentLocation != null) {
            latitude = mCurrentLocation.getLatitude();
            longitude = mCurrentLocation.getLongitude();
            employeeDetails.setLatitude(latitude);
            employeeDetails.setLongitude(longitude);
            if(marker!=null){
                marker.setPosition(new LatLng(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude()));
            }else{
                MarkerOptions opt = new MarkerOptions();
                opt.position(new LatLng(latitude,longitude));
                opt.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                if (geocoder.isPresent()) {
                    try {
                        List<Address> list = geocoder.getFromLocation(latitude,longitude, 1);
                        if (list != null && list.size() > 0) {
                            Address address = list.get(0);
                            StringBuffer str = new StringBuffer();
                            str.append(address.getSubLocality() + ",");
                            str.append(address.getAddressLine(0) + ",");
                            str.append(address.getLocality() + ",");
                            current_address = str.toString();
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                opt.title(current_address);
                marker=gmap.addMarker(opt);
            }
            INIT = new CameraPosition.Builder().target(new LatLng(latitude,longitude)).zoom(15.5F).build();
            gmap.animateCamera(CameraUpdateFactory.newCameraPosition(INIT));
            iv_marker.clearAnimation();
            iv_marker.setVisibility(View.GONE);
            gmap.setPadding(0,0,0,100);
            gmap.getUiSettings().setZoomControlsEnabled(true);
            gmap.getUiSettings().setMyLocationButtonEnabled(true);
            gmap.getUiSettings().setCompassEnabled(true);
            gmap.setMyLocationEnabled(true);
            markerForGeofence(new LatLng(lat,lng));
            startGeofence();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    double distance;
                    Location locationA = new Location("");
                    locationA.setLatitude(latitude);
                    locationA.setLongitude(longitude);
                    Location locationB = new Location("");
                    locationB.setLatitude(lat);
                    locationB.setLongitude(lng);
                    distance = locationA.distanceTo(locationB);//in meters
                    if (GEOFENCE_RADIUS < distance) {
                        SharedPreferences pref = getSharedPreferences("Notification", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("key", "You are out of Geofence");
                        editor.commit();
                        Bitmap bm = ((BitmapDrawable) getResources().getDrawable(R.drawable.reminder_icon)).getBitmap();
                        NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 0, i, 0);
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
                        builder.setContentTitle("Reminder");
                        builder.setSmallIcon(R.drawable.ok);
                        builder.setLargeIcon(bm);
                        builder.setColor(Color.GREEN);
                        builder.setStyle(new NotificationCompat.BigTextStyle().bigText("You are out of Geofence"));
                        builder.setContentText("You are out of Geofence");
                        builder.setContentIntent(pIntent);
                        builder.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND);
                        builder.setAutoCancel(true);
                        nManager.notify(1, builder.build());
                    }
                }
            },10000);
        }else{
            Toast.makeText(getApplicationContext(),"Please wait capturing the location",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap=googleMap;
        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(20.593684, 78.96288),4.4F) );//INDIA
    }
    public void markerForGeofence(LatLng latLng) {
        MarkerOptions options = new MarkerOptions();
        options.position(latLng);
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.work_marker));
        options.title("Work location");
        if (gmap != null) {
            // Remove last geoFenceMarker
            if (geoFenceMarker != null)
                geoFenceMarker.remove();
            geoFenceMarker = gmap.addMarker(options);
        }
    }
    public void startGeofence() {
        try{
            if (geoFenceMarker != null) {
                Geofence geofence=createGeofence(geoFenceMarker.getPosition(), GEOFENCE_RADIUS);
                GeofencingRequest geofenceRequest=createGeofenceRequest(geofence);
                addGeofence(geofenceRequest);
            } else {
                Log.e("==Geo-fence INFO==", "NO Geo-fence marker");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public Geofence createGeofence(LatLng latLng, float radius) {
        return new Geofence.Builder()
                .setRequestId(GEOFENCE_REQ_ID)
                .setCircularRegion(latLng.latitude, latLng.longitude, radius)
                .setExpirationDuration(60 * 60000)
                .setTransitionTypes( Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT )
                .build();
    }
    public GeofencingRequest createGeofenceRequest(Geofence geofence) {
        try{
            builder = new GeofencingRequest.Builder();
            builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
            builder.addGeofence(geofence);
        }catch (Exception e){
            e.printStackTrace();
        }
        return builder.build();
    }
    public void addGeofence(GeofencingRequest request) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.GeofencingApi.addGeofences(mGoogleApiClient, request, createGeofencePendingIntent()).setResultCallback(this);
    }
    public PendingIntent createGeofencePendingIntent() {
        if (geoFencePendingIntent != null) {
            return geoFencePendingIntent;
        }
        Intent intent = new Intent(this, GeofenceTrasitionService.class);
        return PendingIntent.getService(this, GEOFENCE_REQ_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
    // Create a Intent send by the notification
    public static Intent makeNotificationIntent(Context context, String msg) {
        Intent intent = new Intent(context,GeofenceLocationActivity.class);
        intent.putExtra(NOTIFICATION_MSG, msg);
        return intent;
    }
    @Override
    public void onResult(Status status) {
        if (status.isSuccess()) {
            if (geoFenceLimits != null)
                geoFenceLimits.remove();
            circleOptions = new CircleOptions()
                    .center(geoFenceMarker.getPosition())
                    .strokeColor(Color.parseColor("#4169E1"))
                    .fillColor(Color.parseColor("#FF91A4"))
                    .radius(GEOFENCE_RADIUS);
            geoFenceLimits = gmap.addCircle(circleOptions);
        }
    }
    class GetLocationTask extends AsyncTask<String,Void,StringBuilder> {

        @Override
        protected StringBuilder doInBackground(String... params) {
            int b;
            try{
                String address=params[0];
                String msg=address.replaceAll(" ","%20");
                HttpPost httppost = new HttpPost("http://maps.google.com/maps/api/geocode/json?address=" + msg + "&sensor=false");
                HttpClient client = new DefaultHttpClient();
                HttpResponse response=client.execute(httppost);
                HttpEntity entity = response.getEntity();
                InputStream stream = entity.getContent();
                while ((b = stream.read()) != -1) {
                    stringBuilder.append((char) b);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return stringBuilder;
        }
        @Override
        protected void onPostExecute(StringBuilder stringBuilder) {
            super.onPostExecute(stringBuilder);
            JSONObject jsonObject;
            if(stringBuilder.length()>0){
                try{
                    jsonObject = new JSONObject(stringBuilder.toString());
                    lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                            .getJSONObject("geometry").getJSONObject("location")
                            .getDouble("lat");
                    lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                            .getJSONObject("geometry").getJSONObject("location")
                            .getDouble("lng");
                    SharedPreferences pref=getSharedPreferences("Mock", Context.MODE_PRIVATE);
                    String location=pref.getString("location","loc");
                    if(location.equals("loc")){
                        updateUI();
                    }else{
                        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(GeofenceLocationActivity.this).create();
                        alertDialog.setTitle("Fake GPS Provider");
                        alertDialog.setMessage("Goto Developer options to disable the fake GPS...");
                        alertDialog.setCancelable(false);
                        alertDialog.setIcon(R.mipmap.ic_alert);
                        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    finishAffinity();
                                    moveTaskToBack(true);
                                }else{
                                    finish();
                                    moveTaskToBack(true);
                                }
                            }
                        });
                        alertDialog.show();
                        bt_next.setEnabled(false);
                        bt_next.setBackgroundColor(getResources().getColor(R.color.disable));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(getApplicationContext(),"Sorry! Please try again",Toast.LENGTH_LONG).show();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finishAffinity();
                    moveTaskToBack(true);
                }else{
                    finish();
                    moveTaskToBack(true);
                }
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            unregisterReceiver(myReceiver);
        }catch(Exception e){e.printStackTrace();}
    }
    //Google services method
    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        boolean service=false;
        try{
            if (ConnectionResult.SUCCESS == status) {
                service=true;
            } else {
                //GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
                service=false;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return service;
    }
    public void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    public void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(GeofenceLocationActivity.this);
        alertDialog.setMessage("Do you want to close the application?");
        alertDialog.setCancelable(false);
        alertDialog.setIcon(R.drawable.red_geofence);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finishAffinity();
                    moveTaskToBack(true);
                }else{
                    finish();
                    moveTaskToBack(true);
                }
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.ic_contact:
                if(CheckNetworkConnection.isConnectionAvailable(GeofenceLocationActivity.this)) {
                    try{
                        new ContactTask(GeofenceLocationActivity.this).execute();
                    }catch(Exception e){e.printStackTrace();}
                }else{
                    new CheckInternetGps(GeofenceLocationActivity.this).callInternetAlert();
                }
                return true;
            case R.id.rise_issue:
                Intent i=new Intent();
                i.setComponent(new ComponentName(getApplicationContext(),IssueActivity.class));
                i.putExtra("emp_object", employeeDetails);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
    class ContactTask extends AsyncTask<Void,Void,Response>{

        private final ProgressDialog progressDialog;
        boolean b;

        public ContactTask(Context ctx) {
            progressDialog = MyCustomProgressDialog.ctor(ctx);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try{
                progressDialog.show();
            }catch(Exception e){e.printStackTrace();}
        }
        @Override
        protected Response doInBackground(Void... voids) {
            Response response=null;
            try{
                final String URL=getString(R.string.report);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                response=restTemplate.getForObject(URL,Response.class);
            }catch (Exception e){
                e.printStackTrace();
            }
            return response;
        }
        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            progressDialog.dismiss();
            JSONObject object,sub_object,array_object;
            JSONArray array;
            try{
                object=new JSONObject(response.toString());
                b=object.getBoolean("successful");
                if(b){
                    sub_object=object.getJSONObject("responseObject");
                    array=sub_object.getJSONArray("employees");
                    ArrayList<ContactModel> pojo_list=new ArrayList<ContactModel>();
                    for(int i=0;i<array.length();i++){
                        array_object=array.getJSONObject(i);
                        ContactModel c=new ContactModel();
                        c.setEmpName(array_object.getString("name"));
                        c.setEmpPhone(array_object.getString("mobile"));
                        c.setEmpEmail(array_object.getString("email"));
                        pojo_list.add(c);
                    }
                    Intent i=new Intent();
                    i.setComponent(new ComponentName(getApplicationContext(),ContactReportActivity.class));
                    i.putExtra("data",pojo_list);
                    startActivity(i);
                }
            }catch (Exception e){e.printStackTrace();}
        }
    }
}
