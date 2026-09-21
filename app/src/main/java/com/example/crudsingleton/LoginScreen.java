package com.example.crudsingleton;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class LoginScreen extends AppCompatActivity {

    private userManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_screen);

        manager = userManager.getInstance();

        EditText etLogEmail = findViewById(R.id.etLogEmail);
        EditText etLogPassword = findViewById(R.id.etLogPassword);
        Button LoginBtn = findViewById(R.id.LoginBtn);
        TextView toRegisterTV = findViewById(R.id.toRegisterTV);

        LoginBtn.setOnClickListener(v -> {
            String email = etLogEmail.getText().toString().trim();
            String password = etLogPassword.getText().toString().trim();

            if (!email.isEmpty() && !password.isEmpty()) {
                Users user = manager.loginUser(email, password);
                if (user != null) {
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                    manager.setCurUser(user);
                    startActivity(new Intent(this, Dashboard.class));
                    finish();
                } else {
                    Toast.makeText(this, "Incorrect Email or Password", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
        });

        toRegisterTV.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }
}