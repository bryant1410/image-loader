package com.novoda.pxfetcher;

import com.novoda.pxfetcher.task.TagWrapper;

public class DefaultTagWrapper<MetadataType> extends TagWrapper<MetadataType> {

    private final MetadataType metadata;

    public DefaultTagWrapper(String url) {
        this(url, null);
    }

    public DefaultTagWrapper(String url, MetadataType metadata) {
        super(url);
        this.metadata = metadata;
    }

    @Override
    public MetadataType getMetadata() {
        return metadata;
    }

}
