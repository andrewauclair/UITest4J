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
import org.uitest4j.swing.core.matcher.JButtonMatcher;
import org.uitest4j.swing.internal.annotation.InternalApi;
import org.uitest4j.swing.internal.assertions.OpenTest4JAssertions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

import static javax.swing.JOptionPane.*;
import static org.uitest4j.swing.driver.JOptionPaneMessageQuery.messageOf;
import static org.uitest4j.swing.driver.JOptionPaneMessageTypeQuery.messageTypeOf;
import static org.uitest4j.swing.driver.JOptionPaneMessageTypes.messageTypeAsText;
import static org.uitest4j.swing.driver.JOptionPaneOptionsQuery.optionsOf;
import static org.uitest4j.swing.driver.JOptionPaneTitleQuery.titleOf;

/**
 * <p>
 * Supports functional testing of {@code JOptionPane}s.
 * </p>
 *
 * <p>
 * <b>Note:</b> This class is intended for internal use only. Please use the classes in the package
 * {@link org.uitest4j.swing.fixture} in your tests.
 * </p>
 *
 * @author Alex Ruiz
 */
@InternalApi
public class JOptionPaneDriver extends JComponentDriver {
	private static final String MESSAGE_PROPERTY = "message";
	private static final String MESSAGE_TYPE_PROPERTY = "messageType";
	private static final String OPTIONS_PROPERTY = "options";
	private static final String TITLE_PROPERTY = "title";

	/**
	 * Creates a new {@link JOptionPaneDriver}.
	 *
	 * @param robot the robot to use to simulate user input.
	 */
	public JOptionPaneDriver(@Nonnull Robot robot) {
		super(robot);
	}

	/**
	 * Asserts that the title in the given {@code JOptionPane} matches the given value.
	 *
	 * @param optionPane the target {@code JOptionPane}.
	 * @param title      the title to match. It can be a regular expression.
	 * @throws AssertionError if the {@code JOptionPane} does not have the given title.
	 */
	@RunsInEDT
	public void requireTitle(@Nonnull JOptionPane optionPane, @Nullable String title) {
//		verifyThat(title(optionPane)).as(propertyName(optionPane, TITLE_PROPERTY)).isEqualOrMatches(title);
		OpenTest4JAssertions.assertEquals(title, title(optionPane), () -> "Expected title of '" + optionPane.getName() +
				"' to be '" + title + "' but was '" + title(optionPane) + "'");
	}

	/**
	 * Asserts that the title in the given {@code JOptionPane} matches the given regular expression pattern.
	 *
	 * @param optionPane the target {@code JOptionPane}.
	 * @param pattern    the regular expression pattern to match.
	 * @throws NullPointerException if the given regular expression pattern is {@code null}.
	 * @throws AssertionError       if the {@code JOptionPane} does not have the given title.
	 */
	@RunsInEDT
	public void requireTitle(@Nonnull JOptionPane optionPane, @Nonnull Pattern pattern) {
		OpenTest4JAssertions.assertMatchesPattern(pattern, title(optionPane),
				() -> String.format("Expected title of '%s' to match pattern '%s' but was '%s'", optionPane.getName(), pattern, title(optionPane)));
	}

	/**
	 * Returns the title of the given {@code JOptionPane}.
	 *
	 * @param optionPane the target {@code JOptionPane}.
	 * @return the title of the given {@code JOptionPane}.
	 */
	@RunsInEDT
	@Nullable
	public String title(@Nonnull JOptionPane optionPane) {
		return titleOf(optionPane);
	}

	/**
	 * Asserts that the title of the {@code JOptionPane} matches the given value. If the given value is a regular
	 * expression and the message in the {@code JOptionPane} is not a {@code String}, this method will use the
	 * {@code toString} representation of such message. message in the {@code JOptionPane} is not a {@code String}, this
	 * method will use the {@code toString} representation of such message.
	 *
	 * @param optionPane the target {@code JOptionPane}.
	 * @param message    the message to verify. If it is a {@code String}, it can be specified as a regular expression.
	 * @throws AssertionError if the message in the {@code JOptionPane} is not equal to or does not match the given
	 *                        message.
	 */
	@RunsInEDT
	public void requireMessage(@Nonnull JOptionPane optionPane, @Nullable Object message) {
		Object actual = messageOf(optionPane);
		if (message instanceof String && actual != null) {
			requireMessage(optionPane, (String) message, actual.toString());
			return;
		}
		OpenTest4JAssertions.assertEquals(message, actual,
				() -> String.format("Expected message of '%s' to be '%s' but was '%s'", optionPane.getName(), message, actual));
	}

