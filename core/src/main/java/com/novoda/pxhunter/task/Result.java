package com.novoda.pxhunter.task;

import com.novoda.pxhunter.impl.BitmapLoader;

public abstract class Result {
    public abstract void poke(BitmapLoader.Callback callback);
}
