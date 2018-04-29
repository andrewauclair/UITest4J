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
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.swing.*;
import java.awt.*;

import static org.assertj.swing.edt.GuiActionRunner.execute;

/**
 * Base test case for {@link JTabbedPaneLocation}.
 * 
 * @author Yvonne Wang
 */
public abstract class JTabbedPaneLocation_TestCase extends RobotBasedTestCase {
  JTabbedPane tabbedPane;
  JTabbedPaneLocation location;

  @Override
  protected final void onSetUp() {
    MyWindow window = MyWindow.createNew(getClass());
    tabbedPane = window.tabbedPane;
    location = new JTabbedPaneLocation();
  }

  static class MyWindow extends TestWindow {
    final JTabbedPane tabbedPane = new JTabbedPane();

    @RunsInEDT
    static MyWindow createNew(final Class<?> testClass) {
      return execute(() -> new MyWindow(testClass));
    }

    private MyWindow(Class<?> testClass) {
      super(testClass);
      tabbedPane.addTab("one", new JPanel());
      tabbedPane.addTab("two", new JPanel());
      tabbedPane.setPreferredSize(new Dimension(200, 100));
      addComponents(tabbedPane);
    }
  }
}
