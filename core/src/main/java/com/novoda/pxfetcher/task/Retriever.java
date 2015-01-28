package com.novoda.pxfetcher.task;

public interface Retriever<T extends TagWrapper<V>, V> {
    Result retrieve(T tagWrapper);

    public interface Callback {
        void onResult(Success ok);

        void onResult(Failure ko);
    }
}
