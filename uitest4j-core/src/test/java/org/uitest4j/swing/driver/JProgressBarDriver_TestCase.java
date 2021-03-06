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

import javax.swing.*;
import java.awt.*;

import static org.uitest4j.swing.driver.JProgressBarSetIndetermintateTask.setIntedeterminate;
import static org.uitest4j.swing.driver.JProgressBarSetValueTask.setValue;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Base test case for {@link JProgressBarDriver}.
 *
 * @author Alex Ruiz
 */
public abstract class JProgressBarDriver_TestCase extends RobotBasedTestCase {
	JProgressBar progressBar;
	JProgressBarDriver driver;

	@Override
	protected final void onSetUp() {
		driver = new JProgressBarDriver(robot);
		MyWindow window = MyWindow.createNew(getClass());
		progressBar = window.progressBar;
		progressBar.setName("TestProgressBar");
	}

	@RunsInEDT
	final void updateValueTo(int value) {
		setValue(progressBar, value);
		robot.waitForIdle();
	}

	@RunsInEDT
	final void makeIndeterminate() {
		setIntedeterminate(progressBar, true);
		robot.waitForIdle();
	}

	static class MyWindow extends TestWindow {
		final JProgressBar progressBar = new JProgressBar();

		@RunsInEDT
		static MyWindow createNew(final Class<?> testClass) {
			return execute(() -> new MyWindow(testClass));
		}

		private MyWindow(Class<?> testClass) {
			super(testClass);
			progressBar.setValue(60);
			progressBar.setString("60%");
			progressBar.setStringPainted(true);
			add(progressBar);
			setPreferredSize(new Dimension(100, 50));
		}
	}
}
