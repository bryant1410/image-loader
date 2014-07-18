package com.novoda.pxhunter.impl;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.novoda.pxhunter.port.ImageSetter;
import com.novoda.pxhunter.task.Failure;
import com.novoda.pxhunter.task.Success;

import java.lang.ref.WeakReference;

public class DefaultImageViewCallback implements BitmapLoader.Callback {

    private final WeakReference<ImageView> imageViewWeakReference;
    private final int placeholderResourceId;
    private final Bitmap errorBitmap;
    private final ImageSetter imageSetter;

    public DefaultImageViewCallback(ImageView imageView, int placeholderResourceId, Bitmap errorBitmap, ImageSetter imageSetter) {
        this.imageViewWeakReference = new WeakReference<ImageView>(imageView);
        this.placeholderResourceId = placeholderResourceId;
        this.errorBitmap = errorBitmap;
        this.imageSetter = imageSetter;
    }

    @Override
    public void onStart() {
        ImageView imageView = imageViewWeakReference.get();
        Tag.toLoading(imageView);
        if (imageView == null) {
            return;
        }
        imageView.setImageResource(placeholderResourceId);
    }

    @Override
    public void onResult(Success ok) {
        ImageView imageView = imageViewWeakReference.get();
        if (imageView == null) {
            return;
        }

        Bitmap bitmap = ok.getBitmap();
        if (ok instanceof MemoryRetriever.Success) {
            imageView.setImageBitmap(bitmap);
        } else {
            imageSetter.setBitmap(imageView, bitmap);
        }
        Tag.toSuccess(imageView);
    }

    @Override
    public void onResult(Failure ko) {
        ImageView imageView = imageViewWeakReference.get();
        if (imageView == null) {
            return;
        }
        imageView.setImageBitmap(errorBitmap);
        Tag.toFailure(imageView);
    }

}
