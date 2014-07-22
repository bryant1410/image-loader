package com.novoda.pxhunter.task;

import com.novoda.pxhunter.impl.Tag;

public abstract class Metadata<T> {

    public static final int DIMENSION_UNSPECIFIED = 0;

    private final Tag tag;

    public Metadata(String url) {
        this.tag = new Tag(url);
    }

    public Tag getTag() {
        return tag;
    }

    public String getSourceUrl() {
        return tag.getSourceUrl();
    }

    /**
     * Override this method to return true when some condition is met.
     *
     * @return whether this tag still represents a URL to load
     */
    public boolean isNoLongerValid() {
        return false;
    }

    public int getTargetWidth() {
        return DIMENSION_UNSPECIFIED;
    }

    public int getTargetHeight() {
        return DIMENSION_UNSPECIFIED;
    }

    public abstract T getCustomData();

}
