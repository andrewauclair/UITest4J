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
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.ExpectedException;
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
import static org.uitest4j.swing.test.task.ComponentSetEnabledTask.disable;

/**
 * Tests for {@link JSplitPaneDriver#moveDividerTo(JSplitPane, int)}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JSplitPaneDriver_moveDividerTo_Test extends RobotBasedTestCase {
	private JSplitPaneDriver driver;

	private static Collection<Object[]> orientations() {
		return newArrayList(new Object[][]{{VERTICAL_SPLIT}, {HORIZONTAL_SPLIT}});
	}

	@Override
	protected final void onSetUp() {
		driver = new JSplitPaneDriver(robot);
	}

	@ParameterizedTest
	@MethodSource("orientations")
	void should_Move_Divider_To_Given_Location(int orientation) {
		MyWindow window = createAndShowWindow(orientation);
		int newLocation = window.splitPane.getDividerLocation() + 100;
		driver.moveDividerTo(window.splitPane, newLocation);
		assertThat(window.splitPane.getDividerLocation()).isEqualTo(newLocation);
	}

	@ParameterizedTest
	@MethodSource("orientations")
	void should_Throw_Error_If_JSplitPane_Is_Disabled(int orientation) {
		MyWindow window = createWindow(orientation);
		disable(window.splitPane);
		robot.waitForIdle();
		ExpectedException.assertIllegalStateIsDisabledComponent(() -> driver.moveDividerTo(window.splitPane, 100));
	}

	@ParameterizedTest
	@MethodSource("orientations")
	void should_Throw_Error_If_JSplitPane_Is_Not_Showing_On_The_Screen(int orientation) {
		MyWindow window = createWindow(orientation);
		ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.moveDividerTo(window.splitPane, 100));
	}

	private MyWindow createAndShowWindow(int orientation) {
		MyWindow window = createWindow(orientation);
		robot.showWindow(window);
		return window;
	}

	private MyWindow createWindow(int orientation) {
		return MyWindow.createNew(orientation, getClass());
	}

	private static class MyWindow extends TestWindow {
		final JSplitPane splitPane;

		@RunsInEDT
		static MyWindow createNew(final int orientation, final Class<?> testClass) {
			return execute(() -> new MyWindow(orientation, testClass));
		}

		private MyWindow(int orientation, Class<?> testClass) {
			super(testClass);
			splitPane = new JSplitPane(orientation, new JList(), new JList());
			splitPane.setDividerLocation(150);
			splitPane.setPreferredSize(new Dimension(300, 300));
			add(splitPane);
		}
	}
}
