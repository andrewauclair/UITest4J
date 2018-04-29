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

import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;
import org.uitest4j.swing.util.StringTextMatcher;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link JTabbedPaneTabIndexQuery#indexOfTab(JTabbedPane, org.uitest4j.swing.util.TextMatcher)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTabbedPaneTabIndexQuery_indexOfTab_Test extends RobotBasedTestCase {
  private JTabbedPane tabbedPane;

  @Override
  protected void onSetUp() {
    MyWindow window = MyWindow.createNew();
    tabbedPane = window.tabbedPane;
  }

  @Test
  public void should_Return_Index_Of_Tab() {
    int index = indexOfTab(tabbedPane, "First");
    assertThat(index).isEqualTo(0);
  }

  @Test
  public void should_Return_Negative_One_If_No_Matching_Title_Found() {
    int index = indexOfTab(tabbedPane, "Hello");
    assertThat(index).isEqualTo(-1);
  }

  @Test
  public void should_Return_Negative_One_If_JTabbedPane_Does_Not_Have_Tabs() {
    removeAllTabsIn(tabbedPane);
    robot.waitForIdle();
    int index = indexOfTab(tabbedPane, "First");
    assertThat(index).isEqualTo(-1);
  }

  @RunsInEDT
  private static int indexOfTab(final JTabbedPane tabbedPane, final String title) {
    return execute(() -> JTabbedPaneTabIndexQuery.indexOfTab(tabbedPane, new StringTextMatcher(title)));
  }

  @RunsInEDT
  private static void removeAllTabsIn(final JTabbedPane tabbedPane) {
    execute(tabbedPane::removeAll);
  }

  private static class MyWindow extends TestWindow {
    final JTabbedPane tabbedPane = new JTabbedPane();

    @RunsInEDT
    static MyWindow createNew() {
      return execute(() -> new MyWindow());
    }

    private MyWindow() {
      super(JTabbedPaneTabIndexQuery_indexOfTab_Test.class);
      tabbedPane.addTab("First", new JPanel());
      tabbedPane.addTab("Second", new JPanel());
      tabbedPane.addTab("Third", new JPanel());
      addComponents(tabbedPane);
    }
  }
}
