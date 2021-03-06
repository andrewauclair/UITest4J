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
import org.uitest4j.swing.test.recorder.ClickRecorder;
import org.uitest4j.swing.test.recorder.ClickRecorderManager;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;
import java.awt.*;

import static javax.swing.BoxLayout.Y_AXIS;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Test case for bug <a href="http://jira.codehaus.org/browse/FEST-297" target="_blank">FEST-297</a>.
 *
 * @author Nicolae Bucalaete
 * @author Alex Ruiz
 */
class FEST297_AutoScrollingDoesNotWork_Test extends RobotBasedTestCase {
	private ClickRecorderManager clickRecorder = new ClickRecorderManager();

	private FrameFixture frame;
	private MyWindow window;

	@Override
	protected void onSetUp() {
		window = MyWindow.createNew();
		frame = new FrameFixture(robot, window);
		frame.show();
	}

	@Test
	public void should_Scroll_And_Click_JButton() {
		ClickRecorder recorder = clickRecorder.attachDirectlyTo(window.button);
		frame.button("button").click();
		recorder.wasClicked();
	}

	private static class MyWindow extends TestWindow {
		private final JButton button = new JButton("Click me");

		@RunsInEDT
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		@RunsInCurrentThread
		private MyWindow() {
			super(FEST297_AutoScrollingDoesNotWork_Test.class);
			button.setName("button");
			setLayout(new BoxLayout(getContentPane(), Y_AXIS));
			add(new JScrollPane(topPanel()));
			add(panelWithSize(750, 700));
			setPreferredSize(new Dimension(200, 300));
		}

		private JPanel topPanel() {
			JPanel panel = new JPanel(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			c.gridx = c.gridy = 0;
			panel.add(panelWithSize(300, 600), c);
			c.gridy++;
			panel.add(button, c);
			return panel;
		}

		private static JPanel panelWithSize(int width, int height) {
			JPanel panel = new JPanel();
			panel.setPreferredSize(new Dimension(width, height));
			return panel;
		}
	}
}
