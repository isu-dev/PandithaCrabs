package com.example.fitme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class viewBmiRecords extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bmi_records);
    }

    //this function takes the user to the interface to calculate BMI
    public void calculateNewBmi(View view)
    {
        Intent intent = new Intent(this, addBmiRecord.class);
        startActivity(intent);
    }

    //this function takes the user back to the main page
    public void backToMainPage(View view)
    {
        Intent intent = new Intent(this, viewBmiRecords.class);
        startActivity(intent);
    }

    //this function allows the user to update a single BMI record
    public void updateBmi(View view)
    {
        Intent intent = new Intent(this, updateBmiRecord.class);
        startActivity(intent);
    }

    //this function allows the user to delete a single BMI record
    public void deleteBmi(View view)
    {
        Intent intent = new Intent(this, viewBmiRecords.class);
        startActivity(intent);
    }
}