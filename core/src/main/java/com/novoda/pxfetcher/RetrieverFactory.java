package com.novoda.pxfetcher;

import android.content.res.Resources;

import com.novoda.pxfetcher.task.Retriever;
import com.novoda.pxfetcher.task.TagWrapper;

public class RetrieverFactory<TagWrapperType extends TagWrapper<MetaDataType>, MetaDataType> {

    private static final double DEFAULT_ALLOWED_STRETCHING_THRESHOLD = .10;
    private final CacheManager cacheManager;
    private final FileNameFactory<MetaDataType> fileManager;
    private final ResourceManager resourceManager;
    private final int maxDownsampling;
    private final Resources resources;

    public RetrieverFactory(CacheManager cacheManager, FileNameFactory<MetaDataType> fileNameFactory, ResourceManager resourceManager, int maxDownsampling, Resources resources) {
        this.cacheManager = cacheManager;
        this.fileManager = fileNameFactory;
        this.resourceManager = resourceManager;
        this.maxDownsampling = maxDownsampling;
        this.resources = resources;
    }

    public Retriever<TagWrapperType, MetaDataType> createDefaultRetriever() {
        Retriever memoryRetriever = createMemoryRetriever();
        Retriever fileRetriever = createFileRetriever();
        Retriever networkRetriever = createNetworkRetriever();
        return new FallbackStrategyRetriever<TagWrapperType, MetaDataType>(memoryRetriever, fileRetriever, networkRetriever);
    }

    public Retriever<TagWrapperType, MetaDataType> createMemoryRetriever() {
        BitmapProcessor bitmapProcessor = new TagWrapperValidityCheckBitmapProcessor();
        return new MemoryRetriever<TagWrapperType, MetaDataType>(cacheManager, bitmapProcessor);
    }

    public Retriever<TagWrapperType, MetaDataType> createNetworkRetriever() {
        Retriever fileRetriever = createFileRetriever();
        BitmapDecoder croppedDecoder = new DownsamplingBitmapDecoder(resources, DEFAULT_ALLOWED_STRETCHING_THRESHOLD, maxDownsampling);
        Retriever networkRetriever = new NetworkRetriever<TagWrapperType, MetaDataType>(resourceManager, fileManager, croppedDecoder, new DummyBitmapProcessor());
        return new FallbackStrategyRetriever<TagWrapperType, MetaDataType>(fileRetriever, networkRetriever);
    }

    public Retriever createFileRetriever() {
        BitmapDecoder croppedDecoder = new DownsamplingBitmapDecoder(resources, DEFAULT_ALLOWED_STRETCHING_THRESHOLD, maxDownsampling);
        return new FileRetriever<TagWrapperType, MetaDataType>(fileManager, croppedDecoder, new DummyBitmapProcessor());
    }

}
