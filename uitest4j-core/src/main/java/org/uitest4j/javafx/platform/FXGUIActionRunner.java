package org.uitest4j.javafx.platform;

import javafx.application.Platform;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class FXGUIActionRunner {

	@Nullable
	public static <T> T execute(@Nonnull Callable<T> query) {
		return execute(new FXGUIQuery<>() {
			@Nullable
			@Override
			protected T executeOnUIThread() throws Exception {
				return query.call();
			}
		});
	}

	@Nullable
	public static <T> T execute(@Nonnull FXGUIQuery<T> query) {
		run(query);
		return resultOf(query);
	}

	private static synchronized void run(@Nonnull final FXGUIAction action) {
		if (Platform.isFxApplicationThread()) {
			action.run();
			return;
		}
		final CountDownLatch latch = new CountDownLatch(1);
		action.executionNotification(latch);
		Platform.runLater(action);
		try {
			latch.await();
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	@Nullable
	private static <T> T resultOf(@Nonnull FXGUIQuery<T> query) {
		T result = query.result();
		query.clearResult();
		// TODO rethrow caught exception
		return result;
	}
}
