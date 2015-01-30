package com.novoda.pxfetcher;

import android.graphics.Bitmap;

import com.novoda.imageloader.core.file.FileManager;
import com.novoda.pxfetcher.task.Result;
import com.novoda.pxfetcher.task.Retriever;
import com.novoda.pxfetcher.task.TagWrapper;

import java.io.File;

public class FileRetriever implements Retriever {

    private final FileManager fileManager;
    private final BitmapProcessor bitmapProcessor;
    private final BitmapDecoder decoder;

    public FileRetriever(FileManager fileManager, BitmapDecoder decoder, BitmapProcessor bitmapProcessor) {
        this.fileManager = fileManager;
        this.bitmapProcessor = bitmapProcessor;
        this.decoder = decoder;
    }

    @Override
    public Result retrieve(TagWrapper tagWrapper) {
        Bitmap bitmap = innerRetrieve(tagWrapper);
        Bitmap elaborated = bitmapProcessor.elaborate(tagWrapper, bitmap);
        if (elaborated == null) {
            return new FileRetrieverFailure();
        }
        return new FileRetrieverSuccess(elaborated);
    }

    private Bitmap innerRetrieve(TagWrapper tagWrapper) {
        String sourceUrl = tagWrapper.getSourceUrl();
        File file = fileManager.getFile(sourceUrl);
        if (isInvalid(file)) {
            return null;
        }
        return decoder.decode(tagWrapper, file);
    }

    private boolean isInvalid(File file) {
        return file == null || !file.exists();
    }

    public static class FileRetrieverSuccess extends com.novoda.pxfetcher.task.Success {
        public FileRetrieverSuccess(Bitmap bitmap) {
            super(bitmap);
        }
    }

    public static class FileRetrieverFailure extends com.novoda.pxfetcher.task.Failure {

    }

}
