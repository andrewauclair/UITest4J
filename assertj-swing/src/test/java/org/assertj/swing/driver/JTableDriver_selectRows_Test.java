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

import org.assertj.swing.test.ExpectedException;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link JTableDriver#selectRows(javax.swing.JTable, int...)}.
 *
 * @author Alex Ruiz
 */
class JTableDriver_selectRows_Test extends JTableDriver_TestCase {

  @Test
  void should_Throw_Error_If_Index_Is_Negative() {
    showWindow();
    ExpectedException.assertContainsMessage(IndexOutOfBoundsException.class, () -> driver.selectRows(table, -1), "row <-1> should be between <0> and <9>");
  }

  @Test
  void should_Throw_Error_If_Index_Is_Equal_To_The_Number_Of_Rows() {
    showWindow();
    ExpectedException.assertContainsMessage(IndexOutOfBoundsException.class, () -> driver.selectRows(table, 10), "row <10> should be between <0> and <9>");
  }

  @Test
  void should_Select_Rows() {
    enableMultipleSelection();
    showWindow();
    driver.selectRows(table, 0, 2);
    requireCellSelected(0, 0);
    requireCellSelected(2, 0);
  }

  @Test
  void should_Select_Row() {
    enableMultipleSelection();
    showWindow();
    driver.selectRows(table, 0);
    requireCellSelected(0, 0);
  }

  @Test
  void should_Throw_Error_If_JTable_Is_Disabled() {
    disableTable();
    robot.settings().clickOnDisabledComponentsAllowed(false);
    ExpectedException.assertIllegalStateIsDisabledComponent(() -> driver.selectRows(table, 0, 2));
  }

  @Test
  void should_Throw_Error_If_JTable_Is_Not_Showing_On_The_Screen() {
    ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.selectRows(table, 0, 2));
  }
}
