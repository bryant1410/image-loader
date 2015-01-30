package com.novoda.pxfetcher.task;

import com.novoda.pxfetcher.BuildConfig;

public final class LoggingExceptionHandlerAttacher {

    private LoggingExceptionHandlerAttacher() {
        // non instantiable
    }

    /**
     * Swallow & Log exceptions on the current thread {@see Thread.currentThread()}
     */
    public static void swallowLiveExceptions() {
        swallowLiveExceptions(Thread.currentThread());
    }

    /**
     * Swallow & Log exceptions on the thread passed in
     *
     * @param thread the Thread to swallow & log exceptions on
     */
    public static void swallowLiveExceptions(Thread thread) {
        if (BuildConfig.DEBUG) {
            return;
        }
        thread.setUncaughtExceptionHandler(new LoggingUncaughtExceptionHandler());
    }

}
