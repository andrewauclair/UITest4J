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

import org.uitest4j.swing.test.ExpectedException;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.swing.*;

import static javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link JTableDriver#requireSelectedRows(javax.swing.JTable, int[])}.
 *
 * @author Alex Ruiz
 */
class JTableDriver_requireSelectedRows_Test extends JTableDriver_TestCase {
  @Test
  void should_Fail_If_JTable_Does_Not_Have_The_Expected_Selected_Rows() {
    selectRows(6, 8);
    String NL = System.getProperty("line.separator");
    ExpectedException.assertContainsMessage(AssertionError.class, () -> driver.requireSelectedRows(table, 0, 1), "property:'selectedRows'", "Expecting:" + NL + " <[6, 7, 8]>" + NL + "to contain:" + NL + " <[0, 1]>" + NL + "but could not find:" + NL + " <[0, 1]>");
  }

  @Test
  void should_Pass_If_JTable_Has_Expected_Selected_Rows() {
    selectRows(6, 8);
    driver.requireSelectedRows(table, 6, 7, 8);
  }

  @Test
  void should_Pass_If_JTable_Has_Expected_Selected_Rows_In_Different_Order() {
    selectRows(6, 8);
    driver.requireSelectedRows(table, 8, 7, 6);
  }

  @Test
  void should_Pass_If_Expected_Selected_Rows_Are_Subset_Of_All_Selected_Rows() {
    selectRows(6, 8);
    driver.requireSelectedRows(table, 6, 7);
  }

  @RunsInEDT
  private void selectRows(int from, int to) {
    selectRows(table, from, to);
    robot.waitForIdle();
  }

  @RunsInEDT
  private static void selectRows(final JTable table, final int from, final int to) {
    execute(() -> {
      if (from != to) {
        table.setSelectionMode(MULTIPLE_INTERVAL_SELECTION);
      }
      table.setRowSelectionInterval(from, to);
    });
  }
}
