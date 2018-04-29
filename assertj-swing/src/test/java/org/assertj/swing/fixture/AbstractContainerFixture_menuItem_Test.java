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
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.core.NeverMatchingComponentMatcher.neverMatches;

/**
 * Tests lookup of {@code JMenuItem}s in {@link AbstractContainerFixture}.
 *
 * @author Alex Ruiz
 */
public class AbstractContainerFixture_menuItem_Test extends RobotBasedTestCase {
  private ContainerFixture fixture;
  private MyWindow window;

  @Override
  protected final void onSetUp() {
    window = MyWindow.createNew(getClass());
    fixture = new ContainerFixture(robot, window);
  }

  @Test
  void should_Find_Visible_JMenuItem_By_Path() {
    robot.showWindow(window);
    JMenuItemFixture menuItem = fixture.menuItemWithPath("File", "New");
    assertThat(menuItem.target()).isSameAs(window.menuNew);
  }

  @Test
  void should_Fail_If_Visible_JMenuItem_Not_Found_By_Path() {
    ExpectedException.assertContainsMessage(ComponentLookupException.class, () -> fixture.menuItemWithPath("Edit"), "Unable to find component using matcher", "label='Edit'");
  }

  @Test
  void should_Find_Visible_JMenuItem_By_Name() {
    robot.showWindow(window);
    JMenuItemFixture menuItem = fixture.menuItem("menuNew");
    assertThat(menuItem.target()).isSameAs(window.menuNew);
  }

  @Test
  void should_Fail_If_Visible_JMenuItem_Not_Found_By_Name() {
    ExpectedException.assertContainsMessage(ComponentLookupException.class, () -> fixture.menuItem("myMenuNew"), "Unable to find component using matcher",
                                  "name='myMenuNew', type=javax.swing.JMenuItem, requireShowing=false");
  }

  @Test
  void should_Find_Visible_JMenuItem_By_Matcher() {
    robot.showWindow(window);
    JMenuItemFixture menuItem = fixture.menuItem(new GenericTypeMatcher<JMenuItem>(JMenuItem.class) {
      @Override
      protected boolean isMatching(@Nonnull JMenuItem m) {
        return "New".equals(m.getText());
      }
    });
    assertThat(menuItem.target()).isSameAs(window.menuNew);
  }

  @Test
  void should_Fail_If_Visible_JMenuItem_Not_Found_By_Matcher() {
    ExpectedException.assertContainsMessage(ComponentLookupException.class, () -> fixture.menuItem(neverMatches(JMenuItem.class)), "Unable to find component using matcher");
  }

  private static class MyWindow extends TestWindow {
    final JMenu menuFile = new JMenu("File");
    final JMenuItem menuNew = new JMenuItem("New");

    @RunsInEDT
    static @Nonnull MyWindow createNew(final @Nonnull Class<?> testClass) {
      return checkNotNull(execute(() -> new MyWindow(testClass)));
    }

    private MyWindow(@Nonnull Class<?> testClass) {
      super(testClass);
      setJMenuBar(new JMenuBar());
      menuFile.add(menuNew);
      menuNew.setName("menuNew");
      getJMenuBar().add(menuFile);
      setPreferredSize(new Dimension(80, 60));
    }
  }
}
