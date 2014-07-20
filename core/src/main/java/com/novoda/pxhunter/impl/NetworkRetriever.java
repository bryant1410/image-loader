package com.novoda.pxhunter.impl;

import android.graphics.Bitmap;

import com.novoda.pxhunter.port.BitmapDecoder;
import com.novoda.pxhunter.port.BitmapProcessor;
import com.novoda.pxhunter.port.Fetcher;
import com.novoda.pxhunter.task.Result;
import com.novoda.pxhunter.task.Retriever;
import com.novoda.pxhunter.task.TagWrapper;

import java.io.InputStream;

public class NetworkRetriever<T extends TagWrapper<V>, V> implements Retriever<T, V> {

    private final Fetcher fetcher;
    private final BitmapProcessor bitmapProcessor;
    private final BitmapDecoder decoder;

    public NetworkRetriever(Fetcher fetcher, BitmapDecoder decoder, BitmapProcessor bitmapProcessor) {
        this.fetcher = fetcher;
        this.bitmapProcessor = bitmapProcessor;
        this.decoder = decoder;
    }

    @Override
    public Result retrieve(T tagWrapper) {
        try {
            InputStream inputStream = fetcher.fetch(tagWrapper.getSourceUrl());
            return elaboratedBitmapResultFrom(inputStream, tagWrapper);
        } catch (Fetcher.UnableToFetchException e) {
            return new Failure();
        }
    }

    private Result elaboratedBitmapResultFrom(InputStream inputStream, T tagWrapper) {
        if (tagWrapper.isNoLongerValid()) {
            return new Failure();
        }
        Bitmap bitmap = decoder.decode(tagWrapper.getTargetWidth(), tagWrapper.getTargetHeight(), inputStream);
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
