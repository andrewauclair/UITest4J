/*
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
package org.uitest4j.swing.util;

import javax.annotation.Nonnull;
import java.awt.*;

import static org.uitest4j.swing.util.Strings.checkNotNullOrEmpty;

/**
 * Utility methods related to colors.
 *
 * @author Alex Ruiz
 */
public final class Colors {
	/**
	 * Returns a {@code Color} from the given {@code String} containing the hexadecimal coding of a color.
	 *
	 * @param hexString contains the hexadecimal coding of a color.
	 * @return a {@code Color} from the given {@code String} containing the hexadecimal coding of a color.
	 * @throws NullPointerException     if the hexadecimal code is {@code null}.
	 * @throws IllegalArgumentException if the hexadecimal code is empty.
	 * @throws NumberFormatException    if the hexadecimal code is empty.
	 */
	@Nonnull
	public static Color colorFromHexString(@Nonnull String hexString) {
		checkNotNullOrEmpty(hexString, "hexString");
		try {
			return new Color(Integer.parseInt(hexString, 16));
		}
		catch (NumberFormatException e) {
			throw new NumberFormatException("The hexadecimal code \"" + hexString + "\" is not a valid color code");
		}
	}

	private Colors() {
	}
}
