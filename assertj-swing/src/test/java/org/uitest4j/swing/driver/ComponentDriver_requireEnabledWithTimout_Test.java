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

import static org.assertj.swing.test.util.StopWatch.startNewStopWatch;
import static org.uitest4j.swing.timing.Timeout.timeout;

import org.uitest4j.swing.exception.WaitTimedOutError;
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.util.StopWatch;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link ComponentDriver#requireEnabled(java.awt.Component, org.uitest4j.swing.timing.Timeout)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class ComponentDriver_requireEnabledWithTimout_Test extends ComponentDriver_TestCase {
  @Test
  void should_Pass_If_Component_Is_Enabled_Before_Timeout() {
    driver.requireEnabled(window.button, timeout(100));
  }

  @Test
  void should_Fail_If_Component_Is_Not_Enabled_Before_Timeout() {
    disableButton();
    int timeout = 1000;
    StopWatch stopWatch = startNewStopWatch();
    try {
      ExpectedException.assertContainsMessage(WaitTimedOutError.class, () -> driver.requireEnabled(window.button, timeout(timeout)), "Timed out waiting for", window.button.getClass().getName(), "to be enabled");
    } finally {
      stopWatch.stop();
      assertThatWaited(stopWatch, timeout);
    }
  }
}
