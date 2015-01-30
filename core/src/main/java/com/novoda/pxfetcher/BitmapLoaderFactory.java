package com.novoda.pxfetcher;

import com.novoda.imageloader.core.cache.CacheManager;
import com.novoda.imageloader.core.file.FileManager;
import com.novoda.imageloader.core.network.NetworkManager;
import com.novoda.pxfetcher.task.Retriever;

public class BitmapLoaderFactory {

    private final CacheManager cacheManager;
    private final FileManager fileManager;
    private final NetworkManager networkManager;

    public BitmapLoaderFactory(CacheManager cacheManager, FileManager fileManager, NetworkManager networkManager) {
        this.cacheManager = cacheManager;
        this.fileManager = fileManager;
        this.networkManager = networkManager;
    }

    public BitmapLoader createDefaultLoader(Retriever retriever) {
        return new BitmapLoader(retriever);
    }

    public Retriever createFallbackStrategyRetriever() {
        Retriever memoryRetriever = createMemoryRetriever();
        Retriever fileRetriever = createFileRetriever();
        Retriever networkRetriever = createNetworkRetriever();
        return new FallbackStrategyRetriever(memoryRetriever, fileRetriever, networkRetriever);
    }

    public Retriever createMemoryRetriever() {
        BitmapProcessor bitmapProcessor = new NonReactiveBitmapProcessor();
        return new MemoryRetriever(cacheManager, bitmapProcessor);
    }

    public FileRetriever createFileRetriever() {
        BitmapProcessor bitmapProcessor = new CacheBitmapProcessor(cacheManager);
        BitmapDecoder bitmapDecoder = new SimpleDecoder();
        return new FileRetriever(fileManager, bitmapDecoder, bitmapProcessor);
    }

    public NetworkRetriever createNetworkRetriever() {
        BitmapProcessor bitmapProcessor = new CacheBitmapProcessor(cacheManager);
        BitmapDecoder bitmapDecoder = new SimpleDecoder();
        return new NetworkRetriever(networkManager, fileManager, bitmapDecoder, bitmapProcessor);
    }

}
