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
package org.uitest4j.swing.internal.assertions;

import org.opentest4j.AssertionFailedError;
import org.uitest4j.swing.assertions.data.RgbColor;

import javax.annotation.Nonnull;
import java.awt.*;
import java.awt.image.BufferedImage;

import static org.uitest4j.swing.assertions.data.RgbColor.color;
import static org.uitest4j.swing.internal.assertions.ColorComparisonResult.ARE_EQUAL;
import static org.uitest4j.swing.internal.assertions.ColorComparisonResult.notEqual;

/**
 * Reusable assertions for <code>{@link BufferedImage}</code>s.
 *
 * @author Yvonne Wang
 */
public class Images {
	private static final Images INSTANCE = new Images();

	/**
	 * Returns the singleton instance of this class.
	 *
	 * @return the singleton instance of this class.
	 */
	public static Images instance() {
		return INSTANCE;
	}

	// Used for tests
	Images() {
	}

	/**
	 * Asserts that two images are equal. Two images are equal if:
	 * <ol>
	 * <li>they have equal size</li>
	 * <li>the the RGB values of the color at each pixel are equal</li>
	 * </ol>
	 *
	 * @param actual   the actual image.
	 * @param expected the expected image.
	 * @throws AssertionError if the actual image is not equal to the expected one.
	 */
	public void assertEqual(BufferedImage actual, BufferedImage expected) {
		assertEqual(actual, expected, 0);
	}

	/**
	 * Asserts that two images are equal. Two images are equal if:
	 * <ol>
	 * <li>they have the same size</li>
	 * <li>the difference between the RGB values of the color at each pixel is less than or equal to the given offset</li>
	 * </ol>
	 *
	 * @param actual   the actual image.
	 * @param expected the expected image.
	 * @param offset   helps decide if the color of two pixels are similar: two pixels that are identical to the human eye
	 *                 may still have slightly different color values. For example, by using an offset of 1 we can indicate that
	 *                 a blue value of 60 is similar to a blue value of 61.
	 * @throws NullPointerException if the given offset is {@code null}.
	 * @throws AssertionError       if the actual image is not equal to the expected one.
	 */
	public void assertEqual(BufferedImage actual, BufferedImage expected, int offset) {
		if (java.util.Objects.equals(actual, expected)) {
			return;
		}
		if (actual == null || expected == null) {
			throw new AssertionFailedError(String.format("expecting images to be equal within offset:<%s>", offset));
		}
		// BufferedImage does not have an implementation of 'equals,' which means that "equality" is verified by identity.
		// We need to verify that two images are equal ourselves.
		if (!haveEqualSize(actual, expected)) {
			Dimension actualSize = sizeOf(actual);
			Dimension expectedSize = sizeOf(expected);
			throw new AssertionFailedError(String.format("expected size:<%sx%s> but was:<%sx%s> in:<%s>", expectedSize.width, expectedSize.height, actualSize.width,
					actualSize.height, actual), sizeOf(actual), sizeOf(expected));
		}
		ColorComparisonResult haveEqualColor = haveEqualColor(actual, expected, offset);
		if (haveEqualColor == ARE_EQUAL) {
			return;
		}
		throw new AssertionFailedError(String.format("expected:<%s> but was:<%s> at:<%s> within offset:<%s>", haveEqualColor.color2, haveEqualColor.color1, haveEqualColor.point, offset));
	}

	/**
	 * Asserts that two images are not equal.
	 *
	 * @param actual the given image.
	 * @param other  the object to compare {@code actual} to.
	 * @throws AssertionError if {@code actual} is equal to {@code other}.
	 */
	public void assertNotEqual(BufferedImage actual, BufferedImage other) {
		if (java.util.Objects.equals(actual, other)) {
			throw new AssertionFailedError("Expected images to not be equal");
		}
		if (actual == null || other == null) {
			return;
		}
		if (!(haveEqualSize(actual, other))) {
			return;
		}
		ColorComparisonResult haveEqualColor = haveEqualColor(actual, other, 0);
		if (haveEqualColor != ARE_EQUAL) {
			return;
		}
		throw new AssertionFailedError("Expected images to not be equal");
	}

	private boolean haveEqualSize(BufferedImage i1, BufferedImage i2) {
		return i1.getWidth() == i2.getWidth() && i1.getHeight() == i2.getHeight();
	}

	private ColorComparisonResult haveEqualColor(BufferedImage i1, BufferedImage i2, int offset) {
		int w = i1.getWidth();
		int h = i1.getHeight();
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				RgbColor c1 = color(i1.getRGB(x, y));
				RgbColor c2 = color(i2.getRGB(x, y));
				if (c1.isEqualTo(c2, offset)) {
					continue;
				}
				return notEqual(c1, c2, x, y);
			}
		}
		return ARE_EQUAL;
	}

	/**
	 * Asserts that the size of the given image is equal to the given size.
	 *
	 * @param actual the given image.
	 * @param size   the expected size of {@code actual}.
	 * @throws NullPointerException if the given size is {@code null}.
	 * @throws AssertionError       if the size of the given image is not equal to the given size.
	 */
	public void assertHasSize(@Nonnull BufferedImage actual, @Nonnull Dimension size) {
		java.util.Objects.requireNonNull(size, "The given size should not be null");
		java.util.Objects.requireNonNull(actual);
		Dimension sizeOfActual = sizeOf(actual);
		if (java.util.Objects.equals(sizeOfActual, size)) {
			return;
		}
		throw new AssertionFailedError(String.format("expected size:<%sx%s> but was:<%sx%s> in:<%s>", size.width, size.height, sizeOfActual.width,
				sizeOfActual.height, actual), sizeOfActual, size);
	}

	// Used for tests
	static Dimension sizeOf(BufferedImage image) {
		return new Dimension(image.getWidth(), image.getHeight());
	}
}
