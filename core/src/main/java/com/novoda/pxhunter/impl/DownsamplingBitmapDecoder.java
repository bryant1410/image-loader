package com.novoda.pxhunter.impl;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;

import com.novoda.pxhunter.port.BitmapDecoder;
import com.novoda.pxhunter.task.Metadata;

public class DownsamplingBitmapDecoder implements BitmapDecoder {

    private final Resources resources;
    private final double allowedStretchingThreshold;
    private final int maxDownsampling;

    public DownsamplingBitmapDecoder(Resources resources, double allowedStretchingThreshold, int maxDownsampling) {
        this.resources = resources;
        this.allowedStretchingThreshold = allowedStretchingThreshold;
        this.maxDownsampling = maxDownsampling;
    }

    // TODO: replace the width and height with Metadata
    @Override
    public Bitmap decode(Metadata metadata, byte[] data) {
        if (metadata.isNoLongerValid()) {
            return null;
        }

        BitmapFactory.Options onlyBounds = new BitmapFactory.Options();
        onlyBounds.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, onlyBounds);

        int width = metadata.getTargetWidth();
        int height = metadata.getTargetHeight();
        int bitmapWidth = onlyBounds.outWidth;
        int bitmapHeight = onlyBounds.outHeight;

        int bitmapSize;
        int viewSize;
        if (bitmapWidth * height > width * bitmapHeight) {
            bitmapSize = bitmapHeight;
            viewSize = height;
        } else {
            bitmapSize = bitmapWidth;
            viewSize = width;
        }
        if (viewSize == 0) {
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            viewSize = (bitmapWidth > bitmapHeight ? displayMetrics.widthPixels : displayMetrics.heightPixels);
        }

        BitmapFactory.Options withDownsampling = getDecodingOptions(bitmapSize, viewSize);
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, withDownsampling);
        return bitmap;
    }

    private BitmapFactory.Options getDecodingOptions(int bitmapSize, int measuredSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = calculateDownsamplingOptions(bitmapSize, measuredSize);
        options.inDither = true;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return options;
    }

    private int calculateDownsamplingOptions(int bitmapSize, int viewSize) {
        int maxStretchedSize = (int) (viewSize * (1.0 - allowedStretchingThreshold));
        int downSamplingFactor = 1;
        int sampledSize = bitmapSize;
        while (sampledSize / 2 > maxStretchedSize && downSamplingFactor < maxDownsampling) {
            downSamplingFactor *= 2;
            sampledSize = sampledSize / 2;
        }
        return downSamplingFactor;
    }

}
