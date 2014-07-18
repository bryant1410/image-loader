package com.novoda.pxhunter.demo.analytics;

public class CrashAnalyticsFactory {

    public CrashAnalytics createCrashAnalytics() {
        return new BugSenseCrashAnalytics();
    }

}
