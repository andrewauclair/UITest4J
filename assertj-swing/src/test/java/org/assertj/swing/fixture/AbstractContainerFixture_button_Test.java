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
package org.assertj.swing.fixture;

import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.exception.ComponentLookupException;
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.jupiter.api.Test;

import javax.annotation.Nonnull;
import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.core.NeverMatchingComponentMatcher.neverMatches;

/**
 * Tests lookups of {@code JButton}s in {@link AbstractContainerFixture}.
 * 
 * @author Alex Ruiz
 */
public class AbstractContainerFixture_button_Test extends RobotBasedTestCase {
  private ContainerFixture fixture;
  private MyWindow window;

  @Override
  protected void onSetUp() {
    window = MyWindow.createNew(getClass());
    fixture = new ContainerFixture(robot, window);
  }

  @Test
  void should_Find_Visible_JButton_By_Name() {
    robot.showWindow(window);
    JButtonFixture button = fixture.button("clickMeButton");
    assertThat(button.target()).isSameAs(window.button);
  }

  @Test
  void should_Fail_If_Visible_JButton_Not_Found_By_Name() {
    ExpectedException.assertContainsMessage(ComponentLookupException.class, () -> fixture.button("myButton"), "Unable to find component using matcher",
        "name='myButton', type=javax.swing.JButton, requireShowing=true");
  }

  @Test
  void should_Find_Visible_JButton_By_Type() {
    robot.showWindow(window);
    JButtonFixture button = fixture.button();
    assertThat(button.target()).isSameAs(window.button);
  }

  @Test
  void should_Fail_If_Visible_JButton_Not_Found_By_Type() {
    ExpectedException.assertContainsMessage(ComponentLookupException.class, () -> fixture.button(), "Unable to find component using matcher",
        "type=javax.swing.JButton, requireShowing=true");
  }

  @Test
  void should_Find_Visible_JButton_By_Matcher() {
    robot.showWindow(window);
    JButtonFixture button = fixture.button(new GenericTypeMatcher<JButton>(JButton.class) {
      @Override
      protected boolean isMatching(@Nonnull JButton b) {
        return "Click Me".equals(b.getText());
      }
    });
    assertThat(button.target()).isSameAs(window.button);
  }

  @Test
  void should_Fail_If_Visible_JButton_Not_Found_By_Matcher() {
    ExpectedException.assertContainsMessage(ComponentLookupException.class, () -> fixture.button(neverMatches(JButton.class)), "Unable to find component using matcher");
  }

  private static class MyWindow extends TestWindow {
    final JButton button = new JButton("Click Me");

    static MyWindow createNew(final Class<?> testClass) {
      return execute(() -> new MyWindow(testClass));
    }

    private MyWindow(Class<?> testClass) {
      super(testClass);
      button.setName("clickMeButton");
      addComponents(button);
    }
  }
}
