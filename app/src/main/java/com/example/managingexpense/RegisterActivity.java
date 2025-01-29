package com.example.managingexpense;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText etFullName, etEmail, etPassword, etConfirmPassword;
    private Button btnRegister;
    private TextView tvLogin;
    private FirebaseAuth mAuth;

    // Regex patterns for validation
    private static final Pattern NAME_PATTERN = Pattern.compile("^[\\p{L} .'-]+$");
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etFullName = findViewById(R.id.et_FullName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.et_ConfirmPassword);
        btnRegister = findViewById(R.id.btn_Register);
        tvLogin = findViewById(R.id.tv_Login);

        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(view -> registerUser());
        tvLogin.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void registerUser() {
        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        // Reset errors before new validation
        etFullName.setError(null);
        etEmail.setError(null);
        etPassword.setError(null);
        etConfirmPassword.setError(null);

        // Full name validation
        if (fullName.isEmpty()) {
            etFullName.setError("Full name is required");
            etFullName.requestFocus();
            return;
        }
        if (!NAME_PATTERN.matcher(fullName).matches()) {
            etFullName.setError("Enter a valid name");
            etFullName.requestFocus();
            return;
        }

        // Email validation
        if (email.isEmpty()) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Enter a valid email");
            etEmail.requestFocus();
            return;
        }

        // Password validation
        if (password.isEmpty()) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return;
        }
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            etPassword.setError("Password must include at least one uppercase, lowercase, digit, and special character");
            etPassword.requestFocus();
            return;
        }

        // Confirm password validation
        if (confirmPassword.isEmpty()) {
            etConfirmPassword.setError("Confirm password is required");
            etConfirmPassword.requestFocus();
            return;
        }
        if (!confirmPassword.equals(password)) {
            etConfirmPassword.setError("Passwords do not match");
            etConfirmPassword.requestFocus();
            return;
        }

        // Firebase registration
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Registration successful!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
