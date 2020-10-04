package com.example.fitme.bmi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitme.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

public class addBmiRecord extends AppCompatActivity {

    EditText etHFeet;
    EditText etHInches;
    EditText etWKg;

    TextView tvBmi;

    Button btnCalculate;

    int feet;
    int inches;
    int weight;
    double heightInMetres;
    double heightInCm;
    double bmiVal;

    DatabaseReference dbRef;
    bmiRecord record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bmi_record);

        //getting the value in the interface to variables inside the class
        etHFeet = findViewById(R.id.etHFeet);
        etHInches = findViewById(R.id.etHInches);
        etWKg = findViewById(R.id.etWKg);
        tvBmi = findViewById(R.id.tv_BmiValue);


        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if( (etHFeet.getText().length() == 0) && (etHInches.getText().length() != 0) && (etWKg.getText().length() != 0) ) {
                    Toast.makeText(getApplicationContext(), "Please enter value for height in whole feet", Toast.LENGTH_SHORT).show();
                    return;
                } else if( (etHFeet.getText().length() != 0) && (etHInches.getText().length() == 0) && (etWKg.getText().length() != 0) ) {
                    Toast.makeText(getApplicationContext(), "Please enter value for height in inches", Toast.LENGTH_SHORT).show();
                    return;
                } else if( (etHFeet.getText().length() != 0) && (etHInches.getText().length() != 0) && (etWKg.getText().length() == 0) ) {
                    Toast.makeText(getApplicationContext(), "Please enter value for weight in kg", Toast.LENGTH_SHORT).show();
                    return;
                } else if( (etHFeet.getText().length() == 0) && (etHInches.getText().length() == 0) && (etWKg.getText().length() == 0) ) {
                    Toast.makeText(getApplicationContext(), "Please enter values", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    //converting the entered values to integers
                    feet = Integer.parseInt(etHFeet.getText().toString());
                    inches = Integer.parseInt(etHInches.getText().toString());
                    weight = Integer.parseInt(etWKg.getText().toString());

                    DecimalFormat precision = new DecimalFormat("0.00");
                    double result = calculateBmi(feet, inches, weight);

                    //displaying the calculated BMI value
                    //tvBmi.setText("BMI : " +  bmiVal);
                    tvBmi.setText(precision.format(result));
                }
            }
        });
    }


    //this function calculates the BMI value
    public double calculateBmi(int feet, int inches, int weight)
    {

        //calculating the height in metres
        heightInCm = ((feet * 12) + inches) * 2.5;
        heightInMetres = heightInCm / 100.0;

        //calculating the BMI value
        bmiVal = weight / (heightInMetres * heightInMetres);

        return bmiVal;
    }

    //function to view all BMI calculation records after adding the record to the database
    public void addNewBmiRecord(View view)
    {
        dbRef = FirebaseDatabase.getInstance().getReference().child("bmiRecord");
        record.setHeightFeet(Integer.parseInt(etHFeet.getText().toString().trim()));
        record.setHeightInches(Integer.parseInt(etHInches.getText().toString().trim()));
        record.setWeightKg(Integer.parseInt(etWKg.getText().toString().trim()));
        record.setBmiVal(bmiVal);
        dbRef.push().setValue(record);
        //dbRef.child("s123").setValue(record);

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