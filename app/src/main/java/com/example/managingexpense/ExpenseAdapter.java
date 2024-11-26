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

import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Expense> expenseList;
    private DatabaseReference databaseRef;

    public ExpenseAdapter(Context context, ArrayList<Expense> expenseList) {
        this.context = context;
        this.expenseList = expenseList;
        databaseRef = FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("expenses");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_expense, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Expense expense = expenseList.get(position);
        holder.tvAmount.setText("Amount: BDT " + expense.getAmount());
        holder.tvNote.setText("Note: " + expense.getNote());
        holder.tvDate.setText("Date: " + expense.getDate());  // Add this line to display the date

        // Edit expense click listener
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditExpenseActivity.class);
            intent.putExtra("expenseId", expense.getId());
            context.startActivity(intent);
        });

        // Delete expense click listener
        holder.btnDelete.setOnClickListener(v -> {
            deleteExpense(expense.getId(), position);
        });
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    private void deleteExpense(String expenseId, int position) {
        // Remove expense from Firebase
        databaseRef.child(expenseId).removeValue()
                .addOnSuccessListener(aVoid -> {
                    // On success, remove item from the list and notify adapter
                    expenseList.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(context, "Expense deleted", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Failed to delete expense", Toast.LENGTH_SHORT).show();
                });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAmount, tvNote, tvDate;  // Add tvDate to display the date
        Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAmount = itemView.findViewById(R.id.tvExpenseAmount);
            tvNote = itemView.findViewById(R.id.tvExpenseNote);
            tvDate = itemView.findViewById(R.id.tvExpenseDate);  // Initialize tvDate
            btnDelete = itemView.findViewById(R.id.btnDeleteExpense);
        }
    }
}
