package com.example.gradconnect2025;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class CandidateSearchActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_search);

        recyclerView = findViewById(R.id.candidates_recycler_view);
        // Set up search functionality and RecyclerView adapter
    }
}