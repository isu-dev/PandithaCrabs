package com.example.fitme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitme.fat.BFMainActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void goToBMICalculator(View view) {
        Intent intent = new Intent(this, BFMainActivity.class);
        startActivity(intent);
    }
}