package com.novoda.pxhunter.impl;

import android.graphics.Bitmap;
import android.util.Log;

import com.novoda.pxhunter.port.BitmapProcessor;
import com.novoda.pxhunter.port.Cacher;
import com.novoda.pxhunter.task.Result;
import com.novoda.pxhunter.task.Retriever;
import com.novoda.pxhunter.task.TagWrapper;

public class MemoryRetriever<T extends TagWrapper<V>, V> implements Retriever<T,V> {

    private static final String TAG = MemoryRetriever.class.getSimpleName();

    private final Cacher<Bitmap> cacher;
    private final BitmapProcessor bitmapProcessor;

    public MemoryRetriever(Cacher cacher, BitmapProcessor bitmapProcessor) {
        this.cacher = cacher;
        this.bitmapProcessor = bitmapProcessor;
    }

    @Override
    public Result retrieve(T tagWrapper) {
        try {
            Bitmap bitmap = cacher.get(tagWrapper.getSourceUrl());
            return elaboratedBitmapResultFrom(bitmap, tagWrapper);
        } catch (Cacher.CachedItemNotFoundException e) {
            Log.w(TAG, "Cached item not found for url: " + tagWrapper.getSourceUrl(), e);
            return new Failure();
        }
    }

    private Result elaboratedBitmapResultFrom(Bitmap bitmap, T tagWrapper) {
        if (tagWrapper.isNoLongerValid()) {
            return new Failure();
        }
        Bitmap elaborated = bitmapProcessor.elaborate(tagWrapper, bitmap);
        if (elaborated == null) {
            return new Failure();
        }
        return new Success(elaborated);
    }

    public static class Success extends com.novoda.pxhunter.task.Success {

        public Success(Bitmap bitmap) {
            super(bitmap);
        }

    }

    public static class Failure extends com.novoda.pxhunter.task.Failure {

    }

}
