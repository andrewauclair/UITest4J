/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at
  <p>
  http://www.apache.org/licenses/LICENSE-2.0
  <p>
  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.
  <p>
  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.driver;

import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.edt.GuiActionRunner;
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;
import java.awt.*;

import static java.lang.String.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Strings.concat;
import static org.uitest4j.swing.driver.JInternalFrameAction.ICONIFY;
import static org.uitest4j.swing.driver.JInternalFrameSetIconTask.setIcon;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.test.task.ComponentSetEnabledTask.disable;

/**
 * Base test case for {@link JInternalFrameDriver}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class JInternalFrameDriver_TestCase extends RobotBasedTestCase {
	MyWindow window;
	JInternalFrame internalFrame;
	JDesktopPane desktopPane;
	JInternalFrameDriver driver;

	@Override
	protected final void onSetUp() {
		driver = new JInternalFrameDriver(robot);
		window = MyWindow.createNew();
		internalFrame = window.internalFrame;
		GuiActionRunner.execute(() -> internalFrame.setName("TestInternalFrame"));
		desktopPane = window.desktopPane;
	}

	@RunsInEDT
	final void disableInternalFrame() {
		showWindow();
		disable(internalFrame);
		robot.waitForIdle();
	}

	final void showWindow() {
		robot.showWindow(window);
	}

	@RunsInEDT
	final void iconify() {
		setIcon(internalFrame, ICONIFY);
		robot.waitForIdle();
	}

	@RunsInEDT
	final void assertThatIsMaximized() {
		assertThat(isMaximized()).isTrue();
	}

	@RunsInEDT
	final boolean isMaximized() {
		return isMaximized(internalFrame);
	}

	@RunsInEDT
	private static boolean isMaximized(final JInternalFrame internalFrame) {
		return execute(internalFrame::isMaximum);
	}

	@RunsInEDT
	final int zOrder() {
		return zOrder(desktopPane, internalFrame);
	}

	@RunsInEDT
	private static int zOrder(final JDesktopPane desktopPane, final JInternalFrame internalFrame) {
		return execute(() -> desktopPane.getComponentZOrder(internalFrame));
	}

	static class MyWindow extends TestWindow {
		final JDesktopPane desktopPane;
		final JInternalFrame internalFrame;

		@RunsInEDT
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		private MyWindow() {
			super(JInternalFrameDriver_TestCase.class);
			MyInternalFrame.resetIndex();
			desktopPane = new JDesktopPane();
			internalFrame = new MyInternalFrame();
			addInternalFrames();
			setContentPane(desktopPane);
			setPreferredSize(new Dimension(400, 300));
		}

		private void addInternalFrames() {
			addInternalFrame(new MyInternalFrame());
			addInternalFrame(internalFrame);
		}

		private void addInternalFrame(JInternalFrame f) {
			desktopPane.add(f);
			f.toFront();
			f.setVisible(true);
		}
	}

	private static class MyInternalFrame extends JInternalFrame {
		private static int index;

		static void resetIndex() {
			index = 0;
		}

		MyInternalFrame() {
			super(concat("Internal Frame ", valueOf(index++)), true, true, true, true);
			setSize(200, 100);
		}
	}
}
