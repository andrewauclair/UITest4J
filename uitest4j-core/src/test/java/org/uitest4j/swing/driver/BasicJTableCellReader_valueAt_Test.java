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
import org.uitest4j.swing.annotation.RunsInCurrentThread;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.swing.CustomCellRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Arrays.array;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link BasicJTableCellReader#valueAt(JTable, int, int)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class BasicJTableCellReader_valueAt_Test extends BasicJTableCellReader_TestCase {
	@Test
	void should_Return_ToString_From_Model_If_CellRenderer_Is_Not_Recognized() {
		setModelData(table, new Object[][]{array(new Jedi("Yoda"))}, array("Names"));
		setNotRecognizedCellRendererTo(table);
		robot.waitForIdle();
		String value = valueAt(reader, table, 0, 0);
		assertThat(value).isEqualTo("Yoda");
	}

	@RunsInEDT
	private static void setModelData(final JTable table, final Object[][] data, final Object[] columnNames) {
		execute(() -> {
			DefaultTableModel model = new DefaultTableModel(data, columnNames);
			table.setModel(model);
		});
	}

	@RunsInEDT
	private static void setNotRecognizedCellRendererTo(final JTable table) {
		execute(() -> setCellRendererComponent(table, new JToolBar()));
	}

	@Test
	void should_Return_Text_From_CellRenderer_If_It_Is_JLabel() {
		setJLabelAsCellRenderer();
		String value = valueAt(reader, table, 0, 0);
		assertThat(value).isEqualTo("Hello");
	}

	@Test
	void should_Return_Selection_From_CellRenderer_If_It_Is_JComboBox() {
		setJComboBoxAsCellRendererWithSelection(1);
		robot.waitForIdle();
		String value = valueAt(reader, table, 0, 0);
		assertThat(value).isEqualTo("Two");
	}

	@Test
	void should_Return_Null_If_CellRenderer_Is_JComboBox_Without_Selection() {
		setJComboBoxAsCellRendererWithSelection(-1);
		robot.waitForIdle();
		String value = valueAt(reader, table, 0, 0);
		assertThat(value).isNull();
	}

	@RunsInEDT
	private void setJComboBoxAsCellRendererWithSelection(int itemIndex) {
		setJComboBoxAsCellRenderer(table, itemIndex);
		robot.waitForIdle();
	}

	@RunsInEDT
	private static void setJComboBoxAsCellRenderer(final JTable table, final int itemIndex) {
		execute(() -> {
			JComboBox comboBox = new JComboBox(array("One", "Two"));
			comboBox.setSelectedIndex(itemIndex);
			setCellRendererComponent(table, comboBox);
		});
	}

	@Test
	void should_Return_Selection_From_CellRenderer_If_It_Is_JCheckBox() {
		setJCheckBoxAsCellRenderer(table, "Hello", true);
		robot.waitForIdle();
		String value = valueAt(reader, table, 0, 0);
		assertThat(value).isEqualTo("true");
	}

	@RunsInEDT
	private static void setJCheckBoxAsCellRenderer(final JTable table, final String text, final boolean selected) {
		execute(() -> {
			JCheckBox checkBox = new JCheckBox(text, selected);
			setCellRendererComponent(table, checkBox);
		});
	}

	@RunsInCurrentThread
	private static void setCellRendererComponent(JTable table, Component renderer) {
		CustomCellRenderer cellRenderer = new CustomCellRenderer(renderer);
		table.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);
	}

	@RunsInEDT
	private static String valueAt(final BasicJTableCellReader reader, final JTable table, final int row, final int column) {
		return execute(() -> reader.valueAt(table, row, column));
	}
}
