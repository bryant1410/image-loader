package com.novoda.pxfetcher;

import com.novoda.pxfetcher.task.Result;
import com.novoda.pxfetcher.task.Retriever;
import com.novoda.pxfetcher.task.Success;
import com.novoda.pxfetcher.task.TagWrapper;

public class FallbackStrategyRetriever<TagWrapperType extends TagWrapper<MetadataType>, MetadataType> implements Retriever<TagWrapperType, MetadataType> {

    private final Retriever<TagWrapperType, MetadataType>[] retrievers;

    public FallbackStrategyRetriever(Retriever<TagWrapperType, MetadataType>... retrievers) {
        this.retrievers = retrievers;
    }

    @Override
    public Result retrieve(TagWrapperType tagWrapper) {
        for (Retriever<TagWrapperType, MetadataType> retriever : retrievers) {
            Result result = retriever.retrieve(tagWrapper);
            if (result instanceof Success) {
                return result;
            }
        }
        return new Failure();
    }

    public static class Failure extends com.novoda.pxfetcher.task.Failure {

    }

}
