package com.novoda.pxfetcher;

import com.novoda.pxfetcher.task.Result;
import com.novoda.pxfetcher.task.Retriever;
import com.novoda.pxfetcher.task.Success;
import com.novoda.pxfetcher.task.TagWrapper;

public class SequentialRetrieverQueuer implements RetrieverQueuer {

    private static final SequentailRetrieverQueuerFailure FAILURE = new SequentailRetrieverQueuerFailure();

    @Override
    public Result retrieve(TagWrapper tagWrapper, Retriever... retrievers) {
        for (Retriever currentRetriever : retrievers) {
            Result result = currentRetriever.retrieve(tagWrapper);
            if (result instanceof Success) {
                return result;
            }
        }
        return FAILURE;
    }

    private static class SequentailRetrieverQueuerFailure extends com.novoda.pxfetcher.task.Failure {
    }

}
