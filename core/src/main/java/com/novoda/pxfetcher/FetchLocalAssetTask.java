package com.novoda.pxfetcher;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Dummy task used during development of the task scheduling architecture.
 *
 * Returns a bitmap from the resources directory.
 */
public class FetchLocalAssetTask extends PxFetcherTask<Void, Void, Bitmap> {

    private final Resources resources;

    FetchLocalAssetTask(Metadata metadata, Resources resources) {
        super(metadata.getId(), metadata.getGroupId());
        this.resources = resources;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        return BitmapFactory.decodeResource(resources, R.drawable.ic_launcher);
    }

}
