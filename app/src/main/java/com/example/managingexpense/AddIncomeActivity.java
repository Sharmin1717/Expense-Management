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

public class AddIncomeActivity extends AppCompatActivity {

    private EditText etIncomeAmount, etIncomeNote;
    private Button btnSaveIncome;
    private DatabaseReference databaseRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income);

        etIncomeAmount = findViewById(R.id.etIncomeAmount);
        etIncomeNote = findViewById(R.id.etIncomeNote);
        btnSaveIncome = findViewById(R.id.btnSaveIncome);
        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        databaseRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("incomes");

        btnSaveIncome.setOnClickListener(v -> addIncome());
    }

    private void addIncome() {
        String amount = etIncomeAmount.getText().toString().trim();
        String note = etIncomeNote.getText().toString().trim();

        if (TextUtils.isEmpty(amount) || TextUtils.isEmpty(note)) {
            Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show();
        } else {
            String id = UUID.randomUUID().toString();
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
            Income income = new Income(id, Double.parseDouble(amount), note, date);

            databaseRef.child(id).setValue(income)
                    .addOnSuccessListener(aVoid -> Toast.makeText(AddIncomeActivity.this, "Income Added", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(AddIncomeActivity.this, "Failed to Add Income", Toast.LENGTH_SHORT).show());
        }
    }
}
