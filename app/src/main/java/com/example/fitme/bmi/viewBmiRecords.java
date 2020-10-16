package com.example.fitme.bmi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.fitme.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class viewBmiRecords extends AppCompatActivity {

    private static final String TAG = "View BMI Records";

    DatabaseReference dbRef;
    String recordId;

    private ArrayList hFeet = new ArrayList<>();
    private ArrayList hInches = new ArrayList<>();
    private ArrayList wKg = new ArrayList<>();
    private ArrayList bmis = new ArrayList<>();
    private ArrayList<String> categories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bmi_records);

       // getDetails();
    }

    //this function takes the user to the interface to calculate BMI
    public void calculateNewBmi(View view)
    {
        Intent intent = new Intent(this, addBmiRecord.class);
        Toast.makeText(this, "Opening BMI Calculator", Toast.LENGTH_SHORT).show(); //toast describing user action of calculating new
        startActivity(intent);
    }

    //this function takes the user back to the main page
    public void backToMainPage(View view)
    {
        Intent intent = new Intent(this, viewBmiRecords.class);
        Toast.makeText(this, "Opening Homepage", Toast.LENGTH_SHORT).show(); //toast describing user action of moving back to the homepage
        startActivity(intent);
    }

    //this function allows the user to update a single BMI record
    public void updateBmi(View view)
    {
        Intent intent = new Intent(this, updateBmiRecord.class);
        //intent.putExtra("recordID", recordId);
        Toast.makeText(this, "Opening Update View", Toast.LENGTH_SHORT).show(); //toast describing user action of proceed to update
        startActivity(intent);
    }

    //this function allows the user to delete a single BMI record
    public void deleteBmi(View view)
    {
        DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("bmiRecord");
        delRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(recordId)) {
                    dbRef = FirebaseDatabase.getInstance().getReference().child("bmiRecord").child(recordId);
                    dbRef.removeValue();
                } else {
                    Toast.makeText(viewBmiRecords.this, "No source to delete", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Intent intent = new Intent(this, viewBmiRecords.class);
        Toast.makeText(this, "Deleting Record from History", Toast.LENGTH_SHORT).show(); //toast describing user action of delete
        startActivity(intent);
    }

    private void getDetails() {
        dbRef = FirebaseDatabase.getInstance().getReference().child("bmiRecord");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if( snapshot.hasChildren()) {
                    hFeet.add(snapshot.child("heightFeet").getValue());
                    hInches.add(snapshot.child("heightInches").getValue());
                    wKg.add(snapshot.child("weightKg").getValue());
                    bmis.add(snapshot.child("bmiVal").getValue());
                    categories.add(snapshot.child("bmiCatory").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: started");
        RecyclerView recyclerView = findViewById(R.id.recordContainer);
        bmiRecordsAdapter adapter = new bmiRecordsAdapter(hFeet, hInches, wKg, bmis, categories);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}