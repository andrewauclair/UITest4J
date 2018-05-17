package org.uitest4j.javafx.platform;

import javafx.application.Platform;

import javax.annotation.Nullable;

import static org.uitest4j.exception.ActionFailedException.actionFailure;

/**
 * @author Andrew Auclair
 */
public abstract class FXGUITask extends FXGUIAction {
	@Override
	public void run() {
		if (!Platform.isFxApplicationThread()) {
			throw actionFailure("Query should be executed in the JavaFX Application Thread");
		}
		
		try {
			executeOnUIThread();
		}
		catch (Throwable throwable) {
			caughtException(throwable);
		}
		finally {
			notifyExecutionComplete();
		}
	}
	
	protected abstract void executeOnUIThread() throws Throwable;
}
