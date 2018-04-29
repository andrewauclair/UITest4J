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

import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.task.ComponentSetVisibleTask.hide;

/**
 * Tests for {@link JInternalFrameDriver#maximize(javax.swing.JInternalFrame)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JInternalFrameDriver_maximize_Test extends JInternalFrameDriver_TestCase {
  @Test
  void should_Maximize_JInternalFrame() {
    showWindow();
    driver.maximize(internalFrame);
    assertThatIsMaximized();
  }

  @Test
  void should_Maximize_Iconified_JInternalFrame() {
    showWindow();
    iconify();
    driver.maximize(internalFrame);
    assertThatIsMaximized();
  }

  @Test
  void should_Maximize_Disabled_JInternalFrame() {
    disableInternalFrame();
    driver.maximize(internalFrame);
    assertThatIsMaximized();
  }

  @Test
  void should_Throw_Error_If_JInternalFrame_Is_Not_Showing_On_The_Screen() {
    ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.maximize(internalFrame));
  }

  @Test
  void should_Throw_Error_If_JInternalFrame_Is_Hidden() {
    hideInternalJFrame();
    ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.maximize(internalFrame));
  }

  @RunsInEDT
  private void hideInternalJFrame() {
    hide(internalFrame);
    robot.waitForIdle();
  }

  @Test
  void should_Throw_Error_If_JInternalFrame_Is_Not_Maximizable() {
    makeNotMaximizable();
    showWindow();
    ExpectedException.assertContainsMessage(IllegalStateException.class, () -> driver.maximize(internalFrame), "The JInternalFrame <", "> is not maximizable");
  }

  @RunsInEDT
  private void makeNotMaximizable() {
    setMaximizable(internalFrame, false);
    robot.waitForIdle();
  }

  @RunsInEDT
  private static void setMaximizable(final JInternalFrame internalFrame, final boolean maximizable) {
    execute(() -> internalFrame.setMaximizable(maximizable));
  }
}
