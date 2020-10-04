package com.example.fitme.fat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.fitme.MainActivity;
import com.example.fitme.R;

public class BFCalculatorActivity extends AppCompatActivity {

    public BFCalculator calculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bf_calculator);
        this.calculator = new BFCalculator();

        final BFCalculatorActivity ctx = this;

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

        RadioButton maleRadio = findViewById(R.id.radioButtonMale);
        maleRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ctx.calculator.setGender(BFGender.male);

            }
        });

        RadioButton femaleRadio = findViewById(R.id.radioButtonFemale);
        femaleRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ctx.calculator.setGender(BFGender.male);

            }
        });


        final EditText ageInput = findViewById(R.id.ageInput);
        ageInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                String age = ageInput.getText().toString();

                try {
                    int ageInt = Integer.parseInt(age);
                    ctx.calculator.setAge(ageInt);
                } catch (Exception e){

                }

                return true;
            }
        });


        final EditText height = findViewById(R.id.heightInput);
        height.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                String height = ageInput.getText().toString();

                try {
                    double heightDouble = Double.parseDouble(height);
                    ctx.calculator.setHeight(heightDouble);
                } catch (Exception e){

                }

                return true;
            }
        });


        final EditText neck = findViewById(R.id.neckInput);
        neck.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                String neck = ageInput.getText().toString();

                try {
                    double neckDouble = Double.parseDouble(neck);
                    ctx.calculator.setNeck(neckDouble);
                } catch (Exception e){

                }

                return true;
            }
        });
        
        final EditText hip = findViewById(R.id.hipInput);
        hip.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                String hip = ageInput.getText().toString();

                try {
                    double hipDouble = Double.parseDouble(hip);
                    ctx.calculator.setHip(hipDouble);
                } catch (Exception e){

                }

                return true;
            }
        });


        final EditText weight = findViewById(R.id.weightInput);
        weight.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                String weight = ageInput.getText().toString();

                try {
                    double weightDouble = Double.parseDouble(weight);
                    ctx.calculator.setWeight(weightDouble);
                } catch (Exception e){

                }

                return true;
            }
        });

        Intent parentIntent = getIntent();
        final String id = parentIntent.getStringExtra("UPDATE_ID");


        Button calButton = findViewById(R.id.bfCalBFButton);
        calButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctx.calculator.setBodyFat(ctx.calculator.calculate());

                Intent intent =  new Intent(ctx, BFResultActivity.class);
                intent.putExtra("AGE", ctx.calculator.getAge());
                intent.putExtra("HEIGHT", ctx.calculator.getHeight());
                intent.putExtra("WAIST", ctx.calculator.getWaist());
                intent.putExtra("HIP", ctx.calculator.getHip());
                intent.putExtra("NECK", ctx.calculator.getNeck());
                intent.putExtra("WEIGHT", ctx.calculator.getWeight());
                intent.putExtra("BODYFAT", ctx.calculator.getBodyFat());
                intent.putExtra("UPDATE_ID", id);
                startActivity(intent);
            }
        });


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