package com.example.gradconnect2025;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EmployerProfileActivity extends AppCompatActivity {

    private EditText etCompanyName, etEmail, etPhone, etIndustry, etLocation, etWebsite;
    private Button btnSave;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_profile);

        // Initialize Firebase instances
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize views
        etCompanyName = findViewById(R.id.et_company_name);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);
        etIndustry = findViewById(R.id.et_industry);
        etLocation = findViewById(R.id.et_location);
        etWebsite = findViewById(R.id.et_website);
        btnSave = findViewById(R.id.btn_save);

        // Load current profile data
        loadProfileData();

        // Set up save button
        btnSave.setOnClickListener(v -> saveProfile());
    }

    private void loadProfileData() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            etEmail.setText(user.getEmail());

            // Load additional profile data from Firestore
            db.collection("employers").document(user.getUid())
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            etCompanyName.setText(documentSnapshot.getString("companyName"));
                            etPhone.setText(documentSnapshot.getString("phone"));
                            etIndustry.setText(documentSnapshot.getString("industry"));
                            etLocation.setText(documentSnapshot.getString("location"));
                            etWebsite.setText(documentSnapshot.getString("website"));
                        }
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Failed to load profile", Toast.LENGTH_SHORT).show());
        }
    }

    private void saveProfile() {
        String companyName = etCompanyName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String industry = etIndustry.getText().toString().trim();
        String location = etLocation.getText().toString().trim();
        String website = etWebsite.getText().toString().trim();

        if (companyName.isEmpty()) {
            etCompanyName.setError("Company name is required");
            return;
        }

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            Map<String, Object> employer = new HashMap<>();
            employer.put("companyName", companyName);
            employer.put("phone", phone);
            employer.put("industry", industry);
            employer.put("location", location);
            employer.put("website", website);
            employer.put("email", user.getEmail());

            db.collection("employers").document(user.getUid())
                    .set(employer)
                    .addOnSuccessListener(aVoid -> Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show());
        }
    }
}