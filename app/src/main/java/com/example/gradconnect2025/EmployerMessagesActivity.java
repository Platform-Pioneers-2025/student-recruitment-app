package com.example.gradconnect2025;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class EmployerMessagesActivity extends AppCompatActivity {
    private RecyclerView messagesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_messages);

        messagesRecyclerView = findViewById(R.id.messages_recycler_view);
        // Set up messages list and adapter
    }
}