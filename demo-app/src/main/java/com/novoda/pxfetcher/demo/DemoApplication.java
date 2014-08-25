package com.novoda.pxfetcher.demo;

import android.app.Application;
import android.content.Context;

import com.novoda.pxfetcher.demo.analytics.CrashAnalytics;
import com.novoda.pxfetcher.demo.analytics.CrashAnalyticsFactory;
import com.novoda.pxfetcher.PixelFetcherFactory;
import com.novoda.pxfetcher.Metadata;
import com.novoda.pxfetcher.PixelFetcher;

public class DemoApplication extends Application {

    public static final String TAG = DemoApplication.class.getSimpleName();

    private static Context context;
    private static PixelFetcher<Metadata> pixelFetcher;

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
        initialisePixelFetcherWithSaneDefaults();
    }

    private void initialisePixelFetcherWithSaneDefaults() {
        pixelFetcher = PixelFetcherFactory.newPixelFetcher(this);
    }

    @SuppressWarnings("unused")
    private void initialisePixelFetcherUsingTerseApi() {
        // TODO: demonstrate the verbose settings of PxFetcher
    }

    public static PixelFetcher<Metadata> pixelFetcher() {
        return pixelFetcher;
    }

    public static Context context() {
        return context;
    }

}
