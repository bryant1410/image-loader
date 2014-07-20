package com.novoda.pxhunter.impl;

import android.content.res.Resources;

import com.novoda.pxhunter.port.BitmapDecoder;
import com.novoda.pxhunter.port.BitmapProcessor;
import com.novoda.pxhunter.port.Cacher;
import com.novoda.pxhunter.port.Fetcher;
import com.novoda.pxhunter.task.Retriever;
import com.novoda.pxhunter.task.TagWrapper;

public class RetrieverFactory<T extends TagWrapper<V>, V> {

    private static final double DEFAULT_ALLOWED_STRETCHING_THRESHOLD = .10;
    private final Cacher memoryCacher;
    private final Cacher fileCacher;
    private final Fetcher networkFetcher;
    private final int maxDownsampling;
    private final Resources resources;

    public RetrieverFactory(Cacher memoryCacher, Cacher fileCacher, Fetcher networkFetcher, int maxDownsampling, Resources resources) {
        this.memoryCacher = memoryCacher;
        this.fileCacher = fileCacher;
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
        return new MemoryRetriever<T, V>(memoryCacher, bitmapProcessor);
    }

    public Retriever<T, V> createNetworkRetriever() {
        Retriever fileRetriever = createFileRetriever();
        BitmapDecoder croppedDecoder = new DownsamplingBitmapDecoder(resources, DEFAULT_ALLOWED_STRETCHING_THRESHOLD, maxDownsampling);
        Retriever networkRetriever = new NetworkRetriever<T, V>(networkFetcher, croppedDecoder, new DummyBitmapProcessor());
        return new FallbackStrategyRetriever<T, V>(fileRetriever, networkRetriever);
    }

    public Retriever createFileRetriever() {
        BitmapDecoder croppedDecoder = new DownsamplingBitmapDecoder(resources, DEFAULT_ALLOWED_STRETCHING_THRESHOLD, maxDownsampling);

        return new FileRetriever<T, V>(fileCacher, croppedDecoder, new DummyBitmapProcessor());
    }

}
