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

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link BasicRobot#focusAndWaitForFocusGain(Component)}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class BasicRobot_focusAndWaitForFocusGain_Test extends BasicRobot_TestCase {
  private JButton button;

  @RunsInEDT
  @Override
  void beforeShowingWindow() {
    execute(() -> {
      button = new JButton("Click Me");
      window().add(button);
    });
  }

  @Test
  void should_Give_Focus_And_Wait_Until_Component_Has_Focus() {
    giveFocusAndVerifyThatHasFocus(button);
    giveFocusAndVerifyThatHasFocus(window().textField());
  }

  private void giveFocusAndVerifyThatHasFocus(@Nonnull Component c) {
    robot().focusAndWaitForFocusGain(c);
    assertThat(c.hasFocus()).isTrue();
  }
}
