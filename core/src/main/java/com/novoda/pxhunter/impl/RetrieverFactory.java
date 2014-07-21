package com.novoda.pxhunter.impl;

import android.content.res.Resources;

import com.novoda.pxhunter.port.BitmapDecoder;
import com.novoda.pxhunter.port.BitmapProcessor;
import com.novoda.pxhunter.port.Cacher;
import com.novoda.pxhunter.port.Fetcher;
import com.novoda.pxhunter.task.Metadata;
import com.novoda.pxhunter.task.ResultRetriever;

public class RetrieverFactory<T extends Metadata<V>, V> {

    private static final double DEFAULT_ALLOWED_STRETCHING_THRESHOLD = .10;

    private final Cacher memoryCacher;
    private final Cacher fileCacher;
    private final Fetcher networkFetcher;
    private final int maxDownsamplingFactor;
    private final Resources resources;

    public RetrieverFactory(Cacher memoryCacher, Cacher fileCacher, Fetcher networkFetcher, int maxDownsamplingFactor, Resources resources) {
        this.memoryCacher = memoryCacher;
        this.fileCacher = fileCacher;
        this.networkFetcher = networkFetcher;
        this.maxDownsamplingFactor = maxDownsamplingFactor;
        this.resources = resources;
    }

    public ResultRetriever<T, V> createDefaultRetriever() {
        ResultRetriever memoryRetriever = createMemoryRetriever();
        ResultRetriever fileRetriever = createFileRetriever();
        ResultRetriever networkRetriever = createNetworkRetriever();
        return new FallbackStrategyRetriever<T, V>(memoryRetriever, fileRetriever, networkRetriever);
    }

    public ResultRetriever<T, V> createMemoryRetriever() {
        BitmapProcessor bitmapProcessor = new MetadataValidityCheckBitmapProcessor();
        return new MemoryRetriever<T, V>(memoryCacher, bitmapProcessor);
    }

    public ResultRetriever<T, V> createNetworkRetriever() {
        ResultRetriever fileRetriever = createFileRetriever();
        BitmapDecoder croppedDecoder = new DownsamplingBitmapDecoder(resources, DEFAULT_ALLOWED_STRETCHING_THRESHOLD, maxDownsamplingFactor);
        ResultRetriever networkRetriever = new NetworkRetriever<T, V>(networkFetcher, croppedDecoder, new DummyBitmapProcessor());
        return new FallbackStrategyRetriever<T, V>(fileRetriever, networkRetriever);
    }

    public ResultRetriever createFileRetriever() {
        BitmapDecoder croppedDecoder = new DownsamplingBitmapDecoder(resources, DEFAULT_ALLOWED_STRETCHING_THRESHOLD, maxDownsamplingFactor);

        return new FileRetriever<T, V>(fileCacher, croppedDecoder, new DummyBitmapProcessor());
    }

}
