package com.novoda.pxhunter.adapter;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.novoda.pxhunter.port.Cacher;

public class LruMemoryCacher implements Cacher {

    private static final int MAX_CACHE_SIZE_BYTES = 16 * 1024 * 1024;

    private LruCache<String, Bitmap> cache;
    private int cacheSizeBytes;

    public static LruMemoryCacher newInstance() {
        return new LruMemoryCacher(MAX_CACHE_SIZE_BYTES);
    }

    public static LruMemoryCacher newInstance(int percentageOfMemoryForCache) {
        Runtime runtime = Runtime.getRuntime();
        int calculatedSize = (int) (runtime.maxMemory() * percentageOfMemoryForCache / 100);
        int cacheSizeBytes = Math.min(calculatedSize, MAX_CACHE_SIZE_BYTES);

        return new LruMemoryCacher(cacheSizeBytes);
    }

    LruMemoryCacher(int cacheSizeBytes) {
        this.cacheSizeBytes = cacheSizeBytes;
        reset();
    }

    private void reset() {
        if (cache != null) {
            cache.evictAll();
        } else {
            cache = new LruCache<String, Bitmap>(cacheSizeBytes) {

                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    return 4 * bitmap.getWidth() * bitmap.getHeight();
                }

            };
        }
    }

    @Override
    public Bitmap get(String id) {
        return cache.get(id);
    }

    @Override
    public void put(String id, Bitmap bitmap) {
        cache.put(id, bitmap);
    }

    @Override
    public void remove(String id) {
        cache.remove(id);
    }

    @Override
    public void clean() {
        reset();
    }

}
