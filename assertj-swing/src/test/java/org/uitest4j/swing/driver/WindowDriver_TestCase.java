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

import java.awt.*;

import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.task.ComponentSetEnabledTask.disable;
import static org.assertj.swing.test.task.FrameSetResizableTask.setResizable;

/**
 * Tests for {@link WindowDriver}.
 * 
 * @author Alex Ruiz
 */
public abstract class WindowDriver_TestCase extends RobotBasedTestCase {
  Frame window;
  WindowDriver driver;

  @Override
  protected final void onSetUp() {
    window = TestWindow.createNewWindow(getClass());
    driver = new WindowDriver(robot);
  }

  final void showWindow() {
    robot.showWindow(window, new Dimension(100, 100));
  }

  @RunsInEDT
  final void disableWindow() {
    disable(window);
    robot.waitForIdle();
  }

  @RunsInEDT
  final void makeWindowNotResizable() {
    setResizable(window, false);
    robot.waitForIdle();
  }

  @RunsInEDT
  static boolean isActive(final Window w) {
    return execute(() -> w.isActive());
  }
}
