package com.novoda.pxhunter.impl;

import android.graphics.Bitmap;

import com.novoda.pxhunter.port.BitmapProcessor;
import com.novoda.pxhunter.port.Cacher;
import com.novoda.pxhunter.task.Result;
import com.novoda.pxhunter.task.Retriever;
import com.novoda.pxhunter.task.TagWrapper;

public class MemoryRetriever<T extends TagWrapper<V>, V> implements Retriever<T,V> {

    private static final int IGNORED = 0;
    private final Cacher cacheManager;
    private final BitmapProcessor bitmapProcessor;

    public MemoryRetriever(Cacher cacheManager, BitmapProcessor bitmapProcessor) {
        this.cacheManager = cacheManager;
        this.bitmapProcessor = bitmapProcessor;
    }

    @Override
    public Result retrieve(T tagWrapper) {
        Bitmap bitmap = innerRetrieve(tagWrapper);
        Bitmap elaborated = bitmapProcessor.elaborate(tagWrapper, bitmap);
        if (elaborated == null) {
            return new Failure();
        }
        return new Success(elaborated);
    }

    private Bitmap innerRetrieve(T tagWrapper) {
        return cacheManager.get(tagWrapper.getSourceUrl());
    }

    public static class Success extends com.novoda.pxhunter.task.Success {
        public Success(Bitmap bitmap) {
            super(bitmap);
        }
    }

    public static class Failure extends com.novoda.pxhunter.task.Failure {

    }

}
