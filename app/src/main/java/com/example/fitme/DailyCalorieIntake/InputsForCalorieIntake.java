package com.example.fitme.DailyCalorieIntake;

import android.os.Parcel;
import android.os.Parcelable;

public class InputsForCalorieIntake implements Parcelable {
    private int age,heightInFeet,heightInInches;
    private double weight;
    private String gender,activityLevel,typeOfPerson,Id;

    public InputsForCalorieIntake(){}

    public InputsForCalorieIntake(int age, int heightInFeet, int heightInInches, double weight, String gender, String activityLevel, String typeOfPerson, String Id) {
        this.age = age;
        this.heightInFeet = heightInFeet;
        this.heightInInches = heightInInches;
        this.weight = weight;
        this.gender = gender;
        this.activityLevel = activityLevel;
        this.typeOfPerson = typeOfPerson;
        this.Id = Id;
    }

    protected InputsForCalorieIntake(Parcel in) {
        age = in.readInt();
        heightInFeet = in.readInt();
        heightInInches = in.readInt();
        weight = in.readDouble();
        gender = in.readString();
        activityLevel = in.readString();
        typeOfPerson = in.readString();
        Id = in.readString();
    }

    public static final Creator<InputsForCalorieIntake> CREATOR = new Creator<InputsForCalorieIntake>() {
        @Override
        public InputsForCalorieIntake createFromParcel(Parcel in) {
            return new InputsForCalorieIntake(in);
        }

        @Override
        public InputsForCalorieIntake[] newArray(int size) {
            return new InputsForCalorieIntake[size];
        }
    };

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHeightInFeet() {
        return heightInFeet;
    }

    public void setHeightInFeet(int heightInFeet) {
        this.heightInFeet = heightInFeet;
    }

    public int getHeightInInches() {
        return heightInInches;
    }

    public void setHeightInInches(int heightInInches) {
        this.heightInInches = heightInInches;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(String activityLevel) {
        this.activityLevel = activityLevel;
    }

    public String getTypeOfPerson() {
        return typeOfPerson;
    }

    public void setTypeOfPerson(String typeOfPerson) {
        this.typeOfPerson = typeOfPerson;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(age);
        parcel.writeInt(heightInFeet);
        parcel.writeInt(heightInInches);
        parcel.writeDouble(weight);
        parcel.writeString(gender);
        parcel.writeString(activityLevel);
        parcel.writeString(typeOfPerson);
        parcel.writeString(Id);
    }

}
