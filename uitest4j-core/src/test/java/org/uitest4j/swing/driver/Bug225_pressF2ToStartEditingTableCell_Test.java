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
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.core.RobotBasedTestCase;

import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.driver.JTableCellValueQuery.cellValueOf;

/**
 * Tests for <a href="http://code.google.com/p/fest/issues/detail?id=225" target="_blank">Bug 225</a>.
 *
 * @author Alex Ruiz
 */
public class Bug225_pressF2ToStartEditingTableCell_Test extends RobotBasedTestCase {
	private JTableTextComponentEditorCellWriter writer;
	private TableDialogEditDemoWindow window;

	@Override
	protected void onSetUp() {
		writer = new JTableTextComponentEditorCellWriter(robot);
		window = TableDialogEditDemoWindow.createNew(getClass());
		robot.showWindow(window, new Dimension(500, 100));
	}

	@Test
	void should_Edit_Cell() {
		int row = 4;
		int col = 3;
		writer.enterValue(window.table, row, col, "8");
		assertThat(valueAt(row, col)).isEqualTo(8);
	}

	@RunsInEDT
	private Object valueAt(int row, int column) {
		return cellValueOf(window.table, row, column);
	}
}
