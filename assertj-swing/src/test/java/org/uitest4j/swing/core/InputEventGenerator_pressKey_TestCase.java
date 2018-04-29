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

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.swing.text.JTextComponent;
import java.util.Collection;

import static java.awt.event.KeyEvent.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.task.ComponentRequestFocusAndWaitForFocusGainTask.giveFocusAndWaitTillIsFocused;
import static org.uitest4j.swing.timing.Pause.pause;

/**
 * Base test case for implementations of {@link InputEventGenerator#pressKey(int, char)} and
 * {@link InputEventGenerator#releaseKey(int)}.
 *
 * @author Alex Ruiz
 */
abstract class InputEventGenerator_pressKey_TestCase extends InputEventGenerator_TestCase {
  private static Collection<Object[]> keys() {
    return newArrayList(new Object[][] { { VK_A, "a" }, { VK_S, "s" }, { VK_D, "d" } });
  }

  @ParameterizedTest
  @MethodSource("keys")
  void should_Type_Key(int keyToPress, String expectedText) {
    giveFocusAndWaitTillIsFocused(window.textBox);
    eventGenerator.pressKey(keyToPress, CHAR_UNDEFINED);
    eventGenerator.releaseKey(keyToPress);
    pause(DELAY);
    String text = textOf(window.textBox);
    assertThat(text).isEqualTo(expectedText);
  }

  @RunsInEDT
  private static String textOf(final JTextComponent textComponent) {
    return execute(() -> textComponent.getText());
  }
}
