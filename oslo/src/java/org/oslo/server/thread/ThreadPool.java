package org.oslo.server.thread;

import java.util.List;
import java.util.LinkedList;

/**
 * A very simple thread pool class. The pool size is set at
 * construction time and remains fixed. Threads are cycled
 * through a FIFO idle queue.
 */
public class ThreadPool {
    List idle = new LinkedList();

    public ThreadPool(int poolSize) {
        // Fill up the pool with worker threads

        for (int i = 0; i < poolSize; i++) {
            WorkerThread thread = new WorkerThread(this);

            // Set thread name for debugging. Start it.
            thread.setName("Worker" + (i + 1));
            thread.start();
            idle.add(thread);
        }
    }

    /**
     * Find an idle worker thread, if any. Could return null.
     */
    public WorkerThread getWorker() {
        WorkerThread worker = null;
        synchronized (idle) {
            if (idle.size() > 0) {
                worker = (WorkerThread) idle.remove(0);
            }
        }
        return (worker);
    }

    /**
     * Called by the worker thread to return itself to the
     * idle pool.
     */
    public void returnWorker(WorkerThread worker) {
        synchronized (idle) {
            idle.add(worker);
        }
    }
}