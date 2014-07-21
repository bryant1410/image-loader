package com.novoda.pxhunter.impl;

import android.graphics.Bitmap;

import com.novoda.pxhunter.port.BitmapDecoder;
import com.novoda.pxhunter.port.BitmapProcessor;
import com.novoda.pxhunter.port.Cacher;
import com.novoda.pxhunter.task.Result;
import com.novoda.pxhunter.task.ResultRetriever;
import com.novoda.pxhunter.task.Metadata;

public class FileRetriever<T extends Metadata<V>, V> implements ResultRetriever<T, V> {

    private static final String TAG = FileRetriever.class.getSimpleName();

    private final Cacher<byte[]> cacher;
    private final BitmapProcessor bitmapProcessor;
    private final BitmapDecoder decoder;

    public FileRetriever(Cacher<byte[]> cacher, BitmapDecoder decoder, BitmapProcessor bitmapProcessor) {
        this.cacher = cacher;
        this.bitmapProcessor = bitmapProcessor;
        this.decoder = decoder;
    }

    @Override
    public Result retrieve(T metadata) {
        try {
            byte[] data = cacher.get(metadata.getSourceUrl());
            return elaboratedBitmapResultFrom(data, metadata);
        } catch (Cacher.CachedItemNotFoundException e) {
            return new Failure();
        }
    }

    private Result elaboratedBitmapResultFrom(byte[] data, T metadata) {
        if (metadata.isNoLongerValid()) {
            return new Failure();
        }
        Bitmap bitmap = decoder.decode(metadata, data);
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
