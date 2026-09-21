package com.example.crudsingleton;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Dashboard extends AppCompatActivity {

    userManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);

        manager = userManager.getInstance();

        TextView DashboardName = findViewById(R.id.DashboardName);
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);

        if (manager.getCurUser() != null) {
            DashboardName.setText(manager.getCurUser().getName());
        }

        // Open AddNotesBoard when floating action button is clicked
        floatingActionButton.setOnClickListener(v -> {
            startActivity(new Intent(Dashboard.this, AddNotesBoard.class));
        });
    }
}