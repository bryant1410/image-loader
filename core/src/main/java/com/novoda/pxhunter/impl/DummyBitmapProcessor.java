package com.novoda.pxhunter.impl;

import android.graphics.Bitmap;

import com.novoda.pxhunter.port.BitmapProcessor;
import com.novoda.pxhunter.task.Metadata;

/**
 * This bitmap processor simply forwards the received bitmap as output of its elaboration.
 * This class can be used when no real operation is necessary as last stage of a retrieve operation.
 * 
 */
public class DummyBitmapProcessor implements BitmapProcessor {

    @Override
    public Bitmap elaborate(Metadata metadata, Bitmap bitmap) {
        return bitmap;
    }

}
