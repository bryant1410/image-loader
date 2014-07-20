package com.novoda.pxhunter.impl;

import android.widget.ImageView;

import com.novoda.pxhunter.adapter.LruMemoryCacher;
import com.novoda.pxhunter.adapter.NetworkFetcher;
import com.novoda.pxhunter.port.Cacher;
import com.novoda.pxhunter.port.Fetcher;
import com.novoda.pxhunter.port.FileNameFactory;
import com.novoda.pxhunter.port.ImageSetter;
import com.novoda.pxhunter.port.PxHunter;
import com.novoda.pxhunter.task.Retriever;

public class PxHunterFactory {

    public static PxHunter defaultPxHunter() {
        return DefaultPxHunter.newInstance();
    }

    private static class DefaultPxHunter implements PxHunter<Void> {

        private static final int PERCENTAGE_OF_MEMORY_FOR_CACHE = 15;

        private final Cacher cacher;
        private final Fetcher networkFetcher;
        private final FileNameFactory<Void> fileNameFactory;
        private final ImageSetter imageSetter;
        private final Retriever retriever;

        public static DefaultPxHunter newInstance() {
            Cacher cacher = LruMemoryCacher.newInstance(PERCENTAGE_OF_MEMORY_FOR_CACHE);
            Fetcher networkFetcher = new NetworkFetcher();

            // FIXME: create a PxHunter with real defaults
            return new DefaultPxHunter(cacher, networkFetcher, null, null, null);
        }

        private DefaultPxHunter(Cacher cacher, Fetcher networkFetcher, FileNameFactory<Void> fileNameFactory, ImageSetter imageSetter, Retriever retriever) {
            this.cacher = cacher;
            this.networkFetcher = networkFetcher;
            this.fileNameFactory = fileNameFactory;
            this.imageSetter = imageSetter;
            this.retriever = retriever;
        }

        @Override
        public void load(String url, ImageView view, Void metadata) {

        }

    }

}
