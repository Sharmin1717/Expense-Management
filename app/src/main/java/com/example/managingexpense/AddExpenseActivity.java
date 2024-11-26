package com.example.managingexpense;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class AddExpenseActivity extends AppCompatActivity {

    private EditText etExpenseAmount, etExpenseNote;
    private Button btnSaveExpense;
    private DatabaseReference databaseRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        etExpenseAmount = findViewById(R.id.etExpenseAmount);
        etExpenseNote = findViewById(R.id.etExpenseNote);
        btnSaveExpense = findViewById(R.id.btnSaveExpense);

        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        databaseRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("expenses");

        btnSaveExpense.setOnClickListener(v -> addExpense());
    }

    private void addExpense() {
        String amount = etExpenseAmount.getText().toString().trim();
        String note = etExpenseNote.getText().toString().trim();

        if (TextUtils.isEmpty(amount) || TextUtils.isEmpty(note)) {
            Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show();
        } else {
            String id = UUID.randomUUID().toString();
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
            Expense expense = new Expense(id, Double.parseDouble(amount), note, date);

            databaseRef.child(id).setValue(expense)
                    .addOnSuccessListener(aVoid -> Toast.makeText(AddExpenseActivity.this, "Expense Added", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(AddExpenseActivity.this, "Failed to Add Expense", Toast.LENGTH_SHORT).show());
        }
    }
}
