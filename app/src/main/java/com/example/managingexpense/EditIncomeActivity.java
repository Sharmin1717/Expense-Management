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

public class EditIncomeActivity extends AppCompatActivity {

    private EditText etIncomeAmount, etIncomeNote;
    private Button btnUpdateIncome;
    private DatabaseReference databaseRef;
    private String incomeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income);

        etIncomeAmount = findViewById(R.id.etIncomeAmount);
        etIncomeNote = findViewById(R.id.etIncomeNote);
        btnUpdateIncome = findViewById(R.id.btnSaveIncome);

        incomeId = getIntent().getStringExtra("incomeId");
        databaseRef = FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("incomes").child(incomeId);

        btnUpdateIncome.setText("Update Income");
        btnUpdateIncome.setOnClickListener(v -> updateIncome());
    }

    private void updateIncome() {
        String amount = etIncomeAmount.getText().toString().trim();
        String note = etIncomeNote.getText().toString().trim();

        if (TextUtils.isEmpty(amount) || TextUtils.isEmpty(note)) {
            Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show();
        } else {
            databaseRef.child("amount").setValue(Double.parseDouble(amount));
            databaseRef.child("note").setValue(note);

            Toast.makeText(this, "Income updated", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
