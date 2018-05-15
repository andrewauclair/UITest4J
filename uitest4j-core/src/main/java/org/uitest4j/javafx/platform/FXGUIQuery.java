package org.uitest4j.javafx.platform;

import javafx.application.Platform;

import javax.annotation.Nullable;

public abstract class FXGUIQuery<T> extends FXGUIAction {
	private T result;

	@Override
	public void run() {
		if (!Platform.isFxApplicationThread()) {
			// TODO Throw exception for not running in UI thread
		}
		try {
			result = executeOnUIThread();
		}
		catch (Throwable throwable) {
			caughtException(throwable);
		}
		finally {
			notifyExecutionComplete();
		}
	}

	protected abstract @Nullable
	T executeOnUIThread() throws Throwable;

	final @Nullable
	T result() {
		return result;
	}

	final void clearResult() {
		result = null;
	}
}
