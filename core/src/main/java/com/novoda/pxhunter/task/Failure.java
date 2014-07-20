package com.novoda.pxhunter.task;

import com.novoda.pxhunter.impl.BitmapLoader;

public abstract class Failure extends Result {

    @Override
    public void poke(BitmapLoader.Callback callback) {
        callback.onResult(this);
    }

}
