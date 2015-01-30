package com.novoda.pxfetcher.task;

import android.view.View;

import com.novoda.pxfetcher.Tag;

import java.lang.ref.WeakReference;

public class TagWrapper {

    private final Tag tag;
    private final WeakReference<View> viewReference;

    public TagWrapper(View view, Tag tag) {
        this.tag = tag;
        this.viewReference = new WeakReference<View>(view);
    }

    public String getSourceUrl() {
        return tag.getSourceUrl();
    }

    public View getView() {
        return viewReference == null ? null : viewReference.get();
    }

    public boolean isNoMoreValid() {
        View view = getView();
        if (view == null) {
            return true;
        }
        return !tag.equals(view.getTag());
    }

}
