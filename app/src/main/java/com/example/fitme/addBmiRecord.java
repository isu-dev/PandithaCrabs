package com.example.fitme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class addBmiRecord extends AppCompatActivity {

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
        setContentView(R.layout.activity_add_bmi_record);

        //getting the value in the interface to variables inside the class
        etHFeet = findViewById(R.id.etHFeet);
        etHInches = findViewById(R.id.etHInches);
        etWKg = findViewById(R.id.etWKg);
        tvBmi = findViewById(R.id.tv_BmiValue);
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

    //function to view all BMI calculation records after adding the record to the database
    public void addNewBmiRecord(View view)
    {
        Intent intent = new Intent(this, viewBmiRecords.class);
        Toast.makeText(this, "Adding record to History", Toast.LENGTH_SHORT).show(); //toast describing user action of add
        startActivity(intent);
    }

    //this function takes the user back to all the previous BMI records
    public void openBmiHistory(View view)
    {
        Intent intent = new Intent(this, viewBmiRecords.class);
        Toast.makeText(this, "Opening History", Toast.LENGTH_SHORT).show(); //toast describing user action of opening history
        startActivity(intent);
    }
}