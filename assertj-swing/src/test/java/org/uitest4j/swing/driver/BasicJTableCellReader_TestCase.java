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
import org.assertj.swing.test.swing.CustomCellRenderer;
import org.assertj.swing.test.swing.TestWindow;
import org.uitest4j.swing.annotation.RunsInCurrentThread;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.swing.*;
import java.awt.*;

import static java.awt.Color.BLUE;
import static java.awt.Color.WHITE;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Base test case for {@link BasicJTableCellReader}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class BasicJTableCellReader_TestCase extends RobotBasedTestCase {
  JTable table;
  BasicJTableCellReader reader;

  @Override
  protected final void onSetUp() {
    MyWindow window = MyWindow.createNew(getClass());
    table = window.table;
    reader = new BasicJTableCellReader();
  }

  @RunsInEDT
  final JLabel setJLabelAsCellRenderer() {
    JLabel label = setJLabelAsCellRendererOf(table);
    robot.waitForIdle();
    return label;
  }

  @RunsInEDT
  private static JLabel setJLabelAsCellRendererOf(final JTable table) {
    return execute(() -> {
      JLabel label = new JLabel("Hello");
      label.setBackground(BLUE);
      label.setForeground(WHITE);
      setCellRendererComponent(table, label);
      return label;
    });
  }

  @RunsInCurrentThread
  private static void setCellRendererComponent(JTable table, Component renderer) {
    CustomCellRenderer cellRenderer = new CustomCellRenderer(renderer);
    table.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);
  }

  static class MyWindow extends TestWindow {
    @RunsInEDT
    static MyWindow createNew(final Class<?> testClass) {
      return execute(() -> new MyWindow(testClass));
    }

    final JTable table = new JTable(1, 1);

    private MyWindow(Class<?> testClass) {
      super(testClass);
      addComponents(table);
    }
  }
}
