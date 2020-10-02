package com.example.fitme.AppCommon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitme.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    //Declare widgets
    private EditText et_username, et_email, et_password, et_confirm_password;
    private TextView tv_already_have_an_account;
    private Button bt_register;
    private ProgressBar progressBar;
    private FirebaseAuth userAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        et_username = findViewById(R.id.et_username_register);
        et_email = findViewById(R.id.et_email_register);
        et_password = findViewById(R.id.et_password_register);
        et_confirm_password = findViewById(R.id.et_confirmpassword_register);

        tv_already_have_an_account = findViewById(R.id.tv_already_have_an_account_et);
        bt_register = findViewById(R.id.bt_register_in_register);

        progressBar = findViewById(R.id.pb_register);

        userAuth = FirebaseAuth.getInstance();

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userName = et_username.getText().toString().trim();
                String email = et_email.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                String confirmPassword = et_confirm_password.getText().toString().trim();
                registerUser(userName, email, password, confirmPassword);
            }
        });
    }

    //Validate UserName
    private boolean validateUserName(String userName) {

        String checkSpaces = "\\A\\w{1,20}\\z";
        if (userName.isEmpty()) {
            et_username.setError("Username is required");
            et_username.requestFocus();
            return false;
        } else if (userName.length() > 20) {
            et_username.setError("Username must below 20 Characters");
            et_username.requestFocus();
            return false;
        } else if (!userName.matches(checkSpaces)) {
            et_username.setError("No white spaces are allowed");
            et_username.requestFocus();
            return false;
        } else {
            et_username.setError(null);
            return true;
        }
    }

    //Validate email
    private boolean validateEmail(String email) {

        String checkSpaces = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (email.isEmpty()) {
            et_email.setError("Email is required");
            et_email.requestFocus();
            return false;
        } else if (!email.matches(checkSpaces)) {
            et_email.setError("Invalid Email");
            et_email.requestFocus();
            return false;
        } else {
            et_email.setError(null);
            return true;
        }
    }

    //Validate password
    private boolean validatePassword(String password, String confirmPassword) {
        if (password.isEmpty() | confirmPassword.isEmpty()) {
            et_password.setError("Password is required");
            et_confirm_password.setError("Confirm Password is required");
            et_password.requestFocus();
            et_confirm_password.requestFocus();
            return false;
        } else if (!(password.length() >= 6 & confirmPassword.length() >= 6)) {
            et_password.setError("Minimum length is 6");
            et_confirm_password.setError("Minimum length is 6");
            et_password.requestFocus();
            et_confirm_password.requestFocus();
            return false;

        } else if (password.contains(" ")) {
            et_password.setError("No Spaces are allowed");
            et_password.requestFocus();
            return false;
        } else if (!password.contentEquals(confirmPassword)) {
            et_confirm_password.setError("Password is not matched");
            et_confirm_password.requestFocus();
            return false;
        } else {
            et_password.setError(null);
            et_confirm_password.setError(null);
            return true;
        }
    }


    //Function to register user
    private void registerUser(final String userName, String email, String password, String confirmPassword) {
        if (!validateUserName(userName) | !validateEmail(email) | !validatePassword(password, confirmPassword)) {
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        userAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    String userId = userAuth.getCurrentUser().getUid();
                    Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_SHORT).show();
                    DatabaseReference userDetailsRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
                    userDetailsRef.child("User Name").setValue(userName);
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already registered with the given email", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

}