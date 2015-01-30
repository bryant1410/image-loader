package com.novoda.pxfetcher.task;

import android.util.Log;

public class LoggingUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "ImageLoader20";

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.wtf(TAG, "Swallowed an uncaught exception.", ex);
    }
}
