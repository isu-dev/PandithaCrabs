package com.example.fitme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


import com.example.fitme.fat.BFMainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.fitme.shape.ShapeSaveActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    public void goToBMICalculator(View view) {
        Intent intent = new Intent(this, viewBmiRecords.class);

    public void goToShapeCalculator(View view){
        Intent intent =  new Intent(this, ShapeSaveActivity.class);
        intent.putExtra("UPDATE_ID", 0);
        startActivity(intent);
    }
}