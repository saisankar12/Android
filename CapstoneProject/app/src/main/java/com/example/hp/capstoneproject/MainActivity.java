package com.example.hp.capstoneproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.hp.capstoneproject.fragments.EntertainmentFragment;
import com.example.hp.capstoneproject.fragments.HomeFragment;
import com.example.hp.capstoneproject.fragments.OldMatchesFragment;
import com.example.hp.capstoneproject.fragments.PlayerInfo;
import com.example.hp.capstoneproject.fragments.ScheduleMatchFragment;
import com.example.hp.capstoneproject.fragments.UpcomingMatchesFragment;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String uname;
    /*@InjectView(R.id.textView)
    TextView textView;*/
    ProgressDialog dialog;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ButterKnife.inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        auth=FirebaseAuth.getInstance();
        uname = getIntent().getStringExtra("uname");
        //textView.setText(uname);
        dialog = new ProgressDialog(this);

        getSupportFragmentManager().beginTransaction().
                replace(R.id.containerId, new HomeFragment()).commit();

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_feedback)
        {
            Intent intent=new Intent(this,FeedbackActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_sports) {
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.containerId, new TechnologyFragment()).commit();
            /*Intent intent=new Intent(this, NewsActivity.class);
            startActivity(intent);*/
        } else if (id == R.id.nav_upcoming) {
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.containerId, new UpcomingMatchesFragment()).commit();
        } else if (id == R.id.nav_oldmatches) {
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.containerId, new OldMatchesFragment()).commit();
        } else if (id == R.id.nav_playerInfo) {
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.containerId, new PlayerInfo()).commit();
        } else if (id == R.id.nav_timetable) {
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.containerId, new ScheduleMatchFragment()).commit();

        }  else if (id == R.id.nav_logout)
        {
            auth.signOut();
            startActivity(new Intent(this,LoginActivity.class));

        } else if (id == R.id.nav_homeFragment) {
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.containerId, new HomeFragment()).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
