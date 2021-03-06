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
import org.uitest4j.swing.driver.JTextComponentDriver;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.text.JTextComponent;
import java.util.regex.Pattern;

/**
 * Supports functional testing of {@code JTextComponent}s.
 *
 * @author Alex Ruiz
 */
public class JTextComponentFixture extends
		AbstractJPopupMenuInvokerFixture<JTextComponentFixture, JTextComponent, JTextComponentDriver> implements
		TextInputFixture<JTextComponentFixture> {
	/**
	 * Creates a new {@link JTextComponentFixture}.
	 *
	 * @param robot  performs simulation of user events on the given {@code JTextComponent}.
	 * @param target the {@code JTextComponent} to be managed by this fixture.
	 * @throws NullPointerException if {@code robot} is {@code null}.
	 * @throws NullPointerException if {@code target} is {@code null}.
	 */
	public JTextComponentFixture(@Nonnull Robot robot, @Nonnull JTextComponent target) {
		super(JTextComponentFixture.class, robot, target);
	}

	/**
	 * Creates a new {@link JTextComponentFixture}.
	 *
	 * @param robot             performs simulation of user events on a {@code JTextComponent}.
	 * @param textComponentName the name of the {@code JTextComponent} to find using the given {@code Robot}.
	 * @throws NullPointerException                                  if {@code robot} is {@code null}.
	 * @throws org.uitest4j.swing.exception.ComponentLookupException if a matching {@code JTextComponent} could not be found.
	 * @throws org.uitest4j.swing.exception.ComponentLookupException if more than one matching {@code JTextComponent} is found.
	 */
	public JTextComponentFixture(@Nonnull Robot robot, @Nullable String textComponentName) {
		super(JTextComponentFixture.class, robot, textComponentName, JTextComponent.class);
	}

	@Override
	@Nonnull
	protected JTextComponentDriver createDriver(@Nonnull Robot robot) {
		return new JTextComponentDriver(robot);
	}

	/**
	 * @return the text of this fixture's {@code JTextComponent}.
	 */
	@Override
	@Nullable
	public String text() {
		return driver().textOf(target());
	}

	/**
	 * Simulates a user selecting the given text contained in this fixture's {@code JTextComponent}.
	 *
	 * @param text the text to select.
	 * @return this fixture.
	 * @throws IllegalStateException                              if this fixture's {@code JTextComponent} is disabled.
	 * @throws IllegalStateException                              if this fixture's {@code JTextComponent} is not showing on the screen.
	 * @throws IllegalArgumentException                           if this fixture's {@code JTextComponent} does not contain the given text to
	 *                                                            select.
	 * @throws org.uitest4j.swing.exception.ActionFailedException if the selecting the text in the given range fails.
	 */
	@Override
	@Nonnull
	public JTextComponentFixture select(@Nonnull String text) {
		driver().selectText(target(), text);
		return this;
	}

	/**
	 * Simulates a user selecting a portion of the text contained in this fixture's {@code JTextComponent}.
	 *
	 * @param start index where selection should start.
	 * @param end   index where selection should end.
	 * @return this fixture.
	 * @throws IllegalStateException                              if this fixture's {@code JTextComponent} is disabled.
	 * @throws IllegalStateException                              if this fixture's {@code JTextComponent} is not showing on the screen.
	 * @throws org.uitest4j.swing.exception.ActionFailedException if the selecting the text in the given range fails.
	 */
	@Override
	@Nonnull
	public JTextComponentFixture selectText(int start, int end) {
		driver().selectText(target(), start, end);
		return this;
	}

	/**
	 * Simulates a user selecting all the text contained in this fixture's {@code JTextComponent}.
	 *
	 * @return this fixture.
	 * @throws IllegalStateException if this fixture's {@code JTextComponent} is disabled.
	 * @throws IllegalStateException if this fixture's {@code JTextComponent} is not showing on the screen.
	 */
	@Override
	@Nonnull
	public JTextComponentFixture selectAll() {
		driver().selectAll(target());
		return this;
	}

	/**
	 * Simulates a user deleting all the text in this fixture's {@code JTextComponent}.
	 *
	 * @return this fixture.
	 * @throws IllegalStateException if this fixture's {@code JTextComponent} is disabled.
	 * @throws IllegalStateException if this fixture's {@code JTextComponent} is not showing on the screen.
	 */
	@Override
	@Nonnull
	public JTextComponentFixture deleteText() {
		driver().deleteText(target());
		return this;
	}

	/**
	 * Simulates a user entering the given text in this fixture's {@code JTextComponent}.
	 *
	 * @param text the text to enter.
	 * @return this fixture.
	 * @throws IllegalStateException if this fixture's {@code JTextComponent} is disabled.
	 * @throws IllegalStateException if this fixture's {@code JTextComponent} is not showing on the screen.
	 */
	@Override
	@Nonnull
	public JTextComponentFixture enterText(@Nonnull String text) {
		driver().enterText(target(), text);
		return this;
	}

	/**
	 * Sets the text in this fixture's {@code JTextComponent}. Unlike {@link #enterText(String)}, this method bypasses the
	 * event system and allows immediate updating on the underlying document model.
	 * <p>
	 * Primarily desired for speeding up tests when precise user event fidelity isn't necessary.
	 * </p>
	 *
	 * @param text the text to set.
	 * @return this fixture.
	 * @throws IllegalStateException if this fixture's {@code JTextComponent} is disabled.
	 * @throws IllegalStateException if this fixture's {@code JTextComponent} is not showing on the screen.
	 */
	@Nonnull
	public JTextComponentFixture setText(@Nullable String text) {
		driver().setText(target(), text);
		return this;
	}

	/**
	 * Asserts that the text of this fixture's {@code JTextComponent} is equal to the specified value.
	 *
	 * @param expected the text to match. It can be a regular expression pattern.
	 * @return this fixture.
	 * @throws AssertionError if the text of this fixture's {@code JTextComponent} is not equal to the given one.
	 */
	@Override
	@Nonnull
	public JTextComponentFixture requireText(@Nullable String expected) {
		driver().requireText(target(), expected);
		return this;
	}

	/**
	 * Asserts that the text of this fixture's {@code JTextComponent} matches the given regular expression pattern.
	 *
	 * @param pattern the regular expression pattern to match.
	 * @return this fixture.
	 * @throws NullPointerException if the given regular expression pattern is {@code null}.
	 * @throws AssertionError       if the text of this fixture's {@code JTextComponent} is not eual to the given one.
	 */
	@Override
	@Nonnull
	public JTextComponentFixture requireText(@Nonnull Pattern pattern) {
		driver().requireText(target(), pattern);
		return this;
	}

	/**
	 * Asserts that this fixture's {@code JTextComponent} is editable.
	 *
	 * @return this fixture.
	 * @throws AssertionError if this fixture's {@code JTextComponent} is not editable.
	 */
	@Override
	@Nonnull
	public JTextComponentFixture requireEditable() {
		driver().requireEditable(target());
		return this;
	}

	/**
	 * Asserts that this fixture's {@code JTextComponent} is not editable.
	 *
	 * @return this fixture.
	 * @throws AssertionError if this fixture's {@code JTextComponent} is editable.
	 */
	@Override
	@Nonnull
	public JTextComponentFixture requireNotEditable() {
		driver().requireNotEditable(target());
		return this;
	}

	/**
	 * Asserts that the target text component does not contain any text.
	 *
	 * @return this fixture.
	 * @throws AssertionError if the target text component is not empty.
	 */
	@Nonnull
	public JTextComponentFixture requireEmpty() {
		driver().requireEmpty(target());
		return this;
	}
}
