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
package org.uitest4j.swing.core;

import org.uitest4j.swing.exception.WaitTimedOutError;
import org.uitest4j.swing.test.ExpectedException;
import org.uitest4j.swing.test.swing.TestWindow;
import org.uitest4j.swing.timing.Condition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.core.BasicComponentFinder.finderWithNewAwtHierarchy;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.timing.Pause.pause;

/**
 * Tests for {@link ComponentFoundCondition#descriptionAddendum()}.
 *
 * @author Alex Ruiz
 */
class ComponentFoundCondition_descriptionAddendum_Test {
  private static ComponentFinder finder;
  private static MyWindow window;

  @BeforeAll
  static void setUpOnce() {
    finder = finderWithNewAwtHierarchy();
    window = MyWindow.createNew();
  }

  @Test
  void should_Append_Component_Hierarchy_To_Exception_Message_If_Component_Was_Not_Found() {
    Condition condition = new ComponentFoundCondition("JButton to be found", finder, byType(JButton.class));

    ExpectedException.assertContainsMessage(WaitTimedOutError.class, () -> pause(condition, 1000),
            "Timed out waiting for JButton to be found",
            "Unable to find component using matcher", "MyWindow[name='myWindow'",
            "javax.swing.JLabel[name='null', text='Hello'");
  }

  @Test
  void should_Append_Found_Components_To_Exception_Message_If_Multiple_Components_Were_Found() {
    ComponentFoundCondition condition = new ComponentFoundCondition("JLabel to be found", finder, byType(JLabel.class));
    try {
      ExpectedException.assertContainsMessage(WaitTimedOutError.class, () -> pause(condition, 1000), "Timed out waiting for JLabel to be found",
              "Found more than one component using matcher",
              "javax.swing.JLabel[name='null', text='Hello'",
              "javax.swing.JLabel[name='null', text='World'");
    } finally {
      assertThat(condition.duplicatesFound()).containsOnly(window.helloLabel, window.worldLabel);
    }
  }

  private static TypeMatcher byType(Class<? extends JComponent> type) {
    return new TypeMatcher(type);
  }

  private static class MyWindow extends TestWindow {
    @RunsInEDT
    static MyWindow createNew() {
      return execute(MyWindow::new);
    }

    final JLabel helloLabel = new JLabel("Hello");
    final JLabel worldLabel = new JLabel("World");

    private MyWindow() {
      super(ComponentFoundCondition.class);
      setName("myWindow");
      addComponents(helloLabel, worldLabel);
    }
  }
}
