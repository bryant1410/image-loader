package com.novoda.pxhunter.impl;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.novoda.pxhunter.port.ImageSetter;

public class ImageViewCallbackFactory {

    private final ImageSetter imageSetter;

    public static ImageViewCallbackFactory newInstance() {
        return new ImageViewCallbackFactory(new DefaultImageSetter());
    }

    public ImageViewCallbackFactory(ImageSetter imageSetter) {
        this.imageSetter = imageSetter;
    }

    public BitmapLoader.Callback createCallback(ImageView imageView) {
        return new DefaultImageViewCallback(imageView, 0, null, imageSetter);
    }

    private static class DefaultImageSetter implements ImageSetter {

        @Override
        public void setBitmap(ImageView imageView, Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }

    }

}
