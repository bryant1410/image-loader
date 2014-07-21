package com.novoda.pxhunter.task;

import android.os.AsyncTask;

public class RetrieverAsyncTask extends AsyncTask<Void, Void, Result> {

    private final Metadata metadata;
    private final ResultRetriever retriever;

    private Listener listener;

    public RetrieverAsyncTask(Metadata metadata, ResultRetriever retriever) {
        this.metadata = metadata;
        this.retriever = retriever;
    }

    @Override
    protected Result doInBackground(Void... params) {
        return retriever.retrieve(metadata);
    }

    @Override
    protected void onPostExecute(Result result) {
        if (result == null) {
            return;
        }
        if (metadata.isNoLongerValid()) {
            return;
        }

        if (listener != null) {
            listener.onResult(result);
        }
    }

    public RetrieverAsyncTask setListener(Listener listener) {
        this.listener = listener;
        return this;
    }

    public interface Listener {

        void onResult(Result result);

    }

}
