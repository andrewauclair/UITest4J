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

import static org.uitest4j.swing.driver.JScrollBarSetValueTask.setValue;

import org.assertj.swing.test.ExpectedException;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link JScrollBarDriver#requireValue(javax.swing.JScrollBar, int)}.
 * 
 * @author Alex Ruiz
 */
class JScrollBarDriver_requireValue_Test extends JScrollBarDriver_TestCase {
  @Test
  void should_Pass_If_Value_Is_Equal_To_Expected() {
    setValue(scrollBar, 30);
    robot.waitForIdle();
    driver.requireValue(scrollBar, 30);
  }

  @Test
  void should_Fail_If_Value_Is_Not_Equal_To_Expected() {
    setValue(scrollBar, 30);
    robot.waitForIdle();
    ExpectedException.assertContainsMessage(AssertionError.class, () -> driver.requireValue(scrollBar, 20), "property:'value'", "expected:<[2]0> but was:<[3]0>");
  }
}
