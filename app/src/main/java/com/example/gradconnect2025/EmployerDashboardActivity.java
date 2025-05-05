package com.example.gradconnect2025;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class EmployerDashboardActivity extends AppCompatActivity {

    private TextView tvActiveJobs, tvNewMessages, tvShortlisted;
    private GridView gridActions;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empl_dashboard);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        tvActiveJobs = findViewById(R.id.tv_active_jobs);
        tvNewMessages = findViewById(R.id.tv_new_messages);
        tvShortlisted = findViewById(R.id.tv_shortlisted);
        gridActions = findViewById(R.id.grid_actions);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set up toolbar
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up navigation drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Set up navigation drawer item selection
        navigationView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_logout) {
                showLogoutConfirmation();
                return true;
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        setupActionGrid();
        setupBottomNavigation();
        loadDashboardData();
    }

    private void showLogoutConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", (dialog, which) -> logoutUser())
                .setNegativeButton("No", null)
                .setIcon(ContextCompat.getDrawable(this, R.drawable.ic_logout))
                .show();
    }

    private void logoutUser() {
        mAuth.signOut();
        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();

        // Redirect to LoginActivity and clear back stack
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setupActionGrid() {
        List<DashboardAction> actions = new ArrayList<>();
        actions.add(new DashboardAction("Manage Jobs", R.drawable.ic_manage_jobs,
                v -> startActivity(new Intent(this, ManageJobsActivity.class))));
        actions.add(new DashboardAction("Messages", R.drawable.ic_messages,
                v -> startActivity(new Intent(this, EmployerMessagesActivity.class))));

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
            }
            return false;
        });
    }

    private void loadDashboardData() {
        // Replace with actual data loading logic
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
            title.setText(action.title);

            convertView.setOnClickListener(action.onClickListener);

            return convertView;
        }
    }
}
