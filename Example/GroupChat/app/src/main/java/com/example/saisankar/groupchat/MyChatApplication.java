package com.example.saisankar.groupchat;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Sai Sankar on 09-04-2018.
 */

public class MyChatApplication extends android.app.Application{

    public void onCreate(){
        super.onCreate();
        FirebaseDatabase instance = FirebaseDatabase.getInstance();
        instance.setPersistenceEnabled(true);
        instance.getReference().keepSynced(true);
    }
}
