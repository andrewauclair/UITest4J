/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.monitor;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.test.core.SequentialEDTSafeTestCase;
import org.uitest4j.swing.test.swing.TestWindow;
import org.uitest4j.swing.timing.Condition;
import org.uitest4j.swing.util.RobotFactory;

import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.uitest4j.swing.monitor.TestWindows.newWindowsMock;
import static org.uitest4j.swing.monitor.WindowMetrics.absoluteCenterOf;
import static org.uitest4j.swing.query.ComponentSizeQuery.sizeOf;
import static org.uitest4j.swing.timing.Pause.pause;
import static org.uitest4j.swing.timing.Timeout.timeout;

/**
 * Tests for {@link WindowStatus#checkIfReady(Window)}.
 *
 * @author Alex Ruiz
 */
public class WindowStatus_checkIfReady_Test extends SequentialEDTSafeTestCase {
	private TestWindow window;
	private WindowStatus status;
	private Windows windows;

	@Override
	protected final void onSetUp() {
		window = TestWindow.createNewWindow(getClass());
		windows = newWindowsMock();
		status = new WindowStatus(windows);
	}

	@Override
	protected final void onTearDown() {
		window.destroy();
	}

	@Test
	void should_Move_Mouse_To_Center_Of_Frame_If_Width_Is_Greater_Than_Height() {
		window.display(new Dimension(300, 100));
		pause(500);
		Point center = absoluteCenterOf(window);
		center.x += WindowStatus.sign();
		when(windows.isShowingButNotReady(window)).thenReturn(true);
		status.checkIfReady(window);
		assertThat(MouseInfo.getPointerInfo().getLocation()).isEqualTo(center);
	}

	@Test
	void should_Move_Mouse_To_Center_Of_Frame_If_Height_Is_Greater_Than_Width() {
		window.display(new Dimension(200, 400));
		pause(500);
		Point center = absoluteCenterOf(window);
		center.y += WindowStatus.sign();
		when(windows.isShowingButNotReady(window)).thenReturn(true);
		status.checkIfReady(window);
		assertThat(MouseInfo.getPointerInfo().getLocation()).isEqualTo(center);
	}

	@Test
	void should_Resize_Window_To_Receive_Events() {
		// TODO: test in Windows
		window.display(new Dimension(2, 2));
		final Dimension original = sizeOf(window);
		when(windows.isShowingButNotReady(window)).thenReturn(true);
		status.checkIfReady(window);
		pause(new Condition("Frame to be resized") {
			@Override
			public boolean test() {
				return sizeOf(window).height > original.height;
			}
		}, timeout(5000));
	}

	@Test
	void should_Not_Check_If_Frame_Is_Ready_If_Robot_Is_Null() throws AWTException {
		final RobotFactory factory = mock(RobotFactory.class);
		Point before = MouseInfo.getPointerInfo().getLocation();
		when(factory.newRobotInLeftScreen()).thenReturn(null);
		status = new WindowStatus(windows, factory);
		status.checkIfReady(window);
		// mouse pointer should not have moved
		assertThat(MouseInfo.getPointerInfo().getLocation()).isEqualTo(before);
	}
}
