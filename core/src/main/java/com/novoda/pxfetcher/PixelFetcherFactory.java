package com.novoda.pxfetcher;

import android.content.Context;
import android.widget.ImageView;

public class PixelFetcherFactory {

    public static PixelFetcher newDefaultPixelFetcher(Context context) {
        return new DefaultPixelFetcher();
    }

    private static class DefaultPixelFetcher implements PixelFetcher<Metadata> {

        @Override
        public void load(String url, ImageView view) {
            // TODO create metadata, call load(metadata, view);
        }

        @Override
        public void load(Metadata metadata, ImageView view) {
            // TODO load image, using metadata, into view
        }

    }

}
