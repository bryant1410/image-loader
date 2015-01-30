package com.novoda.pxfetcher;

import android.graphics.Bitmap;

import com.novoda.pxfetcher.task.TagWrapper;

public class NonReactiveBitmapProcessor implements BitmapProcessor {

    @Override
    public Bitmap elaborate(TagWrapper tagWrapper, Bitmap bitmap) {
        return bitmap;
    }
}
