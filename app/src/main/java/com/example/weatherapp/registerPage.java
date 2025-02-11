package com.example.weatherapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class registerPage extends AppCompatActivity {

    private EditText emailEditText, passwordEditText,usernameEditText;
    private Button registerButton;
    private DatabaseHelper dbHelper;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        emailEditText = findViewById(R.id.register_email);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.register_password);
        registerButton = findViewById(R.id.register_button);

        dbHelper = new DatabaseHelper(this);

        // Handle Register
        registerButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty() || username.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("onCreate: inputs",email+username+password);
                if (dbHelper.registerUser(email,username, password)) {
                    Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(registerPage.this, loggingPage.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Registration failed. Email may already be registered.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void loginbtn(View v){
        Intent intent = new Intent(registerPage.this, loggingPage.class);
        startActivity(intent);
    }
}