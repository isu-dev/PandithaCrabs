package com.example.fitme;

import androidx.appcompat.app.AppCompatActivity;

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

    public void goToShapeCalculator(View view){
        Intent intent =  new Intent(this, ShapeSaveActivity.class);
        intent.putExtra("UPDATE_ID", 0);
        startActivity(intent);
    }
}