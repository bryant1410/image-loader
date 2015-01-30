package com.novoda.pxfetcher;

import com.novoda.pxfetcher.task.Result;
import com.novoda.pxfetcher.task.Retriever;
import com.novoda.pxfetcher.task.TagWrapper;

public interface RetrieverQueuer {

    Result retrieve(TagWrapper tagWrapper, Retriever... retrievers);

}
