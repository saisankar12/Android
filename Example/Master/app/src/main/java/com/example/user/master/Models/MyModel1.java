package com.example.user.master.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class MyModel1 implements Parcelable{

    String namee,servings;

    public String getNamee() {
        return namee;
    }

    public void setNamee(String namee) {
        this.namee = namee;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public String getIngArray() {
        return ingArray;
    }

    public void setIngArray(String ingArray) {
        this.ingArray = ingArray;
    }

    public String getStespArray() {
        return stespArray;
    }

    public void setStespArray(String stespArray) {
        this.stespArray = stespArray;
    }

    public static Creator<MyModel1> getCREATOR() {
        return CREATOR;
    }

    public MyModel1(String namee, String servings, String ingArray, String stespArray) {

        this.namee = namee;
        this.servings = servings;
        this.ingArray = ingArray;
        this.stespArray = stespArray;
    }

    String ingArray,stespArray;


    protected MyModel1(Parcel in) {
        namee = in.readString();
        servings = in.readString();
        ingArray = in.readString();
        stespArray = in.readString();
    }

    public static final Creator<MyModel1> CREATOR = new Creator<MyModel1>() {
        @Override
        public MyModel1 createFromParcel(Parcel in) {
            return new MyModel1(in);
        }

        @Override
        public MyModel1[] newArray(int size) {
            return new MyModel1[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(namee);
        dest.writeString(servings);
        dest.writeString(ingArray);
        dest.writeString(stespArray);
    }
}
