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

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.uitest4j.core.api.swing.SwingRobot;
import org.uitest4j.swing.edt.GuiActionRunner;
import org.uitest4j.swing.test.core.SwingRobotBasedTestCase;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Test for <a href="https://github.com/joel-costigliola/assertj-swing/issues/190">github.com - assertj-swing #190</a>
 */
class GitHub_190_FrameFixture_Test extends SwingRobotBasedTestCase {
	private static Collection<Object[]> data() {
		List<Object[]> list = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			list.add(new Object[]{i});
		}
		return list;
	}

	@ParameterizedTest
	@MethodSource("data")
	void test(int in) {
		setup(robot);
		FrameFixture fixture = new FrameFixture(robot, GuiActionRunner.execute(
				MainFrame::new));
		fixture.show();
		fixture.textBox().enterText("Acao");
		fixture.textBox().requireText("Acao");
		fixture.button().click();
		fixture.textBox().requireText("Text: Acao");
		fixture.close();
	}

	public void setup(SwingRobot robot) {
		robot.settings().delayBetweenEvents(100);
		robot.settings().dragDelay(0);
		robot.settings().dropDelay(0);
		robot.settings().eventPostingDelay(0);
		robot.settings().idleTimeout(0);
	}

	public static class MainFrame extends JFrame {

		private static final long serialVersionUID = 1L;

		public JButton button;

		public JTextField text;

		MainFrame() {
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			add(createContentPanel());
			setSize(200, 100);
			setLocationRelativeTo(null);
		}

		private JPanel createContentPanel() {
			JPanel panel = new JPanel(new FlowLayout());
			panel.add(button = createButton());
			panel.add(text = new JTextField(10));
			return panel;
		}

		private JButton createButton() {
			JButton button = new JButton("Teste");
			button.addActionListener(e -> text.setText("Text: " + text.getText()));
			return button;
		}
	}
}