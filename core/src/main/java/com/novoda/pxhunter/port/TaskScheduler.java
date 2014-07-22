package com.novoda.pxhunter.port;

import android.view.View;

import com.novoda.pxhunter.task.PxHunterTask;

public interface TaskScheduler {

    /**
     * Schedule a task.
     * <p/>
     * The behaviour regarding when the task will be scheduled
     * (e.g. run now, run next, run last, etc.) is dependent on
     * the implementation.
     *
     * @param task
     */
    void schedule(PxHunterTask task);

    /**
     * Cancel specified task if running or scheduled to run.
     *
     * @param task
     */
    void cancel(PxHunterTask task);

    /**
     * Cancels any task fetching data from the given url.
     *
     * @param url
     */
    void cancelTasksFetchingFrom(String url);

    /**
     * Cancels any task intending to modify the given View.
     *
     * @param view
     */
    void cancelTasksTargeting(View view);

    /**
     * Cancel all running tasks and clear any pending tasks.
     */
    void clear();

}
