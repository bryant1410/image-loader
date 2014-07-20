package com.novoda.pxhunter.port;

import android.graphics.Bitmap;

import java.io.InputStream;

public interface BitmapDecoder {

    Bitmap decode(int width, int height, InputStream inputStream);

}
