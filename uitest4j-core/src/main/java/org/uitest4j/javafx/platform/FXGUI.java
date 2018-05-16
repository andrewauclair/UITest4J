/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * <p>
 * Copyright 2018 the original author or authors.
 */
package org.uitest4j.javafx.platform;

import javafx.application.Platform;

import java.util.concurrent.CountDownLatch;

/**
 * @author Andrew Auclair
 */
public class FXGUI {
	/**
	 * Runs the specified {@link Runnable} on the
	 * JavaFX application thread and waits for completion.
	 *
	 * @param action the {@link Runnable} to run
	 * @throws NullPointerException if {@code action} is {@code null}
	 */
	public static void runAndWait(Runnable action) {
		if (action == null)
			throw new NullPointerException("action");

		// run synchronously on JavaFX thread
		if (Platform.isFxApplicationThread()) {
			action.run();
			return;
		}

		// queue on JavaFX thread and wait for completion
		final CountDownLatch doneLatch = new CountDownLatch(1);
		Platform.runLater(() -> {
			try {
				action.run();
			} finally {
				doneLatch.countDown();
			}
		});

		try {
			doneLatch.await();
		} catch (InterruptedException e) {
			// ignore exception
		}
	}
}
