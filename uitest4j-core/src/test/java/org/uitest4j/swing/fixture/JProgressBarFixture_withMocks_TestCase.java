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
import org.uitest4j.swing.core.Robot;
import org.uitest4j.swing.driver.JProgressBarDriver;

import javax.swing.*;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.uitest4j.swing.timing.Timeout.timeout;

/**
 * Test cases for {@link JProgressBarFixture}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class JProgressBarFixture_withMocks_TestCase {
	private JProgressBarFixture fixture;

	@BeforeEach
	void setUp() {
		fixture = new JProgressBarFixture(mock(Robot.class), mock(JProgressBar.class));
		fixture.replaceDriverWith(mock(JProgressBarDriver.class));
	}

	@Test
	void should_Call_RequireValue_In_Driver_And_Return_Self() {
		assertThat(fixture.requireValue(6)).isSameAs(fixture);
		verify(fixture.driver()).requireValue(fixture.target(), 6);
	}

	@Test
	void should_Call_RequireDeterminate_In_Driver_And_Return_Self() {
		assertThat(fixture.requireDeterminate()).isSameAs(fixture);
		verify(fixture.driver()).requireDeterminate(fixture.target());
	}

	@Test
	void should_Call_RequireIndeterminate_In_Driver_And_Return_Self() {
		assertThat(fixture.requireIndeterminate()).isSameAs(fixture);
		verify(fixture.driver()).requireIndeterminate(fixture.target());
	}

	@Test
	void should_Return_Text_Using_Driver() {
		JProgressBarDriver driver = fixture.driver();
		JProgressBar target = fixture.target();
		when(driver.textOf(target)).thenReturn("Hello");
		assertThat(fixture.text()).isEqualTo("Hello");
		verify(driver).textOf(target);
	}

	@Test
	void should_Call_RequireText_In_Driver_And_Return_Self() {
		assertThat(fixture.requireText("Hello")).isSameAs(fixture);
		verify(fixture.driver()).requireText(fixture.target(), "Hello");
	}

	@Test
	void should_Call_RequireText_With_Pattern_In_Driver_And_Return_Self() {
		Pattern pattern = Pattern.compile("Hello");
		assertThat(fixture.requireText(pattern)).isSameAs(fixture);
		verify(fixture.driver()).requireText(fixture.target(), pattern);
	}

	@Test
	void should_Call_WaitUntilValueIs_In_Driver_And_Return_Self() {
		assertThat(fixture.waitUntilValueIs(6)).isSameAs(fixture);
		verify(fixture.driver()).waitUntilValueIs(fixture.target(), 6);
	}

	@Test
	void should_Call_WaitUntilValueIs_With_Timeout_In_Driver_And_Return_Self() {
		assertThat(fixture.waitUntilValueIs(6, timeout(8))).isSameAs(fixture);
		verify(fixture.driver()).waitUntilValueIs(fixture.target(), 6, timeout(8));
	}

	@Test
	void should_Call_WaitUntilIsDeterminate_In_Driver_And_Return_Self() {
		assertThat(fixture.waitUntilIsDeterminate()).isSameAs(fixture);
		verify(fixture.driver()).waitUntilIsDeterminate(fixture.target());
	}

	@Test
	void should_Call_WaitUntilIsDeterminate_With_Timeout_In_Driver_And_Return_Self() {
		assertThat(fixture.waitUntilIsDeterminate(timeout(8))).isSameAs(fixture);
		verify(fixture.driver()).waitUntilIsDeterminate(fixture.target(), timeout(8));
	}
}
