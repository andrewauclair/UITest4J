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
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link JComboBoxDriver#selectAllText(javax.swing.JComboBox)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JComboBoxDriver_selectAllText_Test extends JComboBoxDriver_TestCase {
  @Test
  void should_Throw_Error_If_JComboBox_Is_Disabled() {
    disableComboBox();
    ExpectedException.assertIllegalStateIsDisabledComponent(() -> driver.selectAllText(comboBox));
  }

  @Test
  void should_Throw_Error_If_JComboBox_Is_Not_Showing_On_The_Screen() {
    ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.selectAllText(comboBox));
  }

  @Test
  void should_Throw_Error_If_JComboBox_Is_Not_Editable() {
    showWindow();
    assertThatIllegalStateExceptionCauseIsNotEditableComboBox(() -> driver.selectAllText(comboBox));
  }

  @Test
  void should_Select_All_Text() {
    showWindow();
    makeEditableAndSelectFirstItem();
    robot.waitForIdle();
    driver.selectAllText(comboBox);
    assertSelectedTextIs("first");
  }

  @RunsInEDT
  private void assertSelectedTextIs(String expected) {
    assertThat(selectedTextOf(comboBox)).isEqualTo(expected);
  }

  @RunsInEDT
  private static String selectedTextOf(final JComboBox comboBox) {
    return execute(() -> {
      Component editor = comboBox.getEditor().getEditorComponent();
      assertThat(editor).isInstanceOf(JTextComponent.class);
      return ((JTextComponent) editor).getSelectedText();
    });
  }
}
