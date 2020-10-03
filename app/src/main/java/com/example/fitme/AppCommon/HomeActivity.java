package com.example.fitme.AppCommon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitme.DailyCalorieIntake.MyCalorieRecords;
import com.example.fitme.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Toolbar mainToolBar;
    private TextView toolBarTitle;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FirebaseAuth userAuth;
    private FirebaseUser user;
    private TextView userNameInHeader;
    private LinearLayout headerLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userAuth = FirebaseAuth.getInstance();
        user = userAuth.getCurrentUser();

        mainToolBar = findViewById(R.id.main_tool_bar);
        toolBarTitle = mainToolBar.findViewById(R.id.toolbar_title);

        setSupportActionBar(mainToolBar);
        toolBarTitle.setText("FitMe");
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(HomeActivity.this);
        userNameInHeader = navigationView.getHeaderView(0).findViewById(R.id.tv_userName_inHeader);
        setHeaderDetails(navigationView);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,mainToolBar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //load home fragment
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
        }
    }

    //set header details in navigation drawer
    public void setHeaderDetails(NavigationView navigationView){
        String UserId = user.getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(UserId).child("User Name");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userName = (String) snapshot.getValue();
                userNameInHeader.setText(userName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        user = userAuth.getCurrentUser();

        if(user == null){
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }
    }

    //Drawer menu navigation item selection
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem Item) {
        switch (Item.getItemId()){
            case R.id.signout:
                userAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this,MainAppScreen.class));
                break;
            case R.id.bmi_records:
                Toast.makeText(HomeActivity.this,"BMI records",Toast.LENGTH_LONG).show();
                break;
            case R.id.fat_records:
                Toast.makeText(HomeActivity.this,"Fat records",Toast.LENGTH_LONG).show();
                break;
            case R.id.calorie_intake_records:
                startActivity(new Intent(getApplicationContext(), MyCalorieRecords.class));
                break;
            case R.id.body_shape_records:
                Toast.makeText(HomeActivity.this,"Body shape",Toast.LENGTH_LONG).show();
                break;
            case R.id.settings:
//                navigationView.setCheckedItem(R.id.SignOut);
                Toast.makeText(HomeActivity.this,"Settings",Toast.LENGTH_LONG).show();
                break;
            case R.id.help_and_feedback:
                Toast.makeText(HomeActivity.this,"Help and Feedback",Toast.LENGTH_LONG).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}