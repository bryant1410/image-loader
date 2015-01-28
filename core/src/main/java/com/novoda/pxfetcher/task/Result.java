package com.novoda.pxfetcher.task;

import com.novoda.pxfetcher.AsyncRetriever;

public abstract class Result {
    public abstract void poke(AsyncRetriever.Callback callback);
}
