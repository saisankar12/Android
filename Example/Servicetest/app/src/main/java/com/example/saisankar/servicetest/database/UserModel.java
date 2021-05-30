package com.example.saisankar.servicetest.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "userinfo")
public class UserModel {
    @PrimaryKey @NonNull
    private String uid;
    @ColumnInfo(name = "user_name")
    private String uname;
    @ColumnInfo(name = "user_mail")
    private String umail;
    @ColumnInfo(name = "user_mobile")
    private String umobile;


    public UserModel() {

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUmail() {
        return umail;
    }

    public void setUmail(String umail) {
        this.umail = umail;
    }

    public String getUmobile() {
        return umobile;
    }

    public void setUmobile(String umobile) {
        this.umobile = umobile;
    }
}
