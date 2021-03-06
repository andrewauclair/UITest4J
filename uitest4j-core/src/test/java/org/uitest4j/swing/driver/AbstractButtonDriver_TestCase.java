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
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;
import org.uitest4j.swing.test.task.ComponentSetVisibleTask;

import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.driver.AbstractButtonSelectedQuery.isSelected;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.test.task.AbstractButtonSetArmedTask.setArmed;
import static org.uitest4j.swing.test.task.AbstractButtonSetSelectedTask.setSelected;
import static org.uitest4j.swing.test.task.ComponentSetEnabledTask.disable;

/**
 * Base test case for {@link AbstractButtonDriver}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class AbstractButtonDriver_TestCase extends RobotBasedTestCase {
	AbstractButtonDriver driver;
	MyWindow window;
	JCheckBox checkBox;

	@Override
	protected final void onSetUp() {
		driver = new AbstractButtonDriver(robot);
		window = MyWindow.createNew(getClass());
		checkBox = window.checkBox;
		checkBox.setName("TestCheckBox");
	}

	@RunsInEDT
	final void disableCheckBox() {
		disable(checkBox);
		robot.waitForIdle();
	}

	@RunsInEDT
	final void showWindow() {
		robot.showWindow(window);
	}

	@RunsInEDT
	final void hideWindow() {
		ComponentSetVisibleTask.hide(window);
		robot.waitForIdle();
	}

	@RunsInEDT
	final void assertThatCheckBoxIsNotSelected() {
		assertThat(isSelected(checkBox)).isFalse();
	}

	@RunsInEDT
	final void selectCheckBox() {
		setSelected(checkBox, true);
		robot.waitForIdle();
	}

	@RunsInEDT
	final void unselectCheckBox() {
		setSelected(checkBox, false);
		robot.waitForIdle();
	}

	@RunsInEDT
	final void armCheckBox() {
		setArmed(checkBox, true);
		robot.waitForIdle();
	}

	@RunsInEDT
	final void unarmCheckBox() {
		setArmed(checkBox, false);
		robot.waitForIdle();
	}

	static class MyWindow extends TestWindow {
		@RunsInEDT
		static MyWindow createNew(final Class<?> testClass) {
			return execute(() -> new MyWindow(testClass));
		}

		final JCheckBox checkBox = new JCheckBox("Hello", true);

		private MyWindow(Class<?> testClass) {
			super(testClass);
			add(checkBox);
		}
	}
}
