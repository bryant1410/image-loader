package com.novoda.pxfetcher.task;

import com.novoda.pxfetcher.AsyncRetriever;

public abstract class Failure extends Result {
    @Override
    public void poke(AsyncRetriever.Callback callback) {
        callback.onResult(this);
    }
}
