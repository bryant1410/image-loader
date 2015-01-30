package com.novoda.pxfetcher;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class FadingImageSetter implements ImageSetter {

    private final Resources resources;
    private final int animationDurationMs;

    public FadingImageSetter(Resources resources, int animationDurationMs) {
        this.resources = resources;
        this.animationDurationMs = animationDurationMs;
    }

    @Override
    public void setBitmap(ImageView imageView, Bitmap bitmap) {
        if (canTransition(imageView)) {
            runTransition(imageView, bitmap);
        } else {
            imageView.setImageBitmap(bitmap);
        }
    }

    private boolean canTransition(ImageView imageView) {
        return imageView.getDrawable() != null;
    }

    private void runTransition(ImageView imageView, Bitmap bitmap) {
        Drawable oldDrawable = imageView.getDrawable();
        Drawable newDrawable = new BitmapDrawable(resources, bitmap);

        FadingImageTransition transition = new FadingImageTransition(oldDrawable, newDrawable, animationDurationMs);
        transition.startTransition(imageView);
    }
}
