package com.eixox;

import java.util.LinkedList;

public class ThreadPool {

	private final PoolWorker[] threads;
	private final LinkedList<Runnable> queue;

	public ThreadPool(int nThreads) {
		queue = new LinkedList<Runnable>();
		threads = new PoolWorker[nThreads];

		for (int i = 0; i < nThreads; i++) {
			threads[i] = new PoolWorker();
			threads[i].start();
		}
	}

	public ThreadPool() {
		this(16);
	}

	public void execute(Runnable r) {
		synchronized (queue) {
			queue.addLast(r);
			queue.notify();
		}
	}

	public final int getCount() {
		return this.queue.size();
	}

	public final void complete() {
		while (this.queue.size() > 0) {
			try {
				Thread.sleep(100);
			} catch (Throwable e) {
			
			}
		}
	}

	private class PoolWorker extends Thread {
		public void run() {
			Runnable r;

			while (true) {
				synchronized (queue) {
					while (queue.isEmpty()) {
						try {
							queue.wait();
						} catch (InterruptedException ignored) {
						}
					}

					r = (Runnable) queue.removeFirst();
				}

				// If we don't catch RuntimeException,
				// the pool could leak threads
				try {
					r.run();
				} catch (Throwable e) {
				}
			}
		}
	}

}
