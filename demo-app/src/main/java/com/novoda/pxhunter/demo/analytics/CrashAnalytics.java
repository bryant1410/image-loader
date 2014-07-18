package com.novoda.pxhunter.demo.analytics;

import android.content.Context;

public interface CrashAnalytics {

    void startTracking(Context context);

    void log(Exception exception, String message);

    void log(String message);

}
