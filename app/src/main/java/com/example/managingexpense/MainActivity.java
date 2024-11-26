package com.example.managingexpense;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnAddIncome, btnAddExpense, btnViewIncome, btnViewExpense, btnTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddIncome = findViewById(R.id.btnAddIncome);
        btnAddExpense = findViewById(R.id.btnAddExpense);
        btnViewIncome = findViewById(R.id.btnViewIncome);
        btnViewExpense = findViewById(R.id.btnViewExpense);
        btnTotal = findViewById(R.id.btnTotal);

        btnAddIncome.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddIncomeActivity.class)));
        btnAddExpense.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddExpenseActivity.class)));
        btnViewIncome.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ViewIncomeActivity.class)));
        btnViewExpense.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ViewExpenseActivity.class)));
        btnTotal.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TotalActivity.class)));
    }
}
