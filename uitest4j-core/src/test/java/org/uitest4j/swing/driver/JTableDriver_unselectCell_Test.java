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

import static org.uitest4j.swing.data.TableCell.row;

import org.uitest4j.swing.test.ExpectedException;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link JTableDriver#unselectCell(javax.swing.JTable, org.uitest4j.swing.data.TableCell)}.
 *
 * @author Christian Rösch
 */
class JTableDriver_unselectCell_Test extends JTableDriver_TestCase {
  @Test
  void should_Not_Unselect_Cell_If_It_Is_Already_Unselected() {
    showWindow();
    driver.unselectCell(table, row(0).column(0));
    requireCellNotSelected(0, 0);
  }

  @Test
  void should_Throw_Error_If_JTable_Is_Disabled() {
    disableTable();
    robot.settings().clickOnDisabledComponentsAllowed(false);
    ExpectedException.assertIllegalStateIsDisabledComponent(() -> driver.unselectCell(table, row(0).column(0)));
  }

  @Test
  void should_Throw_Error_If_JTable_Is_Not_Showing_On_The_Screen() {
    ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.unselectCell(table, row(0).column(0)));
  }
}
