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
package org.uitest4j.swing.core;

import static java.awt.event.KeyEvent.VK_A;
import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.core.InputModifiers.isAltDown;
import static org.uitest4j.swing.core.InputModifiers.isAltGraphDown;
import static org.uitest4j.swing.core.InputModifiers.isControlDown;
import static org.uitest4j.swing.core.InputModifiers.isMetaDown;
import static org.uitest4j.swing.core.InputModifiers.isShiftDown;
import static org.uitest4j.swing.test.awt.Toolkits.singletonToolkitMock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link EmergencyAbortListener#EmergencyAbortListener(java.awt.Toolkit)}.
 * 
 * @author Alex Ruiz
 */
class EmergencyAbortListener_constructor_Test {
  private EmergencyAbortListener listener;

  @BeforeEach
  void setUp() {
    listener = new EmergencyAbortListener(singletonToolkitMock());
  }

  @Test
  void should_Have_CTRL_SHIFT_A_As_Default_Key_Combination() {
    assertThat(listener.keyCode()).isEqualTo(VK_A);
    assertThatModifiersAreCtrlAndShift(listener.modifiers());
  }

  private void assertThatModifiersAreCtrlAndShift(int modifiers) {
    assertThat(isControlDown(modifiers)).isTrue();
    assertThat(isShiftDown(modifiers)).isTrue();
    assertThat(isAltDown(modifiers)).isFalse();
    assertThat(isAltGraphDown(modifiers)).isFalse();
    assertThat(isMetaDown(modifiers)).isFalse();
  }
}
