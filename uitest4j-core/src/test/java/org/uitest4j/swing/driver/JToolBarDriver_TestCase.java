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

import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;
import java.awt.*;

import static java.awt.BorderLayout.NORTH;
import static javax.swing.SwingUtilities.getWindowAncestor;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Base test class for {@link JToolBarDriver}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JToolBarDriver_TestCase extends RobotBasedTestCase {
	MyWindow window;
	JToolBar toolBar;
	JToolBarDriver driver;

	@Override
	protected void onSetUp() {
		driver = new JToolBarDriver(robot);
		window = MyWindow.createNew(getClass());
		toolBar = window.toolBar;
	}

	final void showWindow() {
		robot.showWindow(window);
	}

	@RunsInEDT
	final Window toolBarAncestor() {
		return ancestorOf(toolBar);
	}

	@RunsInEDT
	private static Window ancestorOf(final JToolBar toolBar) {
		return execute(() -> getWindowAncestor(toolBar));
	}

	@RunsInEDT
	final Point whereToFloatTo() {
		Rectangle bounds = boundsOfWindowAncestor(toolBar);
		int x = bounds.x + bounds.width + 10;
		int y = bounds.y + bounds.height + 10;
		return new Point(x, y);
	}

	@RunsInEDT
	private static Rectangle boundsOfWindowAncestor(final JToolBar toolBar) {
		return execute(() -> getWindowAncestor(toolBar).getBounds());
	}

	static class MyWindow extends TestWindow {
		final BorderLayout borderLayout = new BorderLayout();
		final JToolBar toolBar = new JToolBar();

		@RunsInEDT
		static MyWindow createNew(final Class<?> testClass) {
			return execute(() -> new MyWindow(testClass));
		}

		private MyWindow(Class<?> testClass) {
			super(testClass);
			toolBar.setFloatable(true);
			setLayout(borderLayout);
			add(toolBar, NORTH);
			toolBar.add(new JLabel("Hello"));
			setPreferredSize(new Dimension(300, 200));
		}

		@RunsInEDT
		Component componentAt(final String constraint) {
			return execute(() -> borderLayout.getLayoutComponent(constraint));
		}
	}
}
