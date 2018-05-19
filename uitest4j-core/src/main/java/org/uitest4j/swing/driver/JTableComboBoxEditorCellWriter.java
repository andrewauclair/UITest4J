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

import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.core.api.swing.SwingRobot;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

import static org.uitest4j.swing.driver.JComboBoxEditableQuery.isEditable;
import static org.uitest4j.swing.driver.JTableStopCellEditingTask.stopEditing;

/**
 * {@link org.uitest4j.swing.cell.JTableCellWriter} that knows how to use {@code JComboBox}es as cell editors.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTableComboBoxEditorCellWriter extends AbstractJTableCellWriter {
	private final JComboBoxDriver driver;

	public JTableComboBoxEditorCellWriter(@Nonnull SwingRobot robot) {
		super(robot);
		driver = new JComboBoxDriver(robot);
	}

	@RunsInEDT
	@Override
	public void enterValue(@Nonnull JTable table, int row, int column, @Nonnull String value) {
		JComboBox<?> editor = doStartCellEditing(table, row, column);
		selectOrType(editor, value);
		stopEditing(table, row, column);
	}

	private void selectOrType(@Nonnull JComboBox<?> editor, @Nonnull String value) {
		boolean selectValue = !isEditable(editor);
		if (!selectValue) {
			selectValue = Arrays.asList(driver.contentsOf(editor)).contains(value);
		}
		if (selectValue) {
			driver.selectItem(editor, value);
			return;
		}
		driver.replaceText(editor, value);
	}

	@Override
	@RunsInEDT
	public void startCellEditing(@Nonnull JTable table, int row, int column) {
		doStartCellEditing(table, row, column);
	}

	@RunsInEDT
	private JComboBox<?> doStartCellEditing(@Nonnull JTable table, int row, int column) {
		Point cellLocation = cellLocation(table, row, column, location());
		robot.click(table, cellLocation); // activate JComboBox editor
		JComboBox<?> comboBox = waitForEditorActivation(table, row, column);
		cellEditor(cellEditor(table, row, column));
		return comboBox;
	}

	@RunsInEDT
	private JComboBox<?> waitForEditorActivation(@Nonnull JTable table, int row, int column) {
		return waitForEditorActivation(table, row, column, JComboBox.class);
	}
}
