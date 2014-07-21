package com.novoda.pxhunter.impl;

import android.graphics.Bitmap;

import com.novoda.pxhunter.port.BitmapProcessor;
import com.novoda.pxhunter.port.Cacher;
import com.novoda.pxhunter.task.Metadata;
import com.novoda.pxhunter.task.Result;
import com.novoda.pxhunter.task.ResultRetriever;

public class MemoryRetriever<T extends Metadata<V>, V> implements ResultRetriever<T,V> {

    private static final String TAG = MemoryRetriever.class.getSimpleName();

    private final Cacher<Bitmap> cacher;
    private final BitmapProcessor bitmapProcessor;

    public MemoryRetriever(Cacher cacher, BitmapProcessor bitmapProcessor) {
        this.cacher = cacher;
        this.bitmapProcessor = bitmapProcessor;
    }

    @Override
    public Result retrieve(T metadata) {
        try {
            Bitmap bitmap = cacher.get(metadata.getSourceUrl());
            return elaboratedBitmapResultFrom(bitmap, metadata);
        } catch (Cacher.CachedItemNotFoundException e) {
            return new Failure();
        }
    }

    private Result elaboratedBitmapResultFrom(Bitmap bitmap, T metadata) {
        if (metadata.isNoLongerValid()) {
            return new Failure();
        }
        Bitmap elaborated = bitmapProcessor.elaborate(metadata, bitmap);
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
