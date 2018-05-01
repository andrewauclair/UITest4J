/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * <p>
 * Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.fixture;

import org.assertj.core.description.Description;
import org.assertj.core.description.TextDescription;
import org.uitest4j.swing.util.Strings;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies the state of {@code Font}s.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class FontFixture {
	private static final String PROPERTY_SEPARATOR = " - ";

	private static final String BOLD_PROPERTY = "bold";
	private static final String FAMILY_PROPERTY = "family";
	private static final String ITALIC_PROPERTY = "italic";
	private static final String NAME_PROPERTY = "name";
	private static final String PLAIN_PROPERTY = "plain";
	private static final String SIZE_PROPERTY = "size";

	private final Font target;
	private final Description description;

	/**
	 * Creates a new {@link FontFixture}.
	 *
	 * @param target the font to manage.
	 * @throws NullPointerException if {@code target} is {@code null}.
	 */
	public FontFixture(@Nonnull Font target) {
		this(target, (Description) null);
	}

	/**
	 * Creates a new {@link FontFixture}.
	 *
	 * @param target      the font to manage.
	 * @param description this fixture's description.
	 * @throws NullPointerException if {@code target} is {@code null}.
	 */
	public FontFixture(@Nonnull Font target, @Nonnull String description) {
		this(target, new TextDescription(description));
	}

	/**
	 * Creates a new {@link FontFixture}.
	 *
	 * @param target      the font to manage.
	 * @param description this fixture's description.
	 * @throws NullPointerException if {@code target} is {@code null}.
	 */
	public FontFixture(@Nonnull Font target, @Nullable Description description) {
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
		assertThat(target.getFamily()).as(property(FAMILY_PROPERTY)).isEqualTo(family);
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
		assertThat(target.getName()).as(property(NAME_PROPERTY)).isEqualTo(name);
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
		assertThat(target.getSize()).as(property(SIZE_PROPERTY)).isEqualTo(size);
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
		return requireBold(true);
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
		return requireBold(false);
	}

	@Nonnull
	private FontFixture requireBold(boolean bold) {
		assertThat(target.isBold()).as(property(BOLD_PROPERTY)).isEqualTo(bold);
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
		return requireItalic(true);
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
		return requireItalic(false);
	}

	@Nonnull
	private FontFixture requireItalic(boolean italic) {
		assertThat(target.isItalic()).as(property(ITALIC_PROPERTY)).isEqualTo(italic);
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
		return requirePlain(true);
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
		return requirePlain(false);
	}

	@Nonnull
	private FontFixture requirePlain(boolean plain) {
		assertThat(target.isBold()).as(property(PLAIN_PROPERTY)).isEqualTo(plain);
		return this;
	}

	@Nonnull
	private String property(@Nonnull String s) {
		if (!Strings.isNullOrEmpty(description())) {
			return description.value() + PROPERTY_SEPARATOR + s;
		}
		return s;
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
		return description != null ? description.value() : null;
	}
}
