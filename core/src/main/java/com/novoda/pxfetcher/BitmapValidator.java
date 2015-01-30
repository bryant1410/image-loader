package com.novoda.pxfetcher;

import android.graphics.Bitmap;
import android.view.View;

public interface BitmapValidator {
    boolean validate(View view, Bitmap bitmap);
}
