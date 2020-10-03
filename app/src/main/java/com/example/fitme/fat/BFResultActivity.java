package com.example.fitme.fat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitme.MainActivity;
import com.example.fitme.R;

public class BFResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bf_result);

        final Context ctx = this;

        // Displaying the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Body Fat Result");

        Button saveBtn = findViewById(R.id.bfResultSave);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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