package com.novoda.pxfetcher;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.widget.ImageView;

public class PixelFetcherFactory {

    public static PixelFetcher newPixelFetcher(Context context) {
        return new DefaultPixelFetcher(context.getResources());
    }

    private static class DefaultPixelFetcher implements PixelFetcher<Metadata> {

        private final Resources resources;

        private DefaultPixelFetcher(Resources resources) {
            this.resources = resources;
        }

        @Override
        public void load(String url, ImageView view) {
            load(new DefaultMetadata(url), view);
        }

        @Override
        public void load(Metadata metadata, final ImageView view) {
            PxFetcherTask<Void, Void, Bitmap> task = new FetchLocalAssetTask(metadata, resources);
            task.add(new PxFetcherTask.Callback<Bitmap>() {

                @Override
                public void onComplete(PxFetcherTask completedTask, Bitmap bitmap) {
                    // TODO: cache the bitmap
                    view.setImageBitmap(bitmap);
                }

                @Override
                public void onCancelled(PxFetcherTask cancelledTask) {
                    // do nothing
                }

                @Override
                public void onCancelled(PxFetcherTask cancelledTask, Bitmap bitmap) {
                    // TODO: cache the bitmap, don't set it on the imageview though
                }

            });
            task.execute();
        }

    }

    private static class DefaultMetadata implements Metadata {

        private final Id id;
        private final String url;

        public DefaultMetadata(String url) {
            this.url = url;
            id = new Id(url.hashCode());
        }

        @Override
        public Id getId() {
            return id;
        }

        @Override
        public Id getGroupId() {
            return getId();
        }

        @Override
        public String getUri() {
            return url;
        }

    }



}