	@RunsInEDT
	private void requireMessage(@Nonnull JOptionPane optionPane, @Nullable String expected, @Nullable String actual) {
		OpenTest4JAssertions.assertEquals(expected, actual, () -> "Expected message of '" + optionPane.getName() +
				"' to be '" + expected + "' but was '" + actual + "'");
	}

	/**
	 * Asserts that the title of the {@code JOptionPane} matches the given regular expression pattern. If the message in
	 * the {@code JOptionPane} is not a {@code String}, this method will use the {@code toString} representation of such
	 * message.
	 *
	 * @param optionPane the target {@code JOptionPane}.
	 * @param pattern    the regular expression to match.
	 * @throws NullPointerException if the given regular expression pattern is {@code null}.
	 * @throws AssertionError       if the message in the {@code JOptionPaneFixture} does not match the given regular expression
	 *                              pattern.
	 */
	@RunsInEDT
	public void requireMessage(@Nonnull JOptionPane optionPane, @Nonnull Pattern pattern) {
		Object actual = messageOf(optionPane);
		String s = actual == null ? null : actual.toString();
		OpenTest4JAssertions.assertMatchesPattern(pattern, s, () -> "Expected message of '" + optionPane.getName() +
				"' to match pattern '" + pattern + "' but was '" + s + "'");
	}

	/**
	 * Asserts that the {@code JOptionPane} has the given options.
	 *
	 * @param optionPane the target {@code JOptionPane}.
	 * @param options    the options to verify.
	 * @throws AssertionError if the {@code JOptionPane} does not have the given options.
	 */
	@RunsInEDT
	public void requireOptions(@Nonnull JOptionPane optionPane, @Nonnull Object[] options) {
//		assertThat(optionsOf(optionPane)).as(propertyName(optionPane, OPTIONS_PROPERTY)).isEqualTo(options);
		OpenTest4JAssertions.assertEquals(options, optionsOf(optionPane),
				() -> String.format("Expected options of '%s' to be %s but were %s", optionPane.getName(), Arrays.toString(options), Arrays.toString(optionsOf(optionPane))));
	}

	/**
	 * Finds the "OK" button in the {@code JOptionPane}. This method is independent of locale and platform.
	 *
	 * @param optionPane the target {@code JOptionPane}.
	 * @return the "OK" button.
	 * @throws org.uitest4j.swing.exception.ComponentLookupException if the a "OK" button cannot be found.
	 */
	@RunsInEDT
	@Nonnull
	public JButton okButton(@Nonnull JOptionPane optionPane) {
		return buttonWithTextFromUIManager(optionPane, "OptionPane.okButtonText");
	}

	/**
	 * Finds the "Cancel" button in the {@code JOptionPane}. This method is independent of locale and platform.
	 *
	 * @param optionPane the target {@code JOptionPane}.
	 * @return the "Cancel" button.
	 * @throws org.uitest4j.swing.exception.ComponentLookupException if the a "Cancel" button cannot be found.
	 */
	@RunsInEDT
	@Nonnull
	public JButton cancelButton(@Nonnull JOptionPane optionPane) {
		return buttonWithTextFromUIManager(optionPane, "OptionPane.cancelButtonText");
	}

	/**
	 * Finds the "Yes" button in the {@code JOptionPane}. This method is independent of locale and platform.
	 *
	 * @param optionPane the target {@code JOptionPane}.
	 * @return the "Yes" button.
	 * @throws org.uitest4j.swing.exception.ComponentLookupException if the a "Yes" button cannot be found.
	 */
	@RunsInEDT
	@Nonnull
	public JButton yesButton(@Nonnull JOptionPane optionPane) {
		return buttonWithTextFromUIManager(optionPane, "OptionPane.yesButtonText");
	}

