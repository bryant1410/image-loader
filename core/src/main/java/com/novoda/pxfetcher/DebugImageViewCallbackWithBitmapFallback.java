package com.novoda.pxfetcher;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class DebugImageViewCallbackWithBitmapFallback extends DebugImageViewCallback {

    private static final int NOT_USED = 0;
    private final Bitmap errorBitmap;

    public DebugImageViewCallbackWithBitmapFallback(ImageView imageView, Bitmap errorBitmap, ImageSetter imageSetter) {
        super(imageView, NOT_USED, imageSetter);
        this.errorBitmap = errorBitmap;
    }

    @Override
    protected void showError(ImageView imageView) {
        imageView.setImageBitmap(errorBitmap);
    }
}