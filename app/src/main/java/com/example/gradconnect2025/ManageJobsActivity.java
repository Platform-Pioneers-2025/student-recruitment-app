package com.example.gradconnect2025;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ManageJobsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Job> jobList;
    private ManageJobsAdapter adapter;
    private DatabaseReference jobsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_jobs);

        recyclerView = findViewById(R.id.recyclerJobs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        jobList = new ArrayList<>();
        jobsRef = FirebaseDatabase.getInstance().getReference("jobs");

        adapter = new ManageJobsAdapter(this, jobList, new ManageJobsAdapter.OnJobActionListener() {
            @Override
            public void onEdit(Job job) {
                showEditDialog(job);
            }

            @Override
            public void onDelete(Job job) {
                deleteJob(job);
            }
        });

        recyclerView.setAdapter(adapter);

        loadJobs();
    }

    private void loadJobs() {
        jobsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                jobList.clear();
                for (DataSnapshot jobSnapshot : snapshot.getChildren()) {
                    Job job = jobSnapshot.getValue(Job.class);
                    jobList.add(job);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(ManageJobsActivity.this, "Failed to load jobs.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showEditDialog(Job job) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Job");

        View view = getLayoutInflater().inflate(R.layout.dialog_edit_job, null);
        EditText titleInput = view.findViewById(R.id.editTitle);
        EditText descInput = view.findViewById(R.id.editDesc);

        titleInput.setText(job.getTitle());
        descInput.setText(job.getDescription());

        builder.setView(view);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String updatedTitle = titleInput.getText().toString();
            String updatedDesc = descInput.getText().toString();

            job.setTitle(updatedTitle);
            job.setDescription(updatedDesc);

            // Update in Firebase
            jobsRef.child(job.getJobId()).setValue(job)
                    .addOnSuccessListener(unused ->
                            Toast.makeText(this, "Job updated", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show());

            adapter.notifyDataSetChanged();
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void deleteJob(Job job) {
        jobsRef.child(job.getJobId()).removeValue()
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Job deleted", Toast.LENGTH_SHORT).show();
                    jobList.remove(job);
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Delete failed", Toast.LENGTH_SHORT).show());
    }
}
