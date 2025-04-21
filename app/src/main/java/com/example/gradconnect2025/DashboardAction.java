package com.example.gradconnect2025;

import android.view.View;

public class DashboardAction {
    private String title;
    private int iconRes;
    private View.OnClickListener onClickListener;

    public DashboardAction(String title, int iconRes, View.OnClickListener onClickListener) {
        this.title = title;
        this.iconRes = iconRes;
        this.onClickListener = onClickListener;
    }

    public String getTitle() {
        return title;
    }

    public int getIconRes() {
        return iconRes;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }
}
