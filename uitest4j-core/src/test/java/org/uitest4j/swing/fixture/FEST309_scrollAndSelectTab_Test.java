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
import org.uitest4j.swing.timing.Pause;

import javax.swing.*;
import java.awt.*;

import static java.awt.BorderLayout.CENTER;
import static javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT;
import static javax.swing.SwingConstants.BOTTOM;
import static org.assertj.core.util.Strings.concat;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Test case for bug <a href="http://jira.codehaus.org/browse/FEST-309" target="_blank">FEST-309</a>.
 *
 * @author Nicolae Bucalaete
 * @author Alex Ruiz
 */
public class FEST309_scrollAndSelectTab_Test extends RobotBasedTestCase {
	private FrameFixture frame;

	@Override
	protected void onSetUp() {
		MyWindow window = MyWindow.createNew();
		frame = new FrameFixture(robot, window);
		frame.show();
	}

	@Test
	public void should_Scroll_And_Click_Tab() {
		frame.tabbedPane().selectTab(90);
		Pause.pause(10000);
	}

	private static class MyWindow extends TestWindow {
		@RunsInEDT
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		private final JTabbedPane tabbedPane = new JTabbedPane();

		@RunsInCurrentThread
		private MyWindow() {
			super(FEST309_scrollAndSelectTab_Test.class);
			configureTabbedPane();
			setLayout(new BorderLayout());
			add(tabbedPane, CENTER);
			setPreferredSize(new Dimension(300, 100));
		}

		private void configureTabbedPane() {
			tabbedPane.setTabLayoutPolicy(SCROLL_TAB_LAYOUT);
			tabbedPane.setTabPlacement(BOTTOM);
			for (int i = 0; i < 100; i++) {
				tabbedPane.addTab(concat("Tab ", i), new JPanel());
			}
		}
	}
}
