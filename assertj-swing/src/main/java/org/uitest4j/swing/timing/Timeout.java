/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * <p>
 * Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.timing;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Timeout.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class Timeout {
	private static final int DEFAULT_DELAY = 30000;

	private final long duration;

	/**
	 * Creates a new {@link Timeout} with default duration.
	 *
	 * @return the created {@code Timeout}.
	 */
	@Nonnull
	public static Timeout timeout() {
		return timeout(DEFAULT_DELAY);
	}

	/**
	 * Creates a new {@link Timeout}.
	 *
	 * @param duration the duration of the timeout in milliseconds.
	 * @return the created {@code Timeout}.
	 */
	@Nonnull
	public static Timeout timeout(long duration) {
		return new Timeout(duration);
	}

	/**
	 * Creates a new {@link Timeout}.
	 *
	 * @param duration the duration of the timeout.
	 * @param timeUnit the unit of time of the timeout.
	 * @return the created {@code Timeout}.
	 * @throws NullPointerException if the given time unit is {@code null}.
	 */
	@Nonnull
	public static Timeout timeout(long duration, @Nonnull TimeUnit timeUnit) {
		Objects.requireNonNull(timeUnit);
		return new Timeout(timeUnit.toMillis(duration));
	}

	private Timeout(long duration) {
		this.duration = duration;
	}

	/**
	 * @return the duration of the timeout in milliseconds.
	 */
	public long duration() {
		return duration;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Timeout timeout = (Timeout) o;
		return duration == timeout.duration;
	}

	@Override
	public int hashCode() {

		return Objects.hash(duration);
	}
}
