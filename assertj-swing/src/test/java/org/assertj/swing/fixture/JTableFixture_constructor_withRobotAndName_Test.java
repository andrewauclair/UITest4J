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

import org.assertj.swing.exception.ComponentLookupException;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInCurrentThread;

import javax.swing.*;
import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link JTableFixture#JTableFixture(org.uitest4j.swing.core.Robot, String)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTableFixture_constructor_withRobotAndName_Test extends RobotBasedTestCase {
  private MyWindow window;

  @Override
  protected void onSetUp() {
    window = MyWindow.createNew();
  }

  @Test
  void should_Lookup_Showing_JTable_By_Name() {
    robot.showWindow(window);
    JTableFixture fixture = new JTableFixture(robot, "table");
    assertThat(fixture.robot()).isSameAs(robot);
    assertThat(fixture.target()).isSameAs(window.table);
  }

  @Test
  void should_Throw_Error_If_JTable_With_Matching_Name_Is_Not_Showing() {
    assertThrows(ComponentLookupException.class, () -> new JTableFixture(robot, "table"));
  }

  @Test
  void should_Throw_Error_If_A_JTable_With_Matching_Name_Is_Not_Found() {
    assertThrows(ComponentLookupException.class, () -> new JTableFixture(robot, "other"));
  }

  private static class MyWindow extends TestWindow {
    static MyWindow createNew() {
      return execute(() -> new MyWindow());
    }

    final JTable table = new JTable(8, 6);

    private MyWindow() {
      super(JTableFixture_constructor_withRobotAndName_Test.class);
      table.setName("table");
      addComponents(decorate(table));
    }

    @RunsInCurrentThread
    private static Component decorate(JTable table) {
      JScrollPane scrollPane = new JScrollPane(table);
      scrollPane.setPreferredSize(new Dimension(200, 100));
      return scrollPane;
    }
  }
}
