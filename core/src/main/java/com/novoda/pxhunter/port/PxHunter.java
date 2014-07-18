package com.novoda.pxhunter.port;

import android.widget.ImageView;

public interface PxHunter<T> {

    /**
     * Load image with given id into the specified ImageView
     *
     * @param url      remote URL for the image
     * @param view     the ImageView in which to display the image
     * @param metadata // TODO @dorvaryn ben, can you add desc
     */
    void load(String url, ImageView view, T metadata);

}
