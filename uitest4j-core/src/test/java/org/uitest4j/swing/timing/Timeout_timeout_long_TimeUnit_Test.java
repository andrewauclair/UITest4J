/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.timing;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link Timeout#timeout(long, TimeUnit)}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class Timeout_timeout_long_TimeUnit_Test {
	@Test
	void shouldReturnDurationInMillisecondsWhenCreatedWithSeconds() {
		Timeout timeout = Timeout.timeout(3, TimeUnit.SECONDS);
		assertThat(timeout.duration()).isEqualTo(3000);
	}

	@Test
	void shouldReturnDurationInMillisecondsWhenCreatedWithMilliSeconds() {
		Timeout timeout = Timeout.timeout(3, TimeUnit.MILLISECONDS);
		assertThat(timeout.duration()).isEqualTo(3);
	}

	@Test
	void shouldThrowExceptionIfTimeUnitIsNull() {
		// jsr305 throws IllegalArgumentExceptions when @Nonnull is used
		assertThrows(IllegalArgumentException.class, () -> Timeout.timeout(0, null));
	}
}
