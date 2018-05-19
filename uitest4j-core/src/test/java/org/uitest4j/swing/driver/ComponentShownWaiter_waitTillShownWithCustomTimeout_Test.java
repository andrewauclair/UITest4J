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
import org.uitest4j.swing.exception.WaitTimedOutError;
import org.uitest4j.swing.test.core.SwingRobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;
import org.uitest4j.swing.test.util.StopWatch;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.uitest4j.swing.test.util.StopWatch.startNewStopWatch;
import static org.uitest4j.swing.timing.Pause.pause;

/**
 * Tests for {@link ComponentShownWaiter#waitTillShown(java.awt.Component, long)}.
 *
 * @author Alex Ruiz
 */
public class ComponentShownWaiter_waitTillShownWithCustomTimeout_Test extends SwingRobotBasedTestCase {
	private TestWindow window;

	@Override
	protected void onSetUp() {
		window = TestWindow.createNewWindow(getClass());
	}

	@Test
	void should_Timeout_If_Component_Never_Shown() {
		StopWatch stopWatch = startNewStopWatch();
		int timeout = 500;
		try {
			assertThrows(WaitTimedOutError.class, () -> ComponentShownWaiter.waitTillShown(window, timeout));
		}
		finally {
			stopWatch.stop();
			assertThat(stopWatch.ellapsedTime()).isGreaterThanOrEqualTo(timeout);
		}
	}

	@Test
	void should_Wait_Till_Component_Is_Shown() {
		StopWatch stopWatch = startNewStopWatch();
		int timeout = 10000;
		new Thread(() -> {
			pause(1000);
			robot.showWindow(window);
		}).start();
		ComponentShownWaiter.waitTillShown(window, timeout);
		stopWatch.stop();
		assertThat(stopWatch.ellapsedTime()).isLessThan(timeout);
	}

	@Test
	void should_Not_Wait_If_Component_Is_Already_Shown() {
		robot.showWindow(window);
		StopWatch stopWatch = startNewStopWatch();
		ComponentShownWaiter.waitTillShown(window, 10000);
		stopWatch.stop();
		assertThat(stopWatch.ellapsedTime()).isLessThan(10);
	}
}
