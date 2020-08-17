package com.example.fitme.fat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitme.MainActivity;
import com.example.fitme.R;

public class BFMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bf_main);

        Context ctx = this;

        Button calculatorButton = findViewById(R.id.bfCalculatorButton);
        calculatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(ctx, BFCalculatorActivity.class);
                startActivity(intent);
            }
        });


        Button recordsButton = findViewById(R.id.bfRecordsButton);
        recordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(ctx, BFRecordsActivity.class);
                startActivity(intent);
            }
        });


        ImageView homeButton = findViewById(R.id.bfHomeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(ctx, MainActivity.class);
                startActivity(intent);
            }
        });

        ImageView menuButton = findViewById(R.id.bfMenuButton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(ctx, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void goToResultPage(View view){
        Intent intent =  new Intent(this, BFResultActivity.class);
        startActivity(intent);
    }
}