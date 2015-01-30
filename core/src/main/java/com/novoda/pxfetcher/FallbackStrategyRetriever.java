package com.novoda.pxfetcher;

import com.novoda.pxfetcher.task.Result;
import com.novoda.pxfetcher.task.Retriever;
import com.novoda.pxfetcher.task.Success;
import com.novoda.pxfetcher.task.TagWrapper;

public class FallbackStrategyRetriever implements Retriever {

    private final Retriever[] retrievers;

    public FallbackStrategyRetriever(Retriever... retrievers) {
        this.retrievers = retrievers;
    }

    @Override
    public Result retrieve(TagWrapper tagWrapper) {
        for (Retriever retriever : retrievers) {
            Result result = retriever.retrieve(tagWrapper);
            if (result instanceof Success) {
                return result;
            }
        }
        return new FallbackStrategyRetrieverFailure();
    }

    public static class FallbackStrategyRetrieverFailure extends com.novoda.pxfetcher.task.Failure {

    }

}
