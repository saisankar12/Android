package com.example.saisankar.cinematics.content;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.saisankar.cinematics.LoginActivity;
import com.example.saisankar.cinematics.R;
import com.example.saisankar.cinematics.adapter.TabAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainContentActivity extends AppCompatActivity {
    public static String user_email;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private FirebaseUser user;
    private AdView mAdView;
    private Menu menu;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content);
        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        user_email = user.getEmail();
        Toast.makeText(this, user.getEmail(), Toast.LENGTH_SHORT).show();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainContentActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        viewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tab);
        viewPager.setAdapter(new TabAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        mAdView = findViewById(R.id.banner);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("69C8BD8BEA2715C5ADEC8BA06AF59AB1").build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        this.menu = menu;
        menu.findItem(R.id.useremail).setTitle(user.getEmail());
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.logout:
                auth.signOut();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
