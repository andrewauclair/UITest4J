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

import org.uitest4j.swing.internal.assertions.OpenTest4JAssertions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.Objects;
import java.util.function.Supplier;

import static org.uitest4j.swing.util.Colors.colorFromHexString;

/**
 * Verifies the state of {@code Color}s.
 *
 * @author Alex Ruiz
 */
public class ColorFixture {
	private final Color target;
	private final Supplier<String> description;

	/**
	 * Creates a new {@link ColorFixture}.
	 *
	 * @param target the color to manage.
	 * @throws NullPointerException if {@code target} is {@code null}.
	 */
	public ColorFixture(@Nonnull Color target) {
		this(target, () -> "");
	}

	/**
	 * Creates a new {@link ColorFixture}.
	 *
	 * @param target      the color to manage.
	 * @param description this fixture's description.
	 * @throws NullPointerException if {@code target} is {@code null}.
	 */
	public ColorFixture(@Nonnull Color target, @Nonnull String description) {
		this(target, () -> description);
	}

	/**
	 * Creates a new {@link ColorFixture}.
	 *
	 * @param target      the color to manage.
	 * @param description this fixture's description.
	 * @throws NullPointerException if {@code target} is {@code null}.
	 */
	public ColorFixture(@Nonnull Color target, @Nonnull Supplier<String> description) {
		this.target = Objects.requireNonNull(target);
		this.description = () -> description.get().isEmpty() ? "" : "[" + description.get() + "] ";
	}

	/**
	 * Verifies that this fixture's {@code Color} is equal to the given color represented by the given hexadecimal value
	 * (e.g. "82A9FF").
	 *
	 * @param hexValue the value representing the color to compare to.
	 * @return this fixture.
	 * @throws NullPointerException     if the hexadecimal code is {@code null}.
	 * @throws IllegalArgumentException if the hexadecimal code is empty.
	 * @throws NumberFormatException    if the hexadecimal code is empty.
	 * @throws AssertionError           if this fixture's {@code Color} is not equal to the given one.
	 */
	@Nonnull
	public ColorFixture requireEqualTo(@Nonnull String hexValue) {
		return requireEqualTo(colorFromHexString(hexValue));
	}

	/**
	 * Verifies that this fixture's {@code Color} is equal to the given one.
	 *
	 * @param color the given {@code Color} to compare to.
	 * @return this fixture.
	 * @throws AssertionError if this fixture's {@code Color} is not equal to the given one.
	 */
	@Nonnull
	public ColorFixture requireEqualTo(@Nullable Color color) {
		Supplier<String> message = () -> description.get() + "Expected " + color + " but was " + target;
		OpenTest4JAssertions.assertEquals(color, target, message);
		return this;
	}

	/**
	 * Verifies that this fixture's {@code Color} is not equal to the given color represented by the given hexadecimal
	 * value (e.g. "82A9FF").
	 *
	 * @param hexValue the value representing the color to compare to.
	 * @return this fixture.
	 * @throws NullPointerException     if the hexadecimal code is {@code null}.
	 * @throws IllegalArgumentException if the hexadecimal code is empty.
	 * @throws NumberFormatException    if the hexadecimal code is empty.
	 * @throws AssertionError           if this fixture's {@code Color} is equal to the given one.
	 */
	@Nonnull
	public ColorFixture requireNotEqualTo(@Nonnull String hexValue) {
		return requireNotEqualTo(colorFromHexString(hexValue));
	}

	/**
	 * Verifies that this fixture's {@code Color} is not equal to the given one.
	 *
	 * @param color the given {@code Color} to compare to.
	 * @return this fixture.
	 * @throws AssertionError if this fixture's {@code Color} is equal to the given one.
	 */
	@Nonnull
	public ColorFixture requireNotEqualTo(@Nullable Color color) {
		Supplier<String> message = () -> description.get() + "Expected color to not equal " + target;
		OpenTest4JAssertions.assertNotEqual(color, target, message);
		return this;
	}

	/**
	 * @return this fixture's {@code Color}.
	 */
	@Nonnull
	public Color target() {
		return target;
	}

	/**
	 * @return this fixture's description.
	 */
	public final @Nonnull
	String description() {
		String str = description.get();
		if (!str.isEmpty()) {
			str = str.substring(1, str.length() - 2);
		}
		return str;
	}
}
