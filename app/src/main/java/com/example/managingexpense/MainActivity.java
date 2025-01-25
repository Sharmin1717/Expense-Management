package com.example.managingexpense;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private LinearLayout btnAddIncome, btnAddExpense, btnViewIncome, btnViewExpense, btnTotal, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Initialize UI components (LinearLayouts instead of ImageButtons)
        btnAddIncome = findViewById(R.id.btnAddIncome);
        btnAddExpense = findViewById(R.id.btnAddExpense);
        btnViewIncome = findViewById(R.id.btnViewIncome);
        btnViewExpense = findViewById(R.id.btnViewExpense);
        btnTotal = findViewById(R.id.btnTotal);
        btnLogout = findViewById(R.id.btnLogout);

        // Set up button listeners
        btnAddIncome.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddIncomeActivity.class)));
        btnAddExpense.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddExpenseActivity.class)));
        btnViewIncome.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ViewIncomeActivity.class)));
        btnViewExpense.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ViewExpenseActivity.class)));
        btnTotal.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TotalActivity.class)));

        // Logout functionality
        btnLogout.setOnClickListener(v -> logoutUser());

        // Ensure user is logged in
        checkLoginStatus();
    }

    private void checkLoginStatus() {
        // Check if the user is logged in by checking FirebaseAuth
        if (mAuth.getCurrentUser() == null) {
            // User is not logged in, redirect to login screen
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }

    private void logoutUser() {
        // Sign out the current user
        mAuth.signOut();

        // Redirect to login screen
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();  // Close the current activity
    }

    @Override
    public void onBackPressed() {
        // If the user is logged in, ask for confirmation before exiting the app
        if (mAuth.getCurrentUser() != null) {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialog, id) -> super.onBackPressed())
                    .setNegativeButton("No", null)
                    .show();
        } else {
            // If the user is not logged in, allow the default back button behavior
            super.onBackPressed();
        }
    }
}
