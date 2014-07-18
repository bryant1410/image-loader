package com.novoda.pxhunter.demo.analytics;

import android.content.Context;
import android.util.Log;

import com.bugsense.trace.BugSenseHandler;
import com.novoda.pxhunter.demo.BuildConfig;
import com.novoda.pxhunter.demo.DemoApplication;

class BugSenseCrashAnalytics implements CrashAnalytics {

    private static final String EMPTY_SECOND_LEVEL_MESSAGE = "";

    private boolean tracking;

    @Override
    public void startTracking(Context context) {
        try {
            BugSenseHandler.initAndStartSession(context, BuildConfig.BUGSENSE_API_KEY);
            tracking = true;
        } catch (IllegalArgumentException invalidApiKeyException) {
            Log.e(DemoApplication.TAG, "BugSense API key incorrect, no tracking for you!");
        }
    }

    @Override
    public void log(Exception exception, String message) {
        if (tracking) {
            BugSenseHandler.sendExceptionMessage(message, EMPTY_SECOND_LEVEL_MESSAGE, exception);
        }
    }

    @Override
    public void log(String message) {
        if (tracking) {
            BugSenseHandler.sendEvent(message);
        }
    }

}
