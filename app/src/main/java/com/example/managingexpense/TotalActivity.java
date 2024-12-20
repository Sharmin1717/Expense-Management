package com.example.managingexpense;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

public class TotalActivity extends AppCompatActivity {

    private TextView tvTotalIncome, tvTotalExpense, tvNetBalance;
    private DatabaseReference incomeRef, expenseRef;
    private FirebaseAuth mAuth;
    private double totalIncome = 0.0, totalExpense = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total);

        tvTotalIncome = findViewById(R.id.tvTotalIncome);
        tvTotalExpense = findViewById(R.id.tvTotalExpense);
        tvNetBalance = findViewById(R.id.tvNetBalance);

        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        incomeRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("incomes");
        expenseRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("expenses");

        calculateTotalIncome();
        calculateTotalExpense();
    }

    private void calculateTotalIncome() {
        incomeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                totalIncome = 0.0;
                for (DataSnapshot data : snapshot.getChildren()) {
                    Income income = data.getValue(Income.class);
                    totalIncome += income.getAmount();
                }
                tvTotalIncome.setText("Total Income: BDT " + totalIncome);
                updateNetBalance();
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    private void calculateTotalExpense()  {
        expenseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                totalExpense = 0.0;
                for (DataSnapshot data : snapshot.getChildren()) {
                    Expense expense = data.getValue(Expense.class);
                    totalExpense += expense.getAmount();
                }
                tvTotalExpense.setText("Total Expense: BDT " + totalExpense);
                updateNetBalance();
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    private void updateNetBalance() {
        double netBalance = totalIncome - totalExpense;
        tvNetBalance.setText("Net Balance: BDT " + netBalance);
    }
}
