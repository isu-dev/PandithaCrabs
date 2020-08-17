package com.example.fitme.fat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.fitme.MainActivity;
import com.example.fitme.R;

public class BFCalculatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bf_calculator);


        Context ctx = this;

        ImageView homeButton = findViewById(R.id.bfCalHomeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(ctx, MainActivity.class);
                startActivity(intent);
            }
        });

        ImageView menuButton = findViewById(R.id.bfCalMenuButton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(ctx, BFMainActivity.class);
                startActivity(intent);
            }
        });


        Button calButton = findViewById(R.id.bfCalBFButton);
        calButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(ctx, BFResultActivity.class);
                startActivity(intent);
            }
        });
    }

    public void goToResultPage(View view){
        Intent intent =  new Intent(this, BFResultActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.fat_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fatListIcon:

                Intent intent =  new Intent(this, BFRecordsActivity.class);
                startActivity(intent);

                return true;

            default:

                return super.onOptionsItemSelected(item);
        }
    }
}