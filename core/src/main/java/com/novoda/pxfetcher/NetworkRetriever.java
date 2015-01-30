package com.novoda.pxfetcher;

import android.graphics.Bitmap;

import com.novoda.imageloader.core.exception.ImageNotFoundException;
import com.novoda.imageloader.core.file.FileManager;
import com.novoda.imageloader.core.network.NetworkManager;
import com.novoda.pxfetcher.task.Result;
import com.novoda.pxfetcher.task.Retriever;
import com.novoda.pxfetcher.task.TagWrapper;

import java.io.File;

public class NetworkRetriever implements Retriever {

    private final NetworkManager networkManager;
    private final FileManager fileManager;
    private final BitmapProcessor bitmapProcessor;
    private final BitmapDecoder decoder;

    public NetworkRetriever(NetworkManager networkManager, FileManager fileManager, BitmapDecoder decoder, BitmapProcessor bitmapProcessor) {
        this.networkManager = networkManager;
        this.fileManager = fileManager;
        this.bitmapProcessor = bitmapProcessor;
        this.decoder = decoder;
    }

    @Override
    public Result retrieve(TagWrapper tagWrapper) {
        Bitmap bitmap = innerRetrieve(tagWrapper);
        Bitmap elaborated = bitmapProcessor.elaborate(tagWrapper, bitmap);
        if (elaborated == null) {
            return new Failure();
        }
        return new Success(elaborated);
    }

    private Bitmap innerRetrieve(TagWrapper tagWrapper) {
        String sourceUrl = tagWrapper.getSourceUrl();
        String savedUrl = fileManager.getFile(sourceUrl).getAbsolutePath();
        File file = new File(savedUrl);
        Bitmap bitmap;
        try {
            bitmap = fetchBitmap(tagWrapper, sourceUrl, file);
        } catch (ImageNotFoundException e) {
            bitmap = null;
        }
        return bitmap;
    }

    private Bitmap fetchBitmap(TagWrapper tagWrapper, String sourceUrl, File file) {
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
