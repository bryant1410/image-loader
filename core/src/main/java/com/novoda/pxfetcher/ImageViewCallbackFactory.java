package com.novoda.pxfetcher;

import android.widget.ImageView;

public class ImageViewCallbackFactory {

    private final int placeholderResourceId;
    private final int errorResourceId;
    private final boolean debug;
    private final ImageSetter imageSetter;

    public ImageViewCallbackFactory(int placeholderResourceId, int errorResourceId, boolean debug, ImageSetter imageSetter) {
        this.placeholderResourceId = placeholderResourceId;
        this.errorResourceId = errorResourceId;
        this.debug = debug;
        this.imageSetter = imageSetter;
    }

    public BitmapLoader.Callback createCallback(ImageView imageView) {
        if (debug) {
            return createDebugCallback(imageView);
        }
        return createDefaultCallback(imageView);
    }

    private BitmapLoader.Callback createDefaultCallback(ImageView imageView) {
        return new DefaultImageViewCallback(imageView, placeholderResourceId, errorResourceId, imageSetter);
    }

    private BitmapLoader.Callback createDebugCallback(ImageView imageView) {
        return new DebugImageViewCallback(imageView, placeholderResourceId, errorResourceId, imageSetter);
    }

    public BitmapLoader.Callback createDayDreamCallback(ImageView imageView) {
        return new DayDreamImageViewCallback(imageView, errorResourceId, imageSetter);
    }
}
