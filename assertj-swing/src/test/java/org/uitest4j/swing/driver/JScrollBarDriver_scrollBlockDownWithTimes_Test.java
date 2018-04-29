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
 * Tests for {@link JScrollBarDriver#scrollBlockDown(javax.swing.JScrollBar, int)}.
 * 
 * @author Alex Ruiz
 */
class JScrollBarDriver_scrollBlockDownWithTimes_Test extends JScrollBarDriver_TestCase {
  @Test
  void should_Scroll_Block_Down() {
    showWindow();
    driver.scrollBlockDown(scrollBar, 2);
    assertThatScrollBarValueIs(50);
  }

  @Test
  void should_Throw_Error_If_JScrollBar_Is_Disabled() {
    disableScrollBar();
    ExpectedException.assertIllegalStateIsDisabledComponent(() -> driver.scrollBlockDown(scrollBar, 2));
  }

  @Test
  void should_Throw_Error_If_JScrollBar_Is_Not_Showing_On_The_Screen() {
    ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.scrollBlockDown(scrollBar, 2));
  }
}
