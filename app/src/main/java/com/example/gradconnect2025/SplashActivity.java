package com.example.gradconnect2025;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    // Splash screen display time in milliseconds
    private static final int SPLASH_DISPLAY_TIME = 2000;

    // UI Elements
    private LinearLayout splashContent;
    private FrameLayout logoContainer;
    private ImageView logoImage;
    private TextView appNameText;
    private TextView appTaglineText;

    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initializeViews();
        setupAnimations();
    }

    @Override
    protected void onDestroy() {
        // Remove all callbacks to prevent memory leaks
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    private void initializeViews() {
        splashContent = findViewById(R.id.splash_content);
        logoContainer = findViewById(R.id.logo_container);
        logoImage = findViewById(R.id.logo_image);
        appNameText = findViewById(R.id.app_name_text);
        appTaglineText = findViewById(R.id.app_tagline_text);

        splashContent.setVisibility(View.INVISIBLE);
    }

    private void setupAnimations() {
        // Start animations after a short delay
        handler.postDelayed(this::startAnimations, 30);

        // Navigate to MainActivity after splash display time
        handler.postDelayed(this::navigateToMainActivity, SPLASH_DISPLAY_TIME);
    }

    private void startAnimations() {
        splashContent.setVisibility(View.VISIBLE);

        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation scaleUpAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_up);

        logoContainer.startAnimation(scaleUpAnimation);

        // Chain animations using sequence
        appNameText.postDelayed(() -> {
            appNameText.startAnimation(fadeInAnimation);

            appTaglineText.postDelayed(() -> {
                appTaglineText.startAnimation(fadeInAnimation);
            }, 300);
        }, 300);
    }

    private void navigateToMainActivity() {
        Intent mainIntent = new Intent(this, LoginActivity.class);
        startActivity(mainIntent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}