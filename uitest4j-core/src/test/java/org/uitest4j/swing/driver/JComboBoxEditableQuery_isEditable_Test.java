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

import org.uitest4j.swing.test.core.MethodInvocations;
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.data.BooleanProvider;
import org.uitest4j.swing.test.swing.TestWindow;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.swing.*;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.uitest4j.swing.driver.JComboBoxSetEditableTask.setEditable;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link JComboBoxEditableQuery#isEditable(JComboBox)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JComboBoxEditableQuery_isEditable_Test extends RobotBasedTestCase {
  private MyComboBox comboBox;

  private static Collection<Object[]> booleans() {
    return newArrayList(BooleanProvider.booleans());
  }

  @Override
  protected void onSetUp() {
    MyWindow window = MyWindow.createNew();
    comboBox = window.comboBox;
  }

  @ParameterizedTest
  @MethodSource("booleans")
  void should_Indicate_If_JComboBox_Is_Editable(boolean editable) {
    setEditable(comboBox, editable);
    robot.waitForIdle();
    comboBox.startRecording();
    assertThat(JComboBoxEditableQuery.isEditable(comboBox)).isEqualTo(editable);
    comboBox.requireInvoked("isEditable");
  }

  private static class MyWindow extends TestWindow {
    @RunsInEDT
    static MyWindow createNew() {
      return execute(() -> new MyWindow());
    }

    final MyComboBox comboBox = new MyComboBox("one", "two", "three");

    private MyWindow() {
      super(JComboBoxEditableQuery_isEditable_Test.class);
      add(comboBox);
    }
  }

  private static class MyComboBox extends JComboBox {
    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    MyComboBox(Object... items) {
      super(items);
    }

    void startRecording() {
      recording = true;
    }

    @Override
    public boolean isEditable() {
      if (recording) {
        methodInvocations.invoked("isEditable");
      }
      return super.isEditable();
    }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}
