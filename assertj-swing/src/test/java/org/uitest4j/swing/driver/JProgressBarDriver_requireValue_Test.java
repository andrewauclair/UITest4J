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

import org.uitest4j.swing.test.ExpectedException;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link JProgressBarDriver#requireValue(JProgressBar, int)}.
 * 
 * @author Alex Ruiz
 */
class JProgressBarDriver_requireValue_Test extends JProgressBarDriver_TestCase {
  @Test
  void should_Pass_If_Value_Is_Equal_To_Expected() {
    driver.requireValue(progressBar, 60);
  }

  @Test
  void should_Fail_If_Value_Is_Not_Equal_To_Expected() {
    ExpectedException.assertContainsMessage(AssertionError.class, () -> driver.requireValue(progressBar, 50), "property:'value'", "expected:<[5]0> but was:<[6]0>");
  }
}
