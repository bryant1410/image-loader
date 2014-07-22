package com.novoda.pxhunter.impl;

import android.content.Context;
import android.content.res.Resources;
import android.widget.ImageView;

import com.novoda.pxhunter.adapter.FileCacher;
import com.novoda.pxhunter.adapter.LifoTaskScheduler;
import com.novoda.pxhunter.adapter.LruMemoryCacher;
import com.novoda.pxhunter.adapter.NetworkFetcher;
import com.novoda.pxhunter.port.BitmapDecoder;
import com.novoda.pxhunter.port.Cacher;
import com.novoda.pxhunter.port.Fetcher;
import com.novoda.pxhunter.port.PxHunter;
import com.novoda.pxhunter.port.TaskScheduler;
import com.novoda.pxhunter.task.FetchBitmapTask;
import com.novoda.pxhunter.task.Metadata;
import com.novoda.pxhunter.task.NoCustomMetadata;
import com.novoda.pxhunter.task.PxHunterTask;
import com.novoda.pxhunter.task.Result;
import com.novoda.pxhunter.task.ResultRetriever;
import com.novoda.pxhunter.task.Success;

public class PxHunterFactory {

    public static PxHunter defaultPxHunter(Context context) {
        return DefaultPxHunter.newInstance(context);
    }

    private static class DefaultPxHunter implements PxHunter<Void> {

        private static final double DEFAULT_ALLOWED_STRETCHING_THRESHOLD = 0.1;
        private static final int PERCENTAGE_OF_MEMORY_FOR_CACHE = 15;
        private static final int MAX_DOWNSAMPLING_FACTOR = 3;

        private final ResultRetriever fallbackStrategyBitmapResultRetriever;
        private final ImageViewCallbackFactory callbackFactory;
        private final ResultRetriever memoryBitmapResultRetriever;
        private final TaskScheduler scheduler;

        private final int placeholderResId;

        public static DefaultPxHunter newInstance(Context context) {
            Cacher memoryCacher = LruMemoryCacher.newInstance(PERCENTAGE_OF_MEMORY_FOR_CACHE);
            Cacher fileCacher = FileCacher.newInstance(context);
            Fetcher networkFetcher = new NetworkFetcher();
            Resources resources = context.getResources();

            ImageViewCallbackFactory callbackFactory = ImageViewCallbackFactory.newInstance();
            TaskScheduler scheduler = new LifoTaskScheduler();

            BitmapDecoder bitmapDecoder = new DownsamplingBitmapDecoder(resources, DEFAULT_ALLOWED_STRETCHING_THRESHOLD, MAX_DOWNSAMPLING_FACTOR);
            ResultRetriever memoryBitmapResultRetriever = new MemoryRetriever(memoryCacher, new MetadataValidityCheckBitmapProcessor());
            ResultRetriever fallbackRetriever = newFallbackStrategyResultRetriever(fileCacher, networkFetcher, bitmapDecoder);


            return new DefaultPxHunter(callbackFactory, scheduler, memoryBitmapResultRetriever, fallbackRetriever, 0);
        }

        private static ResultRetriever newFallbackStrategyResultRetriever(Cacher fileCacher, Fetcher networkFetcher, BitmapDecoder bitmapDecoder) {
            ResultRetriever fileRetriever = new FileRetriever(fileCacher, bitmapDecoder, new DummyBitmapProcessor());
            ResultRetriever networkRetriever = new NetworkRetriever(networkFetcher, bitmapDecoder, new DummyBitmapProcessor());

            return new FallbackStrategyRetriever(fileRetriever, networkRetriever);
        }

        private DefaultPxHunter(ImageViewCallbackFactory callbackFactory, TaskScheduler scheduler, ResultRetriever memoryBitmapResultRetriever, ResultRetriever fallbackStrategyBitmapResultRetriever, int placeholderResId) {
            this.callbackFactory = callbackFactory;
            this.scheduler = scheduler;
            this.memoryBitmapResultRetriever = memoryBitmapResultRetriever;
            this.fallbackStrategyBitmapResultRetriever = fallbackStrategyBitmapResultRetriever;
            this.placeholderResId = placeholderResId;
        }

        @Override
        public void load(String url, ImageView view, Void noMetadata) {
            if (Tag.shouldSkip(url, view)) {
                return;
            }
            Metadata metadata = new NoCustomMetadata(url);
            view.setImageResource(placeholderResId);
            view.setTag(metadata.getTag());
            final BitmapLoader.Callback callback = callbackFactory.createCallback(view);

            Result result = memoryBitmapResultRetriever.retrieve(metadata);
            if (result instanceof Success) {
                result.poke(callback);
                return;
            }

            PxHunterTask fetchBitmapTask = new FetchBitmapTask(metadata, fallbackStrategyBitmapResultRetriever);
            fetchBitmapTask.add(new PxHunterTask.OnCompletedListener() {

                @Override
                public void onCompleted(PxHunterTask completedTask, Result result) {
                    if (result instanceof Success) {
                        result.poke(callback);
                        return;
                    }
                }

            });
            scheduler.schedule(fetchBitmapTask);
        }

    }

}
