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

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link JTabbedPaneTabTitlesQuery#tabTitlesOf(JTabbedPane)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTabbedPaneTabTitlesQuery_tabTitlesOf_Test extends RobotBasedTestCase {
	private JTabbedPane tabbedPane;

	@Override
	protected void onSetUp() {
		MyWindow window = MyWindow.createNew();
		tabbedPane = window.tabbedPane;
	}

	@Test
	public void should_Return_Tab_Titles() {
		assertThat(JTabbedPaneTabTitlesQuery.tabTitlesOf(tabbedPane)).containsOnly("One", "Two");
	}

	private static class MyWindow extends TestWindow {
		final JTabbedPane tabbedPane = new JTabbedPane();

		@RunsInEDT
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		private MyWindow() {
			super(JTabbedPaneTabTitlesQuery_tabTitlesOf_Test.class);
			tabbedPane.addTab("One", new JPanel());
			tabbedPane.addTab("Two", new JPanel());
			addComponents(tabbedPane);
		}
	}
}
