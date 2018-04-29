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

import org.uitest4j.swing.core.GenericTypeMatcher;
import org.assertj.swing.exception.ComponentLookupException;
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.jupiter.api.Test;

import javax.annotation.Nonnull;
import javax.swing.*;
import javax.swing.text.JTextComponent;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.core.NeverMatchingComponentMatcher.neverMatches;

/**
 * Tests lookups of {@code JTextComponent}s in {@link AbstractContainerFixture}.
 * 
 * @author Alex Ruiz
 */
public class AbstractContainerFixture_textBox_Test extends RobotBasedTestCase {
  private ContainerFixture fixture;
  private MyWindow window;

  @Override
  protected final void onSetUp() {
    window = MyWindow.createNew(getClass());
    fixture = new ContainerFixture(robot, window);
  }

  @Test
  void should_Find_Visible_JTextComponent_By_Name() {
    robot.showWindow(window);
    JTextComponentFixture textBox = fixture.textBox("typeMeTextField");
    assertThat(textBox.target()).isSameAs(window.textBox);
  }

  @Test
  void should_Fail_If_Visible_JTextComponent_Not_Found_By_Name() {
    ExpectedException.assertContainsMessage(ComponentLookupException.class, () -> fixture.textBox("myTextField"), "Unable to find component using matcher",
        "name='myTextField', type=javax.swing.text.JTextComponent, requireShowing=true");
  }

  @Test
  void should_Find_Visible_JTextComponent_By_Type() {
    robot.showWindow(window);
    JTextComponentFixture textBox = fixture.textBox();
    assertThat(textBox.target()).isSameAs(window.textBox);
  }

  @Test
  void should_Fail_If_Visible_JTextComponent_Not_Found_By_Type() {
    ExpectedException.assertContainsMessage(ComponentLookupException.class, () -> fixture.textBox(), "Unable to find component using matcher",
        "type=javax.swing.text.JTextComponent, requireShowing=true");
  }

  @Test
  void should_Find_Visible_JTextComponent_By_Matcher() {
    robot.showWindow(window);
    JTextComponentFixture textBox = fixture.textBox(new GenericTypeMatcher<JTextComponent>(JTextComponent.class) {
      @Override
      protected boolean isMatching(@Nonnull JTextComponent b) {
        return "".equals(b.getText());
      }
    });
    assertThat(textBox.target()).isSameAs(window.textBox);
  }

  @Test
  void should_Fail_If_Visible_JTextComponent_Not_Found_By_Matcher() {
    ExpectedException.assertContainsMessage(ComponentLookupException.class, () -> fixture.textBox(neverMatches(JTextComponent.class)), "Unable to find component using matcher");
  }

  private static class MyWindow extends TestWindow {
    final JTextComponent textBox = new JTextField(10);

    static @Nonnull MyWindow createNew(final @Nonnull Class<?> testClass) {
      return checkNotNull(execute(() -> new MyWindow(testClass)));
    }

    private MyWindow(@Nonnull Class<?> testClass) {
      super(testClass);
      textBox.setName("typeMeTextField");
      addComponents(textBox);
    }
  }
}
