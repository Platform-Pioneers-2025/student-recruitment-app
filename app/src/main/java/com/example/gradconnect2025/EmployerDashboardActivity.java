package com.example.gradconnect2025;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class EmployerDashboardActivity extends AppCompatActivity {

    private TextView tvActiveJobs, tvNewMessages, tvShortlisted;
    private GridView gridActions;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empl_dashboard);

        tvActiveJobs = findViewById(R.id.tv_active_jobs);
        tvNewMessages = findViewById(R.id.tv_new_messages);
        tvShortlisted = findViewById(R.id.tv_shortlisted);
        gridActions = findViewById(R.id.grid_actions);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        setupActionGrid();
        setupBottomNavigation();
        loadDashboardData();
    }

    private void setupActionGrid() {
        List<DashboardAction> actions = new ArrayList<>();
        actions.add(new DashboardAction("Post Job", R.drawable.ic_post_job,
                v -> startActivity(new Intent(this, PostJobActivity.class))));
        actions.add(new DashboardAction("Search Candidates", R.drawable.ic_search,
                v -> startActivity(new Intent(this, CandidateSearchActivity.class))));
        actions.add(new DashboardAction("Messages", R.drawable.ic_messages,
                v -> startActivity(new Intent(this, EmployerMessagesActivity.class))));
        actions.add(new DashboardAction("Analytics", R.drawable.ic_analytics,
                v -> startActivity(new Intent(this, AnalyticsActivity.class))));

        DashboardGridAdapter adapter = new DashboardGridAdapter(this, actions);
        gridActions.setAdapter(adapter);
    }

    private void setupBottomNavigation() {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                return true;
            } else if (itemId == R.id.nav_jobs) {
                startActivity(new Intent(this, ManageJobsActivity.class));
                return true;
            } else if (itemId == R.id.nav_candidates) {
                startActivity(new Intent(this, CandidateSearchActivity.class));
                return true;
            } else if (itemId == R.id.nav_messages) {
                startActivity(new Intent(this, EmployerMessagesActivity.class));
                return true;
            }
            return false;
        });
    }

    private void loadDashboardData() {
        tvActiveJobs.setText("12");
        tvNewMessages.setText("5");
        tvShortlisted.setText("8");
    }

    private static class DashboardAction {
        String title;
        int iconRes;
        View.OnClickListener onClickListener;

        DashboardAction(String title, int iconRes, View.OnClickListener onClickListener) {
            this.title = title;
            this.iconRes = iconRes;
            this.onClickListener = onClickListener;
        }
    }

    private static class DashboardGridAdapter extends BaseAdapter {
        private final Context context;
        private final List<DashboardAction> actions;

        DashboardGridAdapter(Context context, List<DashboardAction> actions) {
            this.context = context;
            this.actions = actions;
        }

        @Override
        public int getCount() {
            return actions.size();
        }

        @Override
        public Object getItem(int position) {
            return actions.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context)
                        .inflate(R.layout.item_dashboard_action, parent, false);
            }

            DashboardAction action = actions.get(position);

            ImageView icon = convertView.findViewById(R.id.action_icon);
            TextView title = convertView.findViewById(R.id.action_title);

            icon.setImageResource(action.iconRes);
            icon.setColorFilter(ContextCompat.getColor(context, R.color.primary));
            title.setText(action.title);

            convertView.setOnClickListener(action.onClickListener);

            return convertView;
        }
    }
}