	/**
	 * Finds the "No" button in the {@code JOptionPane}. This method is independent of locale and platform.
	 *
	 * @param optionPane the target {@code JOptionPane}.
	 * @return the "No" button.
	 * @throws org.uitest4j.swing.exception.ComponentLookupException if the a "No" button cannot be found.
	 */
	@RunsInEDT
	@Nonnull
	public JButton noButton(@Nonnull JOptionPane optionPane) {
		return buttonWithTextFromUIManager(optionPane, "OptionPane.noButtonText");
	}

	@RunsInEDT
	@Nonnull
	private JButton buttonWithTextFromUIManager(@Nonnull JOptionPane optionPane, @Nonnull String key) {
		return buttonWithText(optionPane, Objects.requireNonNull(UIManager.getString(key)));
	}

	/**
	 * Finds a button in the {@code JOptionPane} containing the given text.
	 *
	 * @param optionPane the target {@code JOptionPane}.
	 * @param text       the text of the button to find and return. It can be a regular expression.
	 * @return a button containing the given text.
	 * @throws org.uitest4j.swing.exception.ComponentLookupException if the a button with the given text cannot be found.
	 */
	@RunsInEDT
	@Nonnull
	public JButton buttonWithText(@Nonnull JOptionPane optionPane, @Nullable String text) {
		return robot.finder().find(optionPane, JButtonMatcher.withText(text).andShowing());
	}

	/**
	 * Finds a button in the {@code JOptionPane} whose text matches the given regular expression pattern.
	 *
	 * @param optionPane the target {@code JOptionPane}.
	 * @param pattern    the regular expression pattern to match.
	 * @return a button containing the given text.
	 * @throws NullPointerException                                  if the given regular expression pattern is {@code null}.
	 * @throws org.uitest4j.swing.exception.ComponentLookupException if the a button with the given text cannot be found.
	 */
	@RunsInEDT
	@Nonnull
	public JButton buttonWithText(@Nonnull JOptionPane optionPane, @Nonnull Pattern pattern) {
		return robot.finder().find(optionPane, JButtonMatcher.withText(pattern).andShowing());
	}

	/**
	 * Asserts that the {@code JOptionPane} is displaying an error message.
	 *
	 * @param optionPane the target {@code JOptionPane}.
	 */
	@RunsInEDT
	public void requireErrorMessage(@Nonnull JOptionPane optionPane) {
		assertEqualMessageType(optionPane, ERROR_MESSAGE);
	}

	/**
	 * Asserts that the {@code JOptionPane} is displaying an information message.
	 *
	 * @param optionPane the target {@code JOptionPane}.
	 */
	@RunsInEDT
	public void requireInformationMessage(@Nonnull JOptionPane optionPane) {
		assertEqualMessageType(optionPane, INFORMATION_MESSAGE);
	}

	/**
	 * Asserts that the {@code JOptionPane} is displaying a warning message.
	 *
	 * @param optionPane the target {@code JOptionPane}.
	 */
	@RunsInEDT
	public void requireWarningMessage(@Nonnull JOptionPane optionPane) {
		assertEqualMessageType(optionPane, WARNING_MESSAGE);
	}

	/**
	 * Asserts that the {@code JOptionPane} is displaying a question.
	 *
	 * @param optionPane the target {@code JOptionPane}.
	 */
	@RunsInEDT
	public void requireQuestionMessage(@Nonnull JOptionPane optionPane) {
		assertEqualMessageType(optionPane, QUESTION_MESSAGE);
	}

	/**
	 * Asserts that the {@code JOptionPane} is displaying a plain message.
	 *
	 * @param optionPane the target {@code JOptionPane}.
	 */
	@RunsInEDT
	public void requirePlainMessage(@Nonnull JOptionPane optionPane) {
		assertEqualMessageType(optionPane, PLAIN_MESSAGE);
	}

	@RunsInEDT
	private void assertEqualMessageType(@Nonnull JOptionPane optionPane, int expected) {
		String actualType = actualMessageTypeAsText(optionPane);
		OpenTest4JAssertions.assertEquals(messageTypeAsText(expected), actualType,
				() -> String.format("Expected message type of '%s' to be '%s' but was '%s'", optionPane.getName(), messageTypeAsText(expected), actualType));
	}

	@RunsInEDT
	private String actualMessageTypeAsText(final @Nonnull JOptionPane optionPane) {
		return messageTypeAsText(messageTypeOf(optionPane));
	}
}
