package com.novoda.pxhunter.impl;

import android.os.AsyncTask;

import com.novoda.pxhunter.task.*;

public class BitmapLoader {

    private final ResultRetriever retriever;

    public BitmapLoader(ResultRetriever retriever) {
        this.retriever = retriever;
    }

    public void load(Metadata metadata, Callback callback) {
        callback.onStart();
        new RetrieverAsyncTask(metadata, retriever)
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

    public interface Callback {

        void onStart();

        void onResult(Success ok);

        void onResult(Failure ko);

    }

}
