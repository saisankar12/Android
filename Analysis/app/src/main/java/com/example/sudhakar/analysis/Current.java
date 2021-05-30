package com.example.sudhakar.analysis;

public class Current {
    public int getPh() {
        return ph;
    }

    public void setPh(int ph) {
        this.ph = ph;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getTemparature() {
        return temparature;
    }

    public void setTemparature(double temparature) {
        this.temparature = temparature;
    }

    private int ph;
    private double humidity;
    private double temparature;
    public Current() {
    }
    public Current(int ph, double humidity, double temparature) {
        this.ph = ph;
        this.humidity = humidity;
        this.temparature = temparature;
    }
    public String toString(){
      return "ph:"+ph+" hum:"+humidity+" temp:"+temparature;
    }
}
