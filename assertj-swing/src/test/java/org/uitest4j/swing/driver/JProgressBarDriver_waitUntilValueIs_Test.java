/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.driver;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.driver.JProgressBarIncrementValueAsyncTask.with;
import static org.uitest4j.swing.driver.JProgressBarValueQuery.valueOf;

import org.uitest4j.swing.exception.WaitTimedOutError;
import org.uitest4j.swing.test.ExpectedException;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link JProgressBarDriver#waitUntilValueIs(JProgressBar, int)}.
 * 
 * @author Alex Ruiz
 */
class JProgressBarDriver_waitUntilValueIs_Test extends JProgressBarDriver_TestCase {
  @Test
  void should_Throw_Error_If_Expected_Value_Is_Less_Than_Minimum() {
    ExpectedException.assertContainsMessage(IllegalArgumentException.class, () -> driver.waitUntilValueIs(progressBar, -1), "Value <-1> should be between <[0, 100]>");
  }

  @Test
  void should_Throw_Error_If_Expected_Value_Is_Greater_Than_Maximum() {
    ExpectedException.assertContainsMessage(IllegalArgumentException.class, () -> driver.waitUntilValueIs(progressBar, 200), "Value <200> should be between <[0, 100]>");
  }

  @Test
  void should_Wait_Until_Value_Is_Equal_To_Expected() {
    updateValueTo(10);
    JProgressBarIncrementValueAsyncTask task = with(progressBar).increment(20).every(1, SECONDS).createTask(robot);
    try {
      task.runAsynchronously();
      driver.waitUntilValueIs(progressBar, 70);
      assertThat(valueOf(progressBar)).isEqualTo(70);
    } finally {
      task.cancelIfNotFinished();
    }
  }

  @Test
  void should_Time_Out_If_Expected_Value_Never_Reached() {
    ExpectedException.assertContainsMessage(WaitTimedOutError.class, () -> driver.waitUntilValueIs(progressBar, 100), "Timed out waiting for value", "to be equal to 100");
  }
}
