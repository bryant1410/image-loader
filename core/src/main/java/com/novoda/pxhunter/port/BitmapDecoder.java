package com.novoda.pxhunter.port;

import android.graphics.Bitmap;

import com.novoda.pxhunter.task.Metadata;

public interface BitmapDecoder {

    Bitmap decode(Metadata metadata, byte[] data);

}
