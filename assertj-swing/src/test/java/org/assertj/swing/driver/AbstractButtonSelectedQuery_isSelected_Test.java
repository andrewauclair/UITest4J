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

import org.assertj.swing.test.core.MethodInvocations;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.data.BooleanProvider;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.swing.*;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.task.AbstractButtonSetSelectedTask.setSelected;

/**
 * Tests for {@link AbstractButtonSelectedQuery#isSelected(javax.swing.AbstractButton)}.
 *
 * @author Alex Ruiz
 */
public class AbstractButtonSelectedQuery_isSelected_Test extends RobotBasedTestCase {
  private MyCheckBox checkBox;

  private static Collection<Object[]> booleans() {
    return newArrayList(BooleanProvider.booleans());
  }

  @Override
  protected void onSetUp() {
    MyWindow window = MyWindow.createNew();
    checkBox = window.checkBox;
  }

  @ParameterizedTest
  @MethodSource("booleans")
  void should_Indicate_If_AbstractButton_Is_Selected(boolean selected) {
    setSelected(checkBox, selected);
    robot.waitForIdle();
    checkBox.startRecording();
    boolean isSelected = AbstractButtonSelectedQuery.isSelected(checkBox);
    assertThat(isSelected).isEqualTo(selected);
    checkBox.requireInvoked("isSelected");
  }

  private static class MyWindow extends TestWindow {
    @RunsInEDT
    static MyWindow createNew() {
      return execute(() -> new MyWindow());
    }

    final MyCheckBox checkBox = new MyCheckBox("A Button");

    private MyWindow() {
      super(AbstractButtonSelectedQuery_isSelected_Test.class);
      addComponents(checkBox);
    }
  }

  private static class MyCheckBox extends JCheckBox {
    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    MyCheckBox(String text) {
      super(text);
    }

    void startRecording() {
      recording = true;
    }

    @Override
    public boolean isSelected() {
      if (recording) {
        methodInvocations.invoked("isSelected");
      }
      return super.isSelected();
    }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}
