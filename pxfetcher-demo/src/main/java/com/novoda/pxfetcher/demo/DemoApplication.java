package com.novoda.pxfetcher.demo;

import android.app.Application;
import android.content.Context;

import com.novoda.pxfetcher.PixelFetcher;
import com.novoda.pxfetcher.PixelFetchers;

public class DemoApplication extends Application {

    public static final String TAG = DemoApplication.class.getSimpleName();

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static PixelFetcher pixelFetcher() {
        return PixelFetchers.getInstance(context);
    }

    public static Context context() {
        return context;
    }

}
