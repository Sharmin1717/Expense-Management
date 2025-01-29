package com.example.managingexpense;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.LinearLayout;
import android.widget.Toast;

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

        // Initialize UI components
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
        // Check if the user is logged in
        if (mAuth.getCurrentUser() == null) {
            // Redirect to login screen if not logged in
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }

    private void logoutUser() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging out...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            try {
                // Clear SharedPreferences login state
                SharedPreferences.Editor editor = getSharedPreferences("UserPrefs", MODE_PRIVATE).edit();
                editor.putBoolean("isLoggedIn", false);
                editor.apply();

                // Firebase logout
                mAuth.signOut();

                // Redirect to LoginActivity
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Logout Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            } finally {
                progressDialog.dismiss();
            }
        }, 1500); // 1.5-second delay ensures logout completes
    }

    @Override
    public void onBackPressed() {
        if (mAuth.getCurrentUser() != null) {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialog, id) -> super.onBackPressed())
                    .setNegativeButton("No", null)
                    .show();
        } else {
            super.onBackPressed();
        }
    }
}
