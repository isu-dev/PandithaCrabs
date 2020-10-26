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

        RadioButton maleRadio = findViewById(R.id.radioButtonMale);
        maleRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    ctx.calculator.setGender(BFGender.male);
                }

            }
        });

        RadioButton femaleRadio = findViewById(R.id.radioButtonFemale);
        femaleRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    ctx.calculator.setGender(BFGender.female);
                }
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

                return false;
            }
        });


        final EditText heightInput = findViewById(R.id.heightInput);
        heightInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                String height = heightInput.getText().toString();

                try {
                    double heightDouble = Double.parseDouble(height);
                    ctx.calculator.setHeight(heightDouble);
                } catch (Exception e){

                }

                return false;
            }
        });


        final EditText neckInput = findViewById(R.id.neckInput);
        neckInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                String neck = neckInput.getText().toString();

                try {
                    double neckDouble = Double.parseDouble(neck);
                    ctx.calculator.setNeck(neckDouble);
                } catch (Exception e){

                }

                return false;
            }
        });

        final EditText waistInput = findViewById(R.id.waistInput);
        waistInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                String waist = waistInput.getText().toString();

                try {
                    double waistDouble = Double.parseDouble(waist);
                    ctx.calculator.setWaist(waistDouble);
                } catch (Exception e){

                }

                return false;
            }
        });
        
        final EditText hipInput = findViewById(R.id.hipInput);
        hipInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                String hip = hipInput.getText().toString();

                try {
                    double hipDouble = Double.parseDouble(hip);
                    ctx.calculator.setHip(hipDouble);
                } catch (Exception e){

                }

                return false;
            }
        });


        final EditText weightInput = findViewById(R.id.weightInput);
        weightInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                String weight = weightInput.getText().toString();

                try {
                    double weightDouble = Double.parseDouble(weight);
                    ctx.calculator.setWeight(weightDouble);
                } catch (Exception e){

                }

                return false;
            }
        });

        Intent parentIntent = getIntent();
        final String id = parentIntent.getStringExtra("UPDATE_ID");

        if(id!=null){
            Intent intent = ctx.getIntent();
            ageInput.setText(intent.getStringExtra("AGE"));
            ctx.calculator.setAge(Integer.parseInt(intent.getStringExtra("AGE")));
            heightInput.setText(intent.getStringExtra("HEIGHT"));
            ctx.calculator.setHeight(Double.parseDouble(intent.getStringExtra("HEIGHT")));
            neckInput.setText(intent.getStringExtra("NECK"));
            ctx.calculator.setNeck(Double.parseDouble(intent.getStringExtra("NECK")));
            waistInput.setText(intent.getStringExtra("WAIST"));
            ctx.calculator.setWaist(Double.parseDouble(intent.getStringExtra("WAIST")));
            hipInput.setText(intent.getStringExtra("HIP"));
            ctx.calculator.setHip(Double.parseDouble(intent.getStringExtra("HIP")));
            weightInput.setText(intent.getStringExtra("WEIGHT"));
            ctx.calculator.setWeight(Double.parseDouble(intent.getStringExtra("WEIGHT")));

            String sex = intent.getStringExtra("SEX");
            if(sex.equals("Male")){
                ctx.calculator.setGender(BFGender.male);
                maleRadio.setChecked(true);

            } else {
                ctx.calculator.setGender(BFGender.female);
                femaleRadio.setChecked(true);
            }
        }


        Button calButton = findViewById(R.id.calculateButton);
        calButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctx.calculator.setBodyFat(ctx.calculator.calculate());

                Intent intent =  new Intent(ctx, BFResultActivity.class);
                intent.putExtra("AGE", String.valueOf(ctx.calculator.getAge()));
                intent.putExtra("HEIGHT", String.valueOf(ctx.calculator.getHeight()));
                intent.putExtra("WAIST", String.valueOf(ctx.calculator.getWaist()));
                intent.putExtra("HIP", String.valueOf(ctx.calculator.getHip()));
                intent.putExtra("NECK", String.valueOf(ctx.calculator.getNeck()));
                intent.putExtra("WEIGHT", String.valueOf(ctx.calculator.getWeight()));
                intent.putExtra("SEX", ctx.calculator.getSex());
                double bodyFat = ctx.calculator.calculate();
                ctx.calculator.setBodyFat(bodyFat);

                intent.putExtra("BODYFAT", String.valueOf(ctx.calculator.getBodyFat()));
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