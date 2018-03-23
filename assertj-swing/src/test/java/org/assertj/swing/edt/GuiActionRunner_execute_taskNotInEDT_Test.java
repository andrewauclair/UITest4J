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
package org.assertj.swing.edt;

import org.assertj.swing.exception.UnexpectedException;
import org.assertj.swing.test.core.MethodInvocations;
import org.assertj.swing.test.core.SequentialEDTSafeTestCase;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link GuiActionRunner#execute(GuiTask)}.
 * 
 * @author Alex Ruiz
 */
public class GuiActionRunner_execute_taskNotInEDT_Test extends SequentialEDTSafeTestCase {
  private boolean executeInEDT;

  @Override
  protected final void onSetUp() {
    executeInEDT = GuiActionRunner.executeInEDT();
    GuiActionRunner.executeInEDT(true);
  }

  @Override
  protected void onTearDown() {
    GuiActionRunner.executeInEDT(executeInEDT);
  }

  @Test
  void should_Execute_Task() {
    TestGuiTask task = new TestGuiTask();
    GuiActionRunner.executeInEDT(false);
    GuiActionRunner.execute(task);
    assertThat(task.wasExecutedInEDT()).isFalse();
    task.requireInvoked("executeInEDT");
  }

  @Test
  void should_Wrap_Any_Thrown_Exception() {
    TestGuiTask task = mock(TestGuiTask.class);
    RuntimeException error = expectedError();
    doThrow(error).when(task).executeInEDT();
    GuiActionRunner.executeInEDT(false);
    UnexpectedException unexpectedException = assertThrows(UnexpectedException.class, () -> GuiActionRunner.execute(task));
    assertThat(unexpectedException.getCause()).isEqualTo(error);
  }

  private RuntimeException expectedError() {
    return new RuntimeException("Thrown on purpose");
  }

  private static class TestGuiTask extends GuiTask {
    private final MethodInvocations methodInvocations = new MethodInvocations();

    TestGuiTask() {
    }

    @Override
    protected void executeInEDT() {
      methodInvocations.invoked("executeInEDT");
    }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}
