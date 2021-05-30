package com.example.user.master.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class MyStepsModel implements Parcelable {

    String stepsidd,shortdes,stepdesc,stepsVideo,stepThumbnil;

    public MyStepsModel(String stepsidd, String shortdes, String stepdesc, String stepsVideo, String stepThumbnil) {
        this.stepsidd = stepsidd;
        this.shortdes = shortdes;
        this.stepdesc = stepdesc;
        this.stepsVideo = stepsVideo;
        this.stepThumbnil = stepThumbnil;
    }

    public String getStepsidd() {
        return stepsidd;
    }

    public void setStepsidd(String stepsidd) {
        this.stepsidd = stepsidd;
    }

    public String getShortdes() {
        return shortdes;
    }

    public void setShortdes(String shortdes) {
        this.shortdes = shortdes;
    }

    public String getStepdesc() {
        return stepdesc;
    }

    public void setStepdesc(String stepdesc) {
        this.stepdesc = stepdesc;
    }

    public String getStepsVideo() {
        return stepsVideo;
    }

    public void setStepsVideo(String stepsVideo) {
        this.stepsVideo = stepsVideo;
    }

    public String getStepThumbnil() {
        return stepThumbnil;
    }

    public void setStepThumbnil(String stepThumbnil) {
        this.stepThumbnil = stepThumbnil;
    }

    public static Creator<MyStepsModel> getCREATOR() {
        return CREATOR;
    }

    public MyStepsModel(Parcel in) {
        stepsidd = in.readString();

        shortdes = in.readString();
        stepdesc = in.readString();

        stepsVideo = in.readString();
        stepThumbnil = in.readString();
    }

    public static final Creator<MyStepsModel> CREATOR = new Creator<MyStepsModel>() {
        @Override
        public MyStepsModel createFromParcel(Parcel in) {
            return new MyStepsModel(in);
        }

        @Override
        public MyStepsModel[] newArray(int size) {
            return new MyStepsModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(stepsidd);
        dest.writeString(shortdes);
        dest.writeString(stepdesc);
        dest.writeString(stepsVideo);
        dest.writeString(stepThumbnil);
    }
}
