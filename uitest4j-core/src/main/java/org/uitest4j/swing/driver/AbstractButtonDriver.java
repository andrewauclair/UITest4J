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

import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.core.Robot;
import org.uitest4j.swing.internal.annotation.InternalApi;
import org.uitest4j.swing.internal.assertions.OpenTest4JAssertions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.util.Objects;
import java.util.regex.Pattern;

import static org.uitest4j.swing.driver.AbstractButtonArmedQuery.isArmed;
import static org.uitest4j.swing.driver.AbstractButtonSelectedQuery.isSelected;
import static org.uitest4j.swing.driver.ComponentPreconditions.checkEnabledAndShowing;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * <p>
 * Supports functional testing of Swing {@code AbstractButton}s.
 * </p>
 *
 * <p>
 * <b>Note:</b> This class is intended for internal use only. Please use the classes in the package
 * {@link org.uitest4j.swing.fixture} in your tests.
 * </p>
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@InternalApi
public class AbstractButtonDriver extends JComponentDriver implements TextDisplayDriver<AbstractButton> {
	/**
	 * Creates a new {@link AbstractButtonDriver}.
	 *
	 * @param robot the robot to use to simulate user input.
	 */
	public AbstractButtonDriver(@Nonnull Robot robot) {
		super(robot);
	}

	/**
	 * Asserts that the text in the given Swing {@code AbstractButton} is equal to or matches the specified {@code String}
	 * .
	 *
	 * @param button   the given {@code AbstractButton}.
	 * @param expected the text to match. It can be a regular expression.
	 * @throws AssertionError if the text of the {@code AbstractButton} is not equal to or does not match the given one.
	 */
	@RunsInEDT
	@Override
	public void requireText(@Nonnull AbstractButton button, @Nullable String expected) {
		OpenTest4JAssertions.assertEquals(expected, textOf(button), () -> "Expected text of '" + button.getName() +
				"' to be '" + expected + "' but was '" + textOf(button) + "'");
	}

	/**
	 * Asserts that the text in the given Swing {@code AbstractButton} matches the given regular expression pattern.
	 *
	 * @param button  the given {@code AbstractButton}.
	 * @param pattern the regular expression pattern to match.
	 * @throws NullPointerException if the given regular expression pattern is {@code null}.
	 * @throws AssertionError       if the text of the {@code AbstractButton} does not match the given regular expression
	 *                              pattern.
	 */
	@Override
	public void requireText(@Nonnull AbstractButton button, @Nonnull Pattern pattern) {
		OpenTest4JAssertions.assertMatchesPattern(pattern, textOf(button), () -> "Expected text of '" + button.getName() +
				"' to match pattern '" + pattern + "' but was '" + textOf(button) + "'");
	}

	/**
	 * Returns the text of the given Swing {@code AbstractButton}.
	 *
	 * @param button the given {@code AbstractButton}.
	 * @return the text of the given {@code AbstractButton}.
	 */
	@RunsInEDT
	@Override
	@Nonnull
	public String textOf(@Nonnull AbstractButton button) {
		return AbstractButtonTextQuery.textOf(button);
	}

	/**
	 * Selects the given Swing {@code AbstractButton} only it is not already selected.
	 *
	 * @param button the target {@code AbstractButton}.
	 * @throws IllegalStateException if the {@code AbstractButton} is disabled.
	 * @throws IllegalStateException if the {@code AbstractButton} is not showing on the screen.
	 */
	@RunsInEDT
	public void select(@Nonnull AbstractButton button) {
		if (checkSelected(button)) {
			return;
		}
		robot.click(button);
	}

	/**
	 * Deselects the given Swing {@code AbstractButton} only if it is selected.
	 *
	 * @param button the target {@code AbstractButton}.
	 * @throws IllegalStateException if the {@code AbstractButton} is disabled.
	 * @throws IllegalStateException if the {@code AbstractButton} is not showing on the screen.
	 */
	@RunsInEDT
	public void deselect(@Nonnull AbstractButton button) {
		if (!checkSelected(button)) {
			return;
		}
		robot.click(button);
	}

	@RunsInEDT
	private static boolean checkSelected(final @Nonnull AbstractButton button) {
		Boolean result = execute(() -> {
			checkEnabledAndShowing(button);
			return button.isSelected();
		});
		return Objects.requireNonNull(result);
	}

	/**
	 * Verifies that the Swing {@code AbstractButton} is selected.
	 *
	 * @param button the given {@code AbstractButton}.
	 * @throws AssertionError if the button is not selected.
	 */
	@RunsInEDT
	public void requireSelected(@Nonnull AbstractButton button) {
		OpenTest4JAssertions.assertTrue(isSelected(button),
				() -> String.format("Expected '%s' to be selected", button.getName()));
	}

	/**
	 * Verifies that the {@code AbstractButton} is not selected.
	 *
	 * @param button the given {@code AbstractButton}.
	 * @throws AssertionError if the {@code AbstractButton} is selected.
	 */
	@RunsInEDT
	public void requireNotSelected(@Nonnull AbstractButton button) {
		OpenTest4JAssertions.assertFalse(isSelected(button),
				() -> String.format("Expected '%s' to not be selected", button.getName()));
	}

	/**
	 * Verifies that the Swing {@code AbstractButton} is armed.
	 *
	 * @param button the given {@code AbstractButton}.
	 * @throws AssertionError if the button is not armed.
	 */
	@RunsInEDT
	public void requireArmed(@Nonnull AbstractButton button) {
		OpenTest4JAssertions.assertTrue(isArmed(button),
				() -> String.format("Expected '%s' to be armed", button.getName()));
	}

	/**
	 * Verifies that the {@code AbstractButton} is not armed.
	 *
	 * @param button the given {@code AbstractButton}.
	 * @throws AssertionError if the {@code AbstractButton} is armed.
	 */
	@RunsInEDT
	public void requireNotArmed(@Nonnull AbstractButton button) {
		OpenTest4JAssertions.assertFalse(isArmed(button),
				() -> String.format("Expected '%s' to not be armed", button.getName()));
	}
}
