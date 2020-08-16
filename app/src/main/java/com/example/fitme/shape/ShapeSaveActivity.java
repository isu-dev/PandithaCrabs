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

public class ShapeSaveActivity extends AppCompatActivity {

    private int updateId = 0;

    private Double bustSize = 0.0;
    private Double hipSize = 0.0;
    private Double waistSize = 0.0;

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

        } catch (NumberFormatException e) {

        }


        // Displaying the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Adding event handlers for number inputs
        Integer[] textBoxIds = {R.id.bustMainInput, R.id.bustSubInput, R.id.waistMainInput, R.id.waistSubInput, R.id.hipMainInput, R.id.hipSubInput};
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
                        Bitmap bitmapImage = BitmapFactory.decodeResource(getResources(), R.drawable.pear);
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
        ShapeSaveActivity ctx = this;

        Button saveBtn = findViewById(R.id.shapeSaveContinue);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmationToast confirm = new ConfirmationToast(ctx, "Successfully saved your shape for today.");
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

            EditText bustMainInput = findViewById(R.id.bustMainInput);
            bustMainInput.setText(String.valueOf(Math.round(Math.ceil(this.bustSize))));

            EditText bustSubInput = findViewById(R.id.bustSubInput);
            bustSubInput.setText(String.valueOf(Math.round((this.bustSize % 1) * 100)));

            EditText waistMainInput = findViewById(R.id.waistMainInput);
            waistMainInput.setText(String.valueOf(Math.round(Math.ceil(this.waistSize))));

            EditText waistSubInput = findViewById(R.id.waistSubInput);
            waistSubInput.setText(String.valueOf(Math.round((this.waistSize % 1) * 100)));

            EditText hipMainInput = findViewById(R.id.hipMainInput);
            hipMainInput.setText(String.valueOf(Math.round(Math.ceil(this.hipSize))));

            EditText hipSubInput = findViewById(R.id.hipSubInput);
            hipSubInput.setText(String.valueOf(Math.round((this.hipSize % 1) * 100)));

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