package com.novoda.pxfetcher;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;

public class CrossFadableDrawable extends TransitionDrawable {

    private static final int PREV_ID = 100;
    private static final int NEXT_ID = 101;

    public static CrossFadableDrawable create(Drawable prevDrawable, Drawable nextDrawable) {
        Drawable[] drawables = {
                prevDrawable,
                nextDrawable
        };
        CrossFadableDrawable drawable = new CrossFadableDrawable(drawables);
        drawable.setId(0, PREV_ID);
        drawable.setId(1, NEXT_ID);
        return drawable;
    }

    CrossFadableDrawable(Drawable[] layers) {
        super(layers);
    }

    public Drawable getPrev() {
        return findDrawableByLayerId(PREV_ID);
    }

    public Drawable getNext() {
        return findDrawableByLayerId(NEXT_ID);
    }

    public void setPrev(Drawable drawable) {
        setDrawableByLayerId(PREV_ID, drawable);
    }

    public void setNext(Drawable drawable) {
        setDrawableByLayerId(NEXT_ID, drawable);
    }

}
