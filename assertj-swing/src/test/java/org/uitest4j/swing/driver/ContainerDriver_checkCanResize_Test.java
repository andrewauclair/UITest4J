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

import org.uitest4j.swing.core.Robot;
import org.uitest4j.swing.test.ExpectedException;
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestMdiWindow;
import org.uitest4j.swing.test.task.ComponentSetEnabledTask;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.swing.*;
import java.awt.*;

import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.test.builder.JButtons.button;
import static org.uitest4j.swing.test.builder.JDialogs.dialog;
import static org.uitest4j.swing.test.builder.JFrames.frame;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link ContainerDriver#checkCanResize(Container)}.
 * 
 * @author Alex Ruiz
 */
public class ContainerDriver_checkCanResize_Test extends RobotBasedTestCase {
  private ContainerDriver driver;

  @Override
  protected void onSetUp() {
    driver = new ContainerDriver(mock(Robot.class)) {
    };
  }

  @Test
  void should_Pass_If_Frame_Is_Resizable() {
    JFrame f = frame().createNew();
    robot.showWindow(f);
    checkCanResize(f);
  }

  @Test
  void should_Fail_If_Frame_Is_Not_Resizable() {
    JFrame f = frame().resizable(false).createNew();
    ExpectedException.assertIllegalStateIsNotResizableComponent(() -> checkCanResize(f));
  }

  @Test
  void should_Fail_If_Frame_Is_Resizable_But_Disabled() {
    JFrame f = frame().createNew();
    disable(f);
    ExpectedException.assertIllegalStateIsDisabledComponent(() -> checkCanResize(f));
  }

  @Test
  void should_Fail_If_Frame_Is_Resizable_But_Not_Showing_On_The_Screen() {
    JFrame f = frame().createNew();
    ExpectedException.assertIllegalStateIsNotShowingComponent(() -> checkCanResize(f));
  }

  @Test
  void should_Pass_If_Dialog_Is_Resizable() {
    JDialog d = dialog().createNew();
    robot.showWindow(d);
    checkCanResize(d);
  }

  @Test
  void should_Fail_If_Dialog_Is_Not_Resizable() {
    JDialog d = dialog().resizable(false).createNew();
    ExpectedException.assertIllegalStateIsNotResizableComponent(() -> checkCanResize(d));
  }

  @Test
  void should_Fail_If_Dialog_Is_Resizable_But_Disabled() {
    JDialog d = dialog().createNew();
    disable(d);
    ExpectedException.assertIllegalStateIsDisabledComponent(() -> checkCanResize(d));
  }

  @RunsInEDT
  private void disable(Container c) {
    ComponentSetEnabledTask.disable(c);
    robot.waitForIdle();
  }

  @Test
  void should_Fail_If_Dialog_Is_Resizable_But_Not_Showing_On_The_Screen() {
    JDialog d = dialog().createNew();
    ExpectedException.assertIllegalStateIsNotShowingComponent(() -> checkCanResize(d));
  }

  @Test
  void should_Pass_If_JInternalFrame_Is_Resizable() {
    TestMdiWindow w = TestMdiWindow.createNewWindow(getClass());
    robot.showWindow(w);
    checkCanResize(w.internalFrame());
  }

  @Test
  void should_Pass_If_JInternalFrame_Is_ResizableAndDisabled() {
    TestMdiWindow w = TestMdiWindow.createNewWindow(getClass());
    robot.showWindow(w);
    disable(w.internalFrame());
    checkCanResize(w.internalFrame());
  }

  @Test
  void should_Fail_If_JInternalFrame_Is_Not_Resizable() {
    TestMdiWindow w = TestMdiWindow.createNewWindow(getClass());
    robot.showWindow(w);
    JInternalFrame i = w.internalFrame();
    makeNotResizable(i);
    robot.waitForIdle();
    ExpectedException.assertIllegalStateIsNotResizableComponent(() -> checkCanResize(i));
  }

  @RunsInEDT
  private static void makeNotResizable(final JInternalFrame internalFrame) {
    execute(() -> internalFrame.setResizable(false));
  }

  @Test
  void should_Fail_If_JInternalFrame_Is_Resizable_But_Not_Showing_On_The_Screen() {
    TestMdiWindow w = TestMdiWindow.createNewWindow(getClass());
    ExpectedException.assertIllegalStateIsNotShowingComponent(() -> checkCanResize(w.internalFrame()));
  }

  @Test
  void should_Fail_If_Component_Is_Not_Window() {
    ExpectedException.assertIllegalStateIsNotResizableComponent(() -> checkCanResize(button().createNew()));
  }

  @RunsInEDT
  private void checkCanResize(final Container c) {
    execute(() -> driver.checkCanResize(c));
  }
}
