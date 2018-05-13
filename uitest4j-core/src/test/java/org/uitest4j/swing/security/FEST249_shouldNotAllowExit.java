/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.security;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.fixture.FrameFixture;
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;

import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.security.NoExitSecurityManagerInstaller.installNoExitSecurityManager;

/**
 * Test case for bug <a href="http://jira.codehaus.org/browse/FEST-249" target="_blank">FEST-249</a>
 *
 * @author Alex Ruiz
 */
public class FEST249_shouldNotAllowExit extends RobotBasedTestCase {
	private FrameFixture frame;
	private NoExitSecurityManagerInstaller noExitSecurityManagerInstaller;

	@Override
	protected void onSetUp() {
		noExitSecurityManagerInstaller = installNoExitSecurityManager();
		MyWindow window = MyWindow.createNew();
		frame = new FrameFixture(robot, window);
		frame.show();
	}

	@Override
	protected void onTearDown() {
		noExitSecurityManagerInstaller.uninstall();
	}

	@Test
	void should_Not_Exit() {
		frame.button().click();
		System.out.println("Test should reach this point");
	}

	private static class MyWindow extends TestWindow {
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		private final JButton button = new JButton("Exit");

		private MyWindow() {
			super(FEST249_shouldNotAllowExit.class);
			button.addActionListener(e -> System.exit(0));
			addComponents(button);
		}
	}
}
