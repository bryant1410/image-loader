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
        if (tagWrapper.isNoLongerValid()) {
            return null;
        }

        String filePath = file.getAbsolutePath();
        View view = tagWrapper.getView();

        BitmapFactory.Options onlyBounds = new BitmapFactory.Options();
        onlyBounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, onlyBounds);

        int bitmapWidth = onlyBounds.outWidth;
        int measuredWidth = view.getMeasuredWidth();

        BitmapFactory.Options withDownsampling = getDecodingOptions(bitmapWidth, measuredWidth);
        return BitmapFactory.decodeFile(filePath, withDownsampling);
    }

    private BitmapFactory.Options getDecodingOptions(int bitmapDimension, int measuredDimension) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = calculateDownsamplingOptions(bitmapDimension, measuredDimension);
        return options;
    }

    private int calculateDownsamplingOptions(int bitmapDimension, int measuredDimension) {
        if (measuredDimension == 0) {
            return 1; // the view has not been measured correctly yet
        }
        int maxStretchedDimension = (int) (measuredDimension * (1.0 - allowedStretchingThreshold));
        int downSampleSize = 1;
        int sampledDimension = bitmapDimension / 2;
        while (sampledDimension > maxStretchedDimension && downSampleSize <= maxDownsampling) {
            downSampleSize++;
            sampledDimension = sampledDimension / 2;
        }
        return downSampleSize;
    }

}
