package com.novoda.pxhunter.impl;

import android.graphics.Bitmap;

import com.novoda.pxhunter.port.NetworkFetcher;
import com.novoda.pxhunter.port.BitmapDecoder;
import com.novoda.pxhunter.port.BitmapProcessor;
import com.novoda.pxhunter.port.FileNameFactory;
import com.novoda.pxhunter.task.Result;
import com.novoda.pxhunter.task.Retriever;
import com.novoda.pxhunter.task.TagWrapper;

import java.io.File;

public class NetworkRetriever<T extends TagWrapper<V>, V> implements Retriever<T, V> {

    private final NetworkFetcher networkFetcher;
    private final FileNameFactory<V> fileNameFactory;
    private final BitmapProcessor bitmapProcessor;
    private final BitmapDecoder decoder;

    public NetworkRetriever(NetworkFetcher networkFetcher, FileNameFactory<V> fileNameFactory, BitmapDecoder decoder, BitmapProcessor bitmapProcessor) {
        this.networkFetcher = networkFetcher;
        this.fileNameFactory = fileNameFactory;
        this.bitmapProcessor = bitmapProcessor;
        this.decoder = decoder;
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
        String sourceUrl = tagWrapper.getSourceUrl();
        String savedUrl = fileNameFactory.getFileName(sourceUrl, tagWrapper.getMetadata());
        File file = new File(savedUrl);
        networkFetcher.retrieveImage(sourceUrl, file);
        return decoder.decode(tagWrapper, file);
    }

    public static class Success extends com.novoda.pxhunter.task.Success {
        public Success(Bitmap bitmap) {
            super(bitmap);
        }
    }

    public static class Failure extends com.novoda.pxhunter.task.Failure {

    }

}
