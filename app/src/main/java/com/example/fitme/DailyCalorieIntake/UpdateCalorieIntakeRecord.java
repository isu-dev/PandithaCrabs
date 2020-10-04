package com.example.fitme.DailyCalorieIntake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitme.AppCommon.HomeActivity;
import com.example.fitme.AppCommon.LoginActivity;
import com.example.fitme.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UpdateCalorieIntakeRecord extends AppCompatActivity {
    private EditText et_age, et_height_in_feet, et_height_in_inches, et_weight;
    private Spinner sp_gender, sp_acivity_level, sp_type_of_person;
    private Button bt_calculate_calorie_intake;
    private FirebaseAuth userAuth;
    private FirebaseUser currentUser;
    InputsForCalorieIntake changedInputs;
    private Toolbar mainToolBar;
    private TextView toolBarTitle;
    private BottomNavigationView bottomNavigationView;

    private String inputsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_calorie_intake_record);

        mainToolBar = findViewById(R.id.main_tool_bar);
        toolBarTitle = mainToolBar.findViewById(R.id.toolbar_title);

        setSupportActionBar(mainToolBar);
        toolBarTitle.setText("Update Daily Calorie Intake");
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        userAuth = FirebaseAuth.getInstance();

        //Get intent from the My Calorie Records class
        Intent intent = getIntent();
        final InputsForCalorieIntake inputsForCalorieIntake = intent.getParcelableExtra("Inputs");

        //initializing the widgets
        et_age = findViewById(R.id.et_age_calorie_calculate);
        et_height_in_feet = findViewById(R.id.et_height_feet_calorie_calculate);
        et_height_in_inches = findViewById(R.id.et_height_inches_calorie_calculate);
        et_weight = findViewById(R.id.et_weight_calorie_calculate);
        sp_gender = findViewById(R.id.sp_gender_calorie_calculate);
        sp_acivity_level = findViewById(R.id.sp_activity_level_calorie_calculate);
        sp_type_of_person = findViewById(R.id.sp_type_of_person_calorie_calculate);

        bt_calculate_calorie_intake = findViewById(R.id.bt_calorie_calculate);

        et_age.setText(String.valueOf(inputsForCalorieIntake.getAge()));
        et_height_in_feet.setText(String.valueOf(inputsForCalorieIntake.getHeightInFeet()));
        et_height_in_inches.setText(String.valueOf(inputsForCalorieIntake.getHeightInInches()));
        et_weight.setText(String.valueOf(inputsForCalorieIntake.getWeight()));

        inputsId = inputsForCalorieIntake.getId();

        bt_calculate_calorie_intake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get upadated user inputs
                int age = Integer.parseInt(et_age.getText().toString());
                int height_in_feet = Integer.parseInt(et_height_in_feet.getText().toString());
                int height_in_inches = Integer.parseInt(et_height_in_inches.getText().toString());
                double weight = Double.parseDouble(et_weight.getText().toString());

                String gender = sp_gender.getSelectedItem().toString();
                String activity_level = sp_acivity_level.getSelectedItem().toString();
                String type_of_person = sp_type_of_person.getSelectedItem().toString();

                changedInputs = new InputsForCalorieIntake();
                changedInputs.setId(inputsId);
                changedInputs.setAge(age);
                changedInputs.setGender(gender);
                changedInputs.setHeightInFeet(height_in_feet);
                changedInputs.setHeightInInches(height_in_inches);
                changedInputs.setWeight(weight);
                changedInputs.setActivityLevel(activity_level);
                changedInputs.setTypeOfPerson(type_of_person);

                Daily_Calorie_Record calculatedRecord = calculateCalorieIntakeInUpdate(changedInputs);
                showCalculatedResultsDialog(calculatedRecord);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        currentUser = userAuth.getCurrentUser();

        if(currentUser == null){
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.bottom_nav_home:
                    Intent intent = new Intent(UpdateCalorieIntakeRecord.this, HomeActivity.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }
    };

    //Function to calculate daily calorie results for updated inputs
    private Daily_Calorie_Record calculateCalorieIntakeInUpdate(InputsForCalorieIntake inputsForCalorieIntake) {
        double height_in_centimeters = inputsForCalorieIntake.getHeightInFeet() * 30.48 + inputsForCalorieIntake.getHeightInInches() * 2.54;
        double weight_in_pounds = inputsForCalorieIntake.getWeight() * 2.205;
        int total_daily_energy_expenditure = 0;
        double resting_energy_expenditure = 0;
        int  protein_intake_grams = 0,fat_intake_grams = 0,carb_intake_grams = 0;
        double protein_intake_calories, carb_intake_calories, fat_intake_calories = 0;
        Daily_Calorie_Record daily_calorie_record ;

        if (inputsForCalorieIntake.getGender().equalsIgnoreCase("Male")) {
            resting_energy_expenditure = 10 * inputsForCalorieIntake.getWeight() + 6.25 * height_in_centimeters - 5 * inputsForCalorieIntake.getAge() + 5;
            /* resting_energy_expenditure = 10 * 88 + 6.25 * 183 - 5 * 29 + 5;*/
            switch (inputsForCalorieIntake.getActivityLevel().toLowerCase()) {
                case "sedentary":
                    total_daily_energy_expenditure = (int) Math.round(resting_energy_expenditure * 1.2);
                    break;
                case "light active":
                    total_daily_energy_expenditure = (int) Math.round(resting_energy_expenditure * 1.375);
                    break;
                case "moderate active":
                    total_daily_energy_expenditure = (int) Math.round(resting_energy_expenditure * 1.55);
                    break;
                case "very active":
                    total_daily_energy_expenditure = (int) Math.round(resting_energy_expenditure * 1.725);
                    break;
            }
        }

        if (inputsForCalorieIntake.getGender().equalsIgnoreCase("Female")) {
            resting_energy_expenditure = ((10 * inputsForCalorieIntake.getWeight()) + (6.25 * height_in_centimeters)) - (5 * inputsForCalorieIntake.getAge()) - 161;
            switch (inputsForCalorieIntake.getActivityLevel().toLowerCase()) {
                case "sedentary":
                    total_daily_energy_expenditure = (int) Math.round(resting_energy_expenditure * 1.2);
                    break;
                case "light active":
                    total_daily_energy_expenditure = (int) Math.round(resting_energy_expenditure * 1.375);
                    break;
                case "moderate active":
                    total_daily_energy_expenditure = (int) Math.round(resting_energy_expenditure * 1.55);
                    break;
                case "very active":
                    total_daily_energy_expenditure = (int) Math.round(resting_energy_expenditure * 1.725);
                    break;
            }
        }

        switch (inputsForCalorieIntake.getTypeOfPerson().toLowerCase()) {
            case "fatty person":
                protein_intake_grams = (int) Math.round(weight_in_pounds * 0.65);
                protein_intake_calories = protein_intake_grams * 4;
                fat_intake_calories = total_daily_energy_expenditure * 0.25;
                fat_intake_grams = (int) Math.round(fat_intake_calories / 9);
                carb_intake_calories = total_daily_energy_expenditure - (protein_intake_calories + fat_intake_calories);
                carb_intake_grams = (int) Math.round(carb_intake_calories / 4);
            case "lean body builder":
                protein_intake_grams = (int) Math.round(weight_in_pounds * 1);
                protein_intake_calories = protein_intake_grams * 4;
                fat_intake_calories = total_daily_energy_expenditure * 0.25;
                fat_intake_grams = (int) Math.round(fat_intake_calories / 9);
                carb_intake_calories = total_daily_energy_expenditure - (protein_intake_calories + fat_intake_calories);
                carb_intake_grams = (int) Math.round(carb_intake_calories / 4);
            case "normal person":
                protein_intake_grams = (int) Math.round(weight_in_pounds * 0.825);
                protein_intake_calories = protein_intake_grams * 4;
                fat_intake_calories = total_daily_energy_expenditure * 0.25;
                fat_intake_grams = (int) Math.round(fat_intake_calories / 9);
                carb_intake_calories = total_daily_energy_expenditure - (protein_intake_calories + fat_intake_calories);
                carb_intake_grams = (int) Math.round(carb_intake_calories / 4);
        }

        daily_calorie_record = new Daily_Calorie_Record();
        daily_calorie_record.setRecordId(changedInputs.getId());
        daily_calorie_record.setDailyCalorieIntake(total_daily_energy_expenditure);
        daily_calorie_record.setProteinIntake(protein_intake_grams);
        daily_calorie_record.setCarbIntake(carb_intake_grams);
        daily_calorie_record.setFatIntake(fat_intake_grams);
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        daily_calorie_record.setCurrentDate(dateformat.format(date));

        return daily_calorie_record;
    }

    //Show calculated results for updated inputs in a dialog box
    private void showCalculatedResultsDialog(final Daily_Calorie_Record daily_calorie_record) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View calculatedResultsDialog = inflater.inflate(R.layout.result_dialog_calorie_intake, null);
        builder.setView(calculatedResultsDialog);
        final TextView tv_daily_calorie_intake = calculatedResultsDialog.findViewById(R.id.tv_calculated_total_daily_calorie_intake_dialog);
        final TextView tv_daily_protein_intake = calculatedResultsDialog.findViewById(R.id.tv_calculated_daily_protein_intake_dialog);
        final TextView tv_daily_carb_intake = calculatedResultsDialog.findViewById(R.id.tv_calculated_daily_carb_intake_dialog);
        final TextView tv_daily_fat_intake = calculatedResultsDialog.findViewById(R.id.tv_calculated_daily_fat_intake_dialog);
        final TextView tv_record_date = calculatedResultsDialog.findViewById(R.id.tv_date_calclated_results_dialog);

        tv_daily_calorie_intake.setText(String.format("Total daily calorie intake = %s Calories", String.valueOf(daily_calorie_record.getDailyCalorieIntake())));
        tv_daily_protein_intake.setText(String.format("Total daily protein intake = %s g", String.valueOf(daily_calorie_record.getProteinIntake())));
        tv_daily_carb_intake.setText(String.format("Total daily carbohydrate intake = %s g", String.valueOf(daily_calorie_record.getCarbIntake())));
        tv_daily_fat_intake.setText(String.format("Total daily fat intake = %s g", String.valueOf(daily_calorie_record.getFatIntake())));
        tv_record_date.setText("Date = " + daily_calorie_record.getCurrentDate());

        final Button bt_save_results = calculatedResultsDialog.findViewById(R.id.bt_save_results_calorie_dialog);
        bt_save_results.setText("Update Results");
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        bt_save_results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDatabase(daily_calorie_record,changedInputs);
                alertDialog.dismiss();
            }
        });
    }

    //update database with calculated results for updated inputs
    private void updateDatabase(final Daily_Calorie_Record daily_calorie_record, final InputsForCalorieIntake inputsForCalorieIntake){
        DatabaseReference updateRef = FirebaseDatabase.getInstance().getReference("Calorie Records").child(currentUser.getUid());
        updateRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(daily_calorie_record.getRecordId())){
                    //Update Calculated Results
                    DatabaseReference dbCalorieIntakeUpdateRef = FirebaseDatabase.getInstance().getReference("Calorie Records").child(currentUser.getUid()).child(daily_calorie_record.getRecordId()).child("Calculated Results");
                    dbCalorieIntakeUpdateRef.setValue(daily_calorie_record);

                    //update inputs
                    DatabaseReference dbInputUpdateRef = FirebaseDatabase.getInstance().getReference("Calorie Records").child(currentUser.getUid()).child(daily_calorie_record.getRecordId()).child("Inputs");
                    dbInputUpdateRef.setValue(inputsForCalorieIntake);
                    Toast.makeText(getApplicationContext(),"Successfully Updated",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }
}