package com.novoda.pxhunter.task;

public interface Retriever<T extends TagWrapper<V>,V> {
    Result retrieve(T tagWrapper);
}
