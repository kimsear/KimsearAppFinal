package com.example.kimsearappfinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button loginButton;

    private TextView signupText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        signupText = findViewById(R.id.signupText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = username.getText().toString();
                String passwords = password.getText().toString();

                if (userName.isEmpty()) {
                    username.setError("Please Input User Name!");
                    username.requestFocus();
                    return;
                } else if (passwords.isEmpty()) {
                    password.setError("Please input password!");
                    password.requestFocus();
                    return;
                }

                if (userName.equals("user") && passwords.equals("1234")) {
                    // Successful login
                    saveLoginState(userName, passwords);
                    Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
                    startActivity(intent);
                } else {
                    // Incorrect username or password
                    Toast.makeText(getApplicationContext(), "Incorrect Username or Password!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set click listener for the signup text
        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveLoginState(String username, String password) {
        SharedPreferences sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("logged_in", true);
        editor.putString("username", username);
        editor.putString("password", password);
        editor.apply();
    }

    // Handle signup text click
    public void onSignUpTextClicked(View view) {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    public void onBackButtonClicked(View view) {
        finish(); // Close the LoginActivity and return to the previous activity
    }
}
