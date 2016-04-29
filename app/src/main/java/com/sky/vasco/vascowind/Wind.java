package com.sky.vasco.vascowind;

/**
 * Created by Vasco on 26/04/2016.
 */
public class Wind {

    public double speed;
    public double deg;
    public int date;

    public Wind() {
    }

    public Wind(double speed, double deg, int date) {
        this.speed = speed;
        this.deg = deg;
        this.date = date;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDeg() {
        return deg;
    }

    public void setDeg(double deg) {
        this.deg = deg;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }
}
