package com.novoda.pxhunter.impl;

import android.widget.ImageView;

import com.novoda.pxhunter.port.ImageSetter;

public class ImageViewCallbackFactory {

    private final ImageSetter imageSetter;

    public ImageViewCallbackFactory(ImageSetter imageSetter) {
        this.imageSetter = imageSetter;
    }

    public BitmapLoader.Callback createCallback(ImageView imageView) {
        return new DefaultImageViewCallback(imageView, 0, null, imageSetter);
    }

}
