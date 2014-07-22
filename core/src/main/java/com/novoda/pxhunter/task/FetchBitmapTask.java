package com.novoda.pxhunter.task;

import android.view.View;

public class FetchBitmapTask extends PxHunterTask<Result> {

    private final ResultRetriever resultRetriever;

    public FetchBitmapTask(Metadata<Result> metadata, ResultRetriever resultRetriever) {
        super(metadata);
        this.resultRetriever = resultRetriever;
    }

    @Override
    public boolean isFetching(String url) {
        return getMetadata().getSourceUrl().equals(url);
    }

    @Override
    public boolean isTargeting(View view) {
        return false;
    }

    @Override
    protected Result doInBackground(Void... params) {
        return resultRetriever.retrieve(getMetadata());
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        if (result == null) {
            return;
        }
    }

}
