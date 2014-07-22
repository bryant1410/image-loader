package com.novoda.pxhunter.task;

import android.view.View;
import android.widget.ImageView;

import com.novoda.pxhunter.impl.BitmapLoader;

import java.lang.ref.WeakReference;

public class LoadBitmapIntoViewTask extends PxHunterTask<Result> {

    private final ResultRetriever resultRetriever;
    private final WeakReference<ImageView> target;
    private final BitmapLoader.Callback loadBitmapCallback;

    public LoadBitmapIntoViewTask(Metadata<Result> metadata, ResultRetriever resultRetriever, ImageView target, BitmapLoader.Callback loadBitmapCallback) {
        super(metadata);
        this.resultRetriever = resultRetriever;
        this.target = new WeakReference<ImageView>(target);
        this.loadBitmapCallback = loadBitmapCallback;
    }

    @Override
    public boolean isFetching(String url) {
        return getMetadata().getSourceUrl().equals(url);
    }

    @Override
    public boolean isTargeting(View view) {
        return target.get().equals(view);
    }

    @Override
    protected Result doInBackground(Void... params) {
        return resultRetriever.retrieve(getMetadata());
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        result.poke(loadBitmapCallback);
    }

}
