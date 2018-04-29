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
package org.uitest4j.swing.data;

import org.assertj.swing.driver.BasicJTableCellReader;
import org.assertj.swing.exception.ActionFailedException;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link TableCellInSelectedRow#findCell(JTable, JTableCellReader)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class TableCellInSelectedRow_findCell_Test extends TableCellFinder_TestCase {
  private TableCellInSelectedRow finder;

  @Override
  void extraSetUp() {
    finder = TableCellInSelectedRow.selectedRow().column(2);
  }

  @Test
  void should_Find_Cell_In_Selected_Row() {
    selectRow(1);
    TableCell cell = finder.findCell(table, new BasicJTableCellReader());
    assertThat(cell.row).isEqualTo(1);
    assertThat(cell.column).isEqualTo(2);
  }

  @RunsInEDT
  private void selectRow(int row) {
    selectRow(table, row);
    robot.waitForIdle();
  }

  @RunsInEDT
  private static void selectRow(final JTable table, final int row) {
    execute(() -> table.setRowSelectionInterval(row, row));
  }

  @Test
  void should_Throw_Error_If_JTable_Does_Not_Have_Selection() {
    assertThrows(ActionFailedException.class, () -> finder.findCell(table, new BasicJTableCellReader()), "The given JTable does not have any selection");
  }
}
