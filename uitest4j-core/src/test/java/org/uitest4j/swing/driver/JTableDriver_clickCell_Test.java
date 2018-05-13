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
import org.uitest4j.swing.data.TableCell;
import org.uitest4j.swing.test.ExpectedException;
import org.uitest4j.swing.test.recorder.ClickRecorder;
import org.uitest4j.swing.test.recorder.ClickRecorderManager;

import static org.uitest4j.swing.core.MouseButton.LEFT_BUTTON;
import static org.uitest4j.swing.data.TableCell.row;

/**
 * Tests for
 * {@link JTableDriver#click(javax.swing.JTable, org.uitest4j.swing.data.TableCell, org.uitest4j.swing.core.MouseButton, int)}
 * .
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTableDriver_clickCell_Test extends JTableDriver_TestCase {
	public ClickRecorderManager clickRecorder = new ClickRecorderManager();

	@Test
	void should_Click_Cell() {
		showWindow();
		ClickRecorder recorder = clickRecorder.attachDirectlyTo(table);
		TableCell cell = row(0).column(1);
		driver.click(table, cell, LEFT_BUTTON, 3);
		recorder.clicked(LEFT_BUTTON).timesClicked(3);
		assertThatCellWasClicked(cell, recorder.pointClicked());
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
