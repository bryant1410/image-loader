package com.novoda.pxfetcher.task;

public interface Retriever<TagWrapperType extends TagWrapper<MetadataType>, MetadataType> {
    Result retrieve(TagWrapperType tagWrapper);
}
