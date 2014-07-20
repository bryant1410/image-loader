package com.novoda.pxhunter.demo;

import android.app.Application;
import android.content.Context;

import com.novoda.pxhunter.demo.analytics.CrashAnalytics;
import com.novoda.pxhunter.demo.analytics.CrashAnalyticsFactory;
import com.novoda.pxhunter.impl.PxHunterFactory;
import com.novoda.pxhunter.port.PxHunter;

public class DemoApplication extends Application {

    public static final String TAG = DemoApplication.class.getSimpleName();

    private static Context context;
    private static PxHunter<Void> pxHunter;

    private final CrashAnalytics crashAnalytics;

    public DemoApplication() {
        super();
        crashAnalytics = new CrashAnalyticsFactory().createCrashAnalytics();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        crashAnalytics.startTracking(this);
        normalPxHunterSettings();
    }

    /**
     * Default PxHunter setup
     */
    private void normalPxHunterSettings() {
        pxHunter = PxHunterFactory.defaultPxHunter();
    }

    /**
     * Verbose PxHunter setup
     */
    @SuppressWarnings("unused")
    private void verbosePxHunterSettings() {
        // TODO: demonstrate the verbose settings of PxHunter
    }

    public static PxHunter<Void> pxHunter() {
        return pxHunter;
    }

    public static Context context() {
        return context;
    }

}
