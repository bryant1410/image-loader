package com.novoda.pxhunter.adapter;

import android.content.Context;

import com.novoda.pxhunter.port.Cacher;
import com.novoda.pxhunter.port.FileNameFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileCacher implements Cacher<InputStream> {

    private static final int BUFFER_SIZE_BYTES = 10 * 1024;

    private final FileCache fileCache;
    private final FileNameFactory fileNameFactory;

    public static FileCacher newInstance(Context context) {
        return new FileCacher(FileCache.newInstance(context), new DefaultFileNameFactory());
    }

    public static FileCacher newInstance(Context context, FileNameFactory fileNameFactory) {
        return new FileCacher(FileCache.newInstance(context), fileNameFactory);
    }

    FileCacher(FileCache fileCache, FileNameFactory fileNameFactory) {
        this.fileCache = fileCache;
        this.fileNameFactory = fileNameFactory;
    }

    @Override
    public InputStream get(String url) throws CachedItemNotFoundException {
        try {
            File file = fileCache.cachedFileFrom(url);
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new CachedItemNotFoundException(e);
        }
    }

    @Override
    public void put(String url, InputStream data) throws UnableToCacheItemException {
        String fileName = fileNameFactory.getFileName(url, null);
        File file = fileCache.cachedFileFrom(fileName);

        try {
            byte[] dataBytes = getBytes(data);
            new FileOutputStream(file).write(dataBytes);
        } catch (IOException e) {
            throw new UnableToCacheItemException(e);
        }
    }

    private byte[] getBytes(InputStream stream) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buf = new byte[BUFFER_SIZE_BYTES];
        int n;
        while ((n = stream.read(buf)) >= 0) {
            output.write(buf, 0, n);
        }
        return output.toByteArray();
    }

    @Override
    public void remove(String url) {
        String fileName = fileNameFactory.getFileName(url, null);
        fileCache.remove(fileName);
    }

    @Override
    public void clean() {
        fileCache.purgeExpiredImages();
    }

}
