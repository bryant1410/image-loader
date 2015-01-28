package com.novoda.pxfetcher;

import com.novoda.pxfetcher.task.Retriever;
import com.novoda.pxfetcher.task.TagWrapper;

public interface AsyncRetriever {
    void load(TagWrapper tagWrapper, Callback callback);

    interface Callback extends Retriever.Callback {
        void onStart();
    }
}
