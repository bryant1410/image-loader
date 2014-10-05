package com.novoda.pxfetcher;

import android.graphics.Bitmap;

import com.novoda.imageloader.core.file.FileManager;
import com.novoda.imageloader.core.file.util.FileUtil;
import com.novoda.imageloader.core.util.Log;

import java.io.File;
import java.io.FileOutputStream;

public class BetterFileManager implements FileManager {

    private static final int BITMAP_QUALITY = 90;

    private final FileUtil fileUtil;
    private final File cacheDirectory;
    private final long cacheExpirationPeriod;

    public BetterFileManager(FileUtil fileUtil, File cacheDirectory, long cacheExpirationPeriod) {
        this.fileUtil = fileUtil;
        this.cacheDirectory = cacheDirectory;
        this.cacheExpirationPeriod = cacheExpirationPeriod;
    }

    /**
     * Clean is removing all the files in the cache directory.
     */
    @Override
    public void clean() {
        fileUtil.deleteFileCache(cacheDirectory.getAbsolutePath());
    }

    /**
     * CleanOldFile is removing all the files in the cache directory where the
     * timestamp is older then the expiration time.
     */
    @Override
    public void cleanOldFiles() {
        fileUtil.reduceFileCache(cacheDirectory.getAbsolutePath(), cacheExpirationPeriod);
    }

    @Override
    public String getFilePath(String imageUrl) {
        File f = getFile(imageUrl);
        if (f.exists()) {
            return f.getAbsolutePath();
        }
        return null;
    }

    @Override
    public void saveBitmap(String fileName, Bitmap b, int width, int height) {
        try {
            FileOutputStream out = new FileOutputStream(fileName + "-" + width + "x" + height);
            b.compress(Bitmap.CompressFormat.PNG, BITMAP_QUALITY, out);
        } catch (Exception e) {
            Log.warning("" + e.getMessage());
        }
    }

    @Override
    public File getFile(String url) {
        String filename = String.valueOf(url.hashCode());
        return getFileFromCache(filename);
    }

    @Override
    public File getFile(String url, int width, int height) {
        String filename = url.hashCode() + "-" + width + "x" + height;
        return getFileFromCache(filename);
    }

    private File getFileFromCache(String filename) {
        return new File(cacheDirectory, filename);
    }

}
