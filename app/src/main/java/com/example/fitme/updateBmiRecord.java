package com.example.fitme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class updateBmiRecord extends AppCompatActivity {

    EditText etHFeet;
    EditText etHInches;
    EditText etWKg;

    TextView tvBmi;

    int feet;
    int inches;
    int weight;
    double heightInMetres;
    double heightInCm;
    double bmiVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_bmi_record);
    }

    //this function takes the user back to all the previous BMI records
    public void openBmiHistory(View view)
    {
        Intent intent = new Intent(this, viewBmiRecords.class);
        startActivity(intent);
    }

    //this function calculates the BMI value
    public void calculateBmi(View view)
    {
        //converting the entered values to integers
        feet = Integer.parseInt(etHFeet.getText().toString());
        inches = Integer.parseInt(etHInches.getText().toString());
        weight = Integer.parseInt(etWKg.getText().toString());

        //calculating the height in metres
        heightInCm = ((feet * 12) + inches) * 2.5;
        heightInMetres = heightInCm / 100.0;

        //calculating the BMI value
        bmiVal = weight / (heightInMetres * heightInMetres);

        //displaying the calculated BMI value
        tvBmi.setText("BMI : " + bmiVal);
    }

    //function to view history after updating the selected BMI Record
    public void updateRecord(View view)
    {
        Intent intent = new Intent(this, viewBmiRecords.class);
        startActivity(intent);
    }
}