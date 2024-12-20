package com.example.managingexpense;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditExpenseActivity extends AppCompatActivity {

    private EditText etEditAmount, etEditNote;
    private Button btnUpdateExpense;
    private DatabaseReference databaseRef;
    private String expenseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);

        etEditAmount = findViewById(R.id.etEditAmount);
        etEditNote = findViewById(R.id.etEditNote);
        btnUpdateExpense = findViewById(R.id.btnUpdateExpense);

        expenseId = getIntent().getStringExtra("expenseId");
        databaseRef = FirebaseDatabase.getInstance().getReference("expenses").child(expenseId);

        btnUpdateExpense.setOnClickListener(v -> updateExpense());
    }

    private void updateExpense() {
        String amount = etEditAmount.getText().toString();
        String note = etEditNote.getText().toString();

        if (amount.isEmpty() || note.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        databaseRef.child("amount").setValue(amount);
        databaseRef.child("note").setValue(note);
        Toast.makeText(this, "Expense Updated", Toast.LENGTH_SHORT).show();
        finish();
    }
}
