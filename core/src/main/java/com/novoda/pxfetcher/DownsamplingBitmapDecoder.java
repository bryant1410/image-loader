package com.novoda.pxfetcher;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.novoda.pxfetcher.task.TagWrapper;

import java.io.File;

public class DownsamplingBitmapDecoder implements BitmapDecoder {

    private final double allowedStretchingThreshold;
    private final int maxDownsampling;

    public DownsamplingBitmapDecoder(double allowedStretchingThreshold, int maxDownsampling) {
        this.allowedStretchingThreshold = allowedStretchingThreshold;
        this.maxDownsampling = maxDownsampling;
    }

    @Override
    public Bitmap decode(TagWrapper tagWrapper, File file) {
        if (tagWrapper.isNoMoreValid()) {
            return null;
        }

        String filePath = file.getAbsolutePath();
        View view = tagWrapper.getView();

        BitmapFactory.Options onlyBounds = new BitmapFactory.Options();
        onlyBounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, onlyBounds);

        int bitmapWidth = onlyBounds.outWidth;
        int bitmapHeight = onlyBounds.outHeight;
        int viewWidth = view.getMeasuredWidth();
        int viewHeight = view.getMeasuredHeight();

        int bitmapSize;
        int viewSize;
        if (bitmapWidth * viewHeight > viewWidth * bitmapHeight) {
            bitmapSize = bitmapHeight;
            viewSize = viewHeight;
        } else {
            bitmapSize = bitmapWidth;
            viewSize = viewWidth;
        }

        BitmapFactory.Options withDownsampling = getDecodingOptions(bitmapSize, viewSize);
        return BitmapFactory.decodeFile(filePath, withDownsampling);
    }

    private BitmapFactory.Options getDecodingOptions(int bitmapSize, int viewSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = calculateDownsamplingOptions(bitmapSize, viewSize);
        return options;
    }

    private int calculateDownsamplingOptions(int bitmapSize, int viewSize) {
        int maxStretchedHeight = (int) (viewSize * (1.0 - allowedStretchingThreshold));
        int downSampleSize = 1;
        int sampledSize = bitmapSize;
        while (sampledSize > maxStretchedHeight && downSampleSize <= maxDownsampling) {
            downSampleSize++;
            sampledSize = sampledSize / 2;
        }
        return downSampleSize;
    }

}
