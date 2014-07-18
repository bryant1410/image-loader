package com.novoda.pxhunter.impl;

import android.graphics.Bitmap;

import com.novoda.pxhunter.port.BitmapProcessor;
import com.novoda.pxhunter.port.Cacher;
import com.novoda.pxhunter.task.TagWrapper;

public class CacheBitmapProcessor implements BitmapProcessor {

    private final Cacher cacher;

    public CacheBitmapProcessor(Cacher cacher) {
        this.cacher = cacher;
    }

    @Override
    public Bitmap elaborate(TagWrapper tagWrapper, Bitmap bitmap) {
        if (tagWrapper.isNoLongerValid()) {
            return null;
        }

        if (bitmap == null) {
            return bitmap;
        }

        cacher.put(tagWrapper.getSourceUrl(), bitmap);
        return bitmap;
    }

}
