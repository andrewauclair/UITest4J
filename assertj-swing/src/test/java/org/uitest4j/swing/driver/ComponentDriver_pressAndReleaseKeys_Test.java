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

import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_C;
import static java.awt.event.KeyEvent.VK_E;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.uitest4j.swing.test.ExpectedException;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link ComponentDriver#pressAndReleaseKeys(java.awt.Component, int...)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class ComponentDriver_pressAndReleaseKeys_Test extends ComponentDriver_TestCase {
  @Test
  void should_Throw_Error_If_Array_Of_Keys_Is_Null() {
    int[] keyCodes = null;
    // jsr305 throws IllegalArgumentExceptions when @Nonnull is used
    assertThrows(IllegalArgumentException.class, () -> driver.pressAndReleaseKeys(window.button, keyCodes));
  }

  @Test
  void should_Press_And_Release_Keys() {
    showWindow();
    assertThatTextFieldIsEmpty();
    int[] keyCodes = { VK_A, VK_C, VK_E };
    driver.pressAndReleaseKeys(window.textField, keyCodes);
    assertThatTextInTextFieldIs("ace");
  }

  @Test
  void should_Throw_Error_If_Component_Is_Disabled() {
    disableTextField();
    try {
      ExpectedException.assertIllegalStateIsDisabledComponent(() -> driver.pressAndReleaseKeys(window.textField, VK_A));
    } finally {
      assertThatTextFieldIsEmpty();
    }
  }

  @Test
  void should_Throw_Error_If_Component_Is_Not_Showing_On_The_Screen() {
    try {
      ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.pressAndReleaseKeys(window.textField, VK_A));
    } finally {
      assertThatTextFieldIsEmpty();
    }
  }
}
