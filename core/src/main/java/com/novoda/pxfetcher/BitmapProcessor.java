package com.novoda.pxfetcher;

import android.graphics.Bitmap;

import com.novoda.pxfetcher.task.TagWrapper;

public interface BitmapProcessor {

    /**
     * Allows for manipulation of the bitmap before it is continued to be processed
     *
     * @param tagWrapper
     * @param bitmap
     * @return the bitmap with any modifications
     */
    Bitmap elaborate(TagWrapper tagWrapper, Bitmap bitmap);

}
