/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.query;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.core.MethodInvocations;
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link ComponentLocationOnScreenQuery#locationOnScreen(java.awt.Component)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class ComponentLocationOnScreenQuery_locationOnScreen_Test extends RobotBasedTestCase {
	private MyWindow window;

	@Override
	protected void onSetUp() {
		window = MyWindow.createNew();
		robot.showWindow(window);
	}

	@Test
	void should_Return_Component_Location_On_Screen() {
		Point expected = new Point(100, 100);
		window.startRecording();
		assertThat(ComponentLocationOnScreenQuery.locationOnScreen(window)).isEqualTo(expected);
		window.requireInvoked("getLocationOnScreen");
	}

	@Test
	void should_Return_Component_Location_On_Screen2() {
		Point expected = new Point(100, 100);
		window.startRecording();
		assertThat(ComponentLocationOnScreenQuery.locationOnScreen(window)).isEqualTo(expected);
		window.requireInvoked("getLocationOnScreen");
	}

	private static class MyWindow extends TestWindow {
		private boolean recording;
		private final MethodInvocations methodInvocations = new MethodInvocations();

		@RunsInEDT
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		private MyWindow() {
			super(ComponentLocationOnScreenQuery_locationOnScreen_Test.class);
			setPreferredSize(new Dimension(500, 300));
		}

		@Override
		public Point getLocationOnScreen() {
			if (recording) {
				methodInvocations.invoked("getLocationOnScreen");
			}
			return super.getLocationOnScreen();
		}

		void startRecording() {
			recording = true;
		}

		MethodInvocations requireInvoked(String methodName) {
			return methodInvocations.requireInvoked(methodName);
		}
	}
}
