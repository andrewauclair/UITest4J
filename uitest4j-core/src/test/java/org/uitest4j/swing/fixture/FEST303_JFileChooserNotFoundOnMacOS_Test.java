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
import org.uitest4j.swing.annotation.RunsInCurrentThread;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;

import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.timing.Timeout.timeout;

/**
 * Test case for bug <a href="http://jira.codehaus.org/browse/FEST-303" target="_blank">FEST-303</a>.
 *
 * @author Alex Ruiz
 */
public class FEST303_JFileChooserNotFoundOnMacOS_Test extends RobotBasedTestCase {
	private FrameFixture frame;

	@Override
	protected void onSetUp() {
		frame = new FrameFixture(robot, MyWindow.createNew());
		frame.show();
	}

	@Test
	public void should_Find_JFileChooser() {
		frame.button().click();
		frame.fileChooser(timeout(5000));
	}

	private static class MyWindow extends TestWindow {
		final JButton button = new JButton("Click me");
		final JFileChooser chooser = new JFileChooser();

		@RunsInEDT
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		@RunsInCurrentThread
		private MyWindow() {
			super(FEST303_JFileChooserNotFoundOnMacOS_Test.class);
			add(button);
			button.addActionListener(e -> chooser.showOpenDialog(MyWindow.this));
		}
	}
}
