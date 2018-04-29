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

import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.swing.*;
import java.awt.*;

import static org.assertj.swing.edt.GuiActionRunner.execute;

/**
 * Base test case for {@link JScrollPaneDriver}.
 * 
 * @author Yvonne Wang
 */
public abstract class JScrollPaneDriver_TestCase extends RobotBasedTestCase {
  MyWindow window;
  JScrollPaneDriver driver;

  @Override
  protected final void onSetUp() {
    window = MyWindow.createNew(getClass());
    driver = new JScrollPaneDriver(robot);
  }

  static class MyWindow extends TestWindow {
    final JScrollPane scrollPane = new JScrollPane();
    final JScrollBar horizontalScrollBar = new JScrollBar();
    final JScrollBar verticalScrollBar = new JScrollBar();

    @RunsInEDT
    static MyWindow createNew(final Class<?> testClass) {
      return execute(() -> new MyWindow(testClass));
    }

    private MyWindow(Class<?> testClass) {
      super(testClass);
      add(scrollPane);
      scrollPane.setHorizontalScrollBar(horizontalScrollBar);
      scrollPane.setVerticalScrollBar(verticalScrollBar);
      setPreferredSize(new Dimension(300, 300));
    }
  }
}
