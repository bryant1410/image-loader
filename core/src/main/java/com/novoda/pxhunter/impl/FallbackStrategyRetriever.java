package com.novoda.pxhunter.impl;

import android.util.Log;

import com.novoda.pxhunter.task.Result;
import com.novoda.pxhunter.task.ResultRetriever;
import com.novoda.pxhunter.task.Success;
import com.novoda.pxhunter.task.Metadata;

public class FallbackStrategyRetriever<T extends Metadata<V>, V> implements ResultRetriever<T, V> {

    private static final String TAG = FallbackStrategyRetriever.class.getSimpleName();

    private final ResultRetriever<T, V>[] retrievers;

    public FallbackStrategyRetriever(ResultRetriever<T, V>... retrievers) {
        this.retrievers = retrievers;
    }

    @Override
    public Result retrieve(T metadata) {
        for (ResultRetriever<T, V> retriever : retrievers) {
            Result result = retriever.retrieve(metadata);
            if (result instanceof Success) {
                return result;
            }
        }
        Log.e(TAG, "Retrieve failed for " + metadata.getSourceUrl());
        return new Failure();
    }

    public static class Failure extends com.novoda.pxhunter.task.Failure {

    }

}
