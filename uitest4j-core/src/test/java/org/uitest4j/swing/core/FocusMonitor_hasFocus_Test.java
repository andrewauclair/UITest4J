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

import org.uitest4j.swing.test.core.SequentialEDTSafeTestCase;
import org.uitest4j.swing.test.swing.TestWindow;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.test.task.ComponentRequestFocusAndWaitForFocusGainTask.giveFocusAndWaitTillIsFocused;

/**
 * Tests for {@link FocusMonitor#hasFocus()}.
 *
 * @author Alex Ruiz
 */
class FocusMonitor_hasFocus_Test extends SequentialEDTSafeTestCase {
  private MyWindow window;

  @Override
  protected void onSetUp() {
    window = MyWindow.createNew();
    window.display();
  }

  @Override
  protected void onTearDown() {
    window.destroy();
  }

  @Test
  void should_Return_True_If_Component_Gains_Focus() {
    FocusMonitor monitor = FocusMonitor.attachTo(window.textBox);
    giveFocusAndWaitTillIsFocused(window.textBox);
    assertThat(monitor.hasFocus()).isTrue();
  }

  @Test
  void should_Return_False_If_Component_Loses_Focus() {
    FocusMonitor monitor = FocusMonitor.attachTo(window.button);
    giveFocusAndWaitTillIsFocused(window.textBox);
    assertThat(monitor.hasFocus()).isFalse();
  }

  @Test
  void should_Return_False_If_Component_Is_Not_Focus_Owner() {
    giveFocusAndWaitTillIsFocused(window.textBox);
    FocusMonitor monitor = FocusMonitor.attachTo(window.button);
    assertThat(monitor.hasFocus()).isFalse();
  }

  private static class MyWindow extends TestWindow {
    final JButton button = new JButton("Click Me");
    final JTextField textBox = new JTextField(20);

    @RunsInEDT
    static MyWindow createNew() {
      return execute(() -> new MyWindow());
    }

    private MyWindow() {
      super(FocusMonitor_hasFocus_Test.class);
      addComponents(button, textBox);
    }
  }
}
