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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    TextView tvBmi, tvBmiCategory;

    Button btnCalculate, btn_addRecord;

    int feet;
    int inches;
    int weight;
    double heightInMetres;
    double heightInCm;
    double bmiVal;
    String category;

    private FirebaseAuth userAuth;
    private FirebaseUser currentUser;
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
        tvBmiCategory = findViewById(R.id.tv_BmiCategory);
        btnCalculate = findViewById(R.id.btnCalculate);
        btn_addRecord = findViewById(R.id.btn_addRecord);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //ensuring that all fields are filled
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

                    category = findCategory(result);

                    //displaying the calculated BMI value and the category
                    tvBmi.setText("BMI : " + precision.format(result));
                    tvBmiCategory.setText("Category : " + category);
                }

            }
        });
    }


    //this function calculates the BMI value
    public double calculateBmi(int feet, int inches, int weight) {

        //converting the entered values to integers
        feet = Integer.parseInt(etHFeet.getText().toString());
        inches = Integer.parseInt(etHInches.getText().toString());
        weight = Integer.parseInt(etWKg.getText().toString());

        //calculating the height in metres
        heightInCm = ((feet * 12) + inches) * 2.5;
        heightInMetres = heightInCm / 100.0;

        DecimalFormat precision = new DecimalFormat("0.00");

        //calculating the BMI value
        double bmiVal = weight / (heightInMetres * heightInMetres);

        return bmiVal;

    }

    //finding the category of the the bmi value
    public String findCategory(double bmiVal) {

        if(bmiVal < 18.5)
            category = "Underweight";
        else if(bmiVal <= 24.9)
            category = "Normal";
        else if(bmiVal <= 29.9)
            category = "Overweight";
        else
            category = "Obese";

        return category;
    }

    //function to view all BMI calculation records after adding the record to the database
    public void addNewBmiRecord(View view)
    {
        dbRef = FirebaseDatabase.getInstance().getReference().child("bmiRecord");
        record.setHeightFeet(Integer.parseInt(etHFeet.getText().toString().trim()));
        record.setHeightInches(Integer.parseInt(etHInches.getText().toString().trim()));
        record.setWeightKg(Integer.parseInt(etWKg.getText().toString().trim()));
        record.setBmiVal(bmiVal);
        record.setBmiCategory(category);
        //dbRef.push().setValue(record);

        /*
        //dbRef = FirebaseDatabase.getInstance().getReference("BMI Records").child(currentUser.getUid());
        String uniqueIdForCalorieRecord = dbRef.push().getKey();
        //dbRef.setRecordId(uniqueIdForCalorieRecord);
        //inputsForCalorieIntake.setId(uniqueIdForCalorieRecord);
        dbRef.child(uniqueIdForCalorieRecord).child("Calculated Results").setValue(record).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Successfully inserted",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Error " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        dbRef.child(uniqueIdForCalorieRecord).child("Inputs").setValue(record).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Successfully inserted",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Error " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        //toast describing user action of add
        Intent intent = new Intent(this, viewBmiRecords.class);
        Toast.makeText(this, "Adding record to History", Toast.LENGTH_SHORT).show();
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