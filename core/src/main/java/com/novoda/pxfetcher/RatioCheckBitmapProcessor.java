package com.novoda.pxfetcher;

import android.graphics.Bitmap;
import android.view.View;

import com.novoda.pxfetcher.task.TagWrapper;

public class RatioCheckBitmapProcessor implements BitmapProcessor {

    private final double allowedRatioChangeTreshold;

    public RatioCheckBitmapProcessor(double allowedRatioChangeTreshold) {
        this.allowedRatioChangeTreshold = allowedRatioChangeTreshold;
    }

    @Override
    public Bitmap elaborate(TagWrapper tagWrapper, Bitmap bitmap) {
        View view = tagWrapper.getView();
        if (view == null || bitmap == null) {
            return null;
        }

        if (isRatioIntegrityIntact(view, bitmap)) {
            return bitmap;
        }
        return null;
    }

    private boolean isRatioIntegrityIntact(View view, Bitmap bitmap) {
        double viewRatio = (double) view.getMeasuredWidth() / view.getMeasuredHeight();
        double bmpRatio = (double) bitmap.getWidth() / bitmap.getHeight();
        double delta = Math.abs(viewRatio - bmpRatio);
        return delta < allowedRatioChangeTreshold;
    }

}
