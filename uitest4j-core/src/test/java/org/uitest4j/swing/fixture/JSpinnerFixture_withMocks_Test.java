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
import org.uitest4j.swing.driver.JSpinnerDriver;

import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link JSpinnerFixture}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class JSpinnerFixture_withMocks_Test {
	private JSpinnerFixture fixture;

	@BeforeEach
	void setUp() {
		fixture = new JSpinnerFixture(mock(Robot.class), mock(JSpinner.class));
		fixture.replaceDriverWith(mock(JSpinnerDriver.class));
	}

	@Test
	void should_Call_Increment_In_Driver_And_Return_Self() {
		assertThat(fixture.increment()).isSameAs(fixture);
		verify(fixture.driver()).increment(fixture.target());
	}

	@Test
	void should_Call_Increment_With_Times_In_Driver_And_Return_Self() {
		assertThat(fixture.increment(6)).isSameAs(fixture);
		verify(fixture.driver()).increment(fixture.target(), 6);
	}

	@Test
	void should_Call_Decrement_In_Driver_And_Return_Self() {
		assertThat(fixture.decrement()).isSameAs(fixture);
		verify(fixture.driver()).decrement(fixture.target());
	}

	@Test
	void should_Call_Decrement_With_Times_In_Driver_And_Return_Self() {
		assertThat(fixture.decrement(6)).isSameAs(fixture);
		verify(fixture.driver()).decrement(fixture.target(), 6);
	}

	@Test
	void should_Call_EnterText_In_Driver_And_Return_Self() {
		assertThat(fixture.enterText("Hello")).isSameAs(fixture);
		verify(fixture.driver()).enterText(fixture.target(), "Hello");
	}

	@Test
	void should_Call_EnterTextAndCommit_In_Driver_And_Return_Self() {
		assertThat(fixture.enterTextAndCommit("Hello")).isSameAs(fixture);
		verify(fixture.driver()).enterTextAndCommit(fixture.target(), "Hello");
	}

	@Test
	void should_Call_Select_In_Driver_And_Return_Self() {
		assertThat(fixture.select("Six")).isSameAs(fixture);
		verify(fixture.driver()).selectValue(fixture.target(), "Six");
	}

	@Test
	void should_Call_RequireValue_In_Driver_And_Return_Self() {
		assertThat(fixture.requireValue("Six")).isSameAs(fixture);
		verify(fixture.driver()).requireValue(fixture.target(), "Six");
	}

	@Test
	void should_Return_Text_Using_Driver() {
		JSpinnerDriver driver = fixture.driver();
		JSpinner target = fixture.target();
		when(driver.textOf(target)).thenReturn("Six");
		assertThat(fixture.text()).isEqualTo("Six");
		verify(driver).textOf(target);
	}
}
