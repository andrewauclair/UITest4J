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
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestMdiWindow;

import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link JInternalFrameCloseTask#close(JInternalFrame)}.
 *
 * @author Yvonne Wang
 */
public class JInternalFrameCloseTask_close_Test extends RobotBasedTestCase {
	private JInternalFrame internalFrame;

	@Override
	protected void onSetUp() {
		TestMdiWindow window = TestMdiWindow.createNewWindow(getClass());
		internalFrame = window.internalFrame();
		robot.showWindow(window);
		assertThat(window.internalFrame().isVisible()).isTrue();
	}

	@Test
	public void should_Close_JInternalFrame() {
		JInternalFrameCloseTask.close(internalFrame);
		robot.waitForIdle();
		assertThat(internalFrame.isVisible()).isFalse();
	}
}
