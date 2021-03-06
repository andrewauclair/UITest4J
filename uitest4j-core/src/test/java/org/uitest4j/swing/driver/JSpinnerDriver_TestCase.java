/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at
  <p>
  http://www.apache.org/licenses/LICENSE-2.0
  <p>
  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.
  <p>
  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.driver;

import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;
import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Arrays.array;
import static org.uitest4j.swing.driver.JSpinnerValueQuery.valueOf;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.test.task.ComponentSetEnabledTask.disable;

/**
 * Base test case for {@link JSpinnerDriver}.
 *
 * @author Alex Ruiz
 */
public abstract class JSpinnerDriver_TestCase extends RobotBasedTestCase {
	MyWindow window;
	JSpinner spinner;
	JSpinnerDriver driver;

	@Override
	protected final void onSetUp() {
		driver = new JSpinnerDriver(robot);
		window = MyWindow.createNew();
		spinner = window.spinner;
		spinner.setName("TestSpinner");
	}

	final void showWindow() {
		robot.showWindow(window);
	}

	@RunsInEDT
	final void assertThatFirstValueIsSelected() {
		assertThatValueIs("Frodo");
	}

	@RunsInEDT
	final void setJLabelAsEditor() {
		setJLabelAsEditorIn(spinner);
		robot.waitForIdle();
	}

	@RunsInEDT
	private static void setJLabelAsEditorIn(final JSpinner spinner) {
		execute(() -> spinner.setEditor(new JLabel()));
	}

	@RunsInEDT
	final void selectLastValue() {
		updateValue("Gandalf");
		robot.waitForIdle();
	}

	@RunsInEDT
	final void updateValue(Object value) {
		setValue(spinner, value);
		robot.waitForIdle();
	}

	@RunsInEDT
	private static void setValue(final JSpinner spinner, final Object value) {
		execute(() -> spinner.setValue(value));
	}

	@RunsInEDT
	final void assertThatLastValueIsSelected() {
		assertThatValueIs("Gandalf");
	}

	@RunsInEDT
	final void assertThatValueIs(Object expected) {
		assertThat(valueOf(spinner)).isEqualTo(expected);
	}

	@RunsInEDT
	final void disableSpinner() {
		disable(spinner);
		robot.waitForIdle();
	}

	private static class MyWindow extends TestWindow {
		final JSpinner spinner = new JSpinner(new SpinnerListModel(array("Frodo", "Sam", "Gandalf")));

		@RunsInEDT
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		private MyWindow() {
			super(JSpinnerDriver_TestCase.class);
			add(spinner);
			setPreferredSize(new Dimension(160, 80));
		}
	}
}
