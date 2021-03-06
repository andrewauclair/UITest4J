/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.fixture;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.List;

import static java.awt.event.InputEvent.CTRL_DOWN_MASK;
import static java.awt.event.InputEvent.SHIFT_DOWN_MASK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.uitest4j.swing.data.TableCell.row;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Test case for bug <a href="http://jira.codehaus.org/browse/FEST-130" target="_blank">FEST-130</a>.
 *
 * @author neals01
 * @author Alex Ruiz
 */
public class FEST130_wrongModifierBehaviorInJTableFixture_Test extends RobotBasedTestCase {
	private JTableFixture table;

	@Override
	protected void onSetUp() {
		robot.showWindow(MyWindow.createNew());
		table = new JTableFixture(robot, "Table");
	}

	@Test
	void should_Use_Control_Modifier() {
		table.cell(row(1).column(0)).click();
		assertThatSelectedRowsAre(1);
		robot.pressModifiers(CTRL_DOWN_MASK);
		table.cell(row(3).column(0)).click();
		table.cell(row(7).column(0)).click();
		robot.releaseModifiers(CTRL_DOWN_MASK);
		assertThatSelectedRowsAre(1, 3, 7);
	}

	@Test
	void should_Use_Shift_Modifier() {
		table.cell(row(1).column(0)).click();
		assertThatSelectedRowsAre(1);
		robot.pressModifiers(SHIFT_DOWN_MASK);
		table.cell(row(4).column(0)).click();
		robot.releaseModifiers(SHIFT_DOWN_MASK);
		assertThatSelectedRowsAre(1, 2, 3, 4);
	}

	@RunsInEDT
	private void assertThatSelectedRowsAre(int... rows) {
		int[] selectedRows = selectedRowsInTable();
		assertThat(selectedRows.length).isEqualTo(rows.length);
		for (int i = 0; i < rows.length; i++) {
			assertThat(selectedRows[i]).isEqualTo(rows[i]);
		}
	}

	@RunsInEDT
	private int[] selectedRowsInTable() {
		return selectedRowsIn(table.target());
	}

	@RunsInEDT
	private static int[] selectedRowsIn(final JTable table) {
		return execute(table::getSelectedRows);
	}

	private static class MyWindow extends TestWindow {
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		final JTable table = new JTable();

		private MyWindow() {
			super(FEST130_wrongModifierBehaviorInJTableFixture_Test.class);
			table.setName("Table");
			List<String> values = newArrayList();
			for (int i = 0; i < 15; i++) {
				values.add("AB" + i);
			}
			table.setModel(new MyTableModel(values));
			addComponents(table);
		}
	}

	private static class MyTableModel extends AbstractTableModel {
		private final List<String> values;

		MyTableModel(List<String> values) {
			this.values = values;
		}

		@Override
		public String getColumnName(int col) {
			return "Col" + col;
		}

		@Override
		public int getColumnCount() {
			return 3;
		}

		@Override
		public Object getValueAt(int row, int col) {
			String val = values.get(row);
			if (val == null) {
				return "";
			}
			return Character.toString(val.charAt(col));
		}

		@Override
		public boolean isCellEditable(int row, int col) {
			return false;
		}

		@Override
		public int getRowCount() {
			return values.size();
		}
	}
}
