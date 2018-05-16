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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.CountDownLatch;

/**
 * @author Andrew Auclair
 */
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
