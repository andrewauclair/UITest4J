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

import org.assertj.swing.exception.ActionFailedException;
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.accessibility.*;
import javax.swing.*;
import java.awt.*;
import java.util.Locale;

import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link ComponentPerformDefaultAccessibleActionTask#performDefaultAccessibleAction(Component)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ComponentPerformDefaultAccessibleActionTask_performDefaultAccessibleAction_Test extends RobotBasedTestCase {
  private AccessibleAction accessibleAction;
  private AccessibleContextStub accessibleContext;
  private Component component;

  @Override
  protected void onSetUp() {
    accessibleAction = mock(AccessibleAction.class);
    accessibleContext = new AccessibleContextStub(accessibleAction);
    MyWindow window = MyWindow.createNew(accessibleContext);
    component = window.component;
  }

  @Test
  void should_Execute_First_Action_In_AccessibleAction() {
    when(accessibleAction.getAccessibleActionCount()).thenReturn(1);
    when(accessibleAction.doAccessibleAction(0)).thenReturn(true);
    ComponentPerformDefaultAccessibleActionTask.performDefaultAccessibleAction(component);
    robot.waitForIdle();
  }

  @Test
  void should_Throw_Error_If_AccessibleAction_Is_Null() {
    accessibleContext.accessibleAction(null);
    try {
      assertActionFailedThrown(() -> {
        ComponentPerformDefaultAccessibleActionTask.performDefaultAccessibleAction(component);

        robot.waitForIdle();
      });
    } finally {
      verifyZeroInteractions(accessibleAction);
    }
  }

  @Test
  void should_Throw_Error_If_AccessibleAction_Is_Empty() {
    when(accessibleAction.getAccessibleActionCount()).thenReturn(0);
    assertActionFailedThrown(() -> {
      ComponentPerformDefaultAccessibleActionTask.performDefaultAccessibleAction(component);
      robot.waitForIdle();
    });
  }

  private void assertActionFailedThrown(Executable executable) {
    ExpectedException.assertContainsMessage(ActionFailedException.class, executable, "Unable to perform accessible action for");
  }

  private static class MyWindow extends TestWindow {
    final MyComponent component;

    @RunsInEDT
    static MyWindow createNew(final AccessibleContext accessibleContext) {
      return execute(() -> new MyWindow(accessibleContext));
    }

    private MyWindow(AccessibleContext accessibleContext) {
      super(ComponentPerformDefaultAccessibleActionTask_performDefaultAccessibleAction_Test.class);
      component = new MyComponent(accessibleContext);
      addComponents(component);
    }
  }

  private static class MyComponent extends JTextField {
    private final AccessibleContext accessibleContext;

    MyComponent(AccessibleContext accessibleContext) {
      super(20);
      this.accessibleContext = accessibleContext;
    }

    @Override
    public AccessibleContext getAccessibleContext() {
      return accessibleContext;
    }
  }

  private static class AccessibleContextStub extends AccessibleContext {
    private AccessibleAction accessibleAction;

    AccessibleContextStub(AccessibleAction newAccessibleAction) {
      accessibleAction(newAccessibleAction);
    }

    void accessibleAction(AccessibleAction newAccessibleAction) {
      accessibleAction = newAccessibleAction;
    }

    @Override
    public AccessibleAction getAccessibleAction() {
      return accessibleAction;
    }

    @Override
    public Accessible getAccessibleChild(int i) {
      return null;
    }

    @Override
    public int getAccessibleChildrenCount() {
      return 0;
    }

    @Override
    public int getAccessibleIndexInParent() {
      return 0;
    }

    @Override
    public AccessibleRole getAccessibleRole() {
      return null;
    }

    @Override
    public AccessibleStateSet getAccessibleStateSet() {
      return null;
    }

    @Override
    public Locale getLocale() {
      return null;
    }
  }
}
