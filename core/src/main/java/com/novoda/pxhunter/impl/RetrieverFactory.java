package com.novoda.pxhunter.impl;

import android.content.res.Resources;

import com.novoda.pxhunter.port.BitmapDecoder;
import com.novoda.pxhunter.port.BitmapProcessor;
import com.novoda.pxhunter.port.Cacher;
import com.novoda.pxhunter.port.FileNameFactory;
import com.novoda.pxhunter.port.NetworkFetcher;
import com.novoda.pxhunter.task.Retriever;
import com.novoda.pxhunter.task.TagWrapper;

public class RetrieverFactory<T extends TagWrapper<V>, V> {

    private static final double DEFAULT_ALLOWED_STRETCHING_THRESHOLD = .10;
    private final Cacher cacheManager;
    private final FileNameFactory<V> fileManager;
    private final NetworkFetcher networkFetcher;
    private final int maxDownsampling;
    private final Resources resources;

    public RetrieverFactory(Cacher cacheManager, FileNameFactory<V> fileNameFactory, NetworkFetcher networkFetcher, int maxDownsampling, Resources resources) {
        this.cacheManager = cacheManager;
        this.fileManager = fileNameFactory;
        this.networkFetcher = networkFetcher;
        this.maxDownsampling = maxDownsampling;
        this.resources = resources;
    }

    public Retriever<T, V> createDefaultRetriever() {
        Retriever memoryRetriever = createMemoryRetriever();
        Retriever fileRetriever = createFileRetriever();
        Retriever networkRetriever = createNetworkRetriever();
        return new FallbackStrategyRetriever<T, V>(memoryRetriever, fileRetriever, networkRetriever);
    }

    public Retriever<T, V> createMemoryRetriever() {
        BitmapProcessor bitmapProcessor = new TagWrapperValidityCheckBitmapProcessor();
        return new MemoryRetriever<T, V>(cacheManager, bitmapProcessor);
    }

    public Retriever<T, V> createNetworkRetriever() {
        Retriever fileRetriever = createFileRetriever();
        BitmapDecoder croppedDecoder = new DownsamplingBitmapDecoder(resources, DEFAULT_ALLOWED_STRETCHING_THRESHOLD, maxDownsampling);
        Retriever networkRetriever = new NetworkRetriever<T, V>(networkFetcher, fileManager, croppedDecoder, new DummyBitmapProcessor());
        return new FallbackStrategyRetriever<T, V>(fileRetriever, networkRetriever);
    }

    public Retriever createFileRetriever() {
        BitmapDecoder croppedDecoder = new DownsamplingBitmapDecoder(resources, DEFAULT_ALLOWED_STRETCHING_THRESHOLD, maxDownsampling);
        return new FileRetriever<T, V>(fileManager, croppedDecoder, new DummyBitmapProcessor());
    }

}
