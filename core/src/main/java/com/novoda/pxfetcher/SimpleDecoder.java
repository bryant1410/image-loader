package com.novoda.pxfetcher;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.novoda.pxfetcher.task.TagWrapper;

import java.io.File;

public class SimpleDecoder implements BitmapDecoder {

    @Override
    public Bitmap decode(TagWrapper tagWrapper, File file) {
        String filePath = file.getAbsolutePath();
        return BitmapFactory.decodeFile(filePath);
    }

}
