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

import org.uitest4j.swing.core.Robot;
import org.uitest4j.swing.driver.JTabbedPaneDriver;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.util.regex.Pattern;

/**
 * Supports functional testing of {@code JTabbedPane}s.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 * @author William Bakker
 */
public class JTabbedPaneFixture extends
		AbstractJPopupMenuInvokerFixture<JTabbedPaneFixture, JTabbedPane, JTabbedPaneDriver> {
	/**
	 * Creates a new {@link JTabbedPaneFixture}.
	 *
	 * @param robot  performs simulation of user events on the given {@code JTabbedPane}.
	 * @param target the {@code JTabbedPane} to be managed by this fixture.
	 * @throws NullPointerException if {@code robot} is {@code null}.
	 * @throws NullPointerException if {@code target} is {@code null}.
	 */
	public JTabbedPaneFixture(@Nonnull Robot robot, @Nonnull JTabbedPane target) {
		super(JTabbedPaneFixture.class, robot, target);
	}

	/**
	 * Creates a new {@link JTabbedPaneFixture}.
	 *
	 * @param robot          performs simulation of user events on a {@code JTabbedPane}.
	 * @param tabbedPaneName the name of the {@code JTabbedPane} to find using the given {@code Robot}.
	 * @throws NullPointerException                                  if {@code robot} is {@code null}.
	 * @throws org.uitest4j.swing.exception.ComponentLookupException if a matching {@code JTabbedPane} could not be found.
	 * @throws org.uitest4j.swing.exception.ComponentLookupException if more than one matching {@code JTabbedPane} is found.
	 */
	public JTabbedPaneFixture(@Nonnull Robot robot, @Nonnull String tabbedPaneName) {
		super(JTabbedPaneFixture.class, robot, tabbedPaneName, JTabbedPane.class);
	}

	@Override
	@Nonnull
	protected JTabbedPaneDriver createDriver(@Nonnull Robot robot) {
		return new JTabbedPaneDriver(robot);
	}

	/**
	 * Returns the titles of all the tabs in this fixture's {@code JTabbedPane}.
	 *
	 * @return the titles of all the tabs.
	 */
	public String[] tabTitles() {
		return driver().tabTitles(target());
	}

	/**
	 * Simulates a user selecting the tab located at the given index.
	 *
	 * @param index the index of the tab to select.
	 * @return this fixture.
	 * @throws IllegalStateException     if this fixture's {@code JTabbedPane} is disabled.
	 * @throws IllegalStateException     if this fixture's {@code JTabbedPane} is not showing on the screen.
	 * @throws IndexOutOfBoundsException if the given index is not within the {@code JTabbedPane} bounds.
	 */
	@Nonnull
	public JTabbedPaneFixture selectTab(int index) {
		driver().selectTab(target(), index);
		return this;
	}

	/**
	 * Simulates a user selecting the tab whose title matches the given value.
	 *
	 * @param title the title to match. It can be a regular expression.
	 * @return this fixture.
	 * @throws IllegalStateException                                     if this fixture's {@code JTabbedPane} is disabled.
	 * @throws IllegalStateException                                     if this fixture's {@code JTabbedPane} is not showing on the screen.
	 * @throws org.uitest4j.swing.exception.LocationUnavailableException if a tab matching the given title could not be found.
	 */
	@Nonnull
	public JTabbedPaneFixture selectTab(@Nullable String title) {
		driver().selectTab(target(), title);
		return this;
	}

	/**
	 * Simulates a user selecting the tab whose title matches the given regular expression pattern.
	 *
	 * @param pattern the regular expression pattern to match.
	 * @return this fixture.
	 * @throws IllegalStateException                                     if this fixture's {@code JTabbedPane} is disabled.
	 * @throws IllegalStateException                                     if this fixture's {@code JTabbedPane} is not showing on the screen.
	 * @throws NullPointerException                                      if the given regular expression pattern is {@code null}.
	 * @throws org.uitest4j.swing.exception.LocationUnavailableException if a tab matching the given regular expression pattern could not be found.
	 */
	@Nonnull
	public JTabbedPaneFixture selectTab(@Nonnull Pattern pattern) {
		driver().selectTab(target(), pattern);
		return this;
	}

	/**
	 * Asserts that the title of the tab at the given index matches the given value.
	 *
	 * @param title the expected title. It can be a regular expression.
	 * @param index the index of the tab.
	 * @return this fixture.
	 * @throws IndexOutOfBoundsException if the given index is not within the {@code JTabbedPane} bounds.
	 * @throws AssertionError            if the title of the tab at the given index does not match the given one.
	 */
	@Nonnull
	public JTabbedPaneFixture requireTitle(@Nullable String title, int index) {
		driver().requireTabTitle(target(), title, index);
		return this;
	}

	/**
	 * Asserts that the title of the tab at the given index matches the given regular expression pattern.
	 *
	 * @param pattern the regular expression pattern to match.
	 * @param index   the index of the tab.
	 * @return this fixture.
	 * @throws NullPointerException if the given regular expression pattern is {@code null}.
	 * @throws AssertionError       if the title of the tab at the given index does not match the given regular expression
	 *                              pattern.
	 */
	@Nonnull
	public JTabbedPaneFixture requireTitle(@Nonnull Pattern pattern, int index) {
		driver().requireTabTitle(target(), pattern, index);
		return this;
	}

	/**
	 * Asserts that the tabs of this fixture's {@code JTabbedPane} have the given titles. The tab titles are evaluated by
	 * index order, for example, the first tab is expected to have the first title in the given array, and so on.
	 *
	 * @param titles the expected titles.
	 * @return this fixture.
	 * @throws AssertionError if the title of any of the tabs is not equal to the expected titles.
	 */
	@Nonnull
	public JTabbedPaneFixture requireTabTitles(@Nonnull String... titles) {
		driver().requireTabTitles(target(), titles);
		return this;
	}

	/**
	 * Asserts that this fixture's {@code JTabbedPane} has the tab at the given index selected.
	 *
	 * @param expected the expected index of the selected tab.
	 * @return this fixture.
	 * @throws AssertionError if this fixture's {@code JTabbedPane}'s selected tab does not have the given index.
	 */
	@Nonnull
	public JTabbedPaneFixture requireSelectedTab(int expected) {
		driver().requireSelectedTab(target(), expected);
		return this;
	}

	/**
	 * Asserts that the toolTipText of the tab at the given index matches the given value.
	 *
	 * @param toolTipText the expected toolTipText. It can be a regular expression.
	 * @param index       the index of the tab.
	 * @return this fixture.
	 * @throws IndexOutOfBoundsException if the given index is not within the {@code JTabbedPane} bounds.
	 * @throws AssertionError            if the toolTipText of the tab at the given index does not match the given one.
	 */
	@Nonnull
	public JTabbedPaneFixture requireToolTipText(@Nullable String toolTipText, int index) {
		driver().requireTabToolTipText(target(), toolTipText, index);
		return this;
	}

	/**
	 * Asserts that the toolTipText of the tab at the given index matches the given regular expression pattern.
	 *
	 * @param pattern the regular expression pattern to match.
	 * @param index   the index of the tab.
	 * @return this fixture.
	 * @throws NullPointerException if the given regular expression pattern is {@code null}.
	 * @throws AssertionError       if the toolTipText of the tab at the given index does not match the given regular expression
	 *                              pattern.
	 */
	@Nonnull
	public JTabbedPaneFixture requireToolTipText(@Nonnull Pattern pattern, int index) {
		driver().requireTabToolTipText(target(), pattern, index);
		return this;
	}

	/**
	 * Asserts that the tab at the given index is enabled.
	 *
	 * @param index the index of the tab.
	 * @return this fixture.
	 * @throws IndexOutOfBoundsException if the given index is not within the {@code JTabbedPane} bounds.
	 * @throws AssertionError            if the tab at the given index is not enabled.
	 */
	@Nonnull
	public JTabbedPaneFixture requireEnabled(int index) {
		driver().requireTabEnabled(target(), index);
		return this;
	}

	/**
	 * Asserts that the tab at the given index is disabled.
	 *
	 * @param index the index of the tab.
	 * @return this fixture.
	 * @throws IndexOutOfBoundsException if the given index is not within the {@code JTabbedPane} bounds.
	 * @throws AssertionError            if the tab at the given index is not disabled.
	 */
	@Nonnull
	public JTabbedPaneFixture requireDisabled(int index) {
		driver().requireTabDisabled(target(), index);
		return this;
	}
}
