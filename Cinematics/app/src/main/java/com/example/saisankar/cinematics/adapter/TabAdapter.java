package com.example.saisankar.cinematics.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.saisankar.cinematics.content.EnglishMovies;
import com.example.saisankar.cinematics.content.GujarathiMovies;
import com.example.saisankar.cinematics.content.HindiMovies;
import com.example.saisankar.cinematics.content.PanjabiMovies;
import com.example.saisankar.cinematics.content.TeluguMovies;
import com.example.saisankar.cinematics.database.FavoriteMovies;

public class TabAdapter extends FragmentPagerAdapter {

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TeluguMovies();
            case 1:
                return new EnglishMovies();
            case 2:
                return new HindiMovies();
            case 3:
                return new GujarathiMovies();
            case 4:
                return new PanjabiMovies();
            case 5:
                return new FavoriteMovies();
        }
        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Telugu";
            case 1:
                return "English";
            case 2:
                return "Hindi";
            case 3:
                return "Gujarath";
            case 4:
                return "Panjab";
            case 5:
                return "Favorites";
        }
        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return 6;
    }
}
