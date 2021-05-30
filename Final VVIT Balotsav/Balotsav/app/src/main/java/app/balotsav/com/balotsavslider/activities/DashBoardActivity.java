package app.balotsav.com.balotsavslider.activities;

import android.app.FragmentManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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
import app.balotsav.com.balotsavslider.utils.GrantPermissions;

public class DashBoardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String scode;
    Event e;
    List<Event> arrayList;
    private Schools schools;
    DrawerLayout layout;

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

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel("1", getString(R.string.notification), importance);
            mChannel.setDescription(getString(R.string.description));
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(mChannel);
        }
        firebaseDatabase = FirebaseDatabase.getInstance();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
            SharedPreferences.Editor editor = getSharedPreferences("Balotsav", MODE_PRIVATE).edit();
            editor.clear();
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
            try {
                new GrantPermissions(this).checkAndRequestPermissions();
            }catch(Exception e){
                Toast.makeText(this, "Enable GPS location", Toast.LENGTH_SHORT).show(); Toast.makeText(this, "Enable GPS location access", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("GPS is disabled in your device");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("Goto Settings to Enable",new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Intent callGPSSettingIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(callGPSSettingIntent);
                    }
                });
                alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = alertDialogBuilder.create();
                alert.getWindow().setBackgroundDrawableResource(R.color.cardview_light_background);
                alert.show();
                //recyclerView.setAdapter(new EventAdapter(rootview, event, getValue(), rootview.getContext()));
            }
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


