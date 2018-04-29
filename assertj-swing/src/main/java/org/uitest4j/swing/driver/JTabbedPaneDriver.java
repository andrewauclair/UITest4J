/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.driver;

import org.assertj.core.description.Description;
import org.assertj.core.util.VisibleForTesting;
import org.opentest4j.AssertionFailedError;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.core.Robot;
import org.uitest4j.swing.data.Index;
import org.uitest4j.swing.edt.GuiQuery;
import org.uitest4j.swing.exception.ActionFailedException;
import org.uitest4j.swing.exception.LocationUnavailableException;
import org.uitest4j.swing.internal.annotation.InternalApi;
import org.uitest4j.swing.internal.assertions.OpenTest4JAssertions;
import org.uitest4j.swing.util.Pair;
import org.uitest4j.swing.util.PatternTextMatcher;
import org.uitest4j.swing.util.StringTextMatcher;
import org.uitest4j.swing.util.TextMatcher;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.driver.ComponentPreconditions.checkEnabledAndShowing;
import static org.uitest4j.swing.driver.JTabbedPaneSelectTabQuery.selectedTabIndexOf;
import static org.uitest4j.swing.driver.JTabbedPaneSelectTabTask.setSelectedTab;
import static org.uitest4j.swing.driver.JTabbedPaneTabTitlesQuery.tabTitlesOf;
import static org.uitest4j.swing.driver.TextAssert.verifyThat;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.format.Formatting.format;

/**
 * <p>
 * Supports functional testing of {@code JTabbedPane}s.
 * </p>
 *
 * <p>
 * <b>Note:</b> This class is intended for internal use only. Please use the classes in the package
 * {@link org.uitest4j.swing.fixture} in your tests.
 * </p>
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 * @author William Bakker
 */
@InternalApi
public class JTabbedPaneDriver extends JComponentDriver {
	private final JTabbedPaneLocation location;

	/**
	 * Creates a new {@link JTabbedPaneDriver}.
	 *
	 * @param robot the robot to use to simulate user input.
	 */
	public JTabbedPaneDriver(@Nonnull Robot robot) {
		this(robot, new JTabbedPaneLocation());
	}

	/**
	 * Creates a new {@link JTabbedPaneDriver}.
	 *
	 * @param robot    the robot to use to simulate user input.
	 * @param location knows how to find the location of a tab.
	 */
	@VisibleForTesting
	JTabbedPaneDriver(@Nonnull Robot robot, @Nonnull JTabbedPaneLocation location) {
		super(robot);
		this.location = location;
	}

	/**
	 * Returns the titles of all the tabs.
	 *
	 * @param tabbedPane the target {@code JTabbedPane}.
	 * @return the titles of all the tabs.
	 */
	@RunsInEDT
	@Nonnull
	public String[] tabTitles(@Nonnull JTabbedPane tabbedPane) {
		return tabTitlesOf(tabbedPane);
	}

	/**
	 * Simulates a user selecting the tab containing the given title.
	 *
	 * @param tabbedPane the target {@code JTabbedPane}.
	 * @param title      the given text to match. It can be a regular expression.
	 * @throws IllegalStateException        if the {@code JTabbedPane} is disabled.
	 * @throws IllegalStateException        if the {@code JTabbedPane} is not showing on the screen.
	 * @throws LocationUnavailableException if a tab matching the given title could not be found.
	 */
	@RunsInEDT
	public void selectTab(@Nonnull JTabbedPane tabbedPane, @Nullable String title) {
		selectTab(tabbedPane, new StringTextMatcher(title));
	}

	/**
	 * Simulates a user selecting the tab whose title matches the given regular expression pattern.
	 *
	 * @param tabbedPane the target {@code JTabbedPane}.
	 * @param pattern    the regular expression pattern to match.
	 * @throws IllegalStateException        if the {@code JTabbedPane} is disabled.
	 * @throws IllegalStateException        if the {@code JTabbedPane} is not showing on the screen.
	 * @throws NullPointerException         if the given regular expression pattern is {@code null}.
	 * @throws LocationUnavailableException if a tab matching the given regular expression pattern could not be found.
	 */
	@RunsInEDT
	public void selectTab(@Nonnull JTabbedPane tabbedPane, @Nonnull Pattern pattern) {
		selectTab(tabbedPane, new PatternTextMatcher(pattern));
	}

