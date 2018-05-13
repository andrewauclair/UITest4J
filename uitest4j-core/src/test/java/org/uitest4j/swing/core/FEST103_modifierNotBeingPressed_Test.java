/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.core;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.fixture.FrameFixture;
import org.uitest4j.swing.fixture.JOptionPaneFixture;
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;
import java.awt.*;

import static java.awt.event.InputEvent.CTRL_DOWN_MASK;
import static java.awt.event.InputEvent.SHIFT_DOWN_MASK;
import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_M;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.KeyStroke.getKeyStroke;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.finder.JOptionPaneFinder.findOptionPane;

/**
 * Tests for bug <a href="http://jira.codehaus.org/browse/FEST-103" target="_blank">FEST_103</a>.
 *
 * @author Alex Ruiz
 */
class FEST103_modifierNotBeingPressed_Test extends RobotBasedTestCase {
	private FrameFixture frameFixture;
	private MyWindow window;

	@Override
	protected void onSetUp() {
		window = MyWindow.createNew();
		frameFixture = new FrameFixture(robot, window);
		frameFixture.show();
	}

	@Test
	void should_Press_Key_And_Modifier() {
		frameFixture.moveToFront(); // ensure the window is active
		robot.pressAndReleaseKey(VK_M, CTRL_DOWN_MASK);
		JOptionPaneFixture optionPane = findOptionPane().using(robot);
		optionPane.requireInformationMessage().requireMessage("Hello World");
	}

	@Test
	void should_Press_Shift_As_Modifier() {
		frameFixture.moveToFront();
		robot.focus(window.textField);
		robot.pressAndReleaseKey(VK_A, SHIFT_DOWN_MASK);
		frameFixture.textBox().requireText("A");
	}

	private static class MyWindow extends TestWindow {
		@RunsInEDT
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		final JTextField textField = new JTextField(5);

		private MyWindow() {
			super(FEST103_modifierNotBeingPressed_Test.class);
			setJMenuBar(menuBar());
			addComponents(textField);
			setPreferredSize(new Dimension(200, 100));
		}

		private JMenuBar menuBar() {
			JMenuBar menuBar = new JMenuBar();
			JMenu viewMenu = new JMenu("View");
			JMenuItem viewMessageMenu = new JMenuItem("Message");
			viewMessageMenu.setAccelerator(getKeyStroke(VK_M, CTRL_DOWN_MASK));
			viewMessageMenu.addActionListener(e -> showMessageDialog(MyWindow.this, "Hello World", "My Message", INFORMATION_MESSAGE));
			viewMenu.add(viewMessageMenu);
			menuBar.add(viewMenu);
			return menuBar;
		}
	}
}
