package com.example.roomlivedata;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Students")
public class StudentModel {

    @PrimaryKey @NonNull
    String rollnumber;

    @ColumnInfo(name = "Studemnt_Name")
    String name;

    String gmail;

    String mobile;

    public StudentModel(String rollnumber, String name, String gmail, String mobile) {
        this.rollnumber = rollnumber;
        this.name = name;
        this.gmail = gmail;
        this.mobile = mobile;
    }

    public String getRollnumber() {
        return rollnumber;
    }

    public void setRollnumber(String rollnumber) {
        this.rollnumber = rollnumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
