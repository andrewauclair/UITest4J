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
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.jupiter.api.Test;

import javax.annotation.Nonnull;
import javax.swing.*;

import static java.awt.Color.RED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.core.NeverMatchingComponentMatcher.neverMatches;

/**
 * Tests lookups of {@code JPanel}s in {@link AbstractContainerFixture}.
 *
 * @author Alex Ruiz
 */
public class AbstractContainerFixture_panel_Test extends RobotBasedTestCase {
  private ContainerFixture fixture;
  private MyWindow window;

  @Override
  protected void onSetUp() {
    window = MyWindow.createNew(getClass());
    fixture = new ContainerFixture(robot, window);
  }

  @Test
  void should_Find_Visible_JPanel_By_Name() {
    robot.showWindow(window);
    JPanelFixture panel = fixture.panel("myPanel");
    assertThat(panel.target()).isSameAs(window.panel);
  }

  @Test
  void should_Fail_If_Visible_JPanel_Not_Found_By_Name() {
    ExpectedException.assertContainsMessage(ComponentLookupException.class, () -> fixture.panel("somePanel"), "Unable to find component using matcher",
                                  "name='somePanel', type=javax.swing.JPanel, requireShowing=true");
  }

  @Test
  void should_Find_Visible_JPanel_By_Type() {
    robot.showWindow(window);
    JPanelFixture panel = fixture.panel();
    assertThat(panel.target()).isSameAs(window.panel);
  }

  @Test
  void should_Fail_If_Visible_JPanel_Not_Found_By_Type() {
    ExpectedException.assertContainsMessage(ComponentLookupException.class, () -> fixture.panel(), "Unable to find component using matcher",
                                  "type=javax.swing.JPanel, requireShowing=true");
  }

  @Test
  void should_Find_Visible_JPanel_By_Matcher() {
    robot.showWindow(window);
    JPanelFixture panel = fixture.panel(new GenericTypeMatcher<JPanel>(JPanel.class) {
      @Override
      protected boolean isMatching(@Nonnull JPanel p) {
        return RED.equals(p.getBackground());
      }
    });
    assertThat(panel.target()).isSameAs(window.panel);
  }

  @Test
  void should_Fail_If_Visible_JPanel_Not_Found_By_Matcher() {
    ExpectedException.assertContainsMessage(ComponentLookupException.class, () -> fixture.panel(neverMatches(JPanel.class)), "Unable to find component using matcher");
  }

  private static class MyWindow extends TestWindow {
    final JPanel panel = new JPanel();

    static @Nonnull MyWindow createNew(final @Nonnull Class<?> testClass) {
      return checkNotNull(execute(() -> new MyWindow(testClass)));
    }

    private MyWindow(@Nonnull Class<?> testClass) {
      super(testClass);
      panel.setName("myPanel");
      panel.setBackground(RED);
      setContentPane(panel);
    }
  }
}
