package com.novoda.pxfetcher;

public interface Metadata {

    /**
     * Unique identifier for this object.
     */
    Id getId();

    /**
     * Group identifier used to group this object into some logical collection.
     */
    Id getGroupId();

    /**
     * Get target of the fetch.
     * <p/>
     * This can be a uri for an image on the network, a local file URI, or contact from People etc.
     * The retriever you use should be able to interpret this URI.
     */
    String getUri();

}
