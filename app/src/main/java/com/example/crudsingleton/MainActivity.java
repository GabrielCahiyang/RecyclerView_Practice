package com.example.crudsingleton;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private userManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        manager = userManager.getInstance();

        EditText etRegName = findViewById(R.id.etRegName);
        EditText etRegEmail = findViewById(R.id.etRegEmail);
        EditText etRegPassword = findViewById(R.id.etRegPassword);
        TextView toLoginTV = findViewById(R.id.toLoginTV);
        Button RegisterBtn = findViewById(R.id.RegisterBtn);

        RegisterBtn.setOnClickListener(v -> {
            String name = etRegName.getText().toString().trim();
            String email = etRegEmail.getText().toString().trim();
            String password = etRegPassword.getText().toString().trim();

            if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                boolean success = manager.registerUser(name, email, password);
                if (success) {
                    Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, LoginScreen.class));
                    finish();
                } else {
                    Toast.makeText(this, "Email already exists!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
        });

        toLoginTV.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginScreen.class));
            finish();
        });
    }
}