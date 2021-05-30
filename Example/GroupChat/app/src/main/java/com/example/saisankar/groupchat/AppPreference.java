package com.example.saisankar.groupchat;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Sai Sankar on 09-04-2018.
 */

public class AppPreference {

    private SharedPreferences mPreferences;
    private String PREF_NAME = "MyGroupChat";
    private String KEY_EMAIL = "email";
    private SharedPreferences.Editor editor;

    public AppPreference(Context mContext){
        mPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = mPreferences.edit();
    }

    public void setEmail(String email){
        editor.putString(KEY_EMAIL, email);
        editor.commit();
    }

    public String getEmail(){
        return  mPreferences.getString(KEY_EMAIL, null);
    }

    public void clear(){
        editor.clear();
        editor.commit();
    }
}
