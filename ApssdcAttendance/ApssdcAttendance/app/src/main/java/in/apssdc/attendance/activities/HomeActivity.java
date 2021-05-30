package in.apssdc.attendance.activities;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.Calendar;

import in.apssdc.attendance.R;
import in.apssdc.attendance.common.CheckInternetGps;
import in.apssdc.attendance.fragments.AttendanceFragment;
import in.apssdc.attendance.fragments.NavigationDrawerFragment;
import in.apssdc.attendance.fragments.WorkLogFragment;
import in.apssdc.attendance.model.EmployeeDetails;
import in.apssdc.attendance.receivers.MyScheduleReceiver;

public class HomeActivity extends AppCompatActivity implements AttendanceFragment.MyInterface,WorkLogFragment.WorkLogInterface{

    FragmentTransaction fragmentTransaction;
    TelephonyManager tManager;
    EmployeeDetails employeeDetails;
    //session Time
    BroadcastReceiver myReceiver;
    int requestCode=1;
    PendingIntent pIntent;
    AlarmManager aManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //session
        IntentFilter filter=new IntentFilter();
        filter.addAction("siva.ACTION_FINISH");
        myReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //return the result to MainActivity
                Intent resultIntent=new Intent();
                resultIntent.putExtra("result",requestCode);
                HomeActivity.this.setResult(RESULT_OK,resultIntent);
                finish();
            }
        };
        registerReceiver(myReceiver,filter);
        employeeDetails=(EmployeeDetails) getIntent().getSerializableExtra("emp_object");
        tManager=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        employeeDetails.setImeiNumber(tManager.getDeviceId());
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frag_container, new AttendanceFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        NavigationDrawerFragment fragment=new NavigationDrawerFragment();
        fragmentTransaction.replace(R.id.fragment_navigation_drawer, fragment);
        DrawerLayout drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        fragment.setUp(drawerLayout,toolbar);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(aManager!=null){
            aManager.cancel(pIntent);
        }
    }
    //send to attendance fragment
    @Override
    public EmployeeDetails getEmpDetails() {
        return employeeDetails;
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
        alertDialog.setMessage("Do you want to close the application?");
        alertDialog.setCancelable(false);
        alertDialog.setIcon(R.drawable.red_geofence);
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
    protected void onPause() {
        super.onPause();
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
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            unregisterReceiver(myReceiver);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
