package com.atr;

import android.app.Application;
import android.util.Log;

import com.atr.lowadventure.activity.ErrorActivity;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import cat.ereza.customactivityoncrash.config.CaocConfig;

public class CrashingApplications extends Application {

    private static final String TAG = "SampleCrashingApp";

    @Override
    public void onCreate() {
        super.onCreate();
        CaocConfig.Builder.create()
                .trackActivities(true)
                .errorActivity(ErrorActivity.class)
                .apply();
    }

    private static class CustomEventListener implements CustomActivityOnCrash.EventListener {
        @Override
        public void onLaunchErrorActivity() {
            Log.i(TAG, "onLaunchErrorActivity()");
        }

        @Override
        public void onRestartAppFromErrorActivity() {
            Log.i(TAG, "onRestartAppFromErrorActivity()");
        }

        @Override
        public void onCloseAppFromErrorActivity() {
            Log.i(TAG, "onCloseAppFromErrorActivity()");
        }
    }
}
