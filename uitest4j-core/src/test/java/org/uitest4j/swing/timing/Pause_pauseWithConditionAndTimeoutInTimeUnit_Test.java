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
import org.uitest4j.swing.exception.WaitTimedOutError;
import org.uitest4j.swing.test.util.StopWatch;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.uitest4j.swing.test.util.StopWatch.startNewStopWatch;
import static org.uitest4j.swing.timing.Timeout.timeout;

/**
 * Tests for {@link Pause#pause(Condition, Timeout)}.
 *
 * @author Alex Ruiz
 */
class Pause_pauseWithConditionAndTimeoutInTimeUnit_Test {
	private static final int TIMEOUT = 1000;

	@Test
	void should_Timeout_If_Condition_Is_Never_Satisfied() {
		assertThrows(WaitTimedOutError.class, () -> Pause.pause(new NeverSatisfiedCondition(), timeout(TIMEOUT)));
	}

	@Test
	void should_Timeout_If_Condition_Runs_Longer_Than_Timeout() {
		assertTimeoutPreemptively(Duration.ofMillis(1100), () -> assertThrows(WaitTimedOutError.class, () -> Pause.pause(new SatisfiedCondition(10000), timeout(1, TimeUnit.SECONDS))));
	}

	@Test
	void should_Wait_Till_Condition_Is_Satisfied() {
		int timeToWaitTillSatisfied = TIMEOUT - 50;
		SatisfiedCondition condition = new SatisfiedCondition(timeToWaitTillSatisfied);

		StopWatch watch = startNewStopWatch();
		Pause.pause(condition, timeout(TIMEOUT));
		watch.stop();

		assertThat(watch.ellapsedTime() >= timeToWaitTillSatisfied).isTrue();
		assertThat(condition.satisfied).isTrue();
	}

	@Test
	void should_Throw_Error_If_Condition_Throws_Any() {
		assertThrows(NumberFormatException.class, () -> Pause.pause(new RuntimeExceptionCondition(new NumberFormatException("expected")), timeout(TIMEOUT)));
	}

	@Test
	void should_Throw_Error_If_Condition_Is_Null() {
		// jsr305 throws IllegalArgumentExceptions when @Nonnull is used
		assertThrows(IllegalArgumentException.class, () -> Pause.pause((Condition) null, timeout(TIMEOUT)));
	}

	@Test
	void should_Throw_Error_If_Timeout_Is_Null() {
		// jsr305 throws IllegalArgumentExceptions when @Nonnull is used
		assertThrows(IllegalArgumentException.class, () -> Pause.pause(new NeverSatisfiedCondition(), null));
	}
}
