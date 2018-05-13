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

import org.junit.jupiter.api.function.Executable;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.ExpectedException;
import org.uitest4j.swing.test.core.MethodInvocations;
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.annotation.Nonnull;
import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Arrays.array;
import static org.uitest4j.swing.driver.JComboBoxMakeEditableAndSelectItemTask.makeEditableAndSelectItem;
import static org.uitest4j.swing.driver.JComboBoxSetEditableTask.setEditable;
import static org.uitest4j.swing.driver.JComboBoxSetSelectedIndexTask.setSelectedIndex;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.test.query.JComboBoxSelectedItemQuery.selectedItemOf;
import static org.uitest4j.swing.test.task.ComponentSetEnabledTask.disable;

/**
 * Base test case for {@link JComboBoxDriver}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class JComboBoxDriver_TestCase extends RobotBasedTestCase {
	JComboBoxCellReaderStub cellReader;
	JComboBox comboBox;
	JComboBoxDriver driver;
	MyWindow window;

	@Override
	protected final void onSetUp() {
		cellReader = new JComboBoxCellReaderStub();
		driver = new JComboBoxDriver(robot);
		driver.replaceCellReader(cellReader);
		window = MyWindow.createNew(getClass());
		comboBox = window.comboBox;
		comboBox.setName("TestComboBox");
	}

	final void showWindow() {
		robot.showWindow(window);
	}

	@RunsInEDT
	final void select(int index) {
		setSelectedIndex(comboBox, index);
		robot.waitForIdle();
	}

	@RunsInEDT
	final void assertThatSelectedItemIs(String expected) {
		assertThat(selectedItemOf(comboBox)).isEqualTo(expected);
	}

	@RunsInEDT
	final void makeEditableAndSelect(Object itemToSelect) {
		makeEditableAndSelectItem(comboBox, itemToSelect);
		robot.waitForIdle();
	}

	@RunsInEDT
	final void clearSelection() {
		setSelectedIndex(comboBox, (-1));
		robot.waitForIdle();
	}

	@RunsInEDT
	final void selectFirstItem() {
		setSelectedIndex(comboBox, 0);
		robot.waitForIdle();
	}

	@RunsInEDT
	final void disableComboBox() {
		disable(comboBox);
		robot.waitForIdle();
	}

	final void assertThatIllegalStateExceptionCauseIsNotEditableComboBox(Executable executable) {
		ExpectedException.assertContainsMessage(IllegalStateException.class, executable, "Expecting component", "to be editable");
	}

	@RunsInEDT
	final void makeEditableAndSelectFirstItem() {
		setEditableAndSelectFirstItem(comboBox, true);
		robot.waitForIdle();
	}

	@RunsInEDT
	private static void setEditableAndSelectFirstItem(final JComboBox comboBox, final boolean editable) {
		execute(() -> {
			comboBox.setSelectedIndex(0);
			comboBox.setEditable(editable);
		});
	}

	@RunsInEDT
	static String textIn(final JComboBox comboBox) {
		return execute(() -> {
			Component editor = comboBox.getEditor().getEditorComponent();
			if (editor instanceof JLabel) {
				return ((JLabel) editor).getText();
			}
			if (editor instanceof JTextComponent) {
				return ((JTextComponent) editor).getText();
			}
			return null;
		});
	}

	@RunsInEDT
	final void makeEditable() {
		setEditable(comboBox, true);
		robot.waitForIdle();
	}

	final void assertThatCellReaderWasCalled() {
		cellReader.requireInvoked("valueAt");
	}

	static class MyWindow extends TestWindow {
		final JComboBox comboBox = new JComboBox(array("first", "second", "third"));

		@RunsInEDT
		static MyWindow createNew(final @Nonnull Class<?> testClass) {
			return execute(() -> new MyWindow(testClass));
		}

		private MyWindow(@Nonnull Class<?> testClass) {
			super(testClass);
			add(comboBox);
		}
	}

	static class JComboBoxCellReaderStub extends BasicJComboBoxCellReader {
		private final MethodInvocations methodInvocations = new MethodInvocations();

		JComboBoxCellReaderStub() {
		}

		@Override
		public String valueAt(@Nonnull JComboBox<?> comboBox, int index) {
			methodInvocations.invoked("valueAt");
			return super.valueAt(comboBox, index);
		}

		MethodInvocations requireInvoked(String methodName) {
			return methodInvocations.requireInvoked(methodName);
		}
	}
}
