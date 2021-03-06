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
import org.uitest4j.swing.core.GenericTypeMatcher;
import org.uitest4j.swing.core.Robot;
import org.uitest4j.swing.driver.JPopupMenuDriver;

import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.uitest4j.swing.test.core.NeverMatchingComponentMatcher.neverMatches;

/**
 * Tests for {@link JPopupMenuFixture}.
 *
 * @author Yvonne Wang
 */
class JPopupMenuFixture_withMocks_Test {
	private JPopupMenuDriver driver;
	private JPopupMenu target;

	private JPopupMenuFixture fixture;

	@BeforeEach
	void setUp() {
		fixture = new JPopupMenuFixture(mock(Robot.class), mock(JPopupMenu.class));
		fixture.replaceDriverWith(mock(JPopupMenuDriver.class));
		driver = fixture.driver();
		target = fixture.target();
	}

	@Test
	void should_Find_JMenuItem_With_Name_Using_Driver() {
		JMenuItem menuItem = mock(JMenuItem.class);
		when(driver.menuItem(target, "File")).thenReturn(menuItem);
		JMenuItemFixture menuItemFixture = fixture.menuItem("File");
		assertThat(menuItemFixture.target()).isSameAs(menuItem);
		verify(driver).menuItem(target, "File");
	}

	@Test
	void should_Find_JMenuItem_With_Matcher_Using_Driver() {
		GenericTypeMatcher<JMenuItem> matcher = neverMatches(JMenuItem.class);
		JMenuItem menuItem = mock(JMenuItem.class);
		when(driver.menuItem(target, matcher)).thenReturn(menuItem);
		JMenuItemFixture menuItemFixture = fixture.menuItem(matcher);
		assertThat(menuItemFixture.target()).isSameAs(menuItem);
		verify(driver).menuItem(target, matcher);
	}

	@Test
	void should_Return_Menu_Labels_Using_Driver() {
		String[] labels = {"One", "Two"};
		when(driver.menuLabelsOf(target)).thenReturn(labels);
		assertThat(fixture.menuLabels()).isSameAs(labels);
		verify(driver).menuLabelsOf(target);
	}
}
