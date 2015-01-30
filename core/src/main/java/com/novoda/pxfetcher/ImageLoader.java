package com.novoda.pxfetcher;

import android.widget.ImageView;

public interface ImageLoader<T> {

    void load(String url, T metadata, ImageView view);

}
