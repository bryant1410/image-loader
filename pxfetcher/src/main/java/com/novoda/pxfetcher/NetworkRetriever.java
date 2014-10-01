package com.novoda.pxfetcher;

import android.graphics.Bitmap;

import com.novoda.imageloader.core.network.NetworkManager;
import com.novoda.pxfetcher.task.Result;
import com.novoda.pxfetcher.task.Retriever;
import com.novoda.pxfetcher.task.TagWrapper;

import java.io.File;

public class NetworkRetriever<T extends TagWrapper<V>, V> implements Retriever<T, V> {

    private final NetworkManager networkManager;
    private final FileNameFactory<V> fileNameFactory;
    private final BitmapProcessor bitmapProcessor;
    private final BitmapDecoder decoder;

    public NetworkRetriever(NetworkManager networkManager, FileNameFactory<V> fileNameFactory, BitmapDecoder decoder, BitmapProcessor bitmapProcessor) {
        this.networkManager = networkManager;
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
        networkManager.retrieveImage(sourceUrl, file);
        return decoder.decode(tagWrapper, file);
    }

    public static class Success extends com.novoda.pxfetcher.task.Success {
        public Success(Bitmap bitmap) {
            super(bitmap);
        }
    }

    public static class Failure extends com.novoda.pxfetcher.task.Failure {

    }

}
