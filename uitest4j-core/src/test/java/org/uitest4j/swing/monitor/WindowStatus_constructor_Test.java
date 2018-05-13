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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.util.RobotFactory;

import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.uitest4j.swing.monitor.TestWindows.singletonWindowsMock;
import static org.uitest4j.swing.util.TestRobotFactories.newRobotFactoryMock;

/**
 * Tests for {@link WindowStatus#WindowStatus(Windows, org.uitest4j.swing.util.RobotFactory)}.
 *
 * @author Alex Ruiz
 */
class WindowStatus_constructor_Test {
	private RobotFactory factory;
	private Windows windows;

	@BeforeEach
	void setUp() {
		factory = newRobotFactoryMock();
		windows = singletonWindowsMock();
	}

	@Test
	void should_Have_Null_Robot_If_Robot_Cannot_Be_Created() throws AWTException {
		when(factory.newRobotInLeftScreen()).thenThrow(new AWTException("Thrown on purpose"));
		WindowStatus status = new WindowStatus(windows, factory);
		assertThat(status.robot).isNull();
	}

}
