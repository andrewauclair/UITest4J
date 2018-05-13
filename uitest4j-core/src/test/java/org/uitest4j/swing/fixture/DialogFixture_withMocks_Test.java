/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at
  <p>
  http://www.apache.org/licenses/LICENSE-2.0
  <p>
  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.
  <p>
  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.fixture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.core.Robot;
import org.uitest4j.swing.driver.DialogDriver;

import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link DialogFixture}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 * @author Andrew Auclair
 */
class DialogFixture_withMocks_Test {
	private DialogFixture fixture;

	@BeforeEach
	void setUp() {
		fixture = new DialogFixture(mock(Robot.class), mock(Dialog.class));
		fixture.replaceDriverWith(mock(DialogDriver.class));
	}

	@Test
	void should_Call_RequireModal_In_Driver_And_Return_Self() {
		assertThat(fixture.requireModal()).isSameAs(fixture);
		verify(fixture.driver()).requireModal(fixture.target());
	}

	@Test
	void calls_requireTitle_in_driver_and_returns_self() {
		assertThat(fixture.requireTitle("Title")).isSameAs(fixture);
		verify(fixture.driver()).requireTitle(fixture.target(), "Title");
	}
}