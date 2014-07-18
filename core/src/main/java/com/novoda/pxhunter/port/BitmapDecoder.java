package com.novoda.pxhunter.port;

import android.graphics.Bitmap;

import com.novoda.pxhunter.task.TagWrapper;

import java.io.File;

public interface BitmapDecoder {

    Bitmap decode(TagWrapper tagWrapper, File file);

}
