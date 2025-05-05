package com.example.gradconnect2025;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class PostJobActivity extends AppCompatActivity {

    private TextInputEditText titleInput, descriptionInput, locationInput, deadlineInput;
    private RadioGroup jobTypeGroup;
    private Button postButton;

    private DatabaseReference jobsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);

        // Initialize Firebase reference
        jobsRef = FirebaseDatabase.getInstance().getReference("jobs");

        // Initialize views
        titleInput = findViewById(R.id.job_title_input);
        descriptionInput = findViewById(R.id.job_description_input);
        locationInput = findViewById(R.id.job_location_input);
        deadlineInput = findViewById(R.id.deadline_input);
        jobTypeGroup = findViewById(R.id.job_type_group);
        postButton = findViewById(R.id.post_job_button);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PostJobActivity.this, "Posting job...", Toast.LENGTH_SHORT).show();
                postJobToFirebase();
            }
        });
    }

    private void postJobToFirebase() {
        String title = titleInput.getText().toString().trim();
        String description = descriptionInput.getText().toString().trim();
        String location = locationInput.getText().toString().trim();
        String deadline = deadlineInput.getText().toString().trim();

        int selectedTypeId = jobTypeGroup.getCheckedRadioButtonId();
        if (selectedTypeId == -1) {
            Toast.makeText(this, "Please select job type", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedType = findViewById(selectedTypeId);
        String jobType = selectedType.getText().toString();

        if (TextUtils.isEmpty(title)) {
            Toast.makeText(this, "Job title is required", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(description)) {
            Toast.makeText(this, "Job description is required", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(location)) {
            Toast.makeText(this, "Job location is required", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(deadline)) {
            Toast.makeText(this, "Deadline is required", Toast.LENGTH_SHORT).show();
            return;
        }

        String jobId = jobsRef.push().getKey(); // Auto-generated ID
        HashMap<String, String> job = new HashMap<>();
        job.put("jobId", jobId);
        job.put("title", title);
        job.put("description", description);
        job.put("location", location);
        job.put("deadline", deadline);
        job.put("type", jobType);

        if (jobId != null) {
            jobsRef.child(jobId).setValue(job).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(PostJobActivity.this, "✅ Job posted successfully", Toast.LENGTH_SHORT).show();
                    clearInputs();
                } else {
                    String error = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                    Toast.makeText(PostJobActivity.this, "❌ Failed to post job: " + error, Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(this, "❌ Error generating job ID", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearInputs() {
        titleInput.setText("");
        descriptionInput.setText("");
        locationInput.setText("");
        deadlineInput.setText("");
        jobTypeGroup.clearCheck();
    }
}
