package com.novoda.pxfetcher;

import android.graphics.Bitmap;

import com.novoda.pxfetcher.task.Result;
import com.novoda.pxfetcher.task.Retriever;
import com.novoda.pxfetcher.task.TagWrapper;

import java.io.File;

public class FileRetriever<TagWrapperType extends TagWrapper<MetadataType>, MetadataType> implements Retriever<TagWrapperType, MetadataType> {

    private final FileNameFactory<MetadataType> fileNameFactory;
    private final BitmapProcessor bitmapProcessor;
    private final BitmapDecoder decoder;

    public FileRetriever(FileNameFactory<MetadataType> fileNameFactory, BitmapDecoder decoder, BitmapProcessor bitmapProcessor) {
        this.fileNameFactory = fileNameFactory;
        this.bitmapProcessor = bitmapProcessor;
        this.decoder = decoder;
    }

    @Override
    public Result retrieve(TagWrapperType tagWrapper) {
        Bitmap bitmap = innerRetrieve(tagWrapper);
        Bitmap elaborated = bitmapProcessor.elaborate(tagWrapper, bitmap);
        if (elaborated == null) {
            return new Failure();
        }
        return new Success(elaborated);
    }

    private Bitmap innerRetrieve(TagWrapperType tagWrapper) {
        String sourceUrl = tagWrapper.getSourceUrl();
        String fileName = fileNameFactory.getFileName(sourceUrl, tagWrapper.getMetadata());
        File file = new File(fileName);
        if (isInvalid(file)) {
            return null;
        }
        return decoder.decode(tagWrapper, file);
    }

    private boolean isInvalid(File file) {
        return file == null || !file.exists();
    }

    public static class Success extends com.novoda.pxfetcher.task.Success {
        public Success(Bitmap bitmap) {
            super(bitmap);
        }
    }

    public static class Failure extends com.novoda.pxfetcher.task.Failure {
    }

}
