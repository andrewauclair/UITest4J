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

import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;

import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Base test case for {@link JComponentDriver}.
 *
 * @author Alex Ruiz
 */
public class JComponentDriver_TestCase extends RobotBasedTestCase {
	MyWindow window;
	JButton button;
	JComponentDriver driver;

	@Override
	protected final void onSetUp() {
		driver = new JComponentDriver(robot);
		window = MyWindow.createNew(getClass());
		button = window.button;
		button.setName("TestButton");
	}

	static class MyWindow extends TestWindow {
		static MyWindow createNew(final Class<?> testClass) {
			return execute(() -> new MyWindow(testClass));
		}

		final JButton button = new JButton("Click Me");

		private MyWindow(Class<?> testClass) {
			super(testClass);
			button.setToolTipText("A ToolTip");
			addComponents(button);
		}
	}
}