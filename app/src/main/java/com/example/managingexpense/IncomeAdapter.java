package com.example.managingexpense;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.IncomeViewHolder> {

    private final List<Income> incomeList;
    private final Context context;
    private final DatabaseReference databaseRef;

    public IncomeAdapter(List<Income> incomeList, Context context) {
        this.incomeList = incomeList;
        this.context = context;
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("incomes");
    }

    @NonNull
    @Override
    public IncomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_income, parent, false);
        return new IncomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeViewHolder holder, int position) {
        Income income = incomeList.get(position);
        holder.tvAmount.setText("Amount: BDT " + income.getAmount());
        holder.tvNote.setText("Note: " + income.getNote());
        holder.tvDate.setText("Date: " + income.getDate());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditIncomeActivity.class);
            intent.putExtra("incomeId", income.getId());
            context.startActivity(intent);
        });

        // Delete income click listener
        holder.btnDelete.setOnClickListener(v -> {
            deleteIncome(income.getId(), position);
        });
    }

    @Override
    public int getItemCount() {
        return incomeList.size();
    }

    private void deleteIncome(String incomeId, int position) {
        // Remove income from Firebase
        databaseRef.child(incomeId).removeValue()
                .addOnSuccessListener(aVoid -> {
                    // On success, remove item from the list and notify adapter
                    incomeList.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(context, "Income deleted", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Failed to delete income", Toast.LENGTH_SHORT).show();
                });
    }

    static class IncomeViewHolder extends RecyclerView.ViewHolder {
        TextView tvAmount, tvNote, tvDate;
        Button btnDelete;  // Button for delete

        public IncomeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAmount = itemView.findViewById(R.id.tvIncomeAmount);
            tvNote = itemView.findViewById(R.id.tvIncomeNote);
            tvDate = itemView.findViewById(R.id.tvIncomeDate);
            btnDelete = itemView.findViewById(R.id.btnDeleteIncome);  // Corrected ID for delete
        }
    }
}
