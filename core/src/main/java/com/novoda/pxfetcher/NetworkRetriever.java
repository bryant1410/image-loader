package com.novoda.pxfetcher;

import android.graphics.Bitmap;

import com.novoda.pxfetcher.task.Result;
import com.novoda.pxfetcher.task.Retriever;
import com.novoda.pxfetcher.task.TagWrapper;

import java.io.File;

public class NetworkRetriever<TagWrapperType extends TagWrapper<MetadataType>, MetadataType> implements Retriever<TagWrapperType, MetadataType> {

    private final ResourceManager resourceManager;
    private final FileNameFactory<MetadataType> fileNameFactory;
    private final BitmapProcessor bitmapProcessor;
    private final BitmapDecoder decoder;

    public NetworkRetriever(ResourceManager resourceManager, FileNameFactory<MetadataType> fileNameFactory, BitmapDecoder decoder, BitmapProcessor bitmapProcessor) {
        this.resourceManager = resourceManager;
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
        String savedUrl = fileNameFactory.getFileName(sourceUrl, tagWrapper.getMetadata());
        File file = new File(savedUrl);
        resourceManager.retrieveImage(sourceUrl, file);
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
