package com.novoda.pxfetcher;

import android.os.AsyncTask;

import com.novoda.pxfetcher.task.Result;
import com.novoda.pxfetcher.task.Retriever;
import com.novoda.pxfetcher.task.RetrieverAsyncTask;
import com.novoda.pxfetcher.task.TagWrapper;

public class DefaultAsyncRetriever implements AsyncRetriever {

    private final Retriever retriever;

    public DefaultAsyncRetriever(Retriever retriever) {
        this.retriever = retriever;
    }

    @Override
    public void load(TagWrapper tagWrapper, Callback callback) {
        callback.onStart();
        new RetrieverAsyncTask(tagWrapper, retriever)
                .setListener(createListener(callback))
                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private RetrieverAsyncTask.Listener createListener(final Callback callback) {
        return new RetrieverAsyncTask.Listener() {
            @Override
            public void onResult(Result result) {
                result.poke(callback);
            }
        };
    }

}
