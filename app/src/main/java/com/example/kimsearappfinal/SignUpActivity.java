package com.example.kimsearappfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    private EditText username;
    private EditText email;
    private EditText phone;
    private EditText password;
    private EditText verifyPassword;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize views
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone_number);
        password = findViewById(R.id.password);
        verifyPassword = findViewById(R.id.verify_password);
        signUpButton = findViewById(R.id.signup_button);

        // Set onClick listener for the SignUp button
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input
                String userName = username.getText().toString().trim();
                String userEmail = email.getText().toString().trim();
                String userPhone = phone.getText().toString().trim();
                String userPassword = password.getText().toString().trim();
                String userVerifyPassword = verifyPassword.getText().toString().trim();

                // Validate input
                if (userName.isEmpty()) {
                    username.setError("Please input username!");
                    username.requestFocus();
                } else if (userEmail.isEmpty()) {
                    email.setError("Please input email!");
                    email.requestFocus();
                } else if (!isValidEmail(userEmail)) {
                    email.setError("Please input a valid email!");
                    email.requestFocus();
                } else if (userPhone.isEmpty()) {
                    phone.setError("Please input phone number!");
                    phone.requestFocus();
                } else if (userPassword.isEmpty()) {
                    password.setError("Please input password!");
                    password.requestFocus();
                } else if (!userPassword.equals(userVerifyPassword)) {
                    verifyPassword.setError("Passwords do not match!");
                    verifyPassword.requestFocus();
                } else {
                    // Proceed with sign up process
                    Toast.makeText(SignUpActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, HomePageActivity.class);
                    startActivity(intent);
                    // TODO: Add code to handle the sign-up process (e.g., save user info, navigate to another activity)
                }
            }
        });
    }

    // Method to validate email address
    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    public void onBackButtonClicked(View view) {
        finish(); // Close the LoginActivity and return to the previous activity
    }
}