	@RunsInEDT
	private void selectTab(@Nonnull JTabbedPane tabbedPane, @Nonnull TextMatcher matcher) {
		Pair<Integer, Point> tabToSelectInfo = tabToSelectInfo(location(), tabbedPane, matcher);
		Point target = tabToSelectInfo.second;
		if (target != null) {
			try {
				checkInEdtEnabledAndShowing(tabbedPane);
				click(tabbedPane, target);
			}
			catch (ActionFailedException e) {
				// On Mac it may be necessary to scroll the tabs to find the one to click.
				setTabDirectly(tabbedPane, tabToSelectInfo.first);
			}
			return;
		}
		setTabDirectly(tabbedPane, tabToSelectInfo.first);
	}

	@RunsInEDT
	@Nonnull
	private static Pair<Integer, Point> tabToSelectInfo(final @Nonnull JTabbedPaneLocation location,
														final @Nonnull JTabbedPane tabbedPane,
														final @Nonnull TextMatcher matcher) {
		Pair<Integer, Point> result = execute(new GuiQuery<Pair<Integer, Point>>() {
			@Override
			protected Pair<Integer, Point> executeInEDT() {
				checkEnabledAndShowing(tabbedPane);
				int index = location.indexOf(tabbedPane, matcher);
				location.checkIndexInBounds(tabbedPane, index);
				Point point = null;
				try {
					point = location.pointAt(tabbedPane, index);
				}
				catch (LocationUnavailableException e) {
				}
				return Pair.of(index, point);
			}
		});
		return Objects.requireNonNull(result);
	}

	/**
	 * Simulates a user selecting the tab located at the given index.
	 *
	 * @param tabbedPane the target {@code JTabbedPane}.
	 * @param index      the index of the tab to select.
	 * @throws IllegalStateException     if the {@code JTabbedPane} is disabled.
	 * @throws IllegalStateException     if the {@code JTabbedPane} is not showing on the screen.
	 * @throws IndexOutOfBoundsException if the given index is not within the {@code JTabbedPane} bounds.
	 */
	@RunsInEDT
	public void selectTab(@Nonnull JTabbedPane tabbedPane, int index) {
		try {
			Point p = pointAtTabWhenShowing(location(), tabbedPane, index);
			checkInEdtEnabledAndShowing(tabbedPane);
			click(tabbedPane, p);
		}
		catch (LocationUnavailableException | ActionFailedException e) {
			setTabDirectly(tabbedPane, index);
		}
	}

	@RunsInEDT
	@Nonnull
	private static Point pointAtTabWhenShowing(final @Nonnull JTabbedPaneLocation location,
											   final @Nonnull JTabbedPane tabbedPane, final int index) {
		Point result = execute(() -> {
			location.checkIndexInBounds(tabbedPane, index);
			checkEnabledAndShowing(tabbedPane);
			return location.pointAt(tabbedPane, index);
		});
		return Objects.requireNonNull(result);
	}

	@RunsInEDT
	@VisibleForTesting
	void setTabDirectly(@Nonnull JTabbedPane tabbedPane, int index) {
		setSelectedTab(tabbedPane, index);
		robot.waitForIdle();
		moveMouseToTab(tabbedPane, index);
	}

	private void moveMouseToTab(@Nonnull JTabbedPane tabbedPane, int index) {
		try {
			Point p = pointAtTab(location(), tabbedPane, index);
			robot.moveMouse(tabbedPane, p);
			robot.waitForIdle();
		}
		catch (LocationUnavailableException ignored) {
		}
	}

	@RunsInEDT
	@Nonnull
	private static Point pointAtTab(final @Nonnull JTabbedPaneLocation location,
									final @Nonnull JTabbedPane tabbedPane, final int index) {
		Point result = execute(() -> location.pointAt(tabbedPane, index));
		return Objects.requireNonNull(result);
	}

