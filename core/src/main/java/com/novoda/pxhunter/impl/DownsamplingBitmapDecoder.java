package com.novoda.pxhunter.impl;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;

import com.novoda.pxhunter.port.BitmapDecoder;
import com.novoda.pxhunter.task.TagWrapper;

import java.io.File;

public class DownsamplingBitmapDecoder implements BitmapDecoder {

    private final Resources resources;
    private final double allowedStretchingThreshold;
    private final int maxDownsampling;

    public DownsamplingBitmapDecoder(Resources resources, double allowedStretchingThreshold, int maxDownsampling) {
        this.resources = resources;
        this.allowedStretchingThreshold = allowedStretchingThreshold;
        this.maxDownsampling = maxDownsampling;
    }

    @Override
    public Bitmap decode(TagWrapper tagWrapper, File file) {
        if (tagWrapper.isNoLongerValid()) {
            return null;
        }

        String filePath = file.getAbsolutePath();
        BitmapFactory.Options onlyBounds = new BitmapFactory.Options();
        onlyBounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, onlyBounds);

        int bitmapWidth = onlyBounds.outWidth;
        int bitmapHeight = onlyBounds.outHeight;
        int viewWidth = tagWrapper.getTargetWidth();
        int viewHeight = tagWrapper.getTargetHeight();

        int bitmapSize;
        int viewSize;
        if (bitmapWidth * viewHeight > viewWidth * bitmapHeight) {
            bitmapSize = bitmapHeight;
            viewSize = viewHeight;
        } else {
            bitmapSize = bitmapWidth;
            viewSize = viewWidth;
        }
        if (viewSize == 0) {
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            viewSize = (bitmapWidth > bitmapHeight ? displayMetrics.widthPixels : displayMetrics.heightPixels);
        }

        BitmapFactory.Options withDownsampling = getDecodingOptions(bitmapSize, viewSize);
        return BitmapFactory.decodeFile(filePath, withDownsampling);
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
