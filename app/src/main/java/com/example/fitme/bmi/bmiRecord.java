package com.example.fitme.bmi;

public class bmiRecord {
    private int heightFeet;
    private int heightInches;
    private int weightKg;
    private double bmiVal;
    private String bmiCategory;

    public bmiRecord() {}

    public int getHeightFeet() {
        return heightFeet;
    }

    public void setHeightFeet(int heightFeet) {
        this.heightFeet = heightFeet;
    }

    public int getHeightInches() {
        return heightInches;
    }

    public void setHeightInches(int heightInches) {
        this.heightInches = heightInches;
    }

    public int getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(int weightKg) {
        this.weightKg = weightKg;
    }

    public double getBmiVal() {
        return bmiVal;
    }

    public void setBmiVal(double bmiVal) {
        this.bmiVal = bmiVal;
    }

    public String getBmiCategory() {
        return bmiCategory;
    }

    public void setBmiCategory(String bmiCategory) {
        this.bmiCategory = bmiCategory;
    }
}
