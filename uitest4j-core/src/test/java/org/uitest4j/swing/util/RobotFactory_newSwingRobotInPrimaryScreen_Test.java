/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link RobotFactory#newRobotInPrimaryScreen}.
 *
 * @author Alex Ruiz
 */
class RobotFactory_newSwingRobotInPrimaryScreen_Test {
	private RobotFactory robotFactory;

	@BeforeEach
	void setUp() {
		robotFactory = new RobotFactory();
	}

	@Test
	void shouldCreateNewRobot() throws AWTException {
		Robot last = null;
		for (int i = 0; i < 6; i++) {
			Robot current = robotFactory.newRobotInLeftScreen();
			assertThat(current).isNotNull().isNotSameAs(last);
			last = current;
		}
	}
}
