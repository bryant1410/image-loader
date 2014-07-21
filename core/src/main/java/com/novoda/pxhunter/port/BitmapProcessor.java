package com.novoda.pxhunter.port;

import android.graphics.Bitmap;

import com.novoda.pxhunter.task.Metadata;

public interface BitmapProcessor {

    Bitmap elaborate(Metadata metadata, Bitmap bitmap);

}
