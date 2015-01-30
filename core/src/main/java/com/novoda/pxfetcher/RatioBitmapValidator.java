package com.novoda.pxfetcher;

import android.graphics.Bitmap;
import android.view.View;

public class RatioBitmapValidator implements BitmapValidator {

    private final double allowedRatioChangeTreshold;

    public RatioBitmapValidator(double allowedRatioChangeTreshold) {
        this.allowedRatioChangeTreshold = allowedRatioChangeTreshold;
    }

    @Override
    public boolean validate(View view, Bitmap bitmap) {
        if (view == null || bitmap == null) {
            return false;
        }

        if (isRatioIntegrityIntact(view, bitmap)) {
            return true;
        }
        return false;
    }

    private boolean isRatioIntegrityIntact(View view, Bitmap bitmap) {
        double viewRatio = 1.0 * view.getMeasuredWidth() / view.getMeasuredHeight();
        double bmpRatio = 1.0 * bitmap.getWidth() / bitmap.getHeight();
        double delta = Math.abs(viewRatio - bmpRatio);
        return delta < allowedRatioChangeTreshold;
    }

}
