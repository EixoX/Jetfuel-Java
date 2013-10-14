package com.eixox;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadPool {

	private final PoolWorker[] threads;
	private final LinkedList<Runnable> queue;
	private Logger logger = Logger.getGlobal();

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

	/**
	 * @return the logger
	 */
	public final Logger getLogger() {
		return logger;
	}

	/**
	 * @param logger
	 *            the logger to set
	 */
	public final void setLogger(Logger logger) {
		this.logger = logger;
	}

	public final int getCount() {
		return this.queue.size();
	}

	public final void complete() {
		while (this.queue.size() > 0) {
			try {
				Thread.sleep(100);
			} catch (Throwable e) {
				// You might want to log something here
				if (logger != null)
					logger.log(Level.SEVERE, getClass().toString(), e);
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
					// You might want to log something here
					if (logger != null && r != null)
						logger.log(Level.SEVERE, r.toString(), e);
				}
			}
		}
	}

}
