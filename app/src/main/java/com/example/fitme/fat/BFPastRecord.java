package com.example.fitme.fat;

public class BFPastRecord {
    private Double height = 0.0;
    private Integer age = 0;
    private Double neck = 0.0;
    private Double waist = 0.0;
    private Double hip = 0.0;
    private Double weight = 0.0;
    private String sex = "Male";
    private String date = "";
    private String firebaseId;

    public BFPastRecord(){}

    public BFPastRecord(
            String firebaseId,
            Double height,
            Integer age,
            Double neck,
            Double waist,
            Double hip,
            Double weight,
            String sex,
            String date
    ) {
        this.firebaseId = firebaseId;
        this.height = height;
        this.age = age;
        this.neck = neck;
        this.waist = waist;
        this.hip = hip;
        this.weight = weight;
        this.sex = sex;
        this.date = date;
    }

    public Double getHeight() {
        return this.height;
    }

    public Integer getAge() {
        return this.age;
    }

    public Double getNeck() {
        return this.neck;
    }

    public Double getWaist() {
        return this.waist;
    }

    public Double getHip() {
        return this.hip;
    }

    public Double getWeight() {
        return this.weight;
    }

    public String getSex(){
        return this.sex;
    }

    public String getDate(){
        return this.date;
    }

    public String firebaseId(){return this.firebaseId;}

}