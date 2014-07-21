package com.novoda.pxhunter.impl;

import android.content.Context;
import android.content.res.Resources;
import android.widget.ImageView;

import com.novoda.pxhunter.adapter.FileCacher;
import com.novoda.pxhunter.adapter.LruMemoryCacher;
import com.novoda.pxhunter.adapter.NetworkFetcher;
import com.novoda.pxhunter.port.BitmapDecoder;
import com.novoda.pxhunter.port.Cacher;
import com.novoda.pxhunter.port.Fetcher;
import com.novoda.pxhunter.port.PxHunter;
import com.novoda.pxhunter.task.NoCustomMetadata;
import com.novoda.pxhunter.task.ResultRetriever;

public class PxHunterFactory {

    public static PxHunter defaultPxHunter(Context context) {
        return DefaultPxHunter.newInstance(context);
    }

    private static class DefaultPxHunter implements PxHunter<Void> {

        private static final double DEFAULT_ALLOWED_STRETCHING_THRESHOLD = 0.1;
        private static final int PERCENTAGE_OF_MEMORY_FOR_CACHE = 15;
        private static final int MAX_DOWNSAMPLING_FACTOR = 3;

        private final BitmapLoader bitmapLoader;
        private final ImageViewCallbackFactory callbackFactory;

        private final int placeholderResId;

        public static DefaultPxHunter newInstance(Context context) {
            Cacher memoryCacher = LruMemoryCacher.newInstance(PERCENTAGE_OF_MEMORY_FOR_CACHE);
            Cacher fileCacher = FileCacher.newInstance(context);
            Fetcher networkFetcher = new NetworkFetcher();
            Resources resources = context.getResources();

            ImageViewCallbackFactory imageViewCallbackFactory = ImageViewCallbackFactory.newInstance();

            BitmapDecoder bitmapDecoder = new DownsamplingBitmapDecoder(resources, DEFAULT_ALLOWED_STRETCHING_THRESHOLD, MAX_DOWNSAMPLING_FACTOR);
            ResultRetriever fallbackRetriever = newFallbackStrategyResultRetriever(memoryCacher, fileCacher, networkFetcher, bitmapDecoder);

            return new DefaultPxHunter(new BitmapLoader(fallbackRetriever), imageViewCallbackFactory, 0);
        }

        private static ResultRetriever newFallbackStrategyResultRetriever(Cacher memoryCacher, Cacher fileCacher, Fetcher networkFetcher, BitmapDecoder bitmapDecoder) {
            ResultRetriever memoryRetriever = new MemoryRetriever(memoryCacher, new MetadataValidityCheckBitmapProcessor());
            ResultRetriever fileRetriever = new FileRetriever(fileCacher, bitmapDecoder, new DummyBitmapProcessor());
            ResultRetriever networkRetriever = new NetworkRetriever(networkFetcher, bitmapDecoder, new DummyBitmapProcessor());

            return new FallbackStrategyRetriever(memoryRetriever, fileRetriever, networkRetriever);
        }

        private DefaultPxHunter(BitmapLoader bitmapLoader, ImageViewCallbackFactory callbackFactory, int placeholderResId) {
            this.bitmapLoader = bitmapLoader;
            this.callbackFactory = callbackFactory;
            this.placeholderResId = placeholderResId;
        }

        @Override
        public void load(String url, ImageView view, Void metadata) {
            if (Tag.shouldSkip(url, view)) {
                return;
            }

            view.setImageResource(placeholderResId);
            NoCustomMetadata noCustomMetadata = new NoCustomMetadata(url);
            view.setTag(noCustomMetadata.getTag());

            // TODO: check the memory retriever first, and then set that if available
            // this will save an asynctask which is needed for file/network
            bitmapLoader.load(noCustomMetadata, callbackFactory.createCallback(view));
        }

    }

}
