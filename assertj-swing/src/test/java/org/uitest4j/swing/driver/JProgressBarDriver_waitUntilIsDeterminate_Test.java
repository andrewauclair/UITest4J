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
import static org.uitest4j.swing.driver.JProgressBarIndeterminateQuery.isIndeterminate;
import static org.uitest4j.swing.driver.JProgressBarMakeDeterminateAsyncTask.makeDeterminate;

import org.uitest4j.swing.exception.WaitTimedOutError;
import org.assertj.swing.test.ExpectedException;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link JProgressBarDriver#waitUntilIsDeterminate(javax.swing.JProgressBar)}.
 * 
 * @author Alex Ruiz
 */
class JProgressBarDriver_waitUntilIsDeterminate_Test extends JProgressBarDriver_TestCase {
  @Test
  void should_Wait_Until_Is_Determinate() {
    makeIndeterminate();
    JProgressBarMakeDeterminateAsyncTask task = makeDeterminate(progressBar).after(1, SECONDS).createTask(robot);
    try {
      task.runAsynchronously();
      driver.waitUntilIsDeterminate(progressBar);
      assertThat(isIndeterminate(progressBar)).isEqualTo(false);
    } finally {
      task.cancelIfNotFinished();
    }
  }

  @Test
  void should_Time_Out_If_Determinate_State_Never_Reached() {
    makeIndeterminate();
    ExpectedException.assertContainsMessage(WaitTimedOutError.class, () -> driver.waitUntilIsDeterminate(progressBar), "Timed out waiting for", "to be in determinate mode");
  }
}
