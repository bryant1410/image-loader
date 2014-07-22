package com.novoda.pxhunter.adapter;

import android.os.AsyncTask;
import android.view.View;

import com.novoda.pxhunter.port.TaskScheduler;
import com.novoda.pxhunter.task.PxHunterTask;
import com.novoda.pxhunter.task.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * A stack based (last in, first out) scheduler.
 * Running tasks are not paused/cancelled when a new task is added.
 */
public class LifoTaskScheduler implements TaskScheduler {

    private final Stack<PxHunterTask> tasks;

    public LifoTaskScheduler() {
        this.tasks = new Stack<PxHunterTask>();
    }

    @Override
    public void schedule(PxHunterTask task) {
        task.add(new PxHunterTask.OnCompletedListener() {

            @Override
            public void onCompleted(PxHunterTask completedTask, Result result) {
                tasks.remove(completedTask);
                startNextPendingTask();
            }

        });
        addTaskToRunNext(task);
    }

    private void startNextPendingTask() {
        for (PxHunterTask task : tasks) {
            if (task.getStatus() == AsyncTask.Status.PENDING) {
                ((AsyncTask<Void, Void, Result>) task).execute();
                return;
            }
        }
    }

    private void addTaskToRunNext(PxHunterTask taskToSchedule) {
        tasks.push(taskToSchedule);

        for (PxHunterTask task : tasks) {
            if (task.getStatus() == AsyncTask.Status.RUNNING) {
                return;
            }
        }
        startNextPendingTask();
    }

    @Override
    public void cancel(PxHunterTask task) {
        task.cancel(true);
        tasks.remove(task);
    }

    @Override
    public void cancelTasksFetchingFrom(String url) {
        List<PxHunterTask> pendingCancelTasks = new ArrayList<PxHunterTask>();
        for (PxHunterTask task : tasks) {
            if (task.isFetching(url)) {
                pendingCancelTasks.add(task);
            }
        }

        for (PxHunterTask pendingCancelTask : pendingCancelTasks) {
            pendingCancelTask.cancel(true);
            tasks.remove(pendingCancelTask);
        }
    }

    @Override
    public void cancelTasksTargeting(View view) {
        List<PxHunterTask> pendingCancelTasks = new ArrayList<PxHunterTask>();
        for (PxHunterTask task : tasks) {
            if (task.isTargeting(view)) {
                pendingCancelTasks.add(task);
            }
        }

        for (PxHunterTask pendingCancelTask : pendingCancelTasks) {
            pendingCancelTask.cancel(true);
            tasks.remove(pendingCancelTask);
        }
    }

    @Override
    public void clear() {
        for (PxHunterTask task : tasks) {
            task.cancel(true);
        }
        tasks.clear();
    }

}
