package com.novoda.pxhunter.adapter;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class FileCache {

    private static final String TAG = FileCache.class.getSimpleName();

    private static final long ONE_WEEK_MILLIS = 7L * 24L * 3600L * 1000L;
    private static final long DEFAULT_EXPIRATION_PERIOD = ONE_WEEK_MILLIS;

    private final long expirationPeriod;
    private final File cacheDirectory;

    public static FileCache newInstance(Context context) {
        return newInstance(context, DEFAULT_EXPIRATION_PERIOD);
    }

    /**
     * Returns an instance of the FileCache, with the specified expiration period.
     *
     * @param context
     * @param expirationPeriod period of time (millis) after which a file is eligible for purging
     * @return
     */
    public static FileCache newInstance(Context context, long expirationPeriod) {
        AndroidStorageUtil androidStorageUtil = AndroidStorageUtil.newInstance(context);
        return new FileCache(cacheDir(androidStorageUtil), expirationPeriod);
    }

    private FileCache(File cacheDirectory, long expirationPeriod) {
        this.expirationPeriod = expirationPeriod;
        this.cacheDirectory = cacheDirectory;
    }

    /**
     * Returns File representing the location of a cached image.
     *
     * @param fileName
     * @return File of cached image, regardless of whether it actually exists
     */
    public File cachedFileFrom(String fileName) {
        return new File(cacheDirectory, fileName);
    }

    public void purgeExpiredImages() {
        if (!cacheDirectory.isDirectory()) {
            return;
        }
        File[] children = cacheDirectory.listFiles();
        for (File image : children) {
            purgeExpired(image);
        }
    }

    private void purgeExpired(File image) {
        long lastModifiedThreshold = System.currentTimeMillis() - expirationPeriod;
        if (image.lastModified() < lastModifiedThreshold) {
            image.delete();
        }
    }

    /**
     * Removes the file specified, if it exists.
     *
     * @param fileName absolute location of file
     * @see {@link #cachedFileFrom(String)}
     */
    public void remove(String fileName) {
        new File(fileName).delete();
    }

    private static File cacheDir(AndroidStorageUtil androidStorageUtil) {
        File dir;
        if (androidStorageUtil.isMounted()) {
            dir = externalCacheDir(androidStorageUtil);
        } else {
            dir = androidStorageUtil.preparePhoneCacheDir();
        }
        addNoMediaFile(dir);
        return dir;
    }

    private static File externalCacheDir(AndroidStorageUtil androidStorageUtil) {
        String relPathToExternalCacheDir = "/Android/data/" + androidStorageUtil.getPackageName() + "/cache/images";
        File externalCacheDir = new File(androidStorageUtil.externalStorageDir(), relPathToExternalCacheDir);
        if (!externalCacheDir.isDirectory()) {
            externalCacheDir.mkdirs();
        }
        return externalCacheDir;
    }

    private static void addNoMediaFile(File dir) {
        try {
            new File(dir, ".nomedia").createNewFile();
        } catch (IOException e) {
            Log.e(TAG, "Unable to save '.nomedia' file", e);
        }
    }

}

