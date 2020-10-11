package com.example.fitme.DailyCalorieIntake;

import android.os.Parcel;
import android.os.Parcelable;

public class Daily_Calorie_Record implements Parcelable {
    int dailyCalorieIntake;
    int proteinIntake;
    int carbIntake;
    int fatIntake;
    String currentDate;
    String recordId;

    public Daily_Calorie_Record(int dailyCalorieIntake, int proteinIntake, int carbIntake, int fatIntake, String currentDate,String recordId) {
        this.dailyCalorieIntake = dailyCalorieIntake;
        this.proteinIntake = proteinIntake;
        this.carbIntake = carbIntake;
        this.fatIntake = fatIntake;
        this.currentDate = currentDate;
        this.recordId = recordId;
    }

    protected Daily_Calorie_Record(Parcel in) {
        dailyCalorieIntake = in.readInt();
        proteinIntake = in.readInt();
        carbIntake = in.readInt();
        fatIntake = in.readInt();
        currentDate = in.readString();
        recordId = in.readString();
    }

    public static final Creator<Daily_Calorie_Record> CREATOR = new Creator<Daily_Calorie_Record>() {
        @Override
        public Daily_Calorie_Record createFromParcel(Parcel in) {
            return new Daily_Calorie_Record(in);
        }

        @Override
        public Daily_Calorie_Record[] newArray(int size) {
            return new Daily_Calorie_Record[size];
        }
    };

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public Daily_Calorie_Record(){}

    public int getDailyCalorieIntake() {
        return dailyCalorieIntake;
    }

    public void setDailyCalorieIntake(int dailyCalorieIntake) {
        this.dailyCalorieIntake = dailyCalorieIntake;
    }

    public int getProteinIntake() {
        return proteinIntake;
    }

    public void setProteinIntake(int proteinIntake) {
        this.proteinIntake = proteinIntake;
    }

    public int getCarbIntake() {
        return carbIntake;
    }

    public void setCarbIntake(int carbIntake) {
        this.carbIntake = carbIntake;
    }

    public int getFatIntake() {
        return fatIntake;
    }

    public void setFatIntake(int fatIntake) {
        this.fatIntake = fatIntake;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(dailyCalorieIntake);
        parcel.writeInt(proteinIntake);
        parcel.writeInt(carbIntake);
        parcel.writeInt(fatIntake);
        parcel.writeString(currentDate);
        parcel.writeString(recordId);
    }
}
