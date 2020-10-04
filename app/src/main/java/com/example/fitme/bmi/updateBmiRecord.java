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

    DatabaseReference dbRef;
    bmiRecord record;
    String recordid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_bmi_record);

        Intent intent = getIntent();
        recordid = intent.getStringExtra(recordID);


        //getting the value in the interface to variables inside the class
        etHFeet = findViewById(R.id.etHFeet);
        etHInches = findViewById(R.id.etHInches);
        etWKg = findViewById(R.id.etWKg);
        tvBmi = findViewById(R.id.tv_BmiValue);

        viewIndividual();
    }

    //this function takes the user back to all the previous BMI records
    public void openBmiHistory(View view)
    {
        Intent intent = new Intent(this, viewBmiRecords.class);
        Toast.makeText(this, "Opening History", Toast.LENGTH_SHORT).show(); //toast describing user action of opening history
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
        DatabaseReference updRef = FirebaseDatabase.getInstance().getReference().child("bmiRecord");
        updRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(recordid)) {

                        record.setHeightFeet(Integer.parseInt(etHFeet.getText().toString().trim()));
                        record.setHeightInches(Integer.parseInt(etHInches.getText().toString().trim()));
                        record.setWeightKg(Integer.parseInt(etWKg.getText().toString().trim()));
                        record.setBmiVal(bmiVal);

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

    private void viewIndividual() {
        dbRef = FirebaseDatabase.getInstance().getReference().child("bmiRecord").child(recordid);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if( snapshot.hasChildren() ) {
                    etHFeet.setText(snapshot.child("heightFeet").getValue().toString());
                    etHInches.setText(snapshot.child("heightInches").getValue().toString());
                    etWKg.setText(snapshot.child("weightKg").getValue().toString());
                    tvBmi.setText(snapshot.child("bmiVal").getValue().toString());
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