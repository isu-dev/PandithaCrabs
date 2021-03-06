package com.example.fitme.bmi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class updateBmiRecord extends AppCompatActivity {

    EditText etHFeet;
    EditText etHInches;
    EditText etWKg;

    TextView tvBmi, tvBmiCategory;

    int feet;
    int inches;
    int weight;
    double heightInMetres;
    double heightInCm;
    double bmiVal;
    String category;


    DatabaseReference dbRef;
    bmiRecord record;
    String recordid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_bmi_record);

        /*Intent intent = getIntent();
        recordid = intent.getStringExtra("recordID");*/


        //getting the value in the interface to variables inside the class
        etHFeet = findViewById(R.id.etHFeet);
        etHInches = findViewById(R.id.etHInches);
        etWKg = findViewById(R.id.etWKg);
        tvBmi = findViewById(R.id.tv_BmiValue);
        tvBmiCategory = findViewById(R.id.tv_BmiCategory);

        //viewIndividual();

        if((etHFeet.getText().length() != 0) && (etHInches.getText().length() != 0) && (etWKg.getText().length() != 0)) {
            feet = Integer.parseInt(etHFeet.getText().toString());
            inches = Integer.parseInt(etHInches.getText().toString());
            weight = Integer.parseInt(etWKg.getText().toString());

            DecimalFormat precision = new DecimalFormat("0.00");
            double result = calculateBmi(feet, inches, weight);

            category = findCategory(result);

            //displaying the calculated BMI value and the category
            tvBmi.setText("BMI : " + precision.format(result));
            tvBmiCategory.setText("Category : " + category);
        } else {
            Toast.makeText(this, "Please enter values", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    //this function takes the user back to all the previous BMI records
    public void openBmiHistory(View view)
    {
        Intent intent = new Intent(this, viewBmiRecords.class);
        Toast.makeText(this, "Opening History", Toast.LENGTH_SHORT).show(); //toast describing user action of opening history
        startActivity(intent);
    }

    //this function calculates the BMI value
    public double calculateBmi(int feet, int inches, int weight)
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

    //function to view history after updating the selected BMI Record
    public void updateRecord(View view)
    {
        DatabaseReference updRef = FirebaseDatabase.getInstance().getReference().child("bmiRecord");
        updRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(recordid)) {

                        record.setHeightFeet(Integer.parseInt(etHFeet.getText().toString().trim()));
                        record.setHeightInches(Integer.parseInt(etHInches.getText().toString().trim()));
                        record.setWeightKg(Integer.parseInt(etWKg.getText().toString().trim()));
                        record.setBmiVal(bmiVal);
                        record.setBmiCategory(category);

                        dbRef = FirebaseDatabase.getInstance().getReference().child("bmiRecord").child(recordid);
                        dbRef.setValue(record);

                } else {
                    Toast.makeText(updateBmiRecord.this, "No record to update", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Intent intent = new Intent(this, viewBmiRecords.class);
        Toast.makeText(this, "Updating Record", Toast.LENGTH_SHORT).show(); //toast describing user action of update
        startActivity(intent);
    }

    //view details of selected entry
    private void viewIndividual() {
        dbRef = FirebaseDatabase.getInstance().getReference().child("bmiRecord").child(recordid);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if( snapshot.hasChild(recordid)) {
                    etHFeet.setText(snapshot.child("heightFeet").getValue().toString());
                    etHInches.setText(snapshot.child("heightInches").getValue().toString());
                    etWKg.setText(snapshot.child("weightKg").getValue().toString());
                    tvBmi.setText(snapshot.child("bmiVal").getValue().toString());
                    tvBmiCategory.setText(snapshot.child("bmiCatory").getValue().toString());
                } else {
                    Toast.makeText(updateBmiRecord.this, "No source to display", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}