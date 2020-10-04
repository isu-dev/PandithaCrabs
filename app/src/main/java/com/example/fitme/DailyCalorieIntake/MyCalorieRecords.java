package com.example.fitme.DailyCalorieIntake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.List;

public class MyCalorieRecords extends AppCompatActivity {
    private List<Daily_Calorie_Record> allRecords;
    private RecyclerView recyclerView;
    private CalorieRecordAdapter calorieRecordAdapter;
    private DatabaseReference databaseReference;
    private FirebaseAuth userAuth;
    private FirebaseUser currentUser;
    private Toolbar mainToolBar;
    private TextView toolBarTitle;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_calorie_records);

        userAuth = FirebaseAuth.getInstance();
        currentUser = userAuth.getCurrentUser();

        mainToolBar = findViewById(R.id.main_tool_bar);
        toolBarTitle = mainToolBar.findViewById(R.id.toolbar_title);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        setSupportActionBar(mainToolBar);
        toolBarTitle.setText("My Calorie Records");
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = findViewById(R.id.rv_calorie_record_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        /*recyclerView.setHasFixedSize(true);*/

        allRecords = new ArrayList<>();

        databaseReference =  FirebaseDatabase.getInstance().getReference("Calorie Records").child(currentUser.getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Daily_Calorie_Record daily_calorie_record = dataSnapshot.child("Calculated Results").getValue(Daily_Calorie_Record.class);
                    allRecords.add(daily_calorie_record);
                }
                calorieRecordAdapter = new CalorieRecordAdapter( allRecords);
                recyclerView.setAdapter(calorieRecordAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
                    Intent intent = new Intent(MyCalorieRecords.this, HomeActivity.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }
    };
}