package com.example.fitme.fat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitme.MainActivity;
import com.example.fitme.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BFResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bf_result);

        final BFResultActivity ctx = this;

        final Intent intent = getIntent();

        // Displaying the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Body Fat Result");

        Button saveBtn = findViewById(R.id.bfResultSave);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = intent.getStringExtra("UPDATE_ID");
                int age = Integer.parseInt(intent.getStringExtra("AGE"));
                double height = Double.parseDouble(intent.getStringExtra("HEIGHT"));
                double waist = Double.parseDouble(intent.getStringExtra("WAIST"));
                double hip = Double.parseDouble(intent.getStringExtra("HIP"));
                double neck = Double.parseDouble(intent.getStringExtra("NECK"));
                double weight = Double.parseDouble(intent.getStringExtra("WEIGHT"));
                double bodyFat = Double.parseDouble(intent.getStringExtra("BODYFAT"));

                BFCalculator calculator = new BFCalculator();
                calculator.setAge(age);
                calculator.setHeight(height);
                calculator.setWaist(waist);
                calculator.setHip(hip);
                calculator.setNeck(neck);
                calculator.setWeight(weight);
                calculator.setBodyFat(bodyFat);

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("");


                DatabaseReference bodyFatRef = ref.child("bodyFats");
                if(id==null) {
                    DatabaseReference newBodyFatRef = bodyFatRef.push();

                    bodyFatRef.setValue(calculator);
                } else {
                    DatabaseReference bodyFatRefExist = bodyFatRef.child(id);

                    bodyFatRefExist.setValue(calculator);
                }

                final BFCalculatorToast confirm = new BFCalculatorToast(ctx,"Successfully saved the result.");

                confirm.setConfirmListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirm.hide();

                        Intent intent =  new Intent(ctx, BFRecordsActivity.class);
                        startActivity(intent);
                    }
                });

                confirm.setCancelListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirm.hide();

                        Intent intent =  new Intent(ctx, MainActivity.class);
                        startActivity(intent);
                    }
                });

                confirm.show();
            }
        });


        Button cancelBtn = findViewById(R.id.bfResultCancel);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(ctx, MainActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}