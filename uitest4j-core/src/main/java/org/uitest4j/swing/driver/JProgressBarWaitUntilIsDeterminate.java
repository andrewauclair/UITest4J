/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at
  <p>
  http://www.apache.org/licenses/LICENSE-2.0
  <p>
  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.
  <p>
  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.driver;

import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.timing.Condition;
import org.uitest4j.swing.timing.Timeout;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.util.function.Supplier;

import static org.uitest4j.swing.driver.JProgressBarIndeterminateQuery.isIndeterminate;
import static org.uitest4j.swing.format.Formatting.format;
import static org.uitest4j.swing.timing.Pause.pause;

/**
 * EDT-safe task that waits until the value of a {@code JProgressBar} is equal to the given expected value.
 *
 * @author Alex Ruiz
 */
final class JProgressBarWaitUntilIsDeterminate {
	@RunsInEDT
	static void waitUntilValueIsDeterminate(final @Nonnull JProgressBar progressBar, final @Nonnull Timeout timeout) {
		pause(new Condition(untilIsDeterminate(progressBar)) {
			@Override
			public boolean test() {
				return !isIndeterminate(progressBar);
			}
		}, timeout);
	}

	private static Supplier<String> untilIsDeterminate(final @Nonnull JProgressBar progressBar) {
		return () -> format(progressBar) + " to be in determinate mode";
	}

	private JProgressBarWaitUntilIsDeterminate() {
	}
}
