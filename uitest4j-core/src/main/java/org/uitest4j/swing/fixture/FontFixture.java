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

/**
 * Verifies the state of {@code Font}s.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class FontFixture {
	private final Font target;
	private final Supplier<String> description;

	/**
	 * Creates a new {@link FontFixture}.
	 *
	 * @param target the font to manage.
	 * @throws NullPointerException if {@code target} is {@code null}.
	 */
	public FontFixture(@Nonnull Font target) {
		this(target, () -> "");
	}

	/**
	 * Creates a new {@link FontFixture}.
	 *
	 * @param target      the font to manage.
	 * @param description this fixture's description.
	 * @throws NullPointerException if {@code target} is {@code null}.
	 */
	public FontFixture(@Nonnull Font target, @Nonnull String description) {
		this(target, () -> description);
	}

	/**
	 * Creates a new {@link FontFixture}.
	 *
	 * @param target      the font to manage.
	 * @param description this fixture's description.
	 * @throws NullPointerException if {@code target} is {@code null}.
	 */
	public FontFixture(@Nonnull Font target, @Nullable Supplier<String> description) {
		this.target = Objects.requireNonNull(target);
		this.description = description;
	}

	/**
	 * Verifies that the family name of this fixture's font is equal to the given one.
	 *
	 * @param family the expected family name.
	 * @return this assertion object.
	 * @throws AssertionError if the family name of this fixture's font is not equal to the given one.
	 * @see Font#getFamily()
	 */
	@Nonnull
	public FontFixture requireFamily(@Nonnull String family) {
		OpenTest4JAssertions.assertEquals(family, target.getFamily(), () -> desc() + "Expected font family to be '" + family +
				"' but was '" + target.getFamily() + "'");
		return this;
	}

	/**
	 * Verifies that the logical name of this fixture's font is equal to the given one.
	 *
	 * @param name the expected logical name.
	 * @return this assertion object.
	 * @throws AssertionError if the logical name of this fixture's font is not equal to the given one.
	 * @see Font#getName()
	 */
	@Nonnull
	public FontFixture requireName(@Nonnull String name) {
		OpenTest4JAssertions.assertEquals(name, target.getName(), () -> desc() + "Expected font name to be '" + name +
				"' but was '" + target.getName() + "'");
		return this;
	}

	/**
	 * Verifies that the point size of this fixture's font is equal to the given one.
	 *
	 * @param size the expected point size.
	 * @return this assertion object.
	 * @throws AssertionError if the point size of this fixture's font is not equal to the given one.
	 * @see Font#getSize()
	 */
	@Nonnull
	public FontFixture requireSize(int size) {
		OpenTest4JAssertions.assertEquals(size, target.getSize(), () -> desc() + "Expected font size to be '" + size +
				"' but was '" + target.getSize() + "'");
		return this;
	}

	/**
	 * Verifies that this fixture's font is bold.
	 *
	 * @return this assertion object.
	 * @throws AssertionError if this fixture's font is not bold.
	 * @see Font#isBold()
	 */
	@Nonnull
	public FontFixture requireBold() {
		OpenTest4JAssertions.assertTrue(target.isBold(), () -> desc() + "Expected font to be bold");
		return this;
	}

	/**
	 * Verifies that this fixture's font is not bold.
	 *
	 * @return this assertion object.
	 * @throws AssertionError if this fixture's font is bold.
	 * @see Font#isBold()
	 */
	@Nonnull
	public FontFixture requireNotBold() {
		OpenTest4JAssertions.assertFalse(target.isBold(), () -> desc() + "Expected font to not be bold");
		return this;
	}

	/**
	 * Verifies that this fixture's font is italic.
	 *
	 * @return this assertion object.
	 * @throws AssertionError if this fixture's font is not italic.
	 * @see Font#isItalic()
	 */
	@Nonnull
	public FontFixture requireItalic() {
		OpenTest4JAssertions.assertTrue(target.isItalic(), () -> desc() + "Expected font to be italic");
		return this;
	}

	/**
	 * Verifies that this fixture's font is not italic.
	 *
	 * @return this assertion object.
	 * @throws AssertionError if this fixture's font is italic.
	 * @see Font#isItalic()
	 */
	@Nonnull
	public FontFixture requireNotItalic() {
		OpenTest4JAssertions.assertFalse(target.isItalic(), () -> desc() + "Expected font to not be italic");
		return this;
	}

	/**
	 * Verifies that this fixture's font is plain.
	 *
	 * @return this assertion object.
	 * @throws AssertionError if this fixture's font is not plain.
	 * @see Font#isPlain()
	 */
	@Nonnull
	public FontFixture requirePlain() {
		OpenTest4JAssertions.assertTrue(target.isPlain(), () -> desc() + "Expected font to be plain");
		return this;
	}

	/**
	 * Verifies that this fixture's font is not plain.
	 *
	 * @return this assertion object.
	 * @throws AssertionError if this fixture's font is plain.
	 * @see Font#isPlain()
	 */
	@Nonnull
	public FontFixture requireNotPlain() {
		OpenTest4JAssertions.assertFalse(target.isPlain(), () -> desc() + "Expected font to not be plain");
		return this;
	}

	/**
	 * @return this fixture's font.
	 */
	@Nonnull
	public Font target() {
		return target;
	}

	/**
	 * @return this fixture's description.
	 */
	public final @Nullable
	String description() {
		return description.get();
	}

	private String desc() {
		if (description.get().isEmpty()) {
			return "";
		}
		else {
			return "[" + description.get() + "] ";
		}
	}
}
