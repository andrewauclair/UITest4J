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
import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link JToolBarDriver#makeFloat(JToolBar)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JToolBarDriver_makeFloat_Test extends JToolBarDriver_TestCase {
  @Test
  void should_Throw_Error_If_JToolBar_Is_Not_Floatable() {
    makeNotFloatable();
    ExpectedException.assertContainsMessage(IllegalStateException.class, () -> driver.makeFloat(toolBar), "is not floatable");
  }

  @RunsInEDT
  private void makeNotFloatable() {
    setFloatable(toolBar, false);
    robot.waitForIdle();
    showWindow();
  }

  @RunsInEDT
  private static void setFloatable(final JToolBar toolBar, final boolean flotable) {
    execute(() -> toolBar.setFloatable(flotable));
  }

  @RunsInEDT
  public void should_Float_JToolbar() {
    Window oldAncestor = toolBarAncestor();
    driver.makeFloat(toolBar);
    Window newAncestor = toolBarAncestor();
    assertThat(newAncestor).isNotSameAs(oldAncestor);
  }
}
