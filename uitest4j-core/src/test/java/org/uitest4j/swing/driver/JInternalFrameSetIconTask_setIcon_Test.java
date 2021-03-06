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
import org.uitest4j.swing.test.swing.TestMdiWindow;

import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.driver.JInternalFrameAction.DEICONIFY;
import static org.uitest4j.swing.driver.JInternalFrameAction.ICONIFY;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link JInternalFrameSetIconTask#setIcon(JInternalFrame, JInternalFrameAction)}.
 *
 * @author Yvonne Wang
 */
public class JInternalFrameSetIconTask_setIcon_Test extends RobotBasedTestCase {
	private JInternalFrame internalFrame;

	@Override
	protected void onSetUp() {
		TestMdiWindow window = TestMdiWindow.createNewWindow(getClass());
		internalFrame = window.internalFrame();
	}

	@Test
	public void should_Iconify_And_Deiconify_JInternalFrame() {
		assertThat(isIcon(internalFrame)).isFalse();
		iconify();
		robot.waitForIdle();
		assertThat(isIcon(internalFrame)).isTrue();
		deiconify();
		robot.waitForIdle();
		assertThat(isIcon(internalFrame)).isFalse();
	}

	@RunsInEDT
	private void iconify() {
		JInternalFrameSetIconTask.setIcon(internalFrame, ICONIFY);
		robot.waitForIdle();
	}

	@RunsInEDT
	private void deiconify() {
		JInternalFrameSetIconTask.setIcon(internalFrame, DEICONIFY);
		robot.waitForIdle();
	}

	@RunsInEDT
	private static boolean isIcon(final JInternalFrame internalFrame) {
		return execute(() -> internalFrame.isIcon() && !internalFrame.isMaximum());
	}
}
