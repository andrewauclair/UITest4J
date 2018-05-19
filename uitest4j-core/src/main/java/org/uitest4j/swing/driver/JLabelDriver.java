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
package org.uitest4j.swing.driver;

import org.uitest4j.core.api.swing.SwingRobot;
import org.uitest4j.driver.TextDisplayDriver;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.internal.annotation.InternalApi;
import org.uitest4j.swing.internal.assertions.OpenTest4JAssertions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.util.regex.Pattern;

/**
 * <p>
 * Supports functional testing of {@code JLabel}s.
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
public class JLabelDriver extends JComponentDriver implements TextDisplayDriver<JLabel> {
	/**
	 * Creates a new {@link JLabelDriver}.
	 *
	 * @param robot the robot to use to simulate user input.
	 */
	public JLabelDriver(@Nonnull SwingRobot robot) {
		super(robot);
	}

	/**
	 * Asserts that the text of the {@code JLabel} is equal to the specified {@code String}.
	 *
	 * @param label    the target {@code JLabel}.
	 * @param expected the text to match.
	 * @throws AssertionError if the text of the {@code JLabel} is not equal to the given one.
	 */
	@RunsInEDT
	@Override
	public void requireText(@Nonnull JLabel label, @Nullable String expected) {
		OpenTest4JAssertions.assertEquals(expected, textOf(label), () -> "Expected text of '" + label.getName() +
				"' to be '" + expected + "' but was '" + textOf(label) + "'");
	}

	/**
	 * Asserts that the text of the {@code JLabel} matches the given regular expression pattern.
	 *
	 * @param label   the target {@code JLabel}.
	 * @param pattern the regular expression pattern to match.
	 * @throws AssertionError       if the text of the {@code JLabel} does not match the given regular expression pattern.
	 * @throws NullPointerException if the given regular expression pattern is {@code null}.
	 */
	@RunsInEDT
	@Override
	public void requireText(@Nonnull JLabel label, @Nonnull Pattern pattern) {
		OpenTest4JAssertions.assertMatchesPattern(pattern, textOf(label), () -> "Expected text of '" + label.getName() +
				"' to match pattern '" + pattern.toString() + "' but was '" + textOf(label) + "'");
	}

	/**
	 * Returns the text of the given {@code JLabel}.
	 *
	 * @param label the given {@code JLabel}.
	 * @return the text of the given {@code JLabel}.
	 */
	@RunsInEDT
	@Override
	@Nullable
	public String textOf(@Nonnull JLabel label) {
		return JLabelTextQuery.textOf(label);
	}
}
