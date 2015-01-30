package com.novoda.pxfetcher;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class ImageViewCallbackFactory {

    private final boolean debug;
    private final ImageSetter imageSetter;

    public ImageViewCallbackFactory(boolean debug, ImageSetter imageSetter) {
        this.debug = debug;
        this.imageSetter = imageSetter;
    }

    public BitmapLoader.Callback createCallback(ImageView imageView, int errorResourceId) {
        if (debug) {
            return createDebugCallback(imageView, errorResourceId);
        }
        return createDefaultCallback(imageView, errorResourceId);
    }

    public BitmapLoader.Callback createCallback(ImageView imageView, Bitmap errorBitmap) {
        if (debug) {
            return createDebugCallback(imageView, errorBitmap);
        }
        return createDefaultCallback(imageView, errorBitmap);
    }

    private BitmapLoader.Callback createDefaultCallback(ImageView imageView, Bitmap errorBitmap) {
        return new DefaultImageViewCallbackWithBitmapFallback(imageView, errorBitmap, imageSetter);
    }

    private BitmapLoader.Callback createDefaultCallback(ImageView imageView, int errorResourceId) {
        return new DefaultImageViewCallback(imageView, errorResourceId, imageSetter);
    }

    private BitmapLoader.Callback createDebugCallback(ImageView imageView, Bitmap errorBitmap) {
        return new DebugImageViewCallbackWithBitmapFallback(imageView, errorBitmap, imageSetter);
    }

    private BitmapLoader.Callback createDebugCallback(ImageView imageView, int errorResourceId) {
        return new DebugImageViewCallback(imageView, errorResourceId, imageSetter);
    }
}
