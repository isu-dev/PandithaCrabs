package com.example.fitme.fat;

public class BFCalculator {
    protected BFGender gender;
    protected int age;
    protected double height;
    protected double waist;
    protected double hip;
    protected double neck;
    protected double weight;
    protected double bodyFat;

    public void setGender(BFGender gender){
        this.gender = gender;
    }

    public void setAge(int age){
        this.age = age;
    }

    public void setHeight(double height){
        this.height = height;
    }

    public void setWaist(double waist){
        this.waist = waist;
    }

    public void setHip(double hip){
        this.hip = hip;
    }

    public void setNeck(double neck){
        this.neck = neck;
    }

    public void setWeight(double weight){
        this.weight = weight;
    }

    public void setBodyFat(double bodyFat){
        this.bodyFat = bodyFat;
    }

    public int getAge(){return this.age;}

    public double getHeight(){return this.height;}

    public double getWaist(){return this.waist;}

    public double getHip(){return this.hip;}

    public double getNeck(){return this.neck;}

    public double getWeight(){return this.weight;}

    public double getBodyFat(){return this.bodyFat;}


    /**
     * Calculating the body fat
     *
     * @link https://www.calculator.net/body-fat-calculator.html
     * @return Calculated body fat
     */
    public double calculate(){
        if(this.gender == BFGender.male){
            return 495/((1.0324 - 0.19077 * Math.log(this.waist - this.neck)) + 0.15456*Math.log(this.height)) -450;
        } else {
            return 495/((1.29579 - 0.35004 * Math.log(this.waist + this.hip - this.neck)) + 0.22100*Math.log(this.height)) -450;
        }
    }
}
