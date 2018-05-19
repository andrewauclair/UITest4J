/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.driver;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.core.SwingRobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.driver.JTabbedPaneSelectTabQuery.selectedTabIndexOf;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link JTabbedPaneSelectTabQuery#selectedTabIndexOf(JTabbedPane)}.
 *
 * @author Christian Rösch
 */
public class JTabbedPaneSelectTabQuery_selectedTabIndexOf_Test extends SwingRobotBasedTestCase {
	private JTabbedPane tabbedPane;

	@Override
	protected void onSetUp() {
		MyWindow window = MyWindow.createNew();
		tabbedPane = window.tabbedPane;
		JTabbedPaneSelectTabTask.setSelectedTab(tabbedPane, 1);
	}

	@Test
	public void should_Select_Tab_Under_Given_Index() {
		robot.waitForIdle();
		assertThat(selectedTabIndexOf(tabbedPane)).isEqualTo(selectedTabIndex());
	}

	@RunsInEDT
	private int selectedTabIndex() {
		return Objects.requireNonNull(execute(() -> tabbedPane.getSelectedIndex()));
	}

	private static class MyWindow extends TestWindow {
		private static final long serialVersionUID = 1L;
		final JTabbedPane tabbedPane = new JTabbedPane();

		@RunsInEDT
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		private MyWindow() {
			super(JTabbedPaneSelectTabQuery_selectedTabIndexOf_Test.class);
			tabbedPane.addTab("One", new JPanel());
			tabbedPane.addTab("Two", new JPanel());
			add(tabbedPane);
			setPreferredSize(new Dimension(300, 200));
		}
	}
}
