package app.balotsav.com.balotsavslider.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import app.balotsav.com.balotsavslider.fragments.EventDisplayFragment;
import app.balotsav.com.balotsavslider.fragments.HomeFragment;
import app.balotsav.com.balotsavslider.R;
import app.balotsav.com.balotsavslider.fragments.RegisteredEventsFragment;
import app.balotsav.com.balotsavslider.fragments.ResultsFragment;
import app.balotsav.com.balotsavslider.fragments.RulesFragment;
import app.balotsav.com.balotsavslider.fragments.ScheduleFragment;
import app.balotsav.com.balotsavslider.fragments.AnnouncementFragment;
import app.balotsav.com.balotsavslider.model.Event;
import app.balotsav.com.balotsavslider.model.Schools;
import app.balotsav.com.balotsavslider.utils.EventAdapter;
import app.balotsav.com.balotsavslider.utils.GrantPermissions;

public class DashBoardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String scode;
    Event e;
    List<Event> arrayList;
    private Schools schools;
    SharedPreferences pref;
    DrawerLayout layout;
    Boolean value;

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        layout = findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, new HomeFragment())
                .commit();
        pref = getSharedPreferences("Balotsav",0);
        firebaseDatabase = FirebaseDatabase.getInstance();

        @SuppressLint("CutPasteId") DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setTitle(getResources().getString(R.string.vvit_balotsav));

        Gson gson = new Gson();
        String json = getSharedPreferences("Balotsav", MODE_PRIVATE).getString("data", "");
        schools = gson.fromJson(json, Schools.class);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView school_name = headerView.findViewById(R.id.textView);
        TextView coordinator_name = headerView.findViewById(R.id.id_coordinator_name);
        school_name.setText(schools.getSchool_Name());
        coordinator_name.setText(schools.getCoordinator_Name());
        navigationView.setNavigationItemSelectedListener(this);
        getTime();


    }
    public void getTime() {
                final Date today = new Date();
                int today_date = today.getDate();
                int today_month = today.getMonth()+1;
                int today_year = today.getYear()+1900;
                String t_date = today_date+"/"+today_month+"/"+today_year;
                Log.i("Test date", t_date);
                String result = pref.getString("expiryDay", "");
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("today",t_date);
                editor.apply();
                String dte[] = result.split("/");
                int d1 = Integer.parseInt(dte[0]);
                int d2 = Integer.parseInt(dte[1]);
                int d3 = Integer.parseInt(dte[2]);
                if (today.getDate() <= d1 && today.getMonth() <= d2 && today.getYear() <= d3) {
                    editor.putBoolean("value",false);
                } else {
                    Toast.makeText(this, getString(R.string.time_exceeded), Toast.LENGTH_LONG).show();
                    editor.putBoolean("value",true);
                }
                editor.apply();

        Log.i("Test","After setting location");
        Log.i("Test: ",String.valueOf(pref.getBoolean("value",true)));
        //getActivity().onBackPressed();
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dash_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            SharedPreferences pref = getSharedPreferences("Balotsav", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            String expiry_date = pref.getString("expiryDay","");
            editor.clear();
            editor.putString("expiryDay",expiry_date);
            editor.apply();
            android.support.v4.app.FragmentManager fm = this.getSupportFragmentManager();
            for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                fm.popBackStack();
            }
            startActivity(new Intent(DashBoardActivity.this, LoginActivity.class));
            finish();
        } else if (id == R.id.action_print) {
            startActivity(new Intent(DashBoardActivity.this,EditRegistrationActivity.class));
            //getData();
        } else if (id == R.id.action_rules) {
            Fragment fragment = new RulesFragment();
            loadFragment(fragment);
        } else if (id == R.id.action_about) {

            Fragment fragment = new ScheduleFragment();
            loadFragment(fragment);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        if (id == R.id.nav_edit) {
            fragment = new RegisteredEventsFragment();

        } else if (id == R.id.nav_event) {
            fragment = new EventDisplayFragment();//vs
        } else if (id == R.id.nav_results) {
            fragment = new ResultsFragment();
        } else if (id == R.id.nav_notifications) {
            fragment = new AnnouncementFragment();
        } else if (id == R.id.nav_rules) {
            fragment = new RulesFragment();
        } else if (id == R.id.nav_home) {
            fragment = new HomeFragment();
            getTime();
        }   else if(id == R.id.about_us){
            startActivity(new Intent(this,AboutActivity.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return loadFragment(fragment);
    }

    //vs
    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            // getTime();
            Log.i("BackStackCount:Support",String.valueOf(this.getSupportFragmentManager().getBackStackEntryCount()));
            Log.i("BackStackCount :",String.valueOf(this.getFragmentManager().getBackStackEntryCount()));
            getSupportFragmentManager().popBackStack(null, android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE);
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack("fragment")
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            message = getResources().getString(R.string.connected_internet);
            color = Color.WHITE;
        } else {
            message = getResources().getString(R.string.check_connection);
            color = Color.RED;
        }

        Snackbar snackbar = Snackbar.make(layout, message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }
}


