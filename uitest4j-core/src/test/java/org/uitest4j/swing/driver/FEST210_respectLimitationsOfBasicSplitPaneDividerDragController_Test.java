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

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;
import static javax.swing.JSplitPane.VERTICAL_SPLIT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.query.ComponentSizeQuery.sizeOf;

/**
 * Test case for bug <a href="http://jira.codehaus.org/browse/FEST-210" target="_blank">FEST-210</a>
 *
 * @author Alex Ruiz
 */
class FEST210_respectLimitationsOfBasicSplitPaneDividerDragController_Test extends RobotBasedTestCase {
	private static final int MINIMUM_SIZE = 20;

	private JSplitPaneDriver driver;

	private static Collection<Object[]> orientations() {
		return newArrayList(new Object[][]{{VERTICAL_SPLIT}, {HORIZONTAL_SPLIT}});
	}

	@Override
	protected void onSetUp() {
		driver = new JSplitPaneDriver(robot);
	}

	@ParameterizedTest
	@MethodSource("orientations")
	void should_Respect_Minimum_Size_When_Moving_Divider(int orientation) {
		MyWindow window = MyWindow.createNew(orientation);
		robot.showWindow(window);

		driver.moveDividerTo(window.splitPane, 30);
		driver.moveDividerTo(window.splitPane, 10);
		assertThatIsGreaterOrLessThanMinimumSize(sizeOf(window.leftPanel));
	}

	private void assertThatIsGreaterOrLessThanMinimumSize(Dimension actual) {
		assertThat(actual.width).isGreaterThanOrEqualTo(MINIMUM_SIZE);
		assertThat(actual.height).isGreaterThanOrEqualTo(MINIMUM_SIZE);
	}

	private static class MyWindow extends TestWindow {
		static MyWindow createNew(final int orientation) {
			return execute(() -> new MyWindow(orientation));
		}

		final JPanel leftPanel = newPanel();
		final JSplitPane splitPane = new JSplitPane(VERTICAL_SPLIT, leftPanel, newPanel());

		MyWindow(int orientation) {
			super(FEST210_respectLimitationsOfBasicSplitPaneDividerDragController_Test.class);
			splitPane.setOrientation(orientation);
			splitPane.setPreferredSize(new Dimension(100, 100));
			addComponents(splitPane);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setPreferredSize(new Dimension(200, 200));
		}

		private static JPanel newPanel() {
			JPanel panel = new JPanel();
			panel.setMinimumSize(new Dimension(MINIMUM_SIZE, MINIMUM_SIZE));
			return panel;
		}
	}
}
