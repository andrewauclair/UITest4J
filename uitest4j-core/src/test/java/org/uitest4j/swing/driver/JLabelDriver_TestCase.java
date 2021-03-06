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

import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Base test case for {@link JLabelDriver}.
 *
 * @author Yvonne Wang
 */
public abstract class JLabelDriver_TestCase extends RobotBasedTestCase {
	JLabel label;
	JLabelDriver driver;

	@Override
	protected final void onSetUp() {
		driver = new JLabelDriver(robot);
		MyWindow window = MyWindow.createNew(getClass());
		label = window.label;
		label.setName("TestLabel");
	}

	static class MyWindow extends TestWindow {
		final JLabel label = new JLabel("Hi");

		@RunsInEDT
		static MyWindow createNew(final Class<?> testClass) {
			return execute(() -> new MyWindow(testClass));
		}

		private MyWindow(Class<?> testClass) {
			super(testClass);
			add(label);
			setPreferredSize(new Dimension(100, 50));
		}
	}
}
