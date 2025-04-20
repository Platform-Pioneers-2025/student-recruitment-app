package com.example.gradconnect.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

/**
 * Utility class for custom animations throughout the app.
 */
public class AnimationUtils {

    /**
     * Creates a fade-in animation for the given view.
     *
     * @param view the view to animate
     * @param duration the duration of the animation in milliseconds
     * @param startDelay the delay before starting the animation in milliseconds
     */
    public static void fadeIn(View view, int duration, int startDelay) {
        view.setAlpha(0f);
        view.setVisibility(View.VISIBLE);

        ObjectAnimator animator = ObjectAnimator.ofFloat(view, View.ALPHA, 0f, 1f);
        animator.setDuration(duration);
        animator.setStartDelay(startDelay);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }

    /**
     * Creates a pulse animation for the given view.
     *
     * @param view the view to animate
     * @param duration the duration of the animation in milliseconds
     */
    public static void pulse(View view, int duration) {
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, 1.1f, 1f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, 1.1f, 1f);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, scaleX, scaleY);
        animator.setDuration(duration);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
    }

    /**
     * Creates a slide-up animation for the given view.
     *
     * @param view the view to animate
     * @param duration the duration of the animation in milliseconds
     */
    public static void slideUp(View view, int duration) {
        view.setTranslationY(100f);
        view.setAlpha(0f);
        view.setVisibility(View.VISIBLE);

        ObjectAnimator animator = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, 100f, 0f);
        animator.setDuration(duration);
        animator.setInterpolator(new DecelerateInterpolator());

        ObjectAnimator fadeAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, 0f, 1f);
        fadeAnimator.setDuration(duration / 2);

        animator.start();
        fadeAnimator.start();
    }
}