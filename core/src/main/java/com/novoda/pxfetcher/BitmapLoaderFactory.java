package com.novoda.pxfetcher;

import com.novoda.imageloader.core.cache.CacheManager;
import com.novoda.imageloader.core.file.FileManager;
import com.novoda.imageloader.core.network.NetworkManager;
import com.novoda.pxfetcher.task.Retriever;

public class BitmapLoaderFactory {

    private static final double DEFAULT_ALLOWED_RATIO_CHANGE_TRESHOLD = .05;
    private static final double DEFAULT_ALLOWED_STRETCHING_TRESHOLD = .25;
    private static final int MAX_DOWNSAMPLING = 2;
    private final CacheManager cacheManager;
    private final FileManager fileManager;
    private final NetworkManager networkManager;

    public BitmapLoaderFactory(CacheManager cacheManager, FileManager fileManager, NetworkManager networkManager) {
        this.cacheManager = cacheManager;
        this.fileManager = fileManager;
        this.networkManager = networkManager;
    }

    public BitmapLoader createDefaultLoader() {
        BitmapProcessor cacheBitmapProcessor = createDefaultBitmapProcessor(cacheManager);
        BitmapDecoder croppedDecoder = createDefaultBitmapDecoder(DEFAULT_ALLOWED_STRETCHING_TRESHOLD, MAX_DOWNSAMPLING);

        Retriever memoryRetriever = createDefaultRetriever();
        Retriever fileRetriever = new FileRetriever(fileManager, croppedDecoder, cacheBitmapProcessor);
        Retriever networkRetriever = new NetworkRetriever(networkManager, fileManager, croppedDecoder, cacheBitmapProcessor);
        Retriever strategy = new FallbackStrategyRetriever(memoryRetriever, fileRetriever, networkRetriever);

        return new BitmapLoader(strategy);
    }

    protected BitmapProcessor createDefaultBitmapProcessor(CacheManager cacheManager) {
        return new CacheBitmapProcessor(cacheManager);
    }

    protected BitmapDecoder createDefaultBitmapDecoder(double defaultAllowedStretchingTreshold, int maxDownsampling) {
        return new DownsamplingBitmapDecoder(defaultAllowedStretchingTreshold, maxDownsampling);
    }

    public Retriever createDefaultRetriever() {
        BitmapProcessor ratioCheckBitmapProcessor = new RatioCheckBitmapProcessor(DEFAULT_ALLOWED_RATIO_CHANGE_TRESHOLD);
        return new MemoryRetriever(cacheManager, ratioCheckBitmapProcessor);
    }

}
