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

import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.swing.*;
import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.driver.JTextComponentSelectedTextQuery.selectedTextOf;
import static org.uitest4j.swing.driver.JTextComponentSetEditableTask.setTextFieldEditable;
import static org.uitest4j.swing.driver.JTextComponentTextQuery.textOf;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.test.task.ComponentSetEnabledTask.disable;

/**
 * Base test case for {@link JTextComponentDriver}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class JTextComponentDriver_TestCase extends RobotBasedTestCase {
  MyWindow window;
  JTextField textField;
  JTextComponentDriver driver;

  @Override
  protected final void onSetUp() {
    driver = new JTextComponentDriver(robot);
    window = MyWindow.createNew(getClass());
    window.setMinimumSize(new Dimension(100, 50));
    textField = window.textField;
	  textField.setName("TestTextField");
    extraSetUp();
  }

  void extraSetUp() {
  }

  @RunsInEDT
  final void requireSelectedTextInTextField(String expected) {
    assertThat(selectedTextOf(textField)).isEqualTo(expected);
  }

  @RunsInEDT
  final void requireTextInTextField(String expected) {
    assertThat(textOf(textField)).isEqualTo(expected);
  }

  @RunsInEDT
  final void requireEmptyTextField() {
    assertThat(textOf(textField)).isNullOrEmpty();
  }

  @RunsInEDT
  final void disableTextField() {
    disable(textField);
    robot.waitForIdle();
  }

  final void showWindow() {
    robot.showWindow(window);
  }

  @RunsInEDT
  final void clearTextField() {
    setTextFieldText("");
  }

  @RunsInEDT
  final void setTextFieldText(String text) {
    setText(textField, text);
    robot.waitForIdle();
  }

  @RunsInEDT
  static void setText(final JTextField textField, final String text) {
    execute(() -> textField.setText(text));
  }

  @RunsInEDT
  final void makeTextFieldEditable() {
    setTextFieldEditable(textField, true);
    robot.waitForIdle();
  }

  @RunsInEDT
  final void makeTextFieldNotEditable() {
    setTextFieldEditable(textField, false);
    robot.waitForIdle();
  }

  static class MyWindow extends TestWindow {
    final JTextField textField = new JTextField("This is a test");

    @RunsInEDT
    static MyWindow createNew(final Class<?> testClass) {
      return execute(() -> new MyWindow(testClass));
    }

    private MyWindow(Class<?> testClass) {
      super(testClass);
      addComponents(textField);
    }
  }
}
