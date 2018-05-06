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
package org.uitest4j.swing.query;

import org.uitest4j.swing.test.core.MethodInvocations;
import org.uitest4j.swing.test.core.SequentialEDTSafeTestCase;
import org.uitest4j.swing.test.swing.TestWindow;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.swing.*;
import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link ComponentParentQuery#parentOf(java.awt.Component)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class ComponentParentQuery_parentOf_Test extends SequentialEDTSafeTestCase {
  private MyButton button;

  @Override
  protected void onSetUp() {
    MyWindow window = MyWindow.createNew();
    button = window.button;
  }

  @Test
  void should_Return_Parent_Of_Component() {
    button.startRecording();
    Container parent = ComponentParentQuery.parentOf(button);
    assertThat(parent).isInstanceOf(JPanel.class);
    assertThat(parent.getName()).isEqualTo("null.contentPane");
    button.requireInvoked("getParent");
  }

  private static class MyWindow extends TestWindow {
    @RunsInEDT
    static MyWindow createNew() {
      return execute(() -> new MyWindow());
    }

    final MyButton button = new MyButton("A button");

    private MyWindow() {
      super(ComponentParentQuery_parentOf_Test.class);
      addComponents(button);
    }
  }

  private static class MyButton extends JButton {
    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    MyButton(String text) {
      super(text);
    }

    @Override
    public Container getParent() {
      if (recording) {
        methodInvocations.invoked("getParent");
      }
      return super.getParent();
    }

    void startRecording() {
      recording = true;
    }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}
