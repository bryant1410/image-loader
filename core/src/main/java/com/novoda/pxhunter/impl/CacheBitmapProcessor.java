package com.novoda.pxhunter.impl;

import android.graphics.Bitmap;
import android.util.Log;

import com.novoda.pxhunter.port.BitmapProcessor;
import com.novoda.pxhunter.port.Cacher;
import com.novoda.pxhunter.task.TagWrapper;

public class CacheBitmapProcessor implements BitmapProcessor {

    private static final String TAG = CacheBitmapProcessor.class.getSimpleName();

    private final Cacher cacher;

    public CacheBitmapProcessor(Cacher cacher) {
        this.cacher = cacher;
    }

    @Override
    public Bitmap elaborate(TagWrapper tagWrapper, Bitmap bitmap) {
        if (tagWrapper.isNoLongerValid()) {
            // FIXME: returning null on a public method is not cool, especially when no javaDoc.
            return null;
        }
        if (bitmap == null) {
            return bitmap;
        }
        try {
            cacher.put(tagWrapper.getSourceUrl(), bitmap);
        } catch (Cacher.UnableToCacheItemException e) {
            Log.e(TAG, "Unable to cache bitmap", e);
        }
        return bitmap;
    }

}
