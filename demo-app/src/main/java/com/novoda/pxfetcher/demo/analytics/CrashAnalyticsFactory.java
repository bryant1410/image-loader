package com.novoda.pxfetcher.demo.analytics;

public class CrashAnalyticsFactory {

    public CrashAnalytics createCrashAnalytics() {
        return new BugSenseCrashAnalytics();
    }

}
