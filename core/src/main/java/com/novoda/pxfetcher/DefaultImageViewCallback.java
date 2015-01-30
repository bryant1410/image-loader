package com.novoda.pxfetcher;

import android.widget.ImageView;

import com.novoda.imageloader20.task.Failure;
import com.novoda.imageloader20.task.Success;

import java.lang.ref.WeakReference;

public class DefaultImageViewCallback implements BitmapLoader.Callback {

    private final WeakReference<ImageView> imageViewWeakReference;
    private final int errorResourceId;
    private final ImageSetter imageSetter;

    public DefaultImageViewCallback(ImageView imageView, int errorResourceId, ImageSetter imageSetter) {
        this.imageViewWeakReference = new WeakReference<ImageView>(imageView);
        this.errorResourceId = errorResourceId;
        this.imageSetter = imageSetter;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onResult(Success ok) {
        ImageView imageView = imageViewWeakReference.get();
        if (imageView == null) {
            return;
        }
        if (ok instanceof MemoryRetriever.MemoryRetrieverSuccess) {
            imageView.setImageBitmap(ok.getBitmap());
        } else {
            imageSetter.setBitmap(imageView, ok.getBitmap());
        }
    }

    @Override
    public void onResult(Failure ko) {
        ImageView imageView = imageViewWeakReference.get();
        if (imageView == null) {
            return;
        }
        showError(imageView);
    }

    protected void showError(ImageView imageView) {
        imageView.setImageResource(errorResourceId);
    }


}
