package com.novoda.pxfetcher;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public interface CustomDrawableCreator {

    Drawable createCustomDrawableFrom(Bitmap bitmap);

}
