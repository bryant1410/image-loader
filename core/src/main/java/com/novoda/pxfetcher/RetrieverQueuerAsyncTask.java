package com.novoda.pxfetcher;

import android.os.AsyncTask;

import com.novoda.imageloader20.task.Result;
import com.novoda.imageloader20.task.Retriever;
import com.novoda.imageloader20.task.TagWrapper;

import static com.novoda.imageloader20.task.LoggingExceptionHandlerAttacher.swallowLiveExceptions;

public class RetrieverQueuerAsyncTask extends AsyncTask<Retriever, Void, Result> {

    private final TagWrapper tagWrapper;
    private final RetrieverQueuer retriever;

    private Listener listener;

    public RetrieverQueuerAsyncTask(TagWrapper tagWrapper, RetrieverQueuer retriever) {
        this.tagWrapper = tagWrapper;
        this.retriever = retriever;
    }

    @Override
    protected Result doInBackground(Retriever... retrievers) {
        swallowLiveExceptions();
        return retriever.retrieve(tagWrapper, retrievers);
    }

    @Override
    protected void onPostExecute(Result result) {
        if (result == null) {
            return;
        }
        if (tagWrapper.isNoLongerValid()) {
            return;
        }

        if (listener != null) {
            listener.onResult(result);
        }
    }

    public RetrieverQueuerAsyncTask setListener(Listener listener) {
        this.listener = listener;
        return this;
    }

    public interface Listener {
        void onResult(Result result);
    }
}
