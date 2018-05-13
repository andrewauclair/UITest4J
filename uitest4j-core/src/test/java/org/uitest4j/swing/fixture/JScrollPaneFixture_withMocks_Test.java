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
import org.uitest4j.swing.driver.JScrollPaneDriver;

import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link JScrollPaneFixture}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class JScrollPaneFixture_withMocks_Test {
	private JScrollPaneDriver driver;
	private JScrollPane target;

	private JScrollPaneFixture fixture;

	@BeforeEach
	void setUp() {
		fixture = new JScrollPaneFixture(mock(Robot.class), mock(JScrollPane.class));
		fixture.replaceDriverWith(mock(JScrollPaneDriver.class));
		driver = fixture.driver();
		target = fixture.target();
	}

	@Test
	void should_Return_Horizontal_JScrollBar_Using_Driver() {
		JScrollBar scrollBar = mock(JScrollBar.class);
		when(driver.horizontalScrollBarIn(target)).thenReturn(scrollBar);
		JScrollBarFixture scrollBarFixture = fixture.horizontalScrollBar();
		assertThat(scrollBarFixture.target()).isSameAs(scrollBar);
		verify(driver).horizontalScrollBarIn(target);
	}

	@Test
	void should_Return_Vertical_JScrollBar_Using_Driver() {
		JScrollBar scrollBar = mock(JScrollBar.class);
		when(driver.verticalScrollBarIn(target)).thenReturn(scrollBar);
		JScrollBarFixture scrollBarFixture = fixture.verticalScrollBar();
		assertThat(scrollBarFixture.target()).isSameAs(scrollBar);
		verify(driver).verticalScrollBarIn(target);
	}
}
