/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.driver;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.test.ExpectedException;

import static org.uitest4j.swing.data.TableCell.row;

/**
 * Tests for {@link JTableDriver#selectCell(javax.swing.JTable, org.uitest4j.swing.data.TableCell)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JTableDriver_selectCell_Test extends JTableDriver_TestCase {
	@Test
	void should_Not_Select_Cell_If_It_Is_Already_Selected() {
		showWindow();
		driver.selectCell(table, row(0).column(0));
		requireCellSelected(0, 0);
		driver.selectCell(table, row(0).column(0));
		requireCellSelected(0, 0);
	}

	@Test
	void should_Throw_Error_If_JTable_Is_Disabled() {
		disableTable();
		robot.settings().clickOnDisabledComponentsAllowed(false);
		ExpectedException.assertIllegalStateIsDisabledComponent(() -> driver.selectCell(table, row(0).column(0)));
	}

	@Test
	void should_Throw_Error_If_JTable_Is_Not_Showing_On_The_Screen() {
		ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.selectCell(table, row(0).column(0)));
	}
}
