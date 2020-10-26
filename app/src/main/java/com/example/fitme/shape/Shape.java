package com.example.fitme.shape;

import com.example.fitme.R;

import java.util.Date;

public class Shape {
    private Double bust;
    private Double waist;
    private Double hip;
    private Double highHip;
    private long time;
    private String firebaseId;
    private int shape;

    public Shape(){
        this.time = new Date().getTime();
    }

    Shape(Double bust, Double waist, Double hip, int shape) {
        this.bust = bust;
        this.waist = waist;
        this.hip = hip;
        this.shape = shape;
        this.time = new Date().getTime();
    }

    public String firebaseId(){return this.firebaseId;}

    public void firebaseId(String id){
        this.firebaseId = id;
    }

    public Double getBustSize() {
        return this.bust;
    }

    public void setBustSize(double bust) {
        this.bust = bust;
    }

    public Double getWaistSize() {
        return this.waist;
    }

    public void setWaistSize(double waist) {
        this.waist = waist;
    }

    public Double getHipSize() {
        return this.hip;
    }

    public void setHipSize(double hip) {
        this.hip = hip;
    }

    public Double getHighHipSize() {return this.highHip;}

    public void setHighHipSize(double highHip) {
        this.highHip = highHip;
    }

    public Long getTime(){
        return this.time;
    }

    public int calculate(){

        if ((bust - hip) <= 1 && (hip - bust) < 3.6 && (bust - waist) >= 9 || (hip - waist) >= 10){
            return R.drawable.hour_glass;
        } else if ((hip - bust) >= 3.6 && (hip - bust) < 10 && (hip - waist) >= 9 && (highHip/waist) < 1.193){
            return R.drawable.hour_glass;
        } else if ((bust - hip) > 1 && (bust - hip) < 10 && (bust - waist) >= 9){
            return R.drawable.hour_glass;
        } else if ((hip - bust) > 2 && (hip - waist) >= 7 && (highHip/waist) >= 1.193){
            return R.drawable.pear;
        } else if ((hip - bust) >= 3.6 && (hip - waist) < 9) {
            return R.drawable.pear;
        } else if ((bust - hip) >= 3.6 && (bust - waist) < 9) {
            return R.drawable.apple;
        }else if ((hip - bust) < 3.6 && (bust - hip) < 3.6 && (bust - waist) < 9 && (hip - waist) < 10){
            return R.drawable.banana;
        }

        return R.drawable.question;
    }

    public int getShape() {
        return this.shape;
    }

    public void setShape(int shape) {
        this.shape = shape;
    }

    public void setTime(long time){
        this.time = time;
    }
}
