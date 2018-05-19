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
import org.uitest4j.swing.test.core.SwingRobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;

import static org.uitest4j.swing.data.TableCell.row;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Test case for bug <a href="http://jira.codehaus.org/browse/FEST-122" target="_blank">FEST-122</a>.
 *
 * @author Alex Ruiz
 */
public class FEST122_addSupportForEditableJComboBoxAsCellEditor_Test extends SwingRobotBasedTestCase {
	private FrameFixture frame;

	@Override
	protected void onSetUp() {
		frame = new FrameFixture(robot, MyWindow.createNew());
		frame.show();
	}

	@Test
	void should_Enter_Value_In_JTable_Cell_With_Editable_JComboBox_As_Editor() {
		JTableCellFixture cell = frame.table("data").cell(row(0).column(0));
		cell.enterValue("Pink");
		cell.requireValue("Pink");
		cell.enterValue("Blue");
		cell.requireValue("Blue");
	}

	private static class MyWindow extends TestWindow {
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		final JTable table = data();

		private MyWindow() {
			super(FEST122_addSupportForEditableJComboBoxAsCellEditor_Test.class);
			table.setName("data");
			add(new JScrollPane(table));
		}

		private static JTable data() {
			JTable table = new JTable(6, 3);
			table.setPreferredScrollableViewportSize(new Dimension(200, 60));
			setUpColorColumn(table.getColumnModel().getColumn(0));
			return table;
		}

		private static void setUpColorColumn(TableColumn column) {
			JComboBox<String> comboBox = new JComboBox<>();
			comboBox.setEditable(true);
			comboBox.addItem("Blue");
			comboBox.addItem("Red");
			comboBox.addItem("Yellow");
			column.setCellEditor(new DefaultCellEditor(comboBox));
		}
	}
}
