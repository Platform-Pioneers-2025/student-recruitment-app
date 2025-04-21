package com.example.gradconnect2025;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.BarChart;

public class AnalyticsActivity extends AppCompatActivity {
    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        // Initialize BarChart
        barChart = findViewById(R.id.job_views_chart);

        // Setup your chart here
        setupBarChart();
    }

    private void setupBarChart() {
        // Configure your bar chart properties here
        barChart.getDescription().setEnabled(false);
        barChart.setDrawGridBackground(false);
        // Add more configuration as needed
    }
}