package com.example.gradconnect2025;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.auth.FirebaseUser;


public class ShortlistedCandidatesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirestoreRecyclerAdapter adapter;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortlisted_candidates);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load shortlisted candidates
        loadShortlistedCandidates();
    }

    private void loadShortlistedCandidates() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            Query query = db.collection("shortlists")
                    .whereEqualTo("employerId", user.getUid())
                    .orderBy("timestamp", Query.Direction.DESCENDING);

            FirestoreRecyclerOptions<Candidate> options = new FirestoreRecyclerOptions.Builder<Candidate>()
                    .setQuery(query, Candidate.class)
                    .build();

            adapter = new FirestoreRecyclerAdapter<Candidate, CandidateViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull CandidateViewHolder holder, int position, @NonNull Candidate model) {
                    holder.bind(model);
                }

                @NonNull
                @Override
                public CandidateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.item_candidate, parent, false);
                    return new CandidateViewHolder(view);
                }
            };

            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }

    private static class CandidateViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName, tvDegree, tvUniversity;
        private final ImageView ivProfile;

        CandidateViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDegree = itemView.findViewById(R.id.tv_degree);
            tvUniversity = itemView.findViewById(R.id.tv_university);
            ivProfile = itemView.findViewById(R.id.iv_profile);
        }

        void bind(Candidate candidate) {
            tvName.setText(candidate.getName());
            tvDegree.setText(candidate.getDegree());
            tvUniversity.setText(candidate.getUniversity());

            if (candidate.getProfileImage() != null && !candidate.getProfileImage().isEmpty()) {
                Glide.with(itemView.getContext())
                        .load(candidate.getProfileImage())
                        .placeholder(R.drawable.ic_profile)
                        .into(ivProfile);
            }
        }
    }
}