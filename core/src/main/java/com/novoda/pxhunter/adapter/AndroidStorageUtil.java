package com.novoda.pxhunter.adapter;

import android.content.Context;

import java.io.File;

public class AndroidStorageUtil {

    private final String packageName;
    private final File cacheDir;

    public static AndroidStorageUtil newInstance(Context context) {
        return new AndroidStorageUtil(context.getPackageName(), context.getCacheDir());
    }

    private AndroidStorageUtil(String packageName, File cacheDir) {
        this.packageName = packageName;
        this.cacheDir = cacheDir;
    }

    boolean isMounted() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    String getPackageName() {
        return packageName;
    }

    File externalStorageDir() {
        return android.os.Environment.getExternalStorageDirectory();
    }

    File preparePhoneCacheDir() {
        return cacheDir;
    }

}