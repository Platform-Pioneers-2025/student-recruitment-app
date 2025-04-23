package com.example.gradconnect2025;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;
    private TextView textViewRegister, textViewForgotPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewRegister = findViewById(R.id.textViewRegister);
        textViewForgotPassword = findViewById(R.id.textViewForgotPassword);

        buttonLogin.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (validateInputs(email, password)) {
                performLogin(email, password);
            }
        });

        textViewRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });

        textViewForgotPassword.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString().trim();
            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                editTextEmail.setError("Enter a valid email to reset password");
                editTextEmail.requestFocus();
                return;
            }
            sendPasswordResetEmail(email);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null && currentUser.isEmailVerified()) {
            navigateToDashboard();
        }
    }

    private boolean validateInputs(String email, String password) {
        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return false;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return false;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Password must be at least 6 characters");
            editTextPassword.requestFocus();
            return false;
        }

        return true;
    }

    private void performLogin(String email, String password) {
        Toast.makeText(this, "Authenticating...", Toast.LENGTH_SHORT).show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            if (user.isEmailVerified()) {
                                navigateToDashboard();
                            } else {
                                resendVerificationEmail(user);
                            }
                        }
                    } else {
                        handleLoginError(task.getException());
                    }
                });
    }

    private void navigateToDashboard() {
        startActivity(new Intent(this, EmployerDashboardActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }

    private void sendPasswordResetEmail(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this,
                                "Failed to send reset email: " + task.getException().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void resendVerificationEmail(FirebaseUser user) {
        user.sendEmailVerification()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this,
                                "Verification email sent. Please verify your email first.",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(LoginActivity.this,
                                "Failed to send verification email: " + task.getException().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                    mAuth.signOut();
                });
    }

    private void handleLoginError(Exception exception) {
        String errorMessage = "Login failed. Please try again.";

        if (exception != null) {
            if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                errorMessage = "Incorrect password. Please try again or reset your password.";
            } else if (exception instanceof FirebaseAuthInvalidUserException) {
                errorMessage = "No account found with this email. Please register first.";
            } else {
                errorMessage = "Error: " + exception.getMessage();
            }
        }

        Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_LONG).show();
    }
}