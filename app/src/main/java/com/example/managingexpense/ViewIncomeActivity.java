package com.example.managingexpense;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import java.util.ArrayList;
import java.util.List;

public class ViewIncomeActivity extends AppCompatActivity {

    private RecyclerView recyclerViewIncome;
    private IncomeAdapter incomeAdapter;
    private List<Income> incomeList;
    private DatabaseReference databaseRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_income);

        recyclerViewIncome = findViewById(R.id.recyclerViewIncome);
        recyclerViewIncome.setLayoutManager(new LinearLayoutManager(this));
        incomeList = new ArrayList<>();
        incomeAdapter = new IncomeAdapter(incomeList, this);
        recyclerViewIncome.setAdapter(incomeAdapter);

        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        databaseRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("incomes");

        fetchIncomes();
    }

    private void fetchIncomes() {
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                incomeList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Income income = dataSnapshot.getValue(Income.class);
                    incomeList.add(income);
                }
                incomeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewIncomeActivity.this, "Failed to load incomes", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
