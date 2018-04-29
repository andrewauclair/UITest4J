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

import org.assertj.swing.test.core.MethodInvocations;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.data.BooleanProvider;
import org.assertj.swing.test.swing.TestDialog;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.uitest4j.swing.annotation.RunsInEDT;

import java.awt.*;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.task.DialogSetModalTask.makeModal;

/**
 * Tests for {@link DialogModalQuery#isModal(java.awt.Dialog)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class DialogModalQuery_isModal_Test extends RobotBasedTestCase {
  private MyDialog dialog;

  private static Collection<Object[]> booleans() {
    return newArrayList(BooleanProvider.booleans());
  }

  @Override
  protected void onSetUp() {
    dialog = MyDialog.createNew();
  }

  @ParameterizedTest
  @MethodSource("booleans")
  void should_Indicate_If_Dialog_Is_Modal(boolean modal) {
    makeModal(dialog, modal);
    robot.waitForIdle();
    dialog.startRecording();
    assertThat(DialogModalQuery.isModal(dialog)).isEqualTo(modal);
    dialog.requireInvoked("isModal");
  }

  private static class MyDialog extends TestDialog {
    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    @RunsInEDT
    static MyDialog createNew() {
      return execute(() -> new MyDialog());
    }

    private MyDialog() {
      super(TestWindow.createNewWindow(DialogModalQuery_isModal_Test.class));
      setPreferredSize(new Dimension(200, 100));
    }

    void startRecording() {
      recording = true;
    }

    @Override
    public boolean isModal() {
      if (recording) {
        methodInvocations.invoked("isModal");
      }
      return super.isModal();
    }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}
