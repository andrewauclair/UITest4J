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
package org.uitest4j.swing.keystroke;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.util.Objects;

/**
 * A mapping between a character and a {@code javax.swing.KeyStroke}.
 *
 * @author Yvonne Wang
 */
public class KeyStrokeMapping {
	private final char character;
	private final KeyStroke keyStroke;

	/**
	 * Creates a new {@link KeyStrokeMapping}.
	 *
	 * @param character the character corresponding to the intended {@code KeyStroke}.
	 * @param keyCode   the numeric key code for the intended {@code KeyStroke}.
	 * @param modifiers the set of modifiers for the intended {@code KeyStroke}.
	 * @return the created {@code KeyStrokeMapping}.
	 */
	@Nonnull
	public static KeyStrokeMapping mapping(char character, int keyCode, int modifiers) {
		return new KeyStrokeMapping(character, keyCode, modifiers);
	}

	/**
	 * Creates a new {@link KeyStrokeMapping}.
	 *
	 * @param character the character corresponding to the intended {@code KeyStroke}.
	 * @param keyCode   the numeric key code for the intended {@code KeyStroke}.
	 * @param modifiers the set of modifiers for the intended {@code KeyStroke}.
	 */
	public KeyStrokeMapping(char character, int keyCode, int modifiers) {
		this(character, Objects.requireNonNull(KeyStroke.getKeyStroke(keyCode, modifiers)));
	}

	/**
	 * Creates a new {@link KeyStrokeMapping}.
	 *
	 * @param character the character corresponding to the given {@code KeyStroke}.
	 * @param keyStroke the {@code KeyStroke} corresponding to the given character.
	 */
	public KeyStrokeMapping(char character, @Nonnull KeyStroke keyStroke) {
		this.character = character;
		this.keyStroke = keyStroke;
	}

	/**
	 * @return the character corresponding to this mapping's {@code KeyStroke}.
	 */
	public char character() {
		return character;
	}

	/**
	 * @return the {@code KeyStroke} corresponding to this mapping's character.
	 */
	@Nonnull
	public KeyStroke keyStroke() {
		return keyStroke;
	}

	@Override
	public String toString() {
		String format = "%s[character='%s', keyStroke=%s]";
		return String.format(format, getClass().getSimpleName(), String.valueOf(character), keyStroke.toString());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		KeyStrokeMapping that = (KeyStrokeMapping) o;
		return character == that.character &&
				Objects.equals(keyStroke, that.keyStroke);
	}

	@Override
	public int hashCode() {
		return Objects.hash(character, keyStroke);
	}
}
