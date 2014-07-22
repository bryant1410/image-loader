package com.novoda.pxhunter.impl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.novoda.pxhunter.port.BitmapDecoder;
import com.novoda.pxhunter.task.Metadata;

public class DefaultBitmapDecoder implements BitmapDecoder {

    @Override
    public Bitmap decode(Metadata metadata, byte[] data) {
        if (metadata.isNoLongerValid()) {
            return null;
        }
        return BitmapFactory.decodeByteArray(data, 0, data.length, null);
    }

}
