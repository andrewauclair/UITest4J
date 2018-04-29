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
package org.assertj.swing.driver;

import org.assertj.swing.test.ExpectedException;
import org.junit.jupiter.api.Test;

import static java.awt.event.InputEvent.SHIFT_DOWN_MASK;
import static java.awt.event.KeyEvent.VK_A;
import static org.uitest4j.swing.core.KeyPressInfo.keyCode;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link ComponentDriver#pressAndReleaseKey(java.awt.Component, org.uitest4j.swing.core.KeyPressInfo)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class ComponentDriver_pressAndReleaseKeyWithPressInfo_Test extends ComponentDriver_TestCase {
  @Test
  void should_Throw_Error_If_KeyPressInfo_Is_Null() {
    // jsr305 throws IllegalArgumentExceptions when @Nonnull is used
    assertThrows(IllegalArgumentException.class, () -> driver.pressAndReleaseKey(window.button, null));
  }

  @Test
  void should_Press_And_Release_Key_In_Given_KeyPressInfo() {
    showWindow();
    assertThatTextFieldIsEmpty();
    driver.pressAndReleaseKey(window.textField, keyCode(VK_A).modifiers(SHIFT_DOWN_MASK));
    assertThatTextInTextFieldIs("A");
  }

  @Test
  void should_Throw_Error_If_Component_Is_Disabled() {
    disableTextField();
    try {
      ExpectedException.assertIllegalStateIsDisabledComponent(() -> driver.pressAndReleaseKey(window.textField, keyCode(VK_A).modifiers(SHIFT_DOWN_MASK)));
    } finally {
      assertThatTextFieldIsEmpty();
    }
  }

  @Test
  void should_Throw_Error_If_Component_Is_Not_Showing_On_The_Screen() {
    try {
      ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.pressAndReleaseKey(window.textField, keyCode(VK_A).modifiers(SHIFT_DOWN_MASK)));
    } finally {
      assertThatTextFieldIsEmpty();
    }
  }
}
