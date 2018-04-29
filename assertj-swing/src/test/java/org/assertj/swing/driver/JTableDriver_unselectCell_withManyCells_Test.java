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
package org.assertj.swing.driver;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;

import static org.assertj.core.util.Lists.newArrayList;
import static org.uitest4j.swing.data.TableCell.row;

/**
 * Tests for {@link JTableDriver#unselectCell(javax.swing.JTable, org.uitest4j.swing.data.TableCell)}.
 *
 * @author Christian Rösch
 */
class JTableDriver_unselectCell_withManyCells_Test extends JTableDriver_TestCase {
  private static Collection<Object[]> cells() {
    return newArrayList(tableCells());
  }

  @ParameterizedTest
  @MethodSource("cells")
  void should_Unselect_Cell(int row, int column) {
    showWindow();
    driver.selectCell(table, row(row).column(column));
    requireCellSelected(row, column);
    driver.unselectCell(table, row(row).column(column));
    requireCellNotSelected(row, column);
  }
}
