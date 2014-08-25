package com.novoda.pxfetcher;

import android.widget.ImageView;

public interface PixelFetcher<M extends Metadata> {

    /**
     * Load image with given id into the specified ImageView
     *
     * @param url  remote URL for the image
     * @param view the ImageView in which to display the image
     */
    void load(String url, ImageView view);

    /**
     * Load image using the given Metadata into specified ImageView.
     *
     * @param metadata information about what and how to load the image
     * @param view     the ImageView in which to display the image
     */
    void load(M metadata, ImageView view);

}