	/**
	 * Returns the currently selected component for the given {@code JTabbedPane}.
	 *
	 * @param tabbedPane the target {@code JTabbedPane}.
	 * @return the currently selected component for the given {@code JTabbedPane}.
	 */
	@RunsInEDT
	@Nullable
	public Component selectedComponentOf(@Nonnull JTabbedPane tabbedPane) {
		return selectedComponent(tabbedPane);
	}

	@RunsInEDT
	@Nullable
	private static Component selectedComponent(final JTabbedPane tabbedPane) {
		return execute(() -> tabbedPane.getSelectedComponent());
	}

	/**
	 * Asserts that the title of the tab at the given index matches the given value.
	 *
	 * @param tabbedPane the target {@code JTabbedPane}.
	 * @param title      the expected title. It can be a regular expression.
	 * @param index      the index of the tab.
	 * @throws IndexOutOfBoundsException if the given index is not within the {@code JTabbedPane} bounds.
	 * @throws AssertionError            if the title of the tab at the given index does not match the given one.
	 */
	@RunsInEDT
	public void requireTabTitle(@Nonnull JTabbedPane tabbedPane, @Nullable String title, @Nonnull Index index) {
		String actualTitle = titleAt(tabbedPane, index);
		verifyThat(actualTitle).as(titleAtProperty(tabbedPane)).isEqualOrMatches(title);
	}

	/**
	 * Asserts that the title of the tab at the given index matches the given regular expression pattern.
	 *
	 * @param tabbedPane the target {@code JTabbedPane}.
	 * @param pattern    the regular expression pattern to match.
	 * @param index      the index of the tab.
	 * @throws NullPointerException      if the given regular expression pattern is {@code null}.
	 * @throws IndexOutOfBoundsException if the given index is not within the {@code JTabbedPane} bounds.
	 * @throws AssertionError            if the title of the tab at the given index does not match the given one.
	 */
	@RunsInEDT
	public void requireTabTitle(@Nonnull JTabbedPane tabbedPane, @Nonnull Pattern pattern, @Nonnull Index index) {
		String actualTitle = titleAt(tabbedPane, index);
		verifyThat(actualTitle).as(titleAtProperty(tabbedPane)).matches(pattern);
	}

	/**
	 * Asserts that the tab at the given index is the selected tab.
	 *
	 * @param tabbedPane the target {@code JTabbedPane}.
	 * @param index      the index of the selected tab.
	 * @throws AssertionError if the index of the selected tab does not match the given one.
	 */
	@RunsInEDT
	public void requireSelectedTab(@Nonnull JTabbedPane tabbedPane, @Nonnull Index index) {
		assertThat(selectedTabIndexOf(tabbedPane).value).as(propertyName(tabbedPane, "selectedIndex"))
				.isEqualTo(index.value);
	}

	/**
	 * Asserts that the toolTipText of the tab at the given index matches the given value.
	 *
	 * @param tabbedPane  the target {@code JTabbedPane}.
	 * @param toolTipText the expected toolTipText. It can be a regular expression.
	 * @param index       the index of the tab.
	 * @throws IndexOutOfBoundsException if the given index is not within the {@code JTabbedPane} bounds.
	 * @throws AssertionError            if the toolTipText of the tab at the given index does not match the given one.
	 */
	@RunsInEDT
	public void requireTabToolTipText(@Nonnull JTabbedPane tabbedPane, @Nullable String toolTipText, @Nonnull Index index) {
		String actualToolTipText = toolTipTextAt(tabbedPane, index);
		verifyThat(actualToolTipText).as(toolTipTextAtProperty(tabbedPane)).isEqualOrMatches(toolTipText);
	}

	/**
	 * Asserts that the toolTipText of the tab at the given index matches the given regular expression pattern.
	 *
	 * @param tabbedPane the target {@code JTabbedPane}.
	 * @param pattern    the regular expression pattern to match.
	 * @param index      the index of the tab.
	 * @throws NullPointerException      if the given regular expression pattern is {@code null}.
	 * @throws IndexOutOfBoundsException if the given index is not within the {@code JTabbedPane} bounds.
	 * @throws AssertionError            if the toolTipText of the tab at the given index does not match the given one.
	 */
	@RunsInEDT
	public void requireTabToolTipText(@Nonnull JTabbedPane tabbedPane, @Nonnull Pattern pattern, @Nonnull Index index) {
		String actualToolTipText = toolTipTextAt(tabbedPane, index);
		verifyThat(actualToolTipText).as(toolTipTextAtProperty(tabbedPane)).matches(pattern);
	}

