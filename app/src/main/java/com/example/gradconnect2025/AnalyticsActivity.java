package com.example.gradconnect2025;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseUser;


import java.util.ArrayList;
import java.util.List;

public class AnalyticsActivity extends AppCompatActivity {

    private PieChart pieChart;
    private BarChart barChart;
    private TextView tvTotalApplications, tvInterviewRate;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize views
        pieChart = findViewById(R.id.pie_chart);
        barChart = findViewById(R.id.bar_chart);
        tvTotalApplications = findViewById(R.id.tv_total_applications);
        tvInterviewRate = findViewById(R.id.tv_interview_rate);

        // Setup charts
        setupPieChart();
        setupBarChart();

        // Load analytics data
        loadAnalyticsData();
    }

    private void setupPieChart() {
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(45f, "Engineering"));
        entries.add(new PieEntry(25f, "Business"));
        entries.add(new PieEntry(15f, "Arts"));
        entries.add(new PieEntry(15f, "Science"));

        PieDataSet dataSet = new PieDataSet(entries, "Applications by Field");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Applications by Field");
        pieChart.animateY(1000);
        pieChart.invalidate();
    }

    private void setupBarChart() {
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 30f));
        entries.add(new BarEntry(1f, 80f));
        entries.add(new BarEntry(2f, 60f));
        entries.add(new BarEntry(3f, 50f));
        entries.add(new BarEntry(4f, 70f));
        entries.add(new BarEntry(5f, 60f));

        BarDataSet dataSet = new BarDataSet(entries, "Applications Last 6 Months");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        BarData data = new BarData(dataSet);
        barChart.setData(data);
        barChart.getDescription().setEnabled(false);
        barChart.animateY(1000);
        barChart.invalidate();
    }

    private void loadAnalyticsData() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            db.collection("employers").document(user.getUid())
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            // Sample data - replace with actual data from your database
                            tvTotalApplications.setText("120");
                            tvInterviewRate.setText("25%");
                        }
                    })
                    .addOnFailureListener(e -> {
                        // Fallback to sample data if database fails
                        tvTotalApplications.setText("120");
                        tvInterviewRate.setText("25%");
                    });
        }
    }
}