package com.novoda.pxfetcher;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class PxFetcherTask<TaskParams, TaskProgress, TaskResult> extends AsyncTask<TaskParams, TaskProgress, TaskResult>
        implements Comparable<PxFetcherTask<TaskParams, TaskProgress, TaskResult>> {

    private static final int CREATION_TIMES_LHS_GREATER_THAN_RHS = 1;
    private static final int CREATION_TIMES_LHS_EQUAL_TO_RHS = 0;
    private static final int CREATION_TIMES_LHS_LESS_THAN_RHS = -1;
    private static final Comparator<Id> GROUP_ID_COMPARATOR = new GroupIdComparator();

    private final Id id;
    private final Id groupId;
    private final long creationTimeNanos;
    private final List<WeakReference<Callback<TaskResult>>> listeners;

    PxFetcherTask(Id id, Id groupId) {
        this.id = id;
        this.groupId = groupId;
        this.creationTimeNanos = System.nanoTime();

        this.listeners = new ArrayList<WeakReference<Callback<TaskResult>>>();
    }

    public void add(Callback<TaskResult> listener) {
        listeners.add(new WeakReference<Callback<TaskResult>>(listener));
    }

    public void remove(Callback<TaskResult> listener) {
        WeakReference reference = new WeakReference<Callback<TaskResult>>(listener);
        if (listeners.contains(reference)) {
            listeners.remove(reference);
        }
    }

    public Id getId() {
        return id;
    }

    public Id getGroupId() {
        return groupId;
    }

    public long getCreationTimeNanos() {
        return creationTimeNanos;
    }

    @Override
    protected void onPostExecute(TaskResult taskResult) {
        if (isCancelled()) {
            return;
        }
        notifyListenersOnCompleted(taskResult);
        listeners.clear();
    }

    private void notifyListenersOnCompleted(TaskResult taskResult) {
        for (WeakReference<Callback<TaskResult>> listener : listeners) {
            Callback<TaskResult> taskResultCallback = listener.get();
            if (taskResultCallback != null) {
                taskResultCallback.onComplete(this, taskResult);
            }
        }
    }

    @Override
    protected void onCancelled() {
        notifyListenersOnCancelled();
    }

    private void notifyListenersOnCancelled() {
        for (WeakReference<Callback<TaskResult>> listener : listeners) {
            Callback<TaskResult> taskResultCallback = listener.get();
            if (taskResultCallback != null) {
                taskResultCallback.onCancelled(this);
            }
        }
    }

    @Override
    protected void onCancelled(TaskResult taskResult) {
        notifyListenersOnCancelled(taskResult);
    }

    private void notifyListenersOnCancelled(TaskResult taskResult) {
        for (WeakReference<Callback<TaskResult>> listener : listeners) {
            Callback<TaskResult> taskResultCallback = listener.get();
            if (taskResultCallback != null) {
                taskResultCallback.onCancelled(this, taskResult);
            }
        }
    }

    /**
     * Defines the "natural ordering" of a pair of PxFetcherTasks.
     *
     * Tasks are ordered on their group ID, _then_ creation date (earlier creation time == ordered before later creation time iff group ).
     * such that a Task with an earlier creation time will be ordered
     * before a task with a later creation time (if their Priority is equal).
     */
    @Override
    public int compareTo(PxFetcherTask another) {
        int priorityCompareResult = GROUP_ID_COMPARATOR.compare(groupId, another.getGroupId());
        return priorityCompareResult != 0 ? priorityCompareResult : compareCreationTime(creationTimeNanos, another.getCreationTimeNanos());
    }

    private int compareCreationTime(long lhsCreationTimeMillis, long rhsCreationTimeMillis) {
        if (lhsCreationTimeMillis == rhsCreationTimeMillis) {
            return CREATION_TIMES_LHS_EQUAL_TO_RHS;
        }

        if (lhsCreationTimeMillis > rhsCreationTimeMillis) {
            return CREATION_TIMES_LHS_GREATER_THAN_RHS;
        }

        return CREATION_TIMES_LHS_LESS_THAN_RHS;
    }

    public interface Callback<R> {

        void onComplete(PxFetcherTask completedTask, R r);

        void onCancelled(PxFetcherTask cancelledTask);

        void onCancelled(PxFetcherTask cancelledTask, R r);

    }

}