	/**
	 * Asserts that the tab at the given index is enabled.
	 *
	 * @param tabbedPane the target {@code JTabbedPane}.
	 * @param index      the index of the tab.
	 * @throws IndexOutOfBoundsException if the given index is not within the {@code JTabbedPane} bounds.
	 * @throws AssertionFailedError            if the tab at the given index is not enabled.
	 */
	@RunsInEDT
	public void requireTabEnabled(@Nonnull JTabbedPane tabbedPane, int index) {
		boolean actualEnabled = isEnabledAt(tabbedPane, index);
		OpenTest4JAssertions.assertTrue(actualEnabled, () -> "Expected tab at index " + index + " to be enabled: " + format(tabbedPane));
	}

	/**
	 * Asserts that the tab at the given index is disabled.
	 *
	 * @param tabbedPane the target {@code JTabbedPane}.
	 * @param index      the index of the tab.
	 * @throws IndexOutOfBoundsException if the given index is not within the {@code JTabbedPane} bounds.
	 * @throws AssertionFailedError            if the tab at the given index is not disabled.
	 */
	@RunsInEDT
	public void requireTabDisabled(@Nonnull JTabbedPane tabbedPane, int index) {
		boolean actualEnabled = isEnabledAt(tabbedPane, index);
		OpenTest4JAssertions.assertFalse(actualEnabled, () -> "Expected tab at index " + index + " to be disabled: " + format(tabbedPane));
	}

	@RunsInEDT
	private Description titleAtProperty(@Nonnull JTabbedPane tabbedPane) {
		return propertyName(tabbedPane, "titleAt");
	}

	@RunsInEDT
	private Description toolTipTextAtProperty(@Nonnull JTabbedPane tabbedPane) {
		return propertyName(tabbedPane, "toolTipTextAt");
	}

	@RunsInEDT
	@Nullable
	private static String titleAt(final @Nonnull JTabbedPane tabbedPane, final @Nonnull Index index) {
		return execute(() -> tabbedPane.getTitleAt(index.value));
	}

	@RunsInEDT
	@Nullable
	private static String toolTipTextAt(final @Nonnull JTabbedPane tabbedPane, final @Nonnull Index index) {
		return execute(() -> tabbedPane.getToolTipTextAt(index.value));
	}

	@RunsInEDT
	private static boolean isEnabledAt(final @Nonnull JTabbedPane tabbedPane, final int index) {
		return Objects.requireNonNull(execute(() -> tabbedPane.isEnabledAt(index)));
	}

	/**
	 * Asserts that the tabs of the given {@code JTabbedPane} have the given titles. The tab titles are evaluated by index
	 * order, for example, the first tab is expected to have the first title in the given array, and so on.
	 *
	 * @param tabbedPane the target {@code JTabbedPane}.
	 * @param titles     the expected titles.
	 * @throws AssertionError if the title of any of the tabs is not equal to the expected titles.
	 */
	@RunsInEDT
	public void requireTabTitles(@Nonnull JTabbedPane tabbedPane, @Nonnull String[] titles) {
		String[] actualTitles = allTabTitlesIn(tabbedPane);
		assertThat(actualTitles).as(propertyName(tabbedPane, "tabTitles")).isEqualTo(titles);
	}

	@RunsInEDT
	@Nonnull
	private static String[] allTabTitlesIn(final @Nonnull JTabbedPane tabbedPane) {
		String[] result = execute(() -> {
			List<String> allTitles = new ArrayList<>();
			int tabCount = tabbedPane.getTabCount();
			for (int i = 0; i < tabCount; i++) {
				allTitles.add(tabbedPane.getTitleAt(i));
			}
			return allTitles.toArray(new String[0]);
		});
		return Objects.requireNonNull(result);
	}

	@Nonnull
	private JTabbedPaneLocation location() {
		return location;
	}
}
