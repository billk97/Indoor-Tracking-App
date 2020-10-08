package com.aueb.rssidataapp;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.aueb.rssidataapp.Ui.error;

public class ExceptionHandler implements java.lang.Thread.UncaughtExceptionHandler {
    private final Activity activity;

    public ExceptionHandler(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void uncaughtException(@NonNull Thread thread, @NonNull Throwable throwable) {
        Intent intent = new Intent(activity, error.class);
        activity.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
