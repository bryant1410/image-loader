package com.novoda.pxhunter.impl;

import com.novoda.pxhunter.task.Result;
import com.novoda.pxhunter.task.Retriever;
import com.novoda.pxhunter.task.Success;
import com.novoda.pxhunter.task.TagWrapper;

public class FallbackStrategyRetriever<T extends TagWrapper<V>, V> implements Retriever<T, V> {

    private final Retriever<T, V>[] retrievers;

    public FallbackStrategyRetriever(Retriever<T, V>... retrievers) {
        this.retrievers = retrievers;
    }

    @Override
    public Result retrieve(T tagWrapper) {
        for (Retriever<T, V> retriever : retrievers) {
            Result result = retriever.retrieve(tagWrapper);
            if (result instanceof Success) {
                return result;
            }
        }
        // just try again
        for (Retriever<T, V> retriever : retrievers) {
            Result result = retriever.retrieve(tagWrapper);
            if (result instanceof Success) {
                return result;
            }
        }
        return new Failure();
    }

    public static class Failure extends com.novoda.pxhunter.task.Failure {

    }

}
