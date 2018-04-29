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

import org.assertj.swing.test.ExpectedException;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.swing.*;
import java.util.regex.Pattern;

import static org.assertj.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link JComboBoxDriver#requireSelection(javax.swing.JComboBox, String)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JComboBoxDriver_requireSelectionByText_Test extends JComboBoxDriver_TestCase {
  @Test
  void should_Pass_If_JComboBox_Has_Expected_Selection() {
    selectFirstItem();
    driver.requireSelection(comboBox, "first");
    assertThatCellReaderWasCalled();
  }

  @Test
  void should_Pass_If_JComboBox_Has_Matching_Selection() {
    selectFirstItem();
    driver.requireSelection(comboBox, "firs.*");
    assertThatCellReaderWasCalled();
  }

  @Test
  void should_Fail_If_JComboBox_Does_Not_Have_Expected_Selection() {
    selectFirstItem();
    ExpectedException.assertAssertionError(() -> driver.requireSelection(comboBox, "second"), "selectedIndex", "first", Pattern.compile("second"));
  }

  @Test
  void should_Fail_If_JComboBox_Does_Not_Have_Any_Selection() {
    clearSelection();
    ExpectedException.assertContainsMessage(AssertionError.class, () -> driver.requireSelection(comboBox, "second"), "property:'selectedIndex'", "No selection");
  }

  @Test
  void should_Pass_If_Editable_JComboBox_Has_Expected_Selection() {
    makeEditableAndSelect("Hello World");
    driver.requireSelection(comboBox, "Hello World");
  }

  @Test
  void should_Fail_If_Editable_JComboBox_Does_Not_Have_Expected_Selection() {
    makeEditableAndSelect("Hello World");
    ExpectedException.assertAssertionError(() -> driver.requireSelection(comboBox, "second"), "selectedIndex", "Hello World", Pattern.compile("second"));
  }

  @Test
  void should_Fail_If_Editable_JComboBox_Does_Not_Have_Any_Selection() {
    makeEditableAndClearSelection(comboBox);
    robot.waitForIdle();
    ExpectedException.assertContainsMessage(AssertionError.class, () -> driver.requireSelection(comboBox, "second"), "property:'selectedIndex'", "No selection");
  }

  @RunsInEDT
  private static void makeEditableAndClearSelection(final JComboBox comboBox) {
    execute(() -> {
      comboBox.setSelectedIndex(-1);
      comboBox.setEditable(true);
    });
  }
}
