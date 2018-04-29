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

import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.uitest4j.swing.driver.JTextComponentSelectedTextQuery.selectedTextOf;
import static org.assertj.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link JTextComponentSelectTextTask#selectTextInRange(JTextComponent, int, int)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTextComponentSelectTextTask_selectTextInRange_Test extends RobotBasedTestCase {
  static final String TEXTBOX_TEXT = "Hello World";

  private JTextComponent textBox;

  private static Collection<Object[]> ranges() {
    return newArrayList(new Object[][] { { 0, 5 }, { 1, 9 }, { 6, 8 } });
  }

  @Override
  protected void onSetUp() {
    MyWindow window = MyWindow.createNew();
    textBox = window.textBox;
    robot.showWindow(window);
  }

  @ParameterizedTest
  @MethodSource("ranges")
  void should_Select_Text(int start, int end) {
    selectTextInRange(textBox, start, end);
    robot.waitForIdle();
    String selection = selectedTextOf(textBox);
    assertThat(selection).isEqualTo(TEXTBOX_TEXT.substring(start, end));
  }

  @RunsInEDT
  private static void selectTextInRange(final JTextComponent textBox, final int start, final int end) {
    execute(() -> JTextComponentSelectTextTask.selectTextInRange(textBox, start, end));
  }

  private static class MyWindow extends TestWindow {
    final JTextField textBox = new JTextField(20);

    @RunsInEDT
    static MyWindow createNew() {
      return execute(() -> new MyWindow());
    }

    private MyWindow() {
      super(JTextComponentSelectTextTask_selectTextInRange_Test.class);
      setPreferredSize(new Dimension(80, 60));
      add(textBox);
      textBox.setText(TEXTBOX_TEXT);
    }
  }
}
