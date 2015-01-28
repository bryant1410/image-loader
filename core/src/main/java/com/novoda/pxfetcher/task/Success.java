package com.novoda.pxfetcher.task;

import android.graphics.Bitmap;

import com.novoda.pxfetcher.AsyncRetriever;

public abstract class Success extends Result {

    private final Bitmap bitmap;

    public Success(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    @Override
    public void poke(AsyncRetriever.Callback callback) {
        callback.onResult(this);
    }

}
