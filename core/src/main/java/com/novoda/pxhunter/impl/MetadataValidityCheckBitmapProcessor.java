package com.novoda.pxhunter.impl;

import android.graphics.Bitmap;

import com.novoda.pxhunter.port.BitmapProcessor;
import com.novoda.pxhunter.task.Metadata;

public class MetadataValidityCheckBitmapProcessor implements BitmapProcessor {

    /**
     * Just checks if the Metadata is still valid
     * ie: it still contains the reference to the ImageView to populate
     *
     * @param metadata
     * @param bitmap
     * @return null if no image as input or the Metadata is not holding an ImageView reference no more
     */
    @Override
    public Bitmap elaborate(Metadata metadata, Bitmap bitmap) {
        if (metadata.isNoLongerValid() || bitmap == null) {
            return null;
        }
        return bitmap;
    }

}
