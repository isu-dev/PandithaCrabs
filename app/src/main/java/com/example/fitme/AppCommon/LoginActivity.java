package com.example.fitme.AppCommon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.fitme.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private Button loginButtonLoginUI, registerButtonRegisterUi;
    private EditText emailInput, passwordInput;
    private ProgressBar progressBar;
    private FirebaseAuth userAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userAuth = FirebaseAuth.getInstance();

        loginButtonLoginUI = findViewById(R.id.loginBtn_InLogin);
        registerButtonRegisterUi = findViewById(R.id.register_Button_In_login);
        progressBar = findViewById(R.id.progress_bar_login);

        emailInput = findViewById(R.id.email_input_Login);
        passwordInput = findViewById(R.id.password_input_Login);
    }

    public void loginProcess(View view) {
        String loginEmail = emailInput.getText().toString().trim();
        String loginPassword = passwordInput.getText().toString().trim();

        if (loginEmail.isEmpty()) {
            emailInput.setError("Email is required");
            emailInput.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(loginEmail).matches()) {
            emailInput.setError("Please enter a valid email");
            emailInput.requestFocus();
            return;
        }

        if (loginPassword.isEmpty()) {
            passwordInput.setError("Password is required");
            passwordInput.requestFocus();
            return;
        }

        if (loginPassword.length() < 6) {
            passwordInput.setError("Minimum length of password should be 6");
            passwordInput.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        userAuth.signInWithEmailAndPassword(loginEmail, loginPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    sendToMainMenu();
                } else {
                    Toast.makeText(getApplicationContext(), "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = userAuth.getCurrentUser();

        if(currentUser != null){
            sendToMainMenu();

        }
    }

    private void sendToMainMenu(){
        Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
        startActivity(intent);
        finish();
    }
}