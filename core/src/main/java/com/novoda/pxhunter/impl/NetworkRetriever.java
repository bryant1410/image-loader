package com.novoda.pxhunter.impl;

import android.graphics.Bitmap;

import com.novoda.pxhunter.port.BitmapDecoder;
import com.novoda.pxhunter.port.BitmapProcessor;
import com.novoda.pxhunter.port.Fetcher;
import com.novoda.pxhunter.task.Result;
import com.novoda.pxhunter.task.ResultRetriever;
import com.novoda.pxhunter.task.Metadata;

public class NetworkRetriever<T extends Metadata<V>, V> implements ResultRetriever<T, V> {

    private final Fetcher fetcher;
    private final BitmapProcessor bitmapProcessor;
    private final BitmapDecoder decoder;

    public NetworkRetriever(Fetcher fetcher, BitmapDecoder decoder, BitmapProcessor bitmapProcessor) {
        this.fetcher = fetcher;
        this.bitmapProcessor = bitmapProcessor;
        this.decoder = decoder;
    }

    @Override
    public Result retrieve(T metadata) {
        try {
            byte[] data = fetcher.fetch(metadata.getSourceUrl());
            return elaboratedBitmapResultFrom(data, metadata);
        } catch (Fetcher.UnableToFetchException e) {
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

//    private static class TempFileWriter {
//
//        private static final String TEMP_FILE_PREFIX = ".temp_"
//        private final FileCache fileCache;
//
//        private TempFileWriter(FileCache fileCache) {
//            this.fileCache = fileCache;
//        }
//
//        public void createTempFile(String url, InputStream inputStream) {
//            File file = fileCache.cachedFileFrom(url);
//            new FileOutputStream(file).write();
//        }
//
//
//
//
//
//    }

}
