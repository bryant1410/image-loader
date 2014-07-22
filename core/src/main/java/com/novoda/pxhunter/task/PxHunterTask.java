package com.novoda.pxhunter.task;

import android.os.AsyncTask;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public abstract class PxHunterTask<T> extends AsyncTask<Void, Void, Result> {

    private final Metadata<T> metadata;

    private final List<OnCompletedListener> listeners;

    PxHunterTask(Metadata<T> metadata) {
        this.metadata = metadata;
        this.listeners = new ArrayList<OnCompletedListener>();
    }

    public void add(OnCompletedListener listener) {
        listeners.add(listener);
    }

    public void remove(OnCompletedListener listener) {
        if (listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    Metadata<T> getMetadata() {
        return metadata;
    }

    /**
     * Returns true if the task's purpose is to fetch data from the given url.
     */
    public abstract boolean isFetching(String url);

    /**
     * Returns true if the task's purpose is to load data into the given View.
     */
    public abstract boolean isTargeting(View view);

    @Override
    protected void onPostExecute(Result result) {
        for (OnCompletedListener listener : listeners) {
            listener.onCompleted(this, result);
        }
        listeners.clear();
    }

    public interface OnCompletedListener {

        void onCompleted(PxHunterTask completedTask, Result result);

    }

}
