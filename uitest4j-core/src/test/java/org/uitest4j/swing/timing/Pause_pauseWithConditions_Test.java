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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.uitest4j.swing.test.util.StopWatch.startNewStopWatch;

/**
 * Tests for {@link Pause#pause(Condition[])}.
 *
 * @author Alex Ruiz
 */
class Pause_pauseWithConditions_Test {
	@Test
	void should_Wait_Till_Conditions_Are_Satisfied() {
		int timeToWaitTillSatisfied = 1000;
		SatisfiedCondition one = new SatisfiedCondition(timeToWaitTillSatisfied);
		SatisfiedCondition two = new SatisfiedCondition(timeToWaitTillSatisfied);
		StopWatch watch = startNewStopWatch();
		Pause.pause(new Condition[]{one, two});
		watch.stop();
		assertThat(watch.ellapsedTime() >= timeToWaitTillSatisfied).isTrue();
		assertThat(one.satisfied).isTrue();
		assertThat(two.satisfied).isTrue();
	}

	@Test
	void should_Timeout_If_Conditions_Are_Never_Satisfied() {
		assertThrows(WaitTimedOutError.class, () -> Pause.pause(new Condition[]{new NeverSatisfiedCondition(), new NeverSatisfiedCondition()}));
	}

	@Test
	void should_Throw_Error_If_Condition_Array_Is_Null() {
		// jsr305 throws IllegalArgumentExceptions when @Nonnull is used
		assertThrows(IllegalArgumentException.class, () -> Pause.pause((Condition[]) null));
	}

	@Test
	void should_Throw_Error_If_Condition_Array_Is_Empty() {
		assertThrows(IllegalArgumentException.class, () -> Pause.pause(new Condition[0]));
	}

	@Test
	void should_Timeout_If_Conditions_Together_Run_Longer_Than_Timeout() {
		assertTimeoutPreemptively(Duration.ofMillis(30100), () -> assertThrows(WaitTimedOutError.class, () -> Pause.pause(new Condition[]{new SatisfiedCondition(15000), new SatisfiedCondition(20000)})));
	}

	@Test
	void should_Timeout_If_Any_Condition_Runs_Longer_Than_Timeout() {
		assertTimeoutPreemptively(Duration.ofMillis(30100), () -> assertThrows(WaitTimedOutError.class, () -> Pause.pause(new Condition[]{new SatisfiedCondition(40000)})));
	}

	@Test
	void should_Throw_Error_If_Any_Condition_Throws_Any() {
		assertThrows(NumberFormatException.class, () -> Pause.pause(new Condition[]{new RuntimeExceptionCondition(new NumberFormatException("expected"))}));
	}

	@Test
	void should_Throw_Error_If_Any_Condition_Is_Null() {
		assertThrows(NullPointerException.class, () -> Pause.pause(new Condition[]{new NeverSatisfiedCondition(), null}));
	}
}
