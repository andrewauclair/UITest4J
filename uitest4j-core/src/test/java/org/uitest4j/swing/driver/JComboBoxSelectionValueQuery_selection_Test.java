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
import org.uitest4j.swing.cell.JComboBoxCellReader;
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;
import org.uitest4j.swing.util.Pair;

import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Arrays.array;
import static org.uitest4j.swing.driver.JComboBoxMakeEditableAndSelectItemTask.makeEditableAndSelectIndex;
import static org.uitest4j.swing.driver.JComboBoxMakeEditableAndSelectItemTask.makeEditableAndSelectItem;
import static org.uitest4j.swing.driver.JComboBoxSetSelectedIndexTask.setSelectedIndex;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link JComboBoxSelectionValueQuery#selection(JComboBox, JComboBoxCellReader)}.
 *
 * @author Alex Ruiz
 */
public class JComboBoxSelectionValueQuery_selection_Test extends RobotBasedTestCase {
	private JComboBox comboBox;
	private JComboBoxCellReader cellReader;

	@Override
	protected void onSetUp() {
		MyWindow window = MyWindow.createNew();
		comboBox = window.comboBox;
		cellReader = new BasicJComboBoxCellReader();
	}

	@Test
	public void should_Return_False_And_Null_If_Not_Editable_JComboBox_Does_Not_Have_Selection() {
		Pair<Boolean, String> selection = JComboBoxSelectionValueQuery.selection(comboBox, cellReader);
		assertThat(selection.first).isFalse();
		assertThat(selection.second).isNull();
	}

	@Test
	public void should_Return_True_And_Text_Of_Selected_Item_In_Not_Editable_JComboBox() {
		setSelectedIndex(comboBox, 0);
		robot.waitForIdle();
		Pair<Boolean, String> selection = JComboBoxSelectionValueQuery.selection(comboBox, cellReader);
		assertThatSelectionIsEqual(selection, "first");
	}

	@Test
	public void should_Return_Text_Of_Selected_Item_In_Editable_JComboBox() {
		makeEditableAndSelectIndex(comboBox, 0);
		robot.waitForIdle();
		Pair<Boolean, String> selection = JComboBoxSelectionValueQuery.selection(comboBox, cellReader);
		assertThatSelectionIsEqual(selection, "first");
	}

	@Test
	public void should_Return_Text_Of_Entered_Item_In_Editable_JComboBox() {
		makeEditableAndSelectItem(comboBox, "Hello");
		robot.waitForIdle();
		Pair<Boolean, String> selection = JComboBoxSelectionValueQuery.selection(comboBox, cellReader);
		assertThatSelectionIsEqual(selection, "Hello");
	}

	private void assertThatSelectionIsEqual(Pair<Boolean, String> selection, String expected) {
		assertThat(selection.first).isTrue();
		assertThat(selection.second).isEqualTo(expected);
	}

	private static class MyWindow extends TestWindow {
		final JComboBox comboBox = new JComboBox(array("first", "second", "third"));

		@RunsInEDT
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		private MyWindow() {
			super(JComboBoxSelectionValueQuery_selection_Test.class);
			comboBox.setSelectedIndex(-1);
			addComponents(comboBox);
		}
	}
}
