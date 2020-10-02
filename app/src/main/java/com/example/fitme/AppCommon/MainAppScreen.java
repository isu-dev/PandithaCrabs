package com.example.fitme.AppCommon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fitme.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainAppScreen extends AppCompatActivity {
    private Button loginBtn,registerBtn;
    private FirebaseAuth userAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app_screen);

        loginBtn = findViewById(R.id.login_btn_main_screen);
        registerBtn = findViewById(R.id.register_btn_main_screen);
        userAuth = FirebaseAuth.getInstance();
    }

    public void directToLogin(View view){
        Intent intent = new Intent(MainAppScreen.this,LoginActivity.class);
        startActivity(intent);
    }

    public void directToregister(View view){
        startActivity(new Intent(this,RegisterActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = userAuth.getCurrentUser();

        if(currentUser != null){
            startActivity(new Intent(this,HomeActivity.class));
        }
    }
}