package com.novoda.pxhunter.port;

import android.graphics.Bitmap;

import com.novoda.pxhunter.task.TagWrapper;

public interface BitmapProcessor {

    Bitmap elaborate(TagWrapper tagWrapper, Bitmap bitmap);

}
