package com.novoda.pxhunter.task;

public interface ResultRetriever<T extends Metadata<V>,V> {

    Result retrieve(T metadata);

}
