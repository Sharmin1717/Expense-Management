package com.example.managingexpense;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button btnAddIncome, btnAddExpense, btnViewIncome, btnViewExpense, btnTotal, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Initialize UI components
        btnAddIncome = findViewById(R.id.btnAddIncome);
        btnAddExpense = findViewById(R.id.btnAddExpense);
        btnViewIncome = findViewById(R.id.btnViewIncome);
        btnViewExpense = findViewById(R.id.btnViewExpense);
        btnTotal = findViewById(R.id.btnTotal);
        btnLogout = findViewById(R.id.btnLogout);
        ImageView logoImageView = findViewById(R.id.logoImageView);

        // Set up button listeners
        btnAddIncome.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddIncomeActivity.class)));
        btnAddExpense.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddExpenseActivity.class)));
        btnViewIncome.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ViewIncomeActivity.class)));
        btnViewExpense.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ViewExpenseActivity.class)));
        btnTotal.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TotalActivity.class)));

        // Logout functionality
        btnLogout.setOnClickListener(v -> logoutUser());
    }

    private void logoutUser() {
        // Sign out the current user
        mAuth.signOut();

        // Redirect to login screen
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();  // Close the current activity
    }
}
