package com.example.fitme.shape;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitme.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShapeSaveActivity extends AppCompatActivity {

    private int updateId = 0;

    private Double bustSize = 0.0;
    private Double hipSize = 0.0;
    private Double waistSize = 0.0;
    private Double highHipSize = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape_save);

        Intent intent = getIntent();

        String updateIdStr = intent.getStringExtra("UPDATE_ID");
        try {
            Integer updateId = Integer.parseInt(updateIdStr);
            this.updateId = updateId;
            this.bustSize = Double.parseDouble(intent.getStringExtra("BUST_SIZE"));
            this.waistSize = Double.parseDouble(intent.getStringExtra("WAIST_SIZE"));
            this.hipSize = Double.parseDouble(intent.getStringExtra("HIP_SIZE"));
            this.highHipSize = Double.parseDouble(intent.getStringExtra("HIGH_HIP_SIZE"));

        } catch (NumberFormatException e) {

        }


        // Displaying the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Shape calculator = new Shape();

        // Adding event handlers for number inputs
        final Integer[] textBoxIds = {R.id.bustMainInput, R.id.bustSubInput, R.id.waistMainInput, R.id.waistSubInput, R.id.hipMainInput, R.id.hipSubInput, R.id.highHipSubInput, R.id.highHipMainInput};
        for (Integer textBoxId : textBoxIds) {

            EditText editText = findViewById(textBoxId);
            editText.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {   //Convert the Text to String
                    boolean validated = true;
                    for (Integer inputId : textBoxIds) {
                        EditText editText = findViewById(inputId);
                        String valueStr = editText.getText().toString();
                        try {
                            Integer value = Integer.parseInt(valueStr);
                        } catch (NumberFormatException e) {
                            validated = false;
                        }
                    }

                    if (validated) {
                        ImageView imgView = findViewById(R.id.shapeSaveImage);

                        Shape calculator = new Shape();

                        EditText bustMainInput = findViewById(R.id.bustMainInput);
                        int bustFeat = Integer.parseInt(bustMainInput.getText().toString());

                        EditText bustSubInput = findViewById(R.id.bustSubInput);
                        int bustInch = Integer.parseInt(bustSubInput.getText().toString()) + (bustFeat*12);
                        calculator.setBustSize(bustInch);

                        EditText waistMainInput = findViewById(R.id.waistMainInput);
                        int waistFeat = Integer.parseInt(waistMainInput.getText().toString());

                        EditText waistSubInput = findViewById(R.id.waistSubInput);
                        int waistInch = Integer.parseInt(waistSubInput.getText().toString()) + (waistFeat*12);
                        calculator.setWaistSize(waistInch);

                        EditText hipMainInput = findViewById(R.id.hipMainInput);
                        int hipFeat = Integer.parseInt(hipMainInput.getText().toString());

                        EditText hipSubInput = findViewById(R.id.hipSubInput);
                        int hipInch = Integer.parseInt(hipSubInput.getText().toString()) + (hipFeat*12);
                        calculator.setHipSize(hipInch);

                        EditText highHipMainInput = findViewById(R.id.highHipMainInput);
                        int highHipFeat = Integer.parseInt(highHipMainInput.getText().toString());

                        EditText highHipSubInput = findViewById(R.id.highHipSubInput);
                        int highHipInch = Integer.parseInt(highHipSubInput.getText().toString()) + (highHipFeat*12);
                        calculator.setHighHipSize(highHipInch);



                        Bitmap bitmapImage = BitmapFactory.decodeResource(getResources(), calculator.calculate());

                        imgView.setImageBitmap(bitmapImage);
                        imgView.invalidate();
                    }
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
            });
        }

        // Adding event listeners for buttons
        final ShapeSaveActivity ctx = this;

        Button saveBtn = findViewById(R.id.shapeSaveContinue);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("");


                DatabaseReference shapeRef = ref.child("shapes");
                if(updateId==0) {
                    DatabaseReference newShapeRef = shapeRef.push();

                    newShapeRef.setValue(calculator);
                } else {
                    DatabaseReference bodyFatRefExist = shapeRef.child(String.valueOf(updateId));

                    bodyFatRefExist.setValue(calculator);
                }

                final ConfirmationToast confirm = new ConfirmationToast(ctx, "Successfully saved your shape for today.");

                confirm.setCancelListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirm.hide();

                        if (ctx.updateId != 0) {
                            ctx.goToList();
                        } else {
                            onBackPressed();
                        }
                    }
                });

                confirm.setConfirmListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirm.hide();
                        ctx.goToList();
                    }
                });

                confirm.show();
            }
        });

        Button cancelBtn = findViewById(R.id.shapeSaveCancel);

        if (this.updateId == 0) {
            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        } else {
            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ctx.goToList();
                }
            });
        }

        if (this.updateId != 0) {

            double bustFeats= Math.round(this.bustSize/12);
            EditText bustMainInput = findViewById(R.id.bustMainInput);
            bustMainInput.setText(String.valueOf(bustFeats));

            double bustInches = this.hipSize - (bustFeats*12);
            EditText bustSubInput = findViewById(R.id.bustSubInput);
            bustSubInput.setText(String.valueOf(bustInches));

            double waistFeats= Math.round(this.waistSize/12);
            EditText waistMainInput = findViewById(R.id.waistMainInput);
            waistMainInput.setText(String.valueOf(waistFeats));

            double waistInches = this.hipSize - (waistFeats*12);
            EditText waistSubInput = findViewById(R.id.waistSubInput);
            waistSubInput.setText(String.valueOf(waistInches));

            double hipFeats= Math.round(this.hipSize/12);
            EditText hipMainInput = findViewById(R.id.hipMainInput);
            hipMainInput.setText(String.valueOf(hipFeats));

            double hipInches = this.hipSize - (hipFeats*12);
            EditText hipSubInput = findViewById(R.id.hipSubInput);
            hipSubInput.setText(String.valueOf(hipInches));

            double highHipFeats= Math.round(this.highHipSize/12);
            EditText highHipMainInput = findViewById(R.id.highHipMainInput);
            highHipMainInput.setText(String.valueOf(highHipFeats));

            double highHipInches = this.hipSize - (highHipFeats*12);
            EditText highHipSubInput = findViewById(R.id.highHipSubInput);
            highHipSubInput.setText(String.valueOf(highHipInches));

            hipSubInput.notify();


            setTitle("Update Shape");
        } else {

            setTitle("Today Shape");
        }


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.shape_menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.shapeListIcon:

                this.goToList();

                return true;

            default:

                return super.onOptionsItemSelected(item);
        }
    }

    public void goToList() {

        Intent intent = new Intent(this, ShapeListActivity.class);
        finish();
        startActivity(intent);
    }
}