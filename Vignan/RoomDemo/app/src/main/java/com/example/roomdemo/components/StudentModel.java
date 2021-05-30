package com.example.roomdemo.components;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "StudentData")
public class StudentModel {

    @ColumnInfo(name = "Student_Name")
    String name;
    @PrimaryKey @NonNull @ColumnInfo(name = "Studnet_Phone")
    String phone;
    @ColumnInfo(name = "Student_Email")
    String email;

    public StudentModel(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
