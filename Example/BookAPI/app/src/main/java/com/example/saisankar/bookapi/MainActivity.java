package com.example.saisankar.bookapi;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {



    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager=findViewById(R.id.pager);
        tabLayout=findViewById(R.id.tab);
        viewPager.setAdapter(new TabAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }

    class TabAdapter extends FragmentPagerAdapter{

        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0:
                    return new AndroidFragment();
                case 1:
                    return new JavaFragment();
                case 2:
                    return new BookSearchFragment();
                case 3:
                    return new StaticFragment();
                case 4:
                    return new SearchFragment();
                case 5:
                    return new MenuFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 6;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch(position)
            {
                case 0:
                    return "Android";
                case 1:
                    return "Java";
                case 2:
                    return "DataSearch";
                case 3:
                    return "Static";
                case 4:
                    return "BookSearch";
                case 5:
                    return "BookMenu";
            }
            return super.getPageTitle(position);
        }
    }
}
