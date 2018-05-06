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
package org.uitest4j.swing.fixture;

import org.uitest4j.swing.core.GenericTypeMatcher;
import org.uitest4j.swing.exception.ComponentLookupException;
import org.uitest4j.swing.test.ExpectedException;
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;
import org.junit.jupiter.api.Test;

import javax.annotation.Nonnull;
import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Arrays.array;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.test.core.NeverMatchingComponentMatcher.neverMatches;

/**
 * Tests lookup of {@code JComboBox}es in {@link AbstractContainerFixture}.
 * 
 * @author Alex Ruiz
 */
public class AbstractContainerFixture_comboBox_Test extends RobotBasedTestCase {
  private ContainerFixture fixture;
  private MyWindow window;

  @Override
  protected void onSetUp() {
    window = MyWindow.createNew(getClass());
    fixture = new ContainerFixture(robot, window);
  }

  @Test
  void should_Find_Visible_JComboBox_By_Name() {
    robot.showWindow(window);
    JComboBoxFixture comboBox = fixture.comboBox("selectMeComboBox");
    assertThat(comboBox.target()).isSameAs(window.comboBox);
  }

  @Test
  void should_Fail_If_Visible_JComboBox_Not_Found_By_Name() {
    ExpectedException.assertContainsMessage(ComponentLookupException.class, () -> fixture.comboBox("myComboBox"), "Unable to find component using matcher",
        "name='myComboBox', type=javax.swing.JComboBox, requireShowing=true");
  }

  @Test
  void should_Find_Visible_JComboBox_By_Type() {
    robot.showWindow(window);
    JComboBoxFixture comboBox = fixture.comboBox();
    assertThat(comboBox.target()).isSameAs(window.comboBox);
  }

  @Test
  void should_Fail_If_Visible_JComboBox_Not_Found_By_Type() {
    ExpectedException.assertContainsMessage(ComponentLookupException.class, () -> fixture.comboBox(), "Unable to find component using matcher",
        "type=javax.swing.JComboBox, requireShowing=true");
  }

  @Test
  void should_Find_Visible_JComboBox_By_Matcher() {
    robot.showWindow(window);
    JComboBoxFixture comboBox = fixture.comboBox(new GenericTypeMatcher<JComboBox>(JComboBox.class) {
      @Override
      protected boolean isMatching(@Nonnull JComboBox c) {
        return c.getItemCount() == 3;
      }
    });
    assertThat(comboBox.target()).isSameAs(window.comboBox);
  }

  @Test
  void should_Fail_If_Visible_JComboBox_Not_Found_By_Matcher() {
    ExpectedException.assertContainsMessage(ComponentLookupException.class, () -> fixture.comboBox(neverMatches(JComboBox.class)), "Unable to find component using matcher");
  }

  private static class MyWindow extends TestWindow {
    final JComboBox comboBox = new JComboBox(array("One", "Two", "Three"));

    static MyWindow createNew(final Class<?> testClass) {
      return execute(() -> new MyWindow(testClass));
    }

    private MyWindow(Class<?> testClass) {
      super(testClass);
      comboBox.setName("selectMeComboBox");
      addComponents(comboBox);
    }
  }
}
