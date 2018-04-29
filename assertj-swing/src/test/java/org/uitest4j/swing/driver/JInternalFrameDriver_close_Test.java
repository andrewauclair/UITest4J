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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link JInternalFrameDriver#close(javax.swing.JInternalFrame)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JInternalFrameDriver_close_Test extends JInternalFrameDriver_TestCase {
  @Test
  void should_Close_JInternalFrame() {
    showWindow();
    driver.close(internalFrame);
    assertThat(isClosed(internalFrame)).isTrue();
  }

  @RunsInEDT
  private static boolean isClosed(final JInternalFrame internalFrame) {
    return execute(internalFrame::isClosed);
  }

  @Test
  void should_Throw_Error_If_JInternalFrame_Is_Not_Closable() {
    makeNotCloseable();
    showWindow();
    ExpectedException.assertContainsMessage(IllegalStateException.class, () -> driver.close(internalFrame), "The JInternalFrame <", "> is not closable");
  }

  @RunsInEDT
  private void makeNotCloseable() {
    setClosable(internalFrame, false);
    robot.waitForIdle();
  }

  @RunsInEDT
  private static void setClosable(final JInternalFrame internalFrame, final boolean closeable) {
    execute(() -> internalFrame.setClosable(closeable));
  }
}
