package com.example.navigationmenu;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.tollbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                0, 0);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (item.getItemId()) {
            case R.id.profile:
                ProfileFragment profileFragment = new ProfileFragment();
                transaction.replace(R.id.contentlayout, profileFragment);
                transaction.commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.dashBoard:
                DashBoard dashBoard = new DashBoard();
                transaction.replace(R.id.contentlayout, dashBoard);
                transaction.commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.gallery:
                BlankFragment blankFragment=new BlankFragment();
                transaction.replace(R.id.contentlayout, blankFragment);
                transaction.commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;

        }
        return false;
    }
}
