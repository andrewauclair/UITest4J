/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.testing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.core.BasicRobot;
import org.uitest4j.swing.core.Robot;
import org.uitest4j.swing.hierarchy.ExistingHierarchy;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link AssertJSwingTestCaseTemplate#setUpRobot()} and {@link AssertJSwingTestCaseTemplate#cleanUp()}.
 *
 * @author Alex Ruiz
 */
class AssertJSwingTestCaseTemplate_setUpRobot_cleanUp_Test {
	private TestCase testCase;

	@BeforeEach
	void setUp() {
		testCase = new TestCase();
	}

	@Test
	void should_Create_Robot_On_SetUp_And_Inactivate_It_On_CleanUp() {
		testCase.setUpRobot();
		Robot robot = testCase.robot();
		assertThat(robot).isInstanceOf(BasicRobot.class);
		assertThat(robot.hierarchy()).isInstanceOf(ExistingHierarchy.class);
		assertThat(robot.isActive()).isTrue();
		testCase.cleanUp();
		assertThat(robot.isActive()).isFalse();
	}

	private static class TestCase extends AssertJSwingTestCaseTemplate {
	}
}
