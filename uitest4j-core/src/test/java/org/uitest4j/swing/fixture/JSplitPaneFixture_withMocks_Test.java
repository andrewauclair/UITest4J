/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.fixture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uitest4j.core.Robot;
import org.uitest4j.swing.driver.JSplitPaneDriver;

import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link JSplitPaneFixture}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class JSplitPaneFixture_withMocks_Test {
	private JSplitPaneFixture fixture;

	@BeforeEach
	void setUp() {
		fixture = new JSplitPaneFixture(mock(Robot.class), mock(JSplitPane.class));
		fixture.replaceDriverWith(mock(JSplitPaneDriver.class));
	}

	@Test
	void should_Call_MoveDividerTo_In_Driver_And_Return_Self() {
		assertThat(fixture.moveDividerTo(6)).isSameAs(fixture);
		verify(fixture.driver()).moveDividerTo(fixture.target(), 6);
	}
}
