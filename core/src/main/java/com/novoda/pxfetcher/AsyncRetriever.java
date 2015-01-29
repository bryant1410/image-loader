package com.novoda.pxfetcher;

import com.novoda.pxfetcher.task.Failure;
import com.novoda.pxfetcher.task.Retriever;
import com.novoda.pxfetcher.task.Success;
import com.novoda.pxfetcher.task.TagWrapper;

public interface AsyncRetriever {
    void load(TagWrapper tagWrapper, Callback callback);

    interface Callback extends Retriever.Callback {
        void onStart();

        @Override
        void onResult(Success ok);

        @Override
        void onResult(Failure ok);
    }
}
