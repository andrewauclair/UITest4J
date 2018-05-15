package org.uitest4j.javafx.platform;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.CountDownLatch;

abstract class FXGUIAction implements Runnable {
	private boolean executedInUIThread;
	private Throwable caughtException;
	private CountDownLatch executionNotification;

	final @Nullable
	Throwable caughtException() {
		return caughtException;
	}

	final void caughtException(@Nullable Throwable catched) {
		caughtException = catched;
	}

	final boolean wasExecutedInUIThread() {
		return executedInUIThread;
	}

	final void clearCaughtException() {
		caughtException = null;
	}

	final void executionNotification(@Nonnull CountDownLatch notification) {
		executionNotification = notification;
	}

	final void notifyExecutionComplete() {
		executedInUIThread();
		if (executionNotification == null) {
			return;
		}
		executionNotification.countDown();
		executionNotification = null;
	}

	private void executedInUIThread() {
		executedInUIThread = true;
	}
}
