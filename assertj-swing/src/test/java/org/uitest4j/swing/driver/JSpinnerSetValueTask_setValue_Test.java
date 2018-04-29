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
import org.assertj.swing.test.core.MethodInvocations;
import org.assertj.swing.test.core.MethodInvocations.Args;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.core.MethodInvocations.Args.args;
import static org.assertj.swing.test.task.ComponentSetEnabledTask.disable;
import static org.assertj.swing.test.task.ComponentSetVisibleTask.hide;

/**
 * Tests for {@link JSpinnerSetValueTask#setValue(JSpinner, Object)}.
 * 
 * @author Alex Ruiz
 */
public class JSpinnerSetValueTask_setValue_Test extends RobotBasedTestCase {
  private MyWindow window;
  private MySpinner spinner;

  @Override
  protected void onSetUp() {
    window = MyWindow.createNew();
    spinner = window.spinner;
  }

  @Test
  void should_Set_Value() {
    robot.showWindow(window);
    String value = "Three";
    spinner.startRecording();
    JSpinnerSetValueTask.setValue(spinner, value);
    robot.waitForIdle();
    assertThat(JSpinnerValueQuery.valueOf(spinner)).isEqualTo(value);
    spinner.requireInvoked("setValue", args(value));
  }

  @Test
  void should_Throw_Error_If_JSpinner_Is_Disabled() {
    disable(spinner);
    robot.waitForIdle();
    ExpectedException.assertIllegalStateIsDisabledComponent(() -> JSpinnerSetValueTask.setValue(spinner, "Two"));
  }

  @Test
  void should_Throw_Error_If_JSpinner_Is_Not_Showing_On_The_Screen() {
    hide(window);
    robot.waitForIdle();
    ExpectedException.assertIllegalStateIsNotShowingComponent(() -> JSpinnerSetValueTask.setValue(spinner, "Two"));
  }

  private static class MyWindow extends TestWindow {
    @RunsInEDT
    static MyWindow createNew() {
      return execute(() -> new MyWindow());
    }

    final MySpinner spinner = new MySpinner("One", "Two", "Three");

    private MyWindow() {
      super(JSpinnerSetValueTask.class);
      spinner.setValue("Two");
      addComponents(spinner);
    }
  }

  private static class MySpinner extends JSpinner {
    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    MySpinner(Object... values) {
      super(new SpinnerListModel(values));
    }

    @Override
    public void setValue(Object value) {
      if (recording) {
        methodInvocations.invoked("setValue", args(value));
      }
      super.setValue(value);
    }

    void startRecording() {
      recording = true;
    }

    MethodInvocations requireInvoked(String methodName, Args args) {
      return methodInvocations.requireInvoked(methodName, args);
    }
  }
}